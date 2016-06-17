package com.example.tangyi.breakingnews.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.tangyi.breakingnews.R;


/**
  * 自定义ListView实现下拉刷新，上拉加载
  * 继承自ListView，实现自AbsListView.OnScrollListener(listView滑动监听)
  * */
public class MyListView extends ListView implements AbsListView.OnScrollListener {
    //尾布局
    private View mFooterView;
    //尾布局高度
    private int mFooterViewHeight;
    //当前显示的最后一个Item的角标值
    private int lastItem;
    //上拉加载中避免重复加载的标记
    private boolean isLoadMore;

     /**
      * 构造函数最好三个都写上，说不定会用得上。
      * */
     public MyListView(Context context) {
         super(context);
         initFooterView();
     }
     public MyListView(Context context, AttributeSet attrs) {
         super(context, attrs);
         initFooterView();
     }

     public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
         super(context, attrs, defStyleAttr);
         initFooterView();
     }
     /**
      * 初始化尾布局
      */
     private void initFooterView(){

         //实例化尾布局，尾布局是自定义的listview_down文件
         mFooterView = View.inflate(getContext(), R.layout.listview_down,null);
         //将尾布局追加到ListView中
         this.addFooterView(mFooterView);
         //调用尾布局的measure()函数进行测量
         mFooterView.measure(0,0);
         //测量完毕后再调用getMeasuredHeight()获取测量到的高度，并赋值给mHeaderViewHeight
         mFooterViewHeight = mFooterView.getMeasuredHeight();
         //用setPadding()进行设置隐藏，四个参数依次是左，上，右，下。
         mFooterView.setPadding(0,-mFooterViewHeight,0,0);
         //设置滑动监听
         this.setOnScrollListener(this);
     }

    /**
     * 滑动监听必须重写的两个回调函数
     * onScroll是滑动过程回调，就是在滑动的过程中不断调用该函数
     * onScrollStateChanged是滑动状态发生改变时的回调
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE){//空闲状态
            //获取最后一个Item的位置,
            lastItem = getLastVisiblePosition();
            //判断是否到达ListView的最底部，如果是最底部并且没有正在加载更多
            if (lastItem==getCount()-1&&!isLoadMore){
                Log.d("TAG","加载更多...");
                //显示尾布局
                mFooterView.setPadding(0,0,0,0);
                //将尾布局显示在最后一个Item上,尾布局就会直接展示在ListView的最后一个Item上，无需手动滑动
                setSelection(getCount()-1);
                //通知主界面加载下一页数据
                if (mListener!=null){
                    mListener.onLoadMore();
                }
            }
        }
    }
    /**
     * 1.下拉刷新，上拉加载的回调接口
     */
    public interface OnRefreshListener{

        //上拉加载
        public void onLoadMore();
    }
    /**
     * 2.定义成员变量，接收监听对象
     */
    private OnRefreshListener mListener;
    /**
     * 3.暴露接口，设置监听
     */
    public void setOnRefreshListener(OnRefreshListener listener){
        mListener=listener;
    }
}
