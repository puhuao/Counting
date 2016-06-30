package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesFinishListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.model.SaleAnaModel.SaleAnaModel;
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
public class SaleGoalAnalysisFragment extends CommonFragment {
    @Bind(R.id.sales_analysis)
    NestedListView lvSalesAnalysis;
    @Bind(R.id.condition)
    ConditionLayout conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titleLayout;
    @Bind(R.id.pie)
    PieChart pieChart;
    SalesFinishListAdapter salesFinishListAdapter;
    PieChartTool pieChartTool;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale_goal_analysis, null);
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
        salesFinishListAdapter = new SalesFinishListAdapter(getActivity());
        lvSalesAnalysis.setAdapter(salesFinishListAdapter);
        pieChartTool = new PieChartTool(pieChart);
        getListData();
        conditionLayout.hideGoods(false);
    }

    private void getListData() {
//        String url = "http://101.200.131.198:8087/gw?cmd=appTopicIndex&class=10&item=10&level=1";
        String url = "http://10.1.100.6/ea/gw?cmd=appTopicIndex&class=10&item=10&level=1";
        OkHttpUtils.post(url)//
                .tag(this)//
                .execute(new DialogCallback<SaleAnaModel>(getContext(), SaleAnaModel.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, SaleAnaModel c, Request request, @Nullable Response response) {
                        Log.i("TAG", c.tableTitle);
                        String[] titles = c.tableTitle.split("\\|");
                        String[] desc = c.tableTitleDesc.split("\\|");
                        titleLayout.initView(titles, desc);
                        salesFinishListAdapter.setItemCloums(titles.length);
                        salesFinishListAdapter.setList(c.tableData);
                        pieChartTool.setData(c.chartData);
                        pieChartTool.setPiechart();

                    }

                });
    }

}
