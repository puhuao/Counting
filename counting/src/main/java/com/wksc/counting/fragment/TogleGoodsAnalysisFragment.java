package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.GoodsSalesAnalysisListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.GoodsAnaEvent;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.goodsSaleAnaModle.GoodSaleModle;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.ConditionLayout;
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
public class TogleGoodsAnalysisFragment extends CommonFragment {
    @Bind(R.id.sales_analysis)
    NestedListView lvSalesAnalysis;
    @Bind(R.id.condition)
    ConditionLayout conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titleLayout;
    @Bind(R.id.pie)
    PieChart pieChart;
    PieChartTool pieChartTool;
    GoodsSalesAnalysisListAdapter goodsSalesAnalysisListAdapter;
    private IConfig config;
    private String code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goods_analysis, null);
//        hideTitleBar();
        showRightButton();
        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().pushFragmentToBackStack(MoreFragment.class,"");
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
        goodsSalesAnalysisListAdapter = new GoodsSalesAnalysisListAdapter(getActivity());
        lvSalesAnalysis.setAdapter(goodsSalesAnalysisListAdapter);
        pieChartTool = new PieChartTool(pieChart);
//        extraParam = "&month=06";
        getListData();
        conditionLayout.hideGoods(false);
        conditionLayout.initViewByParam();
        conditionLayout.setConditionSelect(new ConditionLayout.OnConditionSelect() {
            @Override
            public void postParams() {
                getListData();
            }
        });
    }

    private void getListData() {
        if (flag>0){

            extraParam = conditionLayout.getAllConditions();
        }
        StringBuilder sb = new StringBuilder(Urls.TOPICINDEX);
        config = BaseApplication.getInstance().getCurrentConfig();
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "class", "20")
                .praseToUrl(sb, "level", "2").praseToUrl(sb, "item", "40").praseToUrl(sb, "code", code);
        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<GoodSaleModle>(getContext(), GoodSaleModle.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, GoodSaleModle c, Request request, @Nullable Response response) {
flag++;
//                        String[] titles = c.ftable.tableTitle.split("\\|");
//                        String[] desc = c.table.tableTitleDesc.split("\\|");
//                        titleLayout.clearAllViews();
//                        titleLayout.initView(titles, desc);
//                        goodsSalesAnalysisListAdapter.setItemCloums(titles.length);
//                        goodsSalesAnalysisListAdapter.setList(c.tableData);
////                        pieChartTool.setData(c.chartData);
////                        pieChartTool.setPiechart();
                        if (c.tableData.size() > 0) {
                            titleLayout.clearAllViews();
                            String[] titles = c.table.tableTitle.split("\\|");
                            String[] desc = c.table.tableTitleDesc.split("\\|");
                            titleLayout.initView(titles, desc);
                            goodsSalesAnalysisListAdapter.setItemCloums(titles.length);
                            goodsSalesAnalysisListAdapter.setList(c.tableData);

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
        getListData();
    }
}
