package com.wksc.counting.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.Basedata.BaseDataUtil2;
import com.wksc.counting.R;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.callBack.BaseInfo;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Constans;
import com.wksc.counting.config.Urls;
import com.wksc.counting.model.UpdateInfo;
import com.wksc.counting.model.baseinfo.Channel;
import com.wksc.counting.model.baseinfo.CoreItem;
import com.wksc.counting.model.baseinfo.GoodsClassFirst;
import com.wksc.counting.model.baseinfo.GoodsClassScend;
import com.wksc.counting.model.baseinfo.Region;
import com.wksc.counting.tools.NetWorkTool;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.update.UpdateManager;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.GsonUtil;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
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
    TextView getValidCode;
    private IConfig config;
    private int validType = 0;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTitleHeaderBar.setVisibility(View.GONE);
        final View view = inflater.inflate(R.layout.activity_login, null);
        getVersion();
//        UpdateManager.getUpdateManager().checkAppUpdate(getActivity(),
//                false);
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

    String username;
    String password;

    private void doLogin() {
        if (StringUtils.isBlank(userName.getText().toString())) {
            ToastUtil.showShortMessage(getContext(), "请输入用户名");
            return;
        }
        if (StringUtils.isBlank(passWord.getText().toString())) {
            ToastUtil.showShortMessage(getContext(), "请输入密码");
            return;
        }

        username = userName.getText().toString();
        password = passWord.getText().toString();

        StringBuilder sb = new StringBuilder(Urls.LOGIN);

        if (validType == 0) {
            UrlUtils.getInstance().praseToUrl(sb, "username", username).praseToUrl(sb, "password", password);
        } else {
            UrlUtils.getInstance().praseToUrl(sb, "username", username).praseToUrl(sb, "smsValidCode", password);
        }

        DialogCallback callback = new DialogCallback<Object>(getContext(), Object.class) {

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
            }

            @Override
            public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {
                if (BaseInfo.code == 1) {
                    if (validType == 0)
                        ToastUtil.showShortMessage(getContext(), "密码错误");
                    else {
                        ToastUtil.showShortMessage(getContext(), "验证码错误");
                    }
                } else {
                    getBaseData();
                }

            }
        };
        if (!NetWorkTool.isNetworkAvailable(getActivity())) {
            ToastUtil.showShortMessage(getActivity(), "网络错误\n请检查网络设置");
            return;
        }
        callback.setDialogHide();
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(callback);
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
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "username", username).praseToUrl(sb, "busiType",
                Constans.LOGIN).praseToUrl(sb, "phone", username);
        if (!NetWorkTool.isNetworkAvailable(getActivity())) {
            ToastUtil.showShortMessage(getActivity(), "网络错误");
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

                    }
                });
    }

    @Override
    protected void lazyLoad() {

    }

    private void getBaseData() {
        StringBuilder sb = new StringBuilder(Urls.BASE_INFO);
        UrlUtils.getInstance().addSession(sb, config);
        if (!NetWorkTool.isNetworkAvailable(getActivity())) {
            ToastUtil.showShortMessage(getActivity(), "网络错误");
            return;
        }
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<String>(getActivity(), String.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        ToastUtil.showShortMessage(getContext(), "基础信息获取出错！！");
                    }

                    @Override
                    public void onResponse(boolean isFromCache, String c, Request request, @Nullable Response response) {
                        config = BaseApplication.getInstance().getPreferenceConfig();
                        try {
                            if (!StringUtils.isBlank(c)) {
                                JSONObject object = new JSONObject(c);
                                String region = object.getString("regions");
                                String channel = object.getString("channel");
                                String items = object.getString("coreitem");
                                String topicrule = object.getString("topicrule");
                                config.setString("topicrule", topicrule);
                                config.setString("noderule", object.getString("noderule"));
                                JSONArray array = object.getJSONArray("GoodsClass");
                                BaseDataUtil.clearData();
                                BaseDataUtil2.clearData();
                                BaseDataUtil.main1Region.addAll(
                                        GsonUtil.fromJsonList(region, Region.class));
                                BaseDataUtil.mainRegion.addAll(
                                        GsonUtil.fromJsonList(region, Region.class));
                                BaseDataUtil.coreRegion.addAll(GsonUtil.fromJsonList(region, Region.class));
                                BaseDataUtil2.region.addAll(
                                        GsonUtil.fromJsonList(region, Region.class));
                                BaseDataUtil2.copyConditionSet(region);
                                BaseDataUtil.channels.addAll(GsonUtil.fromJsonList(channel, Channel.class));
                                BaseDataUtil2.channels.addAll(GsonUtil.fromJsonList(channel, Channel.class));
                                BaseDataUtil.coreItems.addAll(GsonUtil.fromJsonList(items, CoreItem.class));
                                BaseDataUtil2.coreItems.addAll(GsonUtil.fromJsonList(items, CoreItem.class));
                                BaseDataUtil.goodsClassFirst.addAll(getGoods(array));
                                BaseDataUtil2.goodsClassFirst.addAll(getGoods(array));
                                BaseDataUtil2.goodsClassFirstGoal.addAll(getGoods(array));
                                BaseDataUtil2.goodsClassFirstChannel.addAll(getGoods(array));
                                BaseDataUtil2.goodsClassFirstVip.addAll(getGoods(array));
                                BaseDataUtil2.goodsClassFirstGoods.addAll(getGoods(array));
                                BaseDataUtil2.goodsClassFirstSave.addAll(getGoods(array));
                                config.setInt("validType", validType);
                                config.setString("username", username);
                                config.setString("password", password);
                                startActivity(MainActivity.class);
                                getActivity().finish();
                            } else {
                                ToastUtil.showShortMessage(getContext(), "数据为空");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    public List<GoodsClassFirst> getGoods(JSONArray array) {
        List<GoodsClassFirst> goodsClassFirsts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = null;
            try {
                obj = array.getJSONObject(i);
                GoodsClassFirst first = new GoodsClassFirst();
                first.name = obj.getString("name");
                first.code = obj.getString("code");
                String classS = obj.getString("class");
                List<GoodsClassScend> goodsClassScends = GsonUtil.
                        fromJsonList(classS, GoodsClassScend.class);
                first.classX = goodsClassScends;
                goodsClassFirsts.add(first);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return goodsClassFirsts;
    }

    private void getVersion() {
        StringBuilder sb = new StringBuilder(Urls.UPDATEINFO);
//        UrlUtils.getInstance().addSession(sb, config);
        if (!NetWorkTool.isNetworkAvailable(getActivity())) {
            ToastUtil.showShortMessage(getActivity(), "网络错误");
            return;
        }

        DialogCallback dialogCallback = new DialogCallback<UpdateInfo>(getActivity(), UpdateInfo.class) {

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
            }

            @Override
            public void onResponse(boolean isFromCache, UpdateInfo o, Request request, @Nullable Response response) {
                if (o != null) {
                    PackageManager manager = getActivity().getPackageManager();
                    try {
                        int remoteVersionCode = Integer.valueOf(o.version);
                        PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
                        int versionCode = info.versionCode;
//                        ToastUtil.showShortMessage(getActivity(), "versonCode=" + remoteVersionCode + " thisVer"
//                                + versionCode);

                        if (remoteVersionCode > versionCode) {
                            UpdateManager.getUpdateManager().setContext(getContext());
                            UpdateManager.getUpdateManager().setUpdateInfo(o);
                            UpdateManager.getUpdateManager().showNoticeDialog();
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        dialogCallback.setDialogHide();
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(dialogCallback);
    }
}
