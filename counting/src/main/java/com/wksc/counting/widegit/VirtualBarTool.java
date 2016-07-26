package com.wksc.counting.widegit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wksc.counting.model.SaleAnaModel.PeiModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/26.
 */
public class VirtualBarTool {
    private Context mContext;
    BarChart mChart;
    private Typeface mTf;
    private PeiModel peiModel;

    public VirtualBarTool(BarChart barChart, Context context) {
        this.mChart = barChart;
        this.mContext = context;
        initBar();
    }

    private void initBar() {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");
        mChart.setTouchEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        mTf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
//        setData();
    }

    public void setData(PeiModel chartData) {
        this.peiModel = chartData;
        setData();
    }
    private void setData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        String[] values = peiModel.chartValue1.split("\\|");
        String[] des = peiModel.chartPoint1.split("\\|");
        if (!values[0].equals("")&&!des[0].equals("")){
            for (int i = 0; i < values.length; i++) {
                yVals1.add(new BarEntry(Float.valueOf(values[i]),i));
            }
            ArrayList<String> xVals = new ArrayList<>();

            for (int i = 0; i < des.length; i++)
                xVals.add(des[i]);

            BarDataSet set1 = new BarDataSet(yVals1, peiModel.chartTitle1);
            set1.setBarSpacePercent(35f);
//            set1.setColors(ColorTemplate.COLOR_PRIMERY);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTf);

            ArrayList<Integer> colors = new ArrayList<>();
            for (int c : ColorTemplate.COLOR_PRIMERY)
                colors.add(c);
            set1.setColors(colors);
//            data.setValueFormatter(new PercentFormatter());
            data.setValueTextColor(Color.BLACK);
            mChart.setData(data);
            mChart.highlightValues(null);
            mChart.invalidate();
        }

    }

}
