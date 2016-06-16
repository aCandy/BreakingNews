package com.example.tangyi.breakingnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

/**
 * Created by 在阳光下唱歌 on 2016/6/16.
 */
public class SplashActivity extends AppCompatActivity {
    private RelativeLayout splashLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        splashLayout=(RelativeLayout)findViewById(R.id.splash_relative);
        initAnimation();
    }
    //初始化动画效果。
    private void initAnimation(){
        Animation animation= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.alpha_scale);
        animation.setFillAfter(true);
        //给布局添加动画效果，也就是给启动页添加动画效果
        splashLayout.startAnimation(animation);
        //给动画设置监听器，当动画展示完毕后就进行判断。
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
             /*   //利用SharedPreferencesUtils工具类的静态函数获取是否是第一进入APP，如果为true，就跳转到新手页面。反之跳转到主页面。
                Boolean isFirstEnter= SharedPreferencesUtils.getBoolean(SplashActivity.this,"is_first_enter",true);
                Intent intent;
                if (isFirstEnter){
                    intent=new Intent(SplashActivity.this,GuideActivity.class);
                }else {
                    intent=new Intent(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();//关闭当前页面*/
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
