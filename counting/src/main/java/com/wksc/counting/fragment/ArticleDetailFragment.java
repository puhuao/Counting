package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wksc.counting.R;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/27.
 *
 * @
 */
public class ArticleDetailFragment extends CommonFragment {
    @Bind(R.id.web)
    WebView web;
    private int id;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article_detail, null);
        setHeaderTitle("公告详情");
        Bundle bundle = (Bundle) getmDataIn();
        id = bundle.getInt("id");
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        String url = "http://bpb.1919.cn/ea/article/mobile/"+id+".page";
        WebSettings webSettings = web.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        web.loadUrl(url);
        //设置Web视图
        web.setWebViewClient(new webViewClient ());

    }

//    //设置回退
//    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
//            web.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        finish();//结束退出程序
//        return false;
//    }

    @Override
    protected void lazyLoad() {

    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
