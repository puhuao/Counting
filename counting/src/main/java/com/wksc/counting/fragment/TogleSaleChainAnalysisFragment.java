package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Contorner.Condition;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesSupplyListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.SaleChannelAnaEvent;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.saleChannelModel.SaleChannelModel;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.ConditionLayout3;
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
public class TogleSaleChainAnalysisFragment extends CommonFragment {
    @Bind(R.id.supply_analysis)
    NestedListView lvSupplyAnalysis;
    @Bind(R.id.condition)
    ConditionLayout3 conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titleLayout;
    @Bind(R.id.pie)
    PieChart pieChart;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    SalesSupplyListAdapter salesSupplyListAdapter;
    private PieChartTool pieChartTool;
    private IConfig config;
    private String code;
    private Condition condition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale_chain_analysis, null);
//        showRightButton();
//        getRightButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getContext().pushFragmentToBackStack(MoreFragment.class,"");
//            }
//        });
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        Bundle bundle = (Bundle) getmDataIn();
        code = bundle.getString("code");
        extraParam = bundle.getString("extra");
        condition = (Condition) bundle.getSerializable("condition");
        initView();
        return v;
    }

    private void initView() {
        salesSupplyListAdapter = new SalesSupplyListAdapter(getActivity());
        lvSupplyAnalysis.setAdapter(salesSupplyListAdapter);
        conditionLayout.init(1);
        conditionLayout.initParams();
        conditionLayout.hideGoods(false);
        conditionLayout.initViewByParam();
        conditionLayout.setcondition(condition);
        pieChartTool = new PieChartTool(pieChart);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListData();
            }
        });
        conditionLayout.setConditionSelect(new ConditionLayout3.OnConditionSelect() {
            @Override
            public void postParams() {
                getListData();
            }
        });
        getListData();
    }

    private void getListData() {
        if (flag > 0) {

            extraParam = conditionLayout.getAllConditions();
        }

        StringBuilder sb = new StringBuilder(Urls.TOPICINDEX);
        config = BaseApplication.getInstance().getCurrentConfig();
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "class", "10")
                .praseToUrl(sb, "level", "2").praseToUrl(sb, "item", "20")
                .praseToUrl(sb, "code", code);
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
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        flag++;
                        if (c.tableData.size() > 0) {
                            titleLayout.clearAllViews();
                            String[] titles = c.table.tableTitle.split("\\|");
                            String[] desc = c.table.tableTitleDesc.split("\\|");
                            titleLayout.initView(titles, desc);
                            salesSupplyListAdapter.setItemCloums(titles.length);
                            salesSupplyListAdapter.setList(c.tableData);


                            PeiModel peiModel = new PeiModel();
                            peiModel.chartPoint1 = c.table.chartTitle;
                            peiModel.chartValue1 = c.table.chartData;
                            peiModel.chartTitle1 = c.table.title;
                            pieChartTool.setData(peiModel);
                            pieChartTool.setPiechart();
                        }

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
        getListData();
    }
}
