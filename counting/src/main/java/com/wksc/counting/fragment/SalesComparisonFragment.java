package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesCompareListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.event.ChangeChartEvent;
import com.wksc.counting.model.coreDetail.CoreDetail;
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
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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
    @Bind(R.id.titles)
    LinearLayout titles;

    SalesCompareListAdapter adapter;
    CoreDetail detail;
    BarChartTool oldBarTool;
    BarChartTool newBarTool;
    LineChartTool lineChartTool;
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
        initView();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        getData();

    }

    private void getData() {
        String url = "http://101.200.131.198:8087/gw?cmd=appCoreDetails";
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

                        for (int i =1 ; i<titles.getChildCount();i++){
                            TextView textView = (TextView) titles.getChildAt(i);
                            textView.setText(tableTitles[i-1]);
                        }
                        adapter.TransData(detail.tableData);

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
