package com.wksc.counting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.FragmentDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.activity.TogleActivity;
import com.wksc.counting.adapter.SalesFinishListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.SaleGoalAnaEvent;
import com.wksc.counting.model.SaleAnaModel.SaleAnaModel;
import com.wksc.counting.tools.Params2;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.ConditionLayout2;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.PieChartTool;
import com.wksc.counting.widegit.TableTitleLayout;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    ConditionLayout2 conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titleLayout;
    @Bind(R.id.pie)
    PieChart pieChart;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    SalesFinishListAdapter salesFinishListAdapter;
    PieChartTool pieChartTool;
    private IConfig config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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

    @Override
    public void onResume() {
        super.onResume();
        Log.e("tag","onrums");
    }

    private void initView() {
        salesFinishListAdapter = new SalesFinishListAdapter(getActivity());
        lvSalesAnalysis.setAdapter(salesFinishListAdapter);
        pieChartTool = new PieChartTool(pieChart);
        conditionLayout.init(0);
        conditionLayout.hideDay();
        conditionLayout.initViewByParam();
        conditionLayout.initParams();
        conditionLayout.hideCity();
        conditionLayout.hideCounty();
        conditionLayout.hideStores();
        if (FragmentDataUtil.saleAnaModel==null){
            extraParam = conditionLayout.getAllConditions();
            getListData();
        }
        conditionLayout.hideBothGoodsAndChannel(true);

        conditionLayout.setConditionSelect(new ConditionLayout2.OnConditionSelect() {
            @Override
            public void postParams() {
                extraParam = conditionLayout.getAllConditions();
                getListData();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListData();
            }
        });
        lvSalesAnalysis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("code", salesFinishListAdapter.getList().get(position).code);
                bundle.putString("extra", extraParam);
                bundle.putInt("flag",1);
                Intent intent = new Intent(getActivity(),TogleActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        if (FragmentDataUtil.saleAnaModel!=null){
            Log.i("TAG", FragmentDataUtil.saleAnaModel.tableTitle);
            String[] titles = FragmentDataUtil.saleAnaModel.tableTitle.split("\\|");
            String[] desc = FragmentDataUtil.saleAnaModel.tableTitleDesc.split("\\|");
            titleLayout.clearAllViews();
            titleLayout.initView(titles, desc);
            salesFinishListAdapter.setItemCloums(titles.length);
            salesFinishListAdapter.setList(FragmentDataUtil.saleAnaModel.tableData);
            pieChartTool.setData(FragmentDataUtil.saleAnaModel.chartData);
            pieChartTool.setPiechart();
        }
    }

    private void getListData() {
        StringBuilder sb = new StringBuilder(Urls.TOPICINDEX);
        config = BaseApplication.getInstance().getCurrentConfig();
        UrlUtils.getInstance().addSession(sb,config).praseToUrl(sb,"class","10")
                .praseToUrl(sb,"level","1").praseToUrl(sb,"item","10");
        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<SaleAnaModel>(getContext(), SaleAnaModel.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, SaleAnaModel c, Request request, @Nullable Response response) {
//                       if (c.tableData.size()>0){
                        if (refreshLayout.isRefreshing()){
                            refreshLayout.setRefreshing(false);
                        }
                        FragmentDataUtil.saleAnaModel =c;
                           Log.i("TAG", FragmentDataUtil.saleAnaModel.tableTitle);
                           String[] titles = FragmentDataUtil.saleAnaModel.tableTitle.split("\\|");
                           String[] desc = FragmentDataUtil.saleAnaModel.tableTitleDesc.split("\\|");
                           titleLayout.clearAllViews();
                           titleLayout.initView(titles, desc);
                           salesFinishListAdapter.setItemCloums(titles.length);
                           salesFinishListAdapter.setList(FragmentDataUtil.saleAnaModel.tableData);
                           pieChartTool.setData(FragmentDataUtil.saleAnaModel.chartData);
                           pieChartTool.setPiechart();

                    }

                });
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void changeChart(SaleGoalAnaEvent event) {

        if (FragmentDataUtil.saleAnaModel==null){
//            conditionLayout.initViewByParam();
//            if (Params2.extraParams!=null){
//                extraParam = Params2.extraParams;
//            }else{
//                extraParam = Params2.extraParams;
                extraParam = conditionLayout.getAllConditions();
//            }
            getListData();
        }
    }
}
