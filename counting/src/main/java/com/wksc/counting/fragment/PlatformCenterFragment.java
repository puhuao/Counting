package com.wksc.counting.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wksc.counting.R;
import com.wksc.counting.adapter.ChainVipListAdapter;
import com.wksc.counting.adapter.VipNormalCustomerListAdapter;
import com.wksc.counting.adapter.VipPurchaseListAdapter;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.GoodsPopupwindow;
import com.wksc.counting.popwindows.IndexPopupwindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.counting.popwindows.TimePopupwindow;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class PlatformCenterFragment extends CommonFragment implements View.OnClickListener {
    @Bind(R.id.lv_vip_purchase)
    NestedListView lvVipListView;
    @Bind(R.id.lv_vip_compare)
    NestedListView lvVipNormalListView;
    @Bind(R.id.lv_chain_vip_grow)
    NestedListView lvVipChainListView;
    @Bind(R.id.goods)
    TextView goods;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.area)
    TextView area;
    @Bind(R.id.pie1)
    PieChart pieChart1;
    @Bind(R.id.pie2)
    PieChart pieChart2;
    @Bind(R.id.pie3)
    PieChart pieChart3;
    VipPurchaseListAdapter vipPurchaseListAdapter;
    VipNormalCustomerListAdapter vipNormalCustomerListAdapter;
    ChainVipListAdapter chainVipListAdapter;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_platform, null);
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
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        vipPurchaseListAdapter = new VipPurchaseListAdapter(getActivity());
        lvVipListView.setAdapter(vipPurchaseListAdapter);
        vipNormalCustomerListAdapter = new VipNormalCustomerListAdapter(getActivity());
        lvVipNormalListView.setAdapter(vipNormalCustomerListAdapter);
        chainVipListAdapter = new ChainVipListAdapter(getActivity());
        lvVipChainListView.setAdapter(chainVipListAdapter);
        setPiechart();
    }

    private void setPiechart() {
        pieChart1.setUsePercentValues(true);
        pieChart1.setDescription("");
        pieChart1.setExtraOffsets(5, 10, 5, 5);

        pieChart1.setDragDecelerationFrictionCoef(0.95f);


        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setHoleColor(Color.WHITE);

        pieChart1.setTransparentCircleColor(Color.WHITE);
        pieChart1.setTransparentCircleAlpha(110);

        pieChart1.setHoleRadius(58f);
        pieChart1.setTransparentCircleRadius(61f);

        pieChart1.setDrawCenterText(true);

        pieChart1.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart1.setRotationEnabled(true);
        pieChart1.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener

        setData(3, 100);

        pieChart1.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = pieChart1.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add("33");

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart1.setData(data);

        // undo all highlights
        pieChart1.highlightValues(null);

        pieChart1.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.area:
                AreaPopupwindow areaPopupwindow = new AreaPopupwindow(getActivity());
                areaPopupwindow.showPopupwindow(v);
                break;
            case R.id.goods:
                GoodsPopupwindow goodsPopupwindow = new GoodsPopupwindow(getActivity());
                goodsPopupwindow.showPopupwindow(v);
                break;
            case R.id.time:
                TimePopupwindow timePopupwindow = new TimePopupwindow(getActivity());
                timePopupwindow.showPopupwindow(v);
                break;
            case R.id.channel:
                SupplyChianPopupwindow supplyChianPopupwindow = new SupplyChianPopupwindow(getActivity());
                supplyChianPopupwindow.showPopupwindow(v);
                break;
            case R.id.index:
                IndexPopupwindow indexPopupwindow = new IndexPopupwindow(getActivity());
                indexPopupwindow.showPopupwindow(v);
                break;

        }
    }
}
