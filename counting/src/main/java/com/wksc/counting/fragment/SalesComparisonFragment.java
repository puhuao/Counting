package com.wksc.counting.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesCompareListAdapter;
import com.wksc.counting.event.ChangeChartEvent;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesComparisonFragment extends CommonFragment {
    @Bind(R.id.list_view)
    ListView list;
    @Bind(R.id.chart)
    LineChart mChart;

    @Bind(R.id.area)
    TextView area;
    @Bind(R.id.all_channel)
            TextView channel;

    @Bind(R.id.chart1)
    HorizontalBarChart chart1;
//    @Bind(R.id.title)
//            TextView titleBar;
//    @Bind(R.id.ly_title)
//            RelativeLayout lyTitle;
//    @Bind(R.id.radio_group)
//    RadioGroup radioGroup;

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

        initChart1();
        initView();
        return v;
    }

    private void initChart1() {
//        chart1.setOnChartValueSelectedListener(this);
        chart1.setDrawGridBackground(false);
        chart1.setDescription("");

        // scaling can now only be done on x- and y-axis separately
        chart1.setPinchZoom(false);

        chart1.setDrawBarShadow(false);
        chart1.setDrawValueAboveBar(true);

        chart1.getAxisLeft().setEnabled(false);
        chart1.getAxisRight().setAxisMaxValue(1000f);
        chart1.getAxisRight().setAxisMinValue(100f);
        chart1.getAxisRight().setDrawGridLines(false);
        chart1.getAxisRight().setDrawZeroLine(true);
        chart1.getAxisRight().setLabelCount(7, false);
        chart1.getAxisRight().setValueFormatter(new CustomFormatter());
        chart1.getAxisRight().setTextSize(9f);

        XAxis xAxis = chart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);

        Legend l = chart1.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        setDownToUp();
    }

    private void setDownToUp() {
        // IMPORTANT: When using negative values in stacked bars, always make sure the negative values are in the array first
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        yValues.add(new BarEntry(new float[]{ 400, 500 }, 0));
        yValues.add(new BarEntry(new float[]{ 350, 600 }, 1));
        yValues.add(new BarEntry(new float[]{ 350, 650}, 2));
        yValues.add(new BarEntry(new float[]{ 300, 700 }, 3));
        yValues.add(new BarEntry(new float[]{ 200, 800 }, 4));

        BarDataSet set = new BarDataSet(yValues, "Age Distribution");
        set.setValueFormatter(new CustomFormatter());
        set.setValueTextSize(7f);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setBarSpacePercent(40f);
        set.setColors(new int[] { R.color.white,Color.rgb(124,181,236)});

        String []xVals = new String[]{"广东", "贵州", "云南", "江苏", "四川"};

        BarData data = new BarData(xVals, set);
        chart1.setData(data);
        chart1.invalidate();
    }

    private void setUpToDown(){
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        yValues.add(new BarEntry(new float[]{  200, 800 }, 0));
        yValues.add(new BarEntry(new float[]{ 300, 700 }, 1));
        yValues.add(new BarEntry(new float[]{ 350, 650 }, 2));
        yValues.add(new BarEntry(new float[]{ 350, 600 }, 3));
        yValues.add(new BarEntry(new float[]{ 400, 500 }, 4));

        BarDataSet set = new BarDataSet(yValues, "Age Distribution");
        set.setValueFormatter(new CustomFormatter());
        set.setValueTextSize(7f);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setBarSpacePercent(40f);
        set.setColors(new int[] { R.color.white,Color.rgb(124,181,236)});

        String []xVals = new String[]{"四川", "江苏","云南", "贵州", "广东"};

        BarData data = new BarData(xVals, set);
        chart1.setData(data);
        chart1.invalidate();
    }

    private Boolean down = true;
    private void initView() {
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.sales_number:
//                        titleBar.setText("销售额对比");
//                        break;
//                    case R.id.gross_profit:
//                        titleBar.setText("毛利额对比");
//                        break;
//                    case R.id.gross_margin:
//                        titleBar.setText("毛利率对比");
//                        break;
//                    case R.id.channel:
//                        titleBar.setText("客单数对比");
//                        break;
//                    case R.id.index:
//                        titleBar.setText("客单价对比");
//                        break;
//
//
//                }
//            }
//        });
//        lyTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (down){
//                    setUpToDown();
//                    adapter.setDownToUp();
//                }else {
//                    setDownToUp();
//                    adapter.setList(ComparisonModel.getData());
//                    adapter.notifyDataSetChanged();
//                }
//                down = !down;
//            }
//        });
//


        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreaPopupwindow areaPopupwindow = new AreaPopupwindow(getActivity());
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
//        sort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SortPopupwindow sortPopupwindow =new SortPopupwindow(getActivity());
//                sortPopupwindow.showPopupwindow(v);
//            }
//        });
        adapter = new SalesCompareListAdapter(getActivity());
        list.setAdapter(adapter);
//        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);

        // add data
        setData(20, 30);

//        mChart.animateX(2500);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tf);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaxValue(200f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaxValue(900);
        rightAxis.setAxisMinValue(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);

    }
    @Subscribe
    public void changeChart(ChangeChartEvent event){
        if(!mChart.isShown()){
            chart1.setVisibility(View.GONE);
            mChart.setVisibility(View.VISIBLE);
        } else{
            chart1.setVisibility(View.VISIBLE);
            mChart.setVisibility(View.GONE);
        }
    }


    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 50;// + (float)
            yVals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        //set1.setFillFormatter(new MyFillFormatter(0f));
//        set1.setDrawHorizontalHighlightIndicator(false);
//        set1.setVisible(false);
//        set1.setCircleHoleColor(Color.WHITE);

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 450;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals2.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(yVals2, "DataSet 2");
        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.WHITE);
        set2.setLineWidth(2f);
        set2.setCircleRadius(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        //set2.setFillFormatter(new MyFillFormatter(900f));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set2);
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }

    private class CustomFormatter implements ValueFormatter, YAxisValueFormatter {

        private DecimalFormat mFormat;

        public CustomFormatter() {
            mFormat = new DecimalFormat("###");
        }

        // data
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(Math.abs(value)) + "";
        }

        // YAxis
        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(Math.abs(value)) + "";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
