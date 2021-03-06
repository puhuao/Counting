package com.wksc.counting.widegit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.wksc.counting.model.coreDetail.BarChartModel;
import com.wksc.framwork.util.StringUtils;

import java.util.ArrayList;

/**
 * Created by puhua on 2016/6/17.
 *
 * @
 */
public class BarChartTool {
    private HorizontalBarChart horizontalBarChart;
    private Typeface tf;
    private Context mContext;

    public BarChartTool(HorizontalBarChart barChart, Context context) {
        this.horizontalBarChart = barChart;
        this.mContext = context;
        initBar();
    }

    public void initBar() {

        horizontalBarChart.setDrawBarShadow(false);

        horizontalBarChart.setDescription("");

        horizontalBarChart.setMaxVisibleValueCount(60);
        horizontalBarChart.setTouchEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        horizontalBarChart.setPinchZoom(false);

        horizontalBarChart.setDrawGridBackground(false);
        horizontalBarChart.setClickable(false);
        horizontalBarChart.setDrawValueAboveBar(false);
        // mChart.setDrawYLabels(false);

        tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");

        XAxis xl = horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);
        xl.setSpaceBetweenLabels(10);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);
        yl.setTextSize(6f);

        YAxis yr = horizontalBarChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        yr.setTextSize(6f);
        horizontalBarChart.invalidate();
    }

    public void setData(BarChartModel coreChart) {

        ArrayList<String> xVals = new ArrayList<>();
        if (!StringUtils.isBlank(coreChart.chartPoint2))
            xVals.add(coreChart.chartPoint2);
        else {
            xVals.add("");
        }
        if (!StringUtils.isBlank(coreChart.chartPoint1))
            xVals.add(coreChart.chartPoint1);
        else {
            xVals.add("");
        }
        ArrayList<BarEntry> yValues = new ArrayList<>();
        if (!StringUtils.isBlank(coreChart.chartValue2))
            yValues.add(new BarEntry(Float.valueOf(coreChart.chartValue2), 0));
        if (!StringUtils.isBlank(coreChart.chartValue1))
            yValues.add(new BarEntry(Float.valueOf(coreChart.chartValue1), 1));

        BarDataSet set = new BarDataSet(yValues, coreChart.chartTitle1);
        set.setDrawValues(false);
        set.setBarSpacePercent(50f);
        int color1 = Color.parseColor("#75e2ff");
        int color2 = Color.parseColor("#FFBD3F");
        int[] colors = new int[]{color2, color1};
        set.setColors(colors);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(6f);
        data.setValueTypeface(tf);
        if (horizontalBarChart != null) {
            Legend l = horizontalBarChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setFormSize(8f);
            l.setXEntrySpace(10f);
            horizontalBarChart.setData(data);
            horizontalBarChart.invalidate();
        }

    }
}
