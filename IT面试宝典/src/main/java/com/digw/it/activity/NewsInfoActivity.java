package com.digw.it.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.digw.it.Constant;
import com.digw.it.R;
import com.digw.it.entity.news.NewsTitle;
import com.digw.it.util.JsoupUtil;

import org.jsoup.nodes.Document;

public class NewsInfoActivity extends BaseActivity {
    private Toolbar toolbar;
    private ProgressBar loadingProgress;
    private WebView webView;
    private WebSettings webSettings;
    private NewsTitle news;

    @Override
    public void viewClick(View v) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        news= (NewsTitle) getIntent().getSerializableExtra("news");
    }

    @Override
    public void initParams(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        news= (NewsTitle) getIntent().getSerializableExtra("news");
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_news_info;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView(View view) {
        toolbar=$(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        loadingProgress=$(R.id.loading_progress);
        webView=$(R.id.web_view);
        webSettings=webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress==100){
                    loadingProgress.setVisibility(View.GONE);
                }else {
                    loadingProgress.setVisibility(View.VISIBLE);
                    loadingProgress.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(news.getTitle());
        getUrlContext();
        //webView.loadUrl(Constant.URL_PREFIX_NEWS_INFO+news.getPostid()+".html");
        //Log.e(TAG, "onResume: "+webView.getUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUrlContext(){
        JsoupUtil.getDocumentByUrl(Constant.URL_PREFIX_NEWS_INFO+news.getPostid()+".html", new JsoupUtil.JsoupCallbackListener() {
            @Override
            public void onFinish(final Document document) {
                document.select("div.a_adtemp.a_topad.js-topad").remove();
                document.select("div.relative_doc.relativedoc-"+news.getPostid()).remove();
                document.select("div.doc-footer-wrapper").remove();
                document.select("a.more_client.more-client").remove();
                document.select("section.article_comment.tie-"+news.getPostid()).remove();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadDataWithBaseURL(document.baseUri(),document.html(),"text/html","UTF-8",null);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }
}
