package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.ChainVipListAdapter;
import com.wksc.counting.adapter.VipNormalCustomerListAdapter;
import com.wksc.counting.adapter.VipPurchaseListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.model.SaleAnaModel.SaleAnaModel;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.PieChartTool;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/29.
 */
public class PlatformCenterFragment extends CommonFragment {
    @Bind(R.id.lv_vip_purchase)
    NestedListView lvVipListView;
    @Bind(R.id.lv_vip_compare)
    NestedListView lvVipNormalListView;
    @Bind(R.id.lv_chain_vip_grow)
    NestedListView lvVipChainListView;
    @Bind(R.id.condition)
    ConditionLayout conditionLayout;
    @Bind(R.id.pie1)
    PieChart pieChart1;
    @Bind(R.id.pie2)
    PieChart pieChart2;
    @Bind(R.id.pie3)
    PieChart pieChart3;
    VipPurchaseListAdapter vipPurchaseListAdapter;
    VipNormalCustomerListAdapter vipNormalCustomerListAdapter;
    ChainVipListAdapter chainVipListAdapter;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_platform, null);
        hideTitleBar();
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
        vipPurchaseListAdapter = new VipPurchaseListAdapter(getActivity());
        lvVipListView.setAdapter(vipPurchaseListAdapter);
        vipNormalCustomerListAdapter = new VipNormalCustomerListAdapter(getActivity());
        lvVipNormalListView.setAdapter(vipNormalCustomerListAdapter);
        chainVipListAdapter = new ChainVipListAdapter(getActivity());
        lvVipChainListView.setAdapter(chainVipListAdapter);
        conditionLayout.hideGoods(false);
        PieChartTool pieChartTool = new PieChartTool(pieChart1);
    }

    private void getListData() {
        String url = "http://101.200.131.198:8087/gw?cmd=appTopicIndex&class=10&item=30&level=1";
        OkHttpUtils.post(url)//
                .tag(this)//
                .execute(new DialogCallback<SaleAnaModel>(getContext(),SaleAnaModel.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, SaleAnaModel c, Request request, @Nullable Response response) {
//                        Log.i("TAG",c.tableTitle);
//                        String[] titles = c.tableTitle.split("\\|");
//                        String[] desc = c.tableTitleDesc.split("\\|");
//                        titleLayout.initView(titles,desc);
//                        salesFinishListAdapter.setItemCloums(titles.length);
//                        salesFinishListAdapter.setList(c.tableData);
//                        pieChartTool.setData(c.chartData);
//                        pieChartTool.setPiechart();

                    }

                });
    }

}
