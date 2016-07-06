package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.activity.SalesComparisonActivity;
import com.wksc.counting.adapter.CoreIndexListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.TurnToMoreFragmentEvent;
import com.wksc.counting.model.CoreIndexListModel;
import com.wksc.counting.model.baseinfo.Channel;
import com.wksc.counting.model.baseinfo.CoreItem;
import com.wksc.counting.model.baseinfo.GoodsClassFirst;
import com.wksc.counting.model.baseinfo.GoodsClassScend;
import com.wksc.counting.model.baseinfo.Region;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.GsonUtil;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
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
 * Created by puhua on 2016/5/27.
 *
 * @
 */
public class CoreIndexFragment extends CommonFragment implements AdapterView.OnItemClickListener {
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.condition)
    ConditionLayout conditionLayout;
    private IConfig config = null;

    CoreIndexListAdapter coreIndexListAdapter;
    List<CoreItem> coreItems;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_core_index, null);
        hideLeftButton();
        showRightButton();
        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new TurnToMoreFragmentEvent(false));
            }
        });
        setHeaderTitle("核心指标");

        return v;
    }

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);

        config = BaseApplication.getInstance().getCurrentConfig();
        coreIndexListAdapter = new CoreIndexListAdapter(getActivity());
        list.setAdapter(coreIndexListAdapter);
        list.setOnItemClickListener(this);
        conditionLayout.hideGoods(true);
        conditionLayout.setConditionSelect(new ConditionLayout.OnConditionSelect() {
            @Override
            public void postParams() {
                getListData();
            }
        });
        isPrepared = true;
        lazyLoad();
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        getContext().pushFragmentToBackStack(RegisterFragment.class, null);
        Bundle bundle = new Bundle();
        bundle.putString("param", coreIndexListAdapter.getList().get(position).coreCode);
        bundle.putString("extraParam",extraParam);
        startActivity(SalesComparisonActivity.class, bundle);
    }

    private void getBaseData() {
        StringBuilder sb = new StringBuilder(Urls.BASE_INFO);
        UrlUtils.getInstance().addSession(sb, config);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<String>(getContext(), String.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, String c, Request request, @Nullable Response response) {
                        config = BaseApplication.getInstance().getPreferenceConfig();
                        try {
                            if(!StringUtils.isBlank(c)){
                                JSONObject object = new JSONObject(c);
                                //                            JSONObject retObject = object.getJSONObject("retObj");
                                String region = object.getString("regions");
                                String channel = object.getString("channel");
                                String items = object.getString("coreitem");
                                JSONArray array = object.getJSONArray("GoodsClass");
                                BaseDataUtil.clearData();
                                BaseDataUtil.region.addAll(
                                        GsonUtil.fromJsonList(region, Region.class));
                                BaseDataUtil.channels.addAll(GsonUtil.fromJsonList(channel, Channel.class));
                                List<GoodsClassFirst> goodsClassFirsts = new ArrayList<>();
                                coreItems = GsonUtil.fromJsonList(items, CoreItem.class);
                                BaseDataUtil.coreItems.addAll(coreItems);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    GoodsClassFirst first = new GoodsClassFirst();
                                    first.name = obj.getString("name");
                                    first.code = obj.getString("code");
                                    String classS = obj.getString("class");
                                    List<GoodsClassScend> goodsClassScends = GsonUtil.
                                            fromJsonList(classS, GoodsClassScend.class);
                                    first.classX = goodsClassScends;
                                    goodsClassFirsts.add(first);
                                }
                                BaseDataUtil.goodsClassFirst.addAll(goodsClassFirsts);
                                Log.i("TAG", goodsClassFirsts.toString());
                                mHasLoadedOnce = true;
                                getListData();
                            }else{
                                ToastUtil.showShortMessage(getContext(),"数据为空");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    private void getListData() {
        extraParam = conditionLayout.getAllConditions();
        StringBuilder sb = new StringBuilder(Urls.COREINDEX);
        UrlUtils.getInstance().addSession(sb, config);
        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
//                    .setCertificates(getContext().getAssets().open("bijia.cer"))
                .execute(new DialogCallback<String>(getContext(), String.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        ToastUtil.showShortMessage(getContext(),"系统错误");
                    }

                    @Override
                    public void onResponse(boolean isFromCache, String c, Request request, @Nullable Response response) {
                        try {
                            if (!StringUtils.isBlank(c)){
                                JSONObject object = new JSONObject(c);
                                String item = object.getString("CoreIndex");
                                List<CoreIndexListModel> coreIndexListModels = GsonUtil.fromJsonList(item, CoreIndexListModel.class);
                                coreIndexListAdapter.setList(coreIndexListModels);
                                Log.i("TAG", coreIndexListModels.toString());
                            }else{
                                ToastUtil.showShortMessage(getContext(),"数据为空");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }




    @Override
    protected void lazyLoad() {
        if (!isPrepared || mHasLoadedOnce) {
            return;
        }
        getBaseData();
    }
}
