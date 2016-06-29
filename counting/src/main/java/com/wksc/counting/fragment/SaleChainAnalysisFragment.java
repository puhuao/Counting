package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesSupplyListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.saleChannelModel.SaleChannelModel;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.PieChartTool;
import com.wksc.counting.widegit.TableTitleLayout;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class SaleChainAnalysisFragment extends CommonFragment {
    @Bind(R.id.supply_analysis)
    NestedListView lvSupplyAnalysis;
    @Bind(R.id.condition)
    ConditionLayout conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titleLayout;
    @Bind(R.id.pie)
    PieChart pieChart;
    SalesSupplyListAdapter salesSupplyListAdapter;
    private PieChartTool pieChartTool;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale_chain_analysis, null);
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
        salesSupplyListAdapter = new SalesSupplyListAdapter(getActivity());
        lvSupplyAnalysis.setAdapter(salesSupplyListAdapter);
        conditionLayout.hideGoods(false);
        pieChartTool = new PieChartTool(pieChart);
        getListData();
    }

    private void getListData() {
        String url = "http://101.200.131.198:8087/gw?cmd=appTopicIndex&class=10&item=20&level=1";
        OkHttpUtils.post(url)//
                .tag(this)//
                .execute(new DialogCallback<SaleChannelModel>(getContext(), SaleChannelModel.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, SaleChannelModel c, Request request, @Nullable Response response) {
//                        Log.i("TAG",c.tableTitle);

                        String[] titles = c.table.tableTitle.split("\\|");
                        String[] desc = c.table.tableTitleDesc.split("\\|");
                        titleLayout.initView(titles, desc);
                        salesSupplyListAdapter.setItemCloums(titles.length);
                        salesSupplyListAdapter.setList(c.tableData);

                        StringBuilder sb1 = new StringBuilder();
                        StringBuilder sb2 = new StringBuilder();
                        for (int i = 0; i < c.tableData.size(); i++) {
                            String[] array = c.tableData.get(i).newValue.split("\\|");
                            sb1.append(array[1]).append("|");
                            sb2.append(array[0]).append("|");
                        }

                        if (sb1.length() > 0) {
                            sb1.deleteCharAt(sb1.length() - 1);
                            sb2.deleteCharAt(sb2.length() - 1);
                        }
                        PeiModel peiModel = new PeiModel();
                        peiModel.chartPoint1 = sb2.toString();
                        peiModel.chartValue1 = sb1.toString();
                        peiModel.chartTitle1 = c.table.title;
                        pieChartTool.setData(peiModel);
                        pieChartTool.setPiechart();
                    }
                });
    }

}
