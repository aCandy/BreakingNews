package com.example.tangyi.breakingnews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.tangyi.breakingnews.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by 在阳光下唱歌 on 2016/6/20.
 */
public class NewsActivity extends AppCompatActivity {
    private WebView newsWebView;
    private ProgressBar newsProgressBar;
    private String newsUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news);

        newsUrl=getIntent().getStringExtra("url");
        newsWebView=(WebView)findViewById(R.id.news_webview);
        newsProgressBar=(ProgressBar)findViewById(R.id.news_progressbar);
        newsWebView.getSettings().setJavaScriptEnabled(true);
        newsWebView.setWebViewClient(new WebViewClient());
        newsWebView.loadUrl(newsUrl);
        newsWebView.setWebChromeClient(new WebChromeClient(){
            //加载进度监听方法
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                newsProgressBar.setProgress(newProgress);
                if (newsProgressBar.getProgress()==100){
                    newsProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
