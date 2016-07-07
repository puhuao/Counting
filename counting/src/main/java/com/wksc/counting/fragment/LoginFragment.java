package com.wksc.counting.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.callBack.BaseInfo;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.CustomDialog;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

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
    private IConfig config;

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

    @OnClick({R.id.fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                String url = "http://bpb.1919.cn/ea/gw?cmd=memberLogin";

                if (StringUtils.isBlank(userName.getText().toString())) {
                    ToastUtil.showShortMessage(getContext(), "请输入用户名");
                    break;
                }
                if (StringUtils.isBlank(passWord.getText().toString())) {
                    ToastUtil.showShortMessage(getContext(), "请输入密码");
                    break;
                }

                final String username = userName.getText().toString();
                final String password = passWord.getText().toString();

                StringBuilder sb = new StringBuilder(Urls.LOGIN);
                UrlUtils.getInstance().praseToUrl(sb, "username", username).praseToUrl(sb, "password", password);
                OkHttpUtils.post(sb.toString())//
                        .tag(this)//
                        .execute(new DialogCallback<Object>(getContext(), Object.class) {

                            @Override
                            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                super.onError(isFromCache, call, response, e);
                            }

                            @Override
                            public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {
//                                ToastUtil.showShortMessage(getContext(),"成功");
                                if (BaseInfo.code == 1) {
                                    ToastUtil.showShortMessage(getContext(), "密码错误");
                                } else {
                                    config.setString("username", username);
                                    config.setString("password", password);
                                    startActivity(MainActivity.class);
                                    getActivity().finish();
                                }

                            }
                        });
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }
}
