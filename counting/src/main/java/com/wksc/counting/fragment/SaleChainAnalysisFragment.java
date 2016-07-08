package com.wksc.counting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.FragmentDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.activity.TogleActivity;
import com.wksc.counting.adapter.SalesSupplyListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.SaleChannelAnaEvent;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.saleChannelModel.SaleChannelModel;
import com.wksc.counting.tools.UrlUtils;
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
public class SaleChainAnalysisFragment extends CommonFragment {
    @Bind(R.id.supply_analysis)
    NestedListView lvSupplyAnalysis;
    @Bind(R.id.condition)
    ConditionLayout2 conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titleLayout;
    @Bind(R.id.pie)
    PieChart pieChart;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    SalesSupplyListAdapter salesSupplyListAdapter;
    private PieChartTool pieChartTool;
    private IConfig config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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
        conditionLayout.init(1);
        conditionLayout.initViewByParam();
//        conditionLayout.initParams();
        pieChartTool = new PieChartTool(pieChart);
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
        lvSupplyAnalysis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("code", salesSupplyListAdapter.getList().get(position).code);
                bundle.putString("extra", extraParam);
                bundle.putInt("flag",2);
//                getContext().pushFragmentToBackStack(ToglSaleGoalAnalysisFragment.class, bundle);
                Intent intent = new Intent(getActivity(),TogleActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
//                getContext().pushFragmentToBackStack(TogleSaleChainAnalysisFragment.class, bundle);
            }
        });
        if (FragmentDataUtil.saleChannelModel!=null){
            titleLayout.clearAllViews();
            String[] titles = FragmentDataUtil.saleChannelModel.table.tableTitle.split("\\|");
            String[] desc = FragmentDataUtil.saleChannelModel.table.tableTitleDesc.split("\\|");
            titleLayout.initView(titles, desc);
            salesSupplyListAdapter.setItemCloums(titles.length);
            salesSupplyListAdapter.setList(FragmentDataUtil.saleChannelModel.tableData);

            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < FragmentDataUtil.saleChannelModel.tableData.size(); i++) {
                String[] array = FragmentDataUtil.saleChannelModel.tableData.get(i).newValue.split("\\|");
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
            peiModel.chartTitle1 = FragmentDataUtil.saleChannelModel.table.title;
            pieChartTool.setData(peiModel);
            pieChartTool.setPiechart();
        }
    }

    private void getListData() {
        StringBuilder sb = new StringBuilder(Urls.TOPICINDEX);
        config = BaseApplication.getInstance().getCurrentConfig();
        UrlUtils.getInstance().addSession(sb,config).praseToUrl(sb,"class","10")
                .praseToUrl(sb,"level","1").praseToUrl(sb,"item","20");
        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<SaleChannelModel>(getContext(), SaleChannelModel.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, SaleChannelModel c, Request request, @Nullable Response response) {
//                        Log.i("TAG",c.tableTitle);
//                        if (c.tableData.size() > 0) {
                        if (refreshLayout.isRefreshing())
                            refreshLayout.setRefreshing(false);
                        FragmentDataUtil.saleChannelModel = c;
                            titleLayout.clearAllViews();
                            String[] titles = FragmentDataUtil.saleChannelModel.table.tableTitle.split("\\|");
                            String[] desc = FragmentDataUtil.saleChannelModel.table.tableTitleDesc.split("\\|");
                            titleLayout.initView(titles, desc);
                            salesSupplyListAdapter.setItemCloums(titles.length);
                            salesSupplyListAdapter.setList(FragmentDataUtil.saleChannelModel.tableData);

                            StringBuilder sb1 = new StringBuilder();
                            StringBuilder sb2 = new StringBuilder();
                            for (int i = 0; i < FragmentDataUtil.saleChannelModel.tableData.size(); i++) {
                                String[] array = FragmentDataUtil.saleChannelModel.tableData.get(i).newValue.split("\\|");
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
                            peiModel.chartTitle1 = FragmentDataUtil.saleChannelModel.table.title;
                            pieChartTool.setData(peiModel);
                            pieChartTool.setPiechart();
//                        }

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
    public void changeChart(SaleChannelAnaEvent event) {
        if (FragmentDataUtil.saleChannelModel==null){
//            if (Params2.extraParams!=null){
//                conditionLayout.initViewByParam();
//                extraParam = Params2.extraParams;
//            }else{
//                extraParam = Params2.extraParams;
                extraParam = conditionLayout.getAllConditions();
//            }
            getListData();
        }

    }
}
