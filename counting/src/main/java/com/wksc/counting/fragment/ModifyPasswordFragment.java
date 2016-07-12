package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.activity.LoginActivity;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.callBack.BaseInfo;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.model.NoticeResult;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.LoadMoreListView;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.ActivityManager;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by puhua on 2016/7/12.
 *
 * @
 */
public class ModifyPasswordFragment extends CommonFragment {

    @Bind(R.id.old_password)
    EditText etOldPassword;
    @Bind(R.id.new_password)
    EditText etNewPassWord;
    @Bind(R.id.make_sure_password)
    EditText etMakeSurePassword;
    @Bind(R.id.sure)
    Button sure;
    private IConfig config;
    private String oldPassword;
    private String newPassword;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_modifypsw, null);
        setHeaderTitle("修改密码");
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        config = BaseApplication.getInstance().getCurrentConfig();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPassword();
            }
        });
        return v;
    }

    private void modifyPassword(){
        oldPassword = etOldPassword.getText().toString();
        newPassword = etNewPassWord.getText().toString();
        String surePassword = etMakeSurePassword.getText().toString();
        if (StringUtils.isBlank(oldPassword)){
            ToastUtil.showShortMessage(getContext(),"请输入旧密码");
            return;
        }
        if (StringUtils.isBlank(newPassword)){
            ToastUtil.showShortMessage(getContext(),"请输入新密码");
            return;
        }
        if (StringUtils.isBlank(surePassword)){
            ToastUtil.showShortMessage(getContext(),"请输入确认密码");
            return;
        }

        if (!newPassword.equals(surePassword)){
            ToastUtil.showShortMessage(getContext(),"确认密码有误，请重新输入");
            return;
        }

        StringBuilder sb = new StringBuilder(Urls.MODIFY_PASSWORD);
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "oldPassword", oldPassword).praseToUrl(sb, "password", newPassword);
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
                                         ToastUtil.showShortMessage(getContext(), "原密码错误");
                                 }else{
                                     ToastUtil.showShortMessage(getContext(), "修改密码成功");
                                     config.setBoolean("setLocusPassword", false);
                                     config.setString("locusPassword","");
                                     ActivityManager.getInstance().finishActivity(MainActivity.class);
                                     getActivity().finish();
                                     startActivity(LoginActivity.class,null);
                                 }
                             }
                         }
                );
    }

    @Override
    protected void lazyLoad() {

    }
}
