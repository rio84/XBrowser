package com.openxsl.xbrowser;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.net.Uri;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
    private WebView webView;

    private FrameLayout mask;
    private String launchUrl= "https://www.openxsl.com/m/index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchUrl=getResources().getString(R.string.launch_url);



        webView=(WebView)findViewById(R.id.mainWebView);
        mask=(FrameLayout)findViewById(R.id.lyMask);


        if (Build.VERSION.SDK_INT >= 19) {

            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri= request.getUrl();
                if(uri!=null) {
                    view.loadUrl(uri.toString());
                }
                return true;

                //return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.i("XSLInfo","onload!!!woca");

                if(mask!=null){Log.i("XSLInfo","mask removing");
                    RelativeLayout parent=(RelativeLayout)mask.getParent();
                    parent.removeView(mask);

                    mask=null;
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {

                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView.loadUrl("file:///android_asset/html/errorpage.html");
                webView.clearHistory();
                super.onReceivedError(view, request, error);
            }
        });
        webView.loadUrl(launchUrl);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
