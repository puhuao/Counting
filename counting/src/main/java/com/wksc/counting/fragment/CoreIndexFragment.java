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
import com.wksc.counting.model.CoreIndexListModel;
import com.wksc.counting.model.baseinfo.Channel;
import com.wksc.counting.model.baseinfo.CoreItem;
import com.wksc.counting.model.baseinfo.GoodsClassFirst;
import com.wksc.counting.model.baseinfo.GoodsClassScend;
import com.wksc.counting.model.baseinfo.Region;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.GsonUtil;

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
        setHeaderTitle("核心指标");
        getBaseData();
        return v;
    }

    private void getBaseData() {
        String url = "http://101.200.131.198:8087/gw?cmd=appGetBaseInfo";
        OkHttpUtils.post(url)//
                .tag(this)//
//                .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")//
                .execute(new DialogCallback<String>(getContext(), String.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, String c, Request request, @Nullable Response response) {
                        config = BaseApplication.getInstance().getPreferenceConfig();
                        try {
                            JSONObject object = new JSONObject(c);
//                            JSONObject retObject = object.getJSONObject("retObj");
                            String region = object.getString("regions");
                            String channel = object.getString("channel");
                            String items = object.getString("coreitem");
                            JSONArray array = object.getJSONArray("GoodsClass");
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
                                List<GoodsClassScend> goodsClassScends = (List<GoodsClassScend>)
                                        GsonUtil.jsonToList(classS);
                                first.classX = goodsClassScends;
                                goodsClassFirsts.add(first);
                            }
                            Log.i("TAG", goodsClassFirsts.toString());
                            getListData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    private void getListData() {
        String url = "http://101.200.131.198:8087/gw?cmd=appCoreIndex";
        OkHttpUtils.post(url)//
                .tag(this)//
                .execute(new DialogCallback<String>(getContext(), String.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, String c, Request request, @Nullable Response response) {
                        try {
                            JSONObject object = new JSONObject(c);
                            String item = object.getString("CoreIndex");
                            List<CoreIndexListModel> coreIndexListModels = GsonUtil.fromJsonList(item, CoreIndexListModel.class);
                            coreIndexListAdapter.setList(coreIndexListModels);
                            Log.i("TAG", coreIndexListModels.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        coreIndexListAdapter = new CoreIndexListAdapter(getActivity());
//        coreIndexListAdapter.setList(CoreIndexModel.getData());
        list.setAdapter(coreIndexListAdapter);
        list.setOnItemClickListener(this);
        conditionLayout.hideGoods(true);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        getContext().pushFragmentToBackStack(RegisterFragment.class, null);
        startActivity(SalesComparisonActivity.class);
    }


}
