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
import com.wksc.counting.activity.LoginActivity;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.callBack.BaseInfo;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.tools.NetWorkTool;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.ActivityManager;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//        if (newPassword.length()<6||newPassword.length()>25){
//            ToastUtil.showShortMessage(getContext(),"密码长度限制为6-25位");
//            return;
//        }

        if (oldPassword.equals(newPassword)){
            ToastUtil.showShortMessage(getContext(),"新密码不能和原密码相同");
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

        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])\\S{6,25}$");
        Matcher m = p.matcher(newPassword);
        if (!m.matches()) {
            ToastUtil.showShortMessage(getContext(), "密码长度限制为6-25位\n必须包含至少一个大写和小写字母");
            return;
        }

        StringBuilder sb = new StringBuilder(Urls.MODIFY_PASSWORD);
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "oldPassword", oldPassword).praseToUrl(sb, "password", newPassword);
        if(!NetWorkTool.isNetworkAvailable(getActivity())){
            ToastUtil.showShortMessage(getActivity(),"网络错误");
            return;
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
