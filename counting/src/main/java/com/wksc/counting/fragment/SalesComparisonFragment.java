package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesCompareListAdapter;
import com.wksc.counting.event.ChangeChartEvent;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.counting.popwindows.TimePopupwindow;
import com.wksc.counting.widegit.BarChartTool;
import com.wksc.counting.widegit.LineChartTool;
import com.wksc.counting.widegit.MarqueeText;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesComparisonFragment extends CommonFragment {
    @Bind(R.id.list_view)
    NestedListView list;
    @Bind(R.id.chart)
    LineChart mChart;
    @Bind(R.id.area)
    LinearLayout area;
    @Bind(R.id.all_channel)
    LinearLayout channel;
    @Bind(R.id.time)
    LinearLayout time;
    @Bind(R.id.chart1)
    LinearLayout chart1;
    @Bind(R.id.bar_chart_old)
    HorizontalBarChart barChartOld;
    @Bind(R.id.bar_chart_new)
    HorizontalBarChart barChartNew;
    @Bind(R.id.tv_area)
    MarqueeText tvArea;

    SalesCompareListAdapter adapter;

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
        BarChartTool oldBarTool = new BarChartTool(barChartOld, getContext());
        oldBarTool.initBar();
        BarChartTool newBarTool = new BarChartTool(barChartNew, getContext());
        newBarTool.initBar();
        LineChartTool lineChartTool = new LineChartTool(mChart, getContext());
        lineChartTool.initLinChart();
        initView();
        return v;
    }

    private Boolean down = true;

    private void initView() {

        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreaPopupwindow areaPopupwindow = new AreaPopupwindow(getActivity());
                areaPopupwindow.bindTextView(tvArea);
                areaPopupwindow.showPopupwindow(v);
            }
        });
        channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplyChianPopupwindow supplyChianPopupwindow = new SupplyChianPopupwindow(getActivity());
                supplyChianPopupwindow.showPopupwindow(v);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePopupwindow timePopupwindow = new TimePopupwindow(getActivity());
                timePopupwindow.showPopupwindow(v);
            }
        });
        adapter = new SalesCompareListAdapter(getActivity());
        list.setAdapter(adapter);

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
