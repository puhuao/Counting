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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wksc.counting.R;
import com.wksc.counting.model.coreDetail.BarChartModel;

import java.util.ArrayList;

/**
 * Created by puhua on 2016/6/17.
 *
 * @
 */
public class BarChartTool {
    private  HorizontalBarChart horizontalBarChart;
    private  Typeface tf;
    private  Context mContext;

    public BarChartTool(HorizontalBarChart barChart,Context context){
        this.horizontalBarChart = barChart;
        this.mContext = context;
    }

    public  void initBar(BarChartModel coreChart){

        horizontalBarChart.setDrawBarShadow(false);

        horizontalBarChart.setDrawValueAboveBar(true);

        horizontalBarChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        horizontalBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        horizontalBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        horizontalBarChart.setDrawGridBackground(false);
        horizontalBarChart.setClickable(false);

        // mChart.setDrawYLabels(false);

        tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");

        XAxis xl = horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);
        xl.setSpaceBetweenLabels(10);

        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
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

        setData(coreChart);

        Legend l = horizontalBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(10f);
        horizontalBarChart.invalidate();
    }

    private  void setData(BarChartModel coreChart) {

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add(coreChart.chartPoint2);
        xVals.add(coreChart.chartPoint1);
        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(Float.valueOf(coreChart.chartValue2), 0));
        yValues.add(new BarEntry(Float.valueOf(coreChart.chartValue1), 1));

        BarDataSet set = new BarDataSet(yValues, coreChart.chartTitle1);
        set.setBarSpacePercent(50f);
        int color1 = Color.parseColor("#70AD47");
        int color2 = Color.parseColor("#BE364B");
        int[] colors = new int[]{color2,color1};
        set.setColors(colors);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(6f);
        data.setValueTypeface(tf);

        horizontalBarChart.setData(data);
    }
}
