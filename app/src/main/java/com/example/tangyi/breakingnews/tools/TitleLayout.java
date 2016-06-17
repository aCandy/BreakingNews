package com.example.tangyi.breakingnews.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tangyi.breakingnews.R;

/**
 * Created by 在阳光下唱歌 on 2016/6/17.
 */
public class TitleLayout extends RelativeLayout {
    private TextView textView;
    public TitleLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar,this);
        textView=(TextView)findViewById(R.id.bar_text);
        textView.setText("设置");
    }
}
