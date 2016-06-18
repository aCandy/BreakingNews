package com.example.tangyi.breakingnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangyi.breakingnews.R;
import com.example.tangyi.breakingnews.tools.CacheUtils;
import com.example.tangyi.breakingnews.tools.JsonBean;
import com.example.tangyi.breakingnews.tools.MyListView;
import com.example.tangyi.breakingnews.tools.URList;
import com.example.tangyi.breakingnews.tools.ViewHolder;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.TabPageIndicator;
import com.lidroid.xutils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager标题栏指示器实现流程：
 * 1.本人使用的是开源框架ViewPagerIndicator(GitHub开源)
 * 2.从GitHub上下载库并导入到Android Studio(具体流程博客上有收藏)
 * 3.解决support-v4冲突(使版本一致即可，我在导入的时候没有发现冲突)
 * 4.设置布局控件com.viewpagerindicator.TabPageIndicator
 * 5.在使用该框架的Activity中增加样式(框架中的样式)
 * 6.根据所需修改当前Activity的背景色
 * 7.根据所需在框架样式中修改文字大小及颜色，以及指示器
 */
public class MainActivity extends Activity {
    private View view,view1,view2,view3,view4,view5,view6;
    private ViewPager viewPager;
    private TabPageIndicator mIndicator;
    private static final String[] TITLE=new String[]{"社会","娱乐","数码","互联网","电影","游戏"};
    private LayoutInflater layoutInflater;
    private List<View> list;
    private SwipeRefreshLayout mSRshLayout;
    private MyListView mListView;
    private ArrayList<JsonBean.ShowApi_Res_Body.PageBean.ContentList> dataList;
    private JsonBean.ShowApi_Res_Body.PageBean.ContentList data;
    private ListViewAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //初始化主页面ViewPager数据
        initViewPagerData();
    }
    private void initViewPagerData(){
        layoutInflater=LayoutInflater.from(this);
        view=(View)findViewById(R.id.content_activity);
        view1=layoutInflater.inflate(R.layout.home_pager1,null);
        view2=layoutInflater.inflate(R.layout.home_pager2,null);
        view3=layoutInflater.inflate(R.layout.home_pager1,null);
        view4=layoutInflater.inflate(R.layout.home_pager1,null);
        view5=layoutInflater.inflate(R.layout.home_pager1,null);
        view6=layoutInflater.inflate(R.layout.home_pager1,null);


        mSRshLayout=(SwipeRefreshLayout)view1.findViewById(R.id.swiperefresh_layout);
        mSRshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSRshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        mListView=(MyListView)view1.findViewById(R.id.home_list);

        list=new ArrayList<>();
        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);
        list.add(view6);
        viewPager=(ViewPager)view.findViewById(R.id.home_viewpager);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //  container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TITLE[position%TITLE.length];
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
        mIndicator=(TabPageIndicator)view.findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);
        String cache=CacheUtils.getCache(MainActivity.this,URList.URL);
        if (!TextUtils.isEmpty(cache)){
            Log.d("TAG","发现缓存，准备解析缓存中的Json数据");
            processData(cache,false);
        }
        getDataFromServer();
    }
    private void getDataFromServer(){
        HttpUtils utils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addHeader("apikey",URList.APIKEY);
        utils.send(HttpRequest.HttpMethod.GET, URList.URL ,requestParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        processData(result,false);
                        Log.d("TAG","请求到新的数据，准备写入缓存");
                        //调用缓存工具类的方法写入缓存
                        CacheUtils.setCache(MainActivity.this,URList.URL,result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void processData(String json,boolean isMore){
        Gson gson=new Gson();
        JsonBean fromJson=gson.fromJson(json,JsonBean.class);
        Log.d("TAG","解析数据："+fromJson);
        if (!isMore){
            dataList=fromJson.getShowapi_res_body().getPagebean().getContentlist();
            listAdapter=new ListViewAdapter();
            mListView.setAdapter(listAdapter);
            if (dataList!=null){

            }
        }else {
           /* ArrayList<JsonBean.ShowApi_Res_Body.PageBean.ContentList.Bean> moreData=
                    fromJson.getShowApi_res_body().getPagebean().getContentList().getBean();
            dataList.addAll(moreData);
            listAdapter.notifyDataSetChanged();*/
       }
    }
    //填充ListView的适配器。
    public class ListViewAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=View.inflate(MainActivity.this,R.layout.list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.titleText=(TextView)convertView.findViewById(R.id.title_text);
                viewHolder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
                convertView.setTag(viewHolder);
            }else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            data = (JsonBean.ShowApi_Res_Body.PageBean.ContentList)getItem(position);
            viewHolder.titleText.setText("        "+ data.getTitle());
            viewHolder.timerText.setText("更新时间："+ data.getPubDate());
            return convertView;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }
        @Override
        public int getCount() {
            return dataList.size();
        }

    }

}
