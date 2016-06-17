package com.wksc.counting.widegit;

import android.content.Context;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

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

    public  void initBar(){

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

        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = horizontalBarChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        setData();
//        horizontalBarChart.animateY(2500);

        Legend l = horizontalBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

    }

    private  void setData() {

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("四川");
        xVals.add("江苏");
        xVals.add("云南");
        xVals.add("贵州");
        xVals.add("广东");
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        yValues.add(new BarEntry(new float[]{400, 500}, 0));
        yValues.add(new BarEntry(new float[]{350, 600}, 1));
        yValues.add(new BarEntry(new float[]{350, 650}, 2));
        yValues.add(new BarEntry(new float[]{300, 700}, 3));
        yValues.add(new BarEntry(new float[]{200, 800}, 4));

        BarDataSet set1 = new BarDataSet(yValues, "DataSet 1");

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(tf);

        horizontalBarChart.setData(data);
    }
}
