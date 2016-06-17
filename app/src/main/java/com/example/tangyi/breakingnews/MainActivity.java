package com.example.tangyi.breakingnews;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 在阳光下唱歌 on 2016/6/17.
 */
public class MainActivity extends Activity {
    private View view,view1,view2;
    private ViewPager viewPager;
    private TabPageIndicator mIndicator;
    private static final String[] TITLE=new String[]{"社会","娱乐","数码","互联网","电影","游戏"};
    private LayoutInflater layoutInflater;
    private List<View> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        layoutInflater=LayoutInflater.from(this);
        view=(View)findViewById(R.id.content_activity);
        view1=layoutInflater.inflate(R.layout.home_pager1,null);
        view2=layoutInflater.inflate(R.layout.home_pager2,null);
        list=new ArrayList<>();
        list.add(view1);
        list.add(view2);
        list.add(view1);
        list.add(view2);
        list.add(view1);
        list.add(view2);
        viewPager=(ViewPager)view.findViewById(R.id.home_viewpager);
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
    }

}
