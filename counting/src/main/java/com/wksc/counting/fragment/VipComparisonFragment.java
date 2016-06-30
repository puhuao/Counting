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
import com.github.mikephil.charting.charts.LineChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesCompareListAdapter;
import com.wksc.counting.adapter.VipCompareAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.event.ChangeChartEvent;
import com.wksc.counting.model.coreDetail.CoreDetail;
import com.wksc.counting.widegit.BarChartTool;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.LineChartTool;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.TableTitleLayout;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.util.StringUtils;

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
    @Bind(R.id.chart)
    LineChart mChart;
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
    LineChartTool lineChartTool;
    private String param;
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

        lineChartTool = new LineChartTool(mChart, getContext());
        lineChartTool.initLinChart();
        Bundle bundle = getArguments();
        param = bundle.getString("param");
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
        adapter = new VipCompareAdapter(getActivity());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("param",param);
                bundle.putString("provice",adapter.getList().get(position).code);
                getContext().pushFragmentToBackStack(TogleVipComparisonFragment.class, bundle);
            }
        });
        getData(null);
    }

    private void getData(String provice) {
//        String url = "http://101.200.131.198:8087/gw?cmd=appCoreDetails";
        String url;
        if (StringUtils.isBlank(provice)){
            url = "http://10.1.100.6/ea/gw?cmd=appCoreDetails&item="+param+
                    "&level=1&year=2016&month=06";
        }else{
            url = "http://10.1.100.6/ea/gw?cmd=appCoreDetails&item="+param+
                    "&level=2&year=2016&month=06&province="+provice;
        }
        OkHttpUtils.post(url)//
                .tag(this)//
                .execute(new DialogCallback<CoreDetail>(getContext(),CoreDetail.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, CoreDetail c, Request request, @Nullable Response response) {
                            Log.i("TAG",c.toString());
                        detail = c;
                        oldBarTool.initBar(c.CoreChart1);
                        newBarTool.initBar(c.CoreChart2);
                        String[] tableTitles = detail.tableTitle.split("\\|");
                        final String[] titleDesc = detail.tableTitleDesc.split("\\|");
                        titles.initView("地区");
                        titles.initView(tableTitles,
                                titleDesc);
//                        adapter.TransData(detail.tableData);
                        adapter.setList(c.tableData);
                    }

                });
    }

    @Subscribe
    public void changeChart(ChangeChartEvent event) {
        if (!mChart.isShown()) {
            chart1.setVisibility(View.GONE);
            mChart.setVisibility(View.VISIBLE);
        } else {
            chart1.setVisibility(View.VISIBLE);
            mChart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
