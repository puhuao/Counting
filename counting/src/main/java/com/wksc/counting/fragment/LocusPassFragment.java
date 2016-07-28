package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.Basedata.BaseDataUtil2;
import com.wksc.counting.R;
import com.wksc.counting.activity.MainActivity;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.model.baseinfo.Channel;
import com.wksc.counting.model.baseinfo.CoreItem;
import com.wksc.counting.model.baseinfo.GoodsClassFirst;
import com.wksc.counting.model.baseinfo.GoodsClassScend;
import com.wksc.counting.model.baseinfo.Region;
import com.wksc.counting.tools.NetWorkTool;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.GsonUtil;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;
import com.wksc.framwork.widget.LocusPassWordView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                    if (config.getInt("validType",0)==0){
                        doLogin();
                    }else{
                        mLocusPassView.clearPassword();
                        startActivity(MainActivity.class);
                        getActivity().finish();
                    }
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

    private void doLogin() {
            StringBuilder sb = new StringBuilder(Urls.LOGIN);
            UrlUtils.getInstance().praseToUrl(sb,"username",config.getString("username",""))
                    .praseToUrl(sb,"password",config.getString("password",""));
        if(!NetWorkTool.isNetworkAvailable(getActivity())){
            ToastUtil.showShortMessage(getActivity(),"网络错误");
            return;
        }
        DialogCallback callback =new DialogCallback<Object>(getContext(), Object.class) {

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
                ToastUtil.showShortMessage(getContext(),"系统错误");
                mLocusPassView.clearPassword();
            }

            @Override
            public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {
                mLocusPassView.clearPassword();
                getBaseData();
//                startActivity(MainActivity.class);
//                getActivity().finish();
            }
        };
        if(!NetWorkTool.isNetworkAvailable(getActivity())){
            ToastUtil.showShortMessage(getActivity(),"请检查网络设置");
            return;
        }
        callback.setDialogHide();
            OkHttpUtils.post(sb.toString())//
                    .tag(this)//
                    .execute(callback);
    }

    @Override
    protected void lazyLoad() {

    }

    private void getBaseData() {
        StringBuilder sb = new StringBuilder(Urls.BASE_INFO);
        UrlUtils.getInstance().addSession(sb, config);
        if(!NetWorkTool.isNetworkAvailable(getActivity())){
            ToastUtil.showShortMessage(getActivity(),"网络错误");
            return;
        }
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<String>(getActivity(), String.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        ToastUtil.showShortMessage(getContext(),"基础信息获取出错！！");
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
                                config.setString("topicrule",topicrule);
                                config.setString("noderule",object.getString("noderule"));
                                JSONArray array = object.getJSONArray("GoodsClass");
                                BaseDataUtil.clearData();
                                BaseDataUtil2.clearData();
                                BaseDataUtil.region.addAll(
                                        GsonUtil.fromJsonList(region, Region.class));
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

    public List<GoodsClassFirst> getGoods(JSONArray array){
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
        return  goodsClassFirsts;
    }
}
