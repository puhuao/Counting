package com.wksc.counting.fragment;

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
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesFinishListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.SaleGoalAnaEvent;
import com.wksc.counting.model.SaleAnaModel.SaleAnaModel;
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
public class ToglSaleGoalAnalysisFragment extends CommonFragment {
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
    private String param;
    private String code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale_goal_analysis, null);
        showRightButton();
        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(MoreFragment.class, "");
            }
        });
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        Bundle bundle = (Bundle) getmDataIn();
        code = bundle.getString("code");
        extraParam = bundle.getString("extra");
        initView();

        return v;
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
        conditionLayout.hideBothGoodsAndChannel(true);
        conditionLayout.setConditionSelect(new ConditionLayout2.OnConditionSelect() {
            @Override
            public void postParams() {
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
//                bundle.putString("param", param);
//                bundle.putString("provice", salesFinishListAdapter.getList().get(position).code);
//                bundle.putString("extra", extraParam);
//                getContext().pushFragmentToBackStack(TogleVipComparisonFragment.class, bundle);
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
                .praseToUrl(sb, "level", "2").praseToUrl(sb, "item", "10").praseToUrl(sb, "code", code);
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
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        Log.i("TAG", c.tableTitle);
                        String[] titles = c.tableTitle.split("\\|");
                        String[] desc = c.tableTitleDesc.split("\\|");
                        titleLayout.clearAllViews();
                        titleLayout.initView(titles, desc);
                        salesFinishListAdapter.setItemCloums(titles.length);
                        salesFinishListAdapter.setList(c.tableData);
                        pieChartTool.setData(c.chartData);
                        pieChartTool.setPiechart();
//                       }
                        flag++;

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
        getListData();
    }
}
