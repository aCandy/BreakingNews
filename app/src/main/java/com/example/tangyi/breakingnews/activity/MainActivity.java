package com.example.tangyi.breakingnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangyi.breakingnews.JsonBean.DigitalBean;
import com.example.tangyi.breakingnews.JsonBean.GameBean;
import com.example.tangyi.breakingnews.JsonBean.InternetBean;
import com.example.tangyi.breakingnews.JsonBean.MovieBean;
import com.example.tangyi.breakingnews.JsonBean.RecreationBean;
import com.example.tangyi.breakingnews.R;
import com.example.tangyi.breakingnews.tools.CacheUtils;
import com.example.tangyi.breakingnews.JsonBean.SocietyBean;
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
public class MainActivity extends Activity implements MyListView.OnRefreshListener,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,ViewPager.OnPageChangeListener{
    private View view,view1,view2,view3,view4,view5,view6,barView,settingView;
    private DrawerLayout mDrawerLayout;
    private LinearLayout leftLayout,aboutLayout;
    private ViewPager viewPager;
    private TabPageIndicator mIndicator;
    private static final String[] TITLE=new String[]{"社会","娱乐","数码","互联网","电影","游戏"};
    private List<View> list;
    //各个频道的ListView
    private MyListView societyListView,recreationListView,digitalListView,internetListView,movieListView,gameListView;
    //社会频道Json数据集合
    private ArrayList<SocietyBean.ShowApi_Res_Body.PageBean.ContentList> societyDataList;
    //社会频道Json数据对象
    private SocietyBean.ShowApi_Res_Body.PageBean.ContentList societyData;
    //娱乐频道Json数据集合
    private ArrayList<RecreationBean.ShowApi_Res_Body.PageBean.ContentList> recreationDataList;
    //娱乐频道Json数据对象
    private RecreationBean.ShowApi_Res_Body.PageBean.ContentList recreationData;
    //数码频道Json数据集合
    private ArrayList<DigitalBean.ShowApi_Res_Body.PageBean.ContentList> digitalDataList;
    //数码频道Json数据对象
    private DigitalBean.ShowApi_Res_Body.PageBean.ContentList digitalData;
    //互联网频道Json数据集合
    private ArrayList<InternetBean.ShowApi_Res_Body.PageBean.ContentList> internetDataList;
    //互联网频道Json数据对象
    private InternetBean.ShowApi_Res_Body.PageBean.ContentList internetData;
    //电影频道Json数据集合
    private ArrayList<MovieBean.ShowApi_Res_Body.PageBean.ContentList> movieDataList;
    //电影频道Json数据对象
    private MovieBean.ShowApi_Res_Body.PageBean.ContentList movieData;
    //游戏频道Json数据集合
    private ArrayList<GameBean.ShowApi_Res_Body.PageBean.ContentList> gameDataList;
    //游戏频道Json数据对象
    private GameBean.ShowApi_Res_Body.PageBean.ContentList gameData;
    //各个频道ListView适配器
    private SocietyListViewAdapter societyListViewAdapter;
    private RecreationListViewAdapter recreationListViewAdapter;
   /* private DigitalListViewAdapter digitalListViewAdapter;
    private InternetListViewAdapter internetListViewAdapter;
    private MovieListViewAdapter movieListViewAdapter;
    private GameListViewAdapter gameListViewAdapter;*/
    //各个频道的SwipeRefreshLayout
    private SwipeRefreshLayout societySwiRefresh,recreationSwiRefresh,digitalSwiRefresh,internetSwiRefresh,movieSwiRefresh,gameSwiRefresh;
    private int page=2;
    private ImageButton openMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.activity_main_drawer);
        leftLayout=(LinearLayout)findViewById(R.id.left_layout);

        //初始化主页面ViewPager数据
        initViewPagerData();
    }
    private void initViewPagerData(){
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        view=findViewById(R.id.content_activity);
        viewPager=(ViewPager)view.findViewById(R.id.home_viewpager);
        barView=view.findViewById(R.id.activity_main_bar);
        settingView=findViewById(R.id.setting_activity);
        aboutLayout=(LinearLayout)settingView.findViewById(R.id.about_layout);
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
            }
        });
        openMenu=(ImageButton)barView.findViewById(R.id.bar_img);
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(leftLayout);
            }
        });


        view1=layoutInflater.inflate(R.layout.home_pager1,null);
        view2=layoutInflater.inflate(R.layout.home_pager2,null);
        view3=layoutInflater.inflate(R.layout.home_pager3,null);
        view4=layoutInflater.inflate(R.layout.home_pager4,null);
        view5=layoutInflater.inflate(R.layout.home_pager5,null);
        view6=layoutInflater.inflate(R.layout.home_pager6,null);

        //各个频道的ListView实例化
        societyListView =(MyListView)view1.findViewById(R.id.home_list);
        recreationListView=(MyListView)view2.findViewById(R.id.home2_list);
        digitalListView=(MyListView)view3.findViewById(R.id.home3_list);
        internetListView=(MyListView)view4.findViewById(R.id.home4_list);
        movieListView=(MyListView)view5.findViewById(R.id.home5_list);
        gameListView=(MyListView)view6.findViewById(R.id.home6_list);

        //各个频道的下拉刷新控件实例化
        societySwiRefresh =(SwipeRefreshLayout)view1.findViewById(R.id.swipere_society);
        recreationSwiRefresh=(SwipeRefreshLayout)view2.findViewById(R.id.swipere_recreation);
        digitalSwiRefresh=(SwipeRefreshLayout)view3.findViewById(R.id.swipere_digital);
        internetSwiRefresh=(SwipeRefreshLayout)view4.findViewById(R.id.swipere_internet);
        movieSwiRefresh=(SwipeRefreshLayout)view5.findViewById(R.id.swipere_movie);
        gameSwiRefresh=(SwipeRefreshLayout)view6.findViewById(R.id.swipere_game);

        //社会频道ListView上拉加载监听，点击事件，下拉刷新
        societyListView.setOnRefreshListener(this);
        societyListView.setOnItemClickListener(this);
        societySwiRefresh.setOnRefreshListener(this);
        societySwiRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        societySwiRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        //娱乐频道ListView上拉加载监听，点击事件，下拉刷新
        recreationListView.setOnRefreshListener(this);
        recreationListView.setOnItemClickListener(this);
        recreationSwiRefresh.setOnRefreshListener(this);
        recreationSwiRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        recreationSwiRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        //数码频道ListView上拉加载监听，点击事件，下拉刷新
        digitalListView.setOnRefreshListener(this);
        digitalListView.setOnItemClickListener(this);
        digitalSwiRefresh.setOnRefreshListener(this);
        digitalSwiRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        digitalSwiRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        //互联网频道ListView上拉加载监听，点击事件，下拉刷新
        internetListView.setOnRefreshListener(this);
        internetListView.setOnItemClickListener(this);
        internetSwiRefresh.setOnRefreshListener(this);
        internetSwiRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        internetSwiRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        //电影频道ListView上拉加载监听，点击事件，下拉刷新
        movieListView.setOnRefreshListener(this);
        movieListView.setOnItemClickListener(this);
        movieSwiRefresh.setOnRefreshListener(this);
        movieSwiRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        movieSwiRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        //游戏频道ListView上拉加载监听，点击事件，下拉刷新
        gameListView.setOnRefreshListener(this);
        gameListView.setOnItemClickListener(this);
        gameSwiRefresh.setOnRefreshListener(this);
        gameSwiRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        gameSwiRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        list=new ArrayList<>();
        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);
        list.add(view6);


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
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
        final String cache=CacheUtils.getCache(MainActivity.this,URList.URL+URList.SOCIETY_ID+URList.PARAMETER_URL+URList.PAGE );
        if (!TextUtils.isEmpty(cache)){
            Log.d("TAG","发现缓存，准备解析缓存中的Json数据");
            processDataSociety(cache,false);
        }
        //ViewPager滑动监听
        viewPager.addOnPageChangeListener(this);
        getDataFromSociety();
    }
    //社会频道数据请求
    private void getDataFromSociety(){
        HttpUtils utils=new HttpUtils();
        //当API接口需要Header请求信息的时候，就可以利用Xutils框架新建RequestParams对象，调用addHeader()方法添加请求头
        RequestParams requestParams=new RequestParams();
        requestParams.addHeader("apikey",URList.APIKEY);
        utils.send(HttpRequest.HttpMethod.GET, URList.URL+URList.SOCIETY_ID+URList.PARAMETER_URL+URList.PAGE ,requestParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        processDataSociety(result,false);
                        Log.d("TAG","请求到新的数据，准备写入缓存");
                        //调用缓存工具类的方法写入缓存
                        CacheUtils.setCache(MainActivity.this,URList.URL+URList.SOCIETY_ID+URList.PARAMETER_URL+URList.PAGE ,result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //社会频道加载更多的调用方法
    private void getMoreFromSociety(){
        HttpUtils utils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addHeader("apikey",URList.APIKEY);
        utils.send(HttpRequest.HttpMethod.GET, URList.URL+URList.SOCIETY_ID+URList.PARAMETER_URL+page+"",requestParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        processDataSociety(result,true);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //社会频道数据解析
    private void processDataSociety(String json, boolean isMore){
        Gson gson=new Gson();
        SocietyBean fromJson=gson.fromJson(json,SocietyBean.class);
        Log.d("TAG","解析数据："+fromJson);
        if (!isMore){
            societyDataList =fromJson.getShowapi_res_body().getPagebean().getContentlist();
            societyListViewAdapter =new SocietyListViewAdapter();

            if (societyDataList !=null){
                societyListView.setAdapter(societyListViewAdapter);
            }
        }else {
           ArrayList<SocietyBean.ShowApi_Res_Body.PageBean.ContentList> moreData=
                    fromJson.getShowapi_res_body().getPagebean().getContentlist();
            societyDataList.addAll(moreData);
            societyListViewAdapter.notifyDataSetChanged();
       }
    }
    //社会频道ListView适配器
    public class SocietyListViewAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=View.inflate(MainActivity.this,R.layout.list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.titleText=(TextView)convertView.findViewById(R.id.title_text);
                viewHolder.newsText=(TextView)convertView.findViewById(R.id.news_text);
                viewHolder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
                convertView.setTag(viewHolder);
            }else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            societyData = (SocietyBean.ShowApi_Res_Body.PageBean.ContentList)getItem(position);
            viewHolder.titleText.setText(societyData.getTitle());
            viewHolder.newsText.setText(societyData.getSource());
            viewHolder.timerText.setText(societyData.getPubDate());
            return convertView;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public Object getItem(int position) {
            return societyDataList.get(position);
        }
        @Override
        public int getCount() {
            return societyDataList.size();
        }

    }

    //娱乐频道数据请求
    private void getDataFromRecreation(){
        HttpUtils utils=new HttpUtils();
        //当API接口需要Header请求信息的时候，就可以利用Xutils框架新建RequestParams对象，调用addHeader()方法添加请求头
        RequestParams requestParams=new RequestParams();
        requestParams.addHeader("apikey",URList.APIKEY);
        utils.send(HttpRequest.HttpMethod.GET, URList.URL+URList.RECREATION_ID+URList.PARAMETER_URL+URList.PAGE ,requestParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        processDataRecreation(result,false);
                        //调用缓存工具类的方法写入缓存
                        CacheUtils.setCache(MainActivity.this, URList.URL+URList.RECREATION_ID+URList.PARAMETER_URL+URList.PAGE  ,result);

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //娱乐频道加载更多的调用方法
    private void getMoreFromRecreation(){
        HttpUtils utils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addHeader("apikey",URList.APIKEY);
        utils.send(HttpRequest.HttpMethod.GET, URList.URL+URList.RECREATION_ID+URList.PARAMETER_URL+page+"",requestParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        processDataSociety(result,true);
                        Log.d("TAG","请求到新的数据，准备读取缓存");
                        //调用缓存工具类的方法写入缓存
                        CacheUtils.setCache(MainActivity.this,URList.URL+URList.RECREATION_ID+URList.PARAMETER_URL+"2",result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //娱乐频道数据解析
    private void processDataRecreation(String json, boolean isMore){
        Gson gson=new Gson();
        RecreationBean fromJson=gson.fromJson(json,RecreationBean.class);
        Log.d("TAG","娱乐解析数据："+fromJson.toString());
        if (!isMore){
            recreationDataList =fromJson.getShowapi_res_body().getPagebean().getContentlist();
            recreationListViewAdapter =new RecreationListViewAdapter();

            if (recreationDataList !=null){
                recreationListView.setAdapter(recreationListViewAdapter);
            }
        }else {
            ArrayList<RecreationBean.ShowApi_Res_Body.PageBean.ContentList> moreData=
                    fromJson.getShowapi_res_body().getPagebean().getContentlist();
            recreationDataList.addAll(moreData);
            recreationListViewAdapter.notifyDataSetChanged();
        }
    }
    //娱乐频道ListView适配器
    public class RecreationListViewAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=View.inflate(MainActivity.this,R.layout.list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.titleText=(TextView)convertView.findViewById(R.id.title_text);
                viewHolder.newsText=(TextView)convertView.findViewById(R.id.news_text);
                viewHolder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
                convertView.setTag(viewHolder);
            }else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            recreationData = (RecreationBean.ShowApi_Res_Body.PageBean.ContentList)getItem(position);
            viewHolder.titleText.setText(recreationData.getTitle());
            viewHolder.newsText.setText(recreationData.getSource());
            viewHolder.timerText.setText(recreationData.getPubDate());
            return convertView;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public Object getItem(int position) {
            return recreationDataList.get(position);
        }
        @Override
        public int getCount() {
            return recreationDataList.size();
        }

    }



    //上拉加载更多回调
    @Override
    public void onLoadMore() {
        getMoreFromSociety();
        if (societyData !=null){
            page++;
        }else {
            page=2;
            Toast.makeText(MainActivity.this,"没有更多新闻",Toast.LENGTH_SHORT).show();
        }

    }
    //下拉刷新回调
    @Override
    public void onRefresh() {
        getDataFromSociety();
        societySwiRefresh.setRefreshing(false);
    }
    //ListView点击事件回调
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(MainActivity.this,NewsActivity.class);;
        switch (parent.getId()){
            case R.id.home_list:
                intent.putExtra("url", societyDataList.get(position).getLink());
                startActivity(intent);
                break;
            case R.id.home2_list:
                intent.putExtra("url", recreationDataList.get(position).getLink());
                startActivity(intent);
                break;
            default:
                break;
        }

    }
    //ViewPager滑动状态回调，共0,1,2三种状态
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    //ViewPager滑动过程回调，参数为滑动的具体值
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    //ViewPager滑动结束回调，参数为结束的具体页面，从0开始
    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                break;
            case 1:
                getDataFromRecreation();
                final String cache1=CacheUtils.getCache(MainActivity.this,URList.URL+URList.RECREATION_ID+URList.PARAMETER_URL+URList.PAGE );
                if (!TextUtils.isEmpty(cache1)){
                    Log.d("TAG","发现缓存，准备解析缓存中的Json数据");
                    processDataRecreation(cache1,false);
                }
                break;
            case 2:
                Log.d("TAG","现在是数码频道");
                break;
            case 3:
                Log.d("TAG","现在是互联网频道");
                break;
            case 4:
                Log.d("TAG","现在是电影频道");
                break;
            case 5:
                Log.d("TAG","现在是游戏频道");
                break;
            default:
                break;
        }
    }


}
