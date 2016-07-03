package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.ToastUtil;
import com.wksc.framwork.widget.LocusPassWordView;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/20.
 */
public class LocusPassFragment extends CommonFragment {
    @Bind(R.id.mLocusPassWordView)
    LocusPassWordView mLocusPassView;
    @Bind(R.id.title)
    TextView title;
    IConfig config;

    private Boolean isLogin;

    private StringBuilder oldPass;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lacus_pass, null);
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        oldPass = new StringBuilder();
        isLogin = (Boolean) getmDataIn();
        if (isLogin) {
            setHeaderTitle("手势登录");
            getTitleHeaderBar().getLeftViewContainer().setVisibility(View.GONE);
        } else {
            setHeaderTitle("管理手势密码");
            getTitleHeaderBar().getLeftViewContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
        config = BaseApplication.getInstance().getCurrentConfig();
        mLocusPassView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {

            @Override
            public void onComplete(String password) {
                // TODO Auto-generated method stub
                if (isLogin) {
                    String savedPassword = config.getString("locusPassword","");
                    if (savedPassword.equals(password)){
                        StringBuilder sb = new StringBuilder(Urls.LOGIN);
                        UrlUtils.getInstance().praseToUrl(sb,"username",config.getString("username",""))
                                .praseToUrl(sb,"password",config.getString("password",""));
                        OkHttpUtils.post(sb.toString())//
                                .tag(this)//
                                .execute(new DialogCallback<Object>(getContext(), Object.class) {

                                    @Override
                                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                        super.onError(isFromCache, call, response, e);
                                        ToastUtil.showShortMessage(getContext(),"系统错误");
                                        mLocusPassView.clearPassword();
                                    }

                                    @Override
                                    public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {
//                                        ToastUtil.showShortMessage(getContext(),"成功");
//                                        config.setString("username",username);
//                                        config.setString("password",password);
                                        mLocusPassView.clearPassword();
                                        startActivity(MainActivity.class);
                                        getActivity().finish();
                                    }
                                });
//                        startActivity(MainActivity.class);
//                        getActivity().finish();
                    }else{
                        title.setText("手势密码错误请重新输入");
                        mLocusPassView.markError(2000);
                    }

                } else {
                    if (oldPass.length() == 0) {
                        oldPass.append(password);
                        mLocusPassView.clearPassword();
                        title.setText("请再次确认手势密码");
                    } else {
                        if (oldPass.toString().equals(password)) {
                            config.setBoolean("setLocusPassword", true);
                            config.setString("locusPassword",password);
                            getActivity().finish();
                        } else {
                            title.setText("手势密码错误");
                            mLocusPassView.markError(1000);
                        }
                    }
                }

            }
        });
        return v;
    }

    @Override
    protected void lazyLoad() {

    }
}
