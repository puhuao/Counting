package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.BaseDataUtil2;
import com.wksc.counting.Basedata.FragmentDataUtil;
import com.wksc.counting.Contorner.Condition;
import com.wksc.counting.R;
import com.wksc.counting.adapter.GoodsSalesAnalysisListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.GoodsAnaEvent;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.goodsSaleAnaModle.GoodSaleModle;
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
public class GoodsAnalysisFragment extends CommonFragment {
    @Bind(R.id.sales_analysis)
    NestedListView lvSalesAnalysis;
    @Bind(R.id.condition)
    ConditionLayout3 conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titleLayout;
    @Bind(R.id.pie)
    PieChart pieChart;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    PieChartTool pieChartTool;
    GoodsSalesAnalysisListAdapter goodsSalesAnalysisListAdapter;
    private IConfig config;
    Condition condition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goods_analysis, null);
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
        goodsSalesAnalysisListAdapter = new GoodsSalesAnalysisListAdapter(getActivity());
        lvSalesAnalysis.setAdapter(goodsSalesAnalysisListAdapter);
        pieChartTool = new PieChartTool(pieChart);
        condition = new Condition(BaseDataUtil2.goodsAnaSet);
        condition.init();
        condition.goodsClassFirst = BaseDataUtil2.goodsClassFirstGoods;
        conditionLayout.hideGoods(false);
        conditionLayout.init(3);
        conditionLayout.initViewByParam();
        conditionLayout.setcondition(condition);
//        conditionLayout.initParams();
        conditionLayout.setConditionSelect(new ConditionLayout3.OnConditionSelect() {
            @Override
            public void postParams() {
                conditionLayout.initViewByParam();
                extraParam = conditionLayout.getAllConditions();
                getListData();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                conditionLayout.initViewByParam();
                extraParam = conditionLayout.getAllConditions();
                getListData();
            }
        });
        lvSalesAnalysis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle bundle = new Bundle();
//                bundle.putString("code", goodsSalesAnalysisListAdapter.getList().get(position).code);
//                bundle.putString("extra", extraParam);
//                bundle.putInt("flag", 3);
//                bundle.putSerializable("condition", condition);
//                String[] array = goodsSalesAnalysisListAdapter.getList().get(position).newValue.split("\\|");
//                bundle.putString("titel", array[0]);
//                Intent intent = new Intent(getActivity(), TogleActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });
        if (FragmentDataUtil.goodSaleModle != null) {
            titleLayout.clearAllViews();
            String[] titles = FragmentDataUtil.goodSaleModle.table.tableTitle.split("\\|");
            String[] desc = FragmentDataUtil.goodSaleModle.table.tableTitleDesc.split("\\|");
            titleLayout.initView(titles, desc);
            goodsSalesAnalysisListAdapter.setItemCloums(titles.length);
            goodsSalesAnalysisListAdapter.setList(FragmentDataUtil.goodSaleModle.tableData);

            PeiModel peiModel = new PeiModel();
            peiModel.chartPoint1 = FragmentDataUtil.goodSaleModle.table.chartTitle;
            peiModel.chartValue1 = FragmentDataUtil.goodSaleModle.table.chartData;
            peiModel.chartTitle1 = FragmentDataUtil.goodSaleModle.table.title;
            pieChartTool.setData(peiModel);
            pieChartTool.setPiechart();
        }
    }

    private void getListData() {

        StringBuilder sb = new StringBuilder(Urls.TOPICINDEX);
        config = BaseApplication.getInstance().getCurrentConfig();
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "class", "20")
                .praseToUrl(sb, "level", "1").praseToUrl(sb, "item", "40");
        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<GoodSaleModle>(getContext(), GoodSaleModle.class, refreshLayout) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, GoodSaleModle c, Request request, @Nullable Response response) {
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        FragmentDataUtil.goodSaleModle = c;
                        titleLayout.clearAllViews();
                        String[] titles = c.table.tableTitle.split("\\|");
                        String[] desc = c.table.tableTitleDesc.split("\\|");
                        titleLayout.initView(titles, desc);
                        goodsSalesAnalysisListAdapter.setItemCloums(titles.length);
                        goodsSalesAnalysisListAdapter.setList(c.tableData);

                        PeiModel peiModel = new PeiModel();
                        peiModel.chartPoint1 = c.table.chartTitle;
                        peiModel.chartValue1 = c.table.chartData;
                        peiModel.chartTitle1 = c.table.title;
                        pieChartTool.setData(peiModel);
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
    public void changeChart(GoodsAnaEvent event) {
        if (FragmentDataUtil.goodSaleModle == null) {

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
