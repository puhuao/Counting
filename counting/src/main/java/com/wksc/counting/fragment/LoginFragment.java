package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.callBack.BaseInfo;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Constans;
import com.wksc.counting.config.Urls;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by puhua on 2016/5/26.
 *
 * @
 */
public class LoginFragment extends CommonFragment {
    @Bind(R.id.fab)
    Button button;
    @Bind(R.id.et_username)
    EditText userName;
    @Bind(R.id.et_password)
    EditText passWord;
    @Bind(R.id.get_valid_code)
    Button getValidCode;
    private IConfig config;
    private int validType = 0;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTitleHeaderBar.setVisibility(View.GONE);
        final View view = inflater.inflate(R.layout.activity_login, null);
        ButterKnife.bind(this, view);
        config = BaseApplication.getInstance().getCurrentConfig();
        userName.setText(config.getString("username", ""));
        passWord.setText(config.getString("password", ""));
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @OnClick({R.id.fab, R.id.get_valid_code})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_valid_code:
                validType = 1;
                getValidCode();
                break;
            case R.id.fab:
                    doLogin();
                break;
        }
    }

    private void doLogin() {
        if (StringUtils.isBlank(userName.getText().toString())) {
            ToastUtil.showShortMessage(getContext(), "请输入用户名");
            return;
        }
        if (StringUtils.isBlank(passWord.getText().toString())) {
            ToastUtil.showShortMessage(getContext(), "请输入密码");
            return;
        }

        final String username = userName.getText().toString();
        final String password = passWord.getText().toString();

        StringBuilder sb = new StringBuilder(Urls.LOGIN);

        if (validType==0){
            UrlUtils.getInstance().praseToUrl(sb, "username", username).praseToUrl(sb, "password", password);
        }else{
            UrlUtils.getInstance().praseToUrl(sb, "username", username).praseToUrl(sb, "smsValidCode", password);
        }
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<Object>(getContext(), Object.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {
                        if (BaseInfo.code == 1) {
                            if(validType==0)
                            ToastUtil.showShortMessage(getContext(), "密码错误");
                            else{
                                ToastUtil.showShortMessage(getContext(), "验证码错误");
                            }
                        } else {
                            config.setInt("validType",validType);
                            config.setString("username", username);
                            config.setString("password", password);
                            startActivity(MainActivity.class);
                            getActivity().finish();
                        }

                    }
                });
    }

    private void getValidCode() {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[6,7,8]))\\d{8}$");
        Matcher m = p.matcher(userName.getText().toString());
        if (!m.matches()) {
            ToastUtil.showShortMessage(getContext(), "请输入正确的手机号");
        }
        if (StringUtils.isBlank(userName.getText().toString())) {
            ToastUtil.showShortMessage(getContext(), "请输入手机号");
            return;
        }

        final String username = userName.getText().toString();

        StringBuilder sb = new StringBuilder(Urls.GET_MOBILE_VALID_CODE);
        UrlUtils.getInstance().praseToUrl(sb, "username", username).praseToUrl(sb, "busiType",
                Constans.LOGIN).praseToUrl(sb,"phone",username);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<Object>(getContext(), Object.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {

                    }
                });
    }

    @Override
    protected void lazyLoad() {

    }
}
