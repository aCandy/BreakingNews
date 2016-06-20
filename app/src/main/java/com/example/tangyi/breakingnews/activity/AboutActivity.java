package com.example.tangyi.breakingnews.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tangyi.breakingnews.R;


/**
 * Created by 在阳光下唱歌 on 2016/6/21.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    private View view;
    private TextView textView;
    private ImageButton imageButton;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_activity);
        view=findViewById(R.id.about_bar);
        textView=(TextView)view.findViewById(R.id.bar_text);
        textView.setText("关于");
        imageButton=(ImageButton)view.findViewById(R.id.bar_img);
        imageButton.setImageResource(R.drawable.ic_back);
        imageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        finish();
    }

}
