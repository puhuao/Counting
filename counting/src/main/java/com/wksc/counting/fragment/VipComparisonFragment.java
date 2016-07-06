package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.FragmentDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.adapter.VipCompareAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.VipComparisonLoadDataEvent;
import com.wksc.counting.model.coreDetail.CoreDetail;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.BarChartTool;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.NestedListView;
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
 * Created by Administrator on 2016/5/29.
 */
public class VipComparisonFragment extends CommonFragment {
    @Bind(R.id.list_view)
    NestedListView list;
    //    @Bind(R.id.chart)
//    LineChart mChart;
    @Bind(R.id.chart1)
    LinearLayout chart1;
    @Bind(R.id.bar_chart_old)
    HorizontalBarChart barChartOld;
    @Bind(R.id.bar_chart_new)
    HorizontalBarChart barChartNew;
    @Bind(R.id.condition)
    ConditionLayout conditionLayout;
    @Bind(R.id.titles)
    TableTitleLayout titles;

    VipCompareAdapter adapter;
    CoreDetail detail;
    BarChartTool oldBarTool;
    BarChartTool newBarTool;
    //    LineChartTool lineChartTool;
    private String param;
    private IConfig config;
    private boolean isFirstShow;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sales_comparison, null);
        hideTitleBar();
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        oldBarTool = new BarChartTool(barChartOld, getContext());

        newBarTool = new BarChartTool(barChartNew, getContext());

//        lineChartTool = new LineChartTool(mChart, getContext());
//        lineChartTool.initLinChart();
        bundle = getArguments();
        param = bundle.getString("param");
        isFirstShow = bundle.getBoolean("isFirstShow");
        initView();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private Boolean down = true;

    private void initView() {
        conditionLayout.hideGoods(true);
        conditionLayout.initViewByParam();
        conditionLayout.setConditionSelect(new ConditionLayout.OnConditionSelect() {
            @Override
            public void postParams() {

                getData();
            }
        });
        adapter = new VipCompareAdapter(getActivity());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("param", param);
                bundle.putString("provice", adapter.getList().get(position).code);
                bundle.putString("extra", extraParam);
                getContext().pushFragmentToBackStack(TogleVipComparisonFragment.class, bundle);
            }
        });
        if (isFirstShow) {

            getData();
        }
        if (FragmentDataUtil.map.get("key" + param).tableData != null) {
            detail = FragmentDataUtil.map.get("key" + param);
            Log.i("TAG", detail.toString());
            titles.clearAllViews();
            oldBarTool.setData(detail.CoreChart1);
            newBarTool.setData(detail.CoreChart2);
            String[] tableTitles = detail.tableTitle.split("\\|");
            final String[] titleDesc = detail.tableTitleDesc.split("\\|");
            titles.initView("地区");
            titles.initView(tableTitles,
                    titleDesc);
//                        adapter.TransData(detail.tableData);
            adapter.setItemCloums(tableTitles.length + 1);
            adapter.setList(detail.tableData);
        }

    }

    private void getData() {
        if (isFirstShow) {
            extraParam = bundle.getString("extraParam");
        } else {
            extraParam = conditionLayout.getAllConditions();
        }
        StringBuilder sb = new StringBuilder(Urls.COREDETAIL);
        config = BaseApplication.getInstance().getCurrentConfig();
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "item", param)
                .praseToUrl(sb, "level", "1");/*.praseToUrl(sb,"year","2016").praseToUrl(sb,"month","06");*/
        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<CoreDetail>(getContext(), CoreDetail.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, CoreDetail c, Request request, @Nullable Response response) {

                        if (c.tableData.size() > 0) {
                            FragmentDataUtil.map.put("key" + param, c);
                            Log.i("TAG", c.toString());
                            titles.clearAllViews();
                            detail = c;
                            oldBarTool.setData(c.CoreChart1);
                            newBarTool.setData(c.CoreChart2);
                            String[] tableTitles = detail.tableTitle.split("\\|");
                            final String[] titleDesc = detail.tableTitleDesc.split("\\|");
                            titles.initView("地区");
                            titles.initView(tableTitles,
                                    titleDesc);
//                        adapter.TransData(detail.tableData);
                            adapter.setItemCloums(tableTitles.length + 1);
                            adapter.setList(c.tableData);
                        }
                    }
                });
    }

//    @Subscribe
//    public void changeChart(ChangeChartEvent event) {
//        if (!mChart.isShown()) {
//            chart1.setVisibility(View.GONE);
//            mChart.setVisibility(View.VISIBLE);
//        } else {
//            chart1.setVisibility(View.VISIBLE);
//            mChart.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void lazyLoad() {
//        getData(null);
    }

    @Subscribe
    public void LoadData(VipComparisonLoadDataEvent event) {
        if (event.item.equals(param))
//            if (FragmentDataUtil.map.get("key" + param).tableData == null)
                getData();
    }


}
