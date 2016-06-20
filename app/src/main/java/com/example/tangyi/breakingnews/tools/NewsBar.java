package com.example.tangyi.breakingnews.tools;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangyi.breakingnews.R;
import com.example.tangyi.breakingnews.activity.NewsActivity;

/**
 * Created by 在阳光下唱歌 on 2016/6/17.
 */
public class NewsBar extends RelativeLayout {
    private TextView textView;
    private ImageButton backButton;

    public NewsBar(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar,this);
        textView=(TextView)findViewById(R.id.bar_text);
        backButton=(ImageButton) findViewById(R.id.bar_img);
        //这里的自定义控件引用将设置按钮替换为了返回按钮
        backButton.setImageResource(R.drawable.ic_back);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *getContext()是获取当前自定义控件布局所依附的Context对象
                 * 然后再将其强转成Activity对象
                 * 这样就能调用Activity对象的finish()方法销毁当前Activity
                 * 故也可直接写作：((Activity)getContext()).finish();
                 */
                Activity mActivity = (Activity)getContext();
                mActivity.finish();
            }
        });
        textView.setText("新闻详情");
    }
}
