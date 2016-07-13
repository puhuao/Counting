package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.wksc.counting.activity.SalesComparisonActivity;
import com.wksc.counting.adapter.SalesCompareListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.SaleComparisonLoadDataEvent;
import com.wksc.counting.event.SaleComparisonLoadDataEvent1;
import com.wksc.counting.model.coreDetail.CoreDetail;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.BarChartTool;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.TableTitleLayout;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
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
public class SalesComparisonFragment1 extends CommonFragment {
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
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    SalesCompareListAdapter adapter;
    CoreDetail detail;
    BarChartTool oldBarTool;
    BarChartTool newBarTool;
    private String param;
    private boolean isPrepared;
    private int currentPos;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;
    private IConfig config;
    private Boolean isFirstShow = true;
    Bundle bundle;
    private String provice;

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
        bundle = getArguments();
        param = bundle.getString("param");
        isFirstShow = bundle.getBoolean("isFirstShow");
        currentPos = bundle.getInt("position");
        provice = bundle.getString("provice");
        initView();
        return v;
    }

    StringBuilder oldParmas = new StringBuilder();

    private void setOldParams(String s){
        if (oldParmas.length()>0)
        this.oldParmas.delete(0,oldParmas.length());
        oldParmas.append(s);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        conditionLayout.hideGoods(true);
        conditionLayout.initViewByParam();
        conditionLayout.setConditionSelect(new ConditionLayout.OnConditionSelect() {
            @Override
            public void postParams() {
                extraParam = conditionLayout.getAllConditions();
                setOldParams(extraParam);
                getData();
            }
        });
        adapter = new SalesCompareListAdapter(getActivity());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (StringUtils.isBlank(provice)){
                    Bundle bundle = new Bundle();
                    bundle.putString("param", param);
                    bundle.putString("provice", adapter.getList().get(position).code);
                    bundle.putString("extraParam", extraParam);
//                bundle.putString("title",adapter.getList().get(position).area);
//                getContext().pushFragmentToBackStack(TogleFragment.class, bundle);
//                    getContext().pushFragmentToBackStack(CompareFragment.class, bundle);
                   startActivity(SalesComparisonActivity.class, bundle);
                }
            }
        });
        if (isFirstShow) {
            conditionLayout.initViewByParam();
            extraParam = bundle.getString("extraParam");
            setOldParams(extraParam);
            getData();
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                conditionLayout.initViewByParam();
                extraParam = conditionLayout.getAllConditions();
                setOldParams(extraParam);
                getData();
            }
        });
        if( FragmentDataUtil.map1.get("key" + param)!=null)
        if (FragmentDataUtil.map1.get("key" + param).tableData != null) {
            detail = FragmentDataUtil.map1.get("key" + param);
            titles.clearAllViews();
            if (oldBarTool != null)
                oldBarTool.setData(detail.CoreChart1);
            if (newBarTool != null)
                newBarTool.setData(detail.CoreChart2);
            String[] tableTitles = detail.tableTitle.split("\\|");
            final String[] titleDesc = detail.tableTitleDesc.split("\\|");
            if (titles != null) {
                titles.initView("地区");
                titles.initView(tableTitles,
                        titleDesc);
            }
            if (adapter != null)
                adapter.TransData(detail.tableData);
        }
    }


    private void getData() {
        StringBuilder sb = new StringBuilder(Urls.COREDETAIL);
        config = BaseApplication.getInstance().getCurrentConfig();
        if (StringUtils.isBlank(provice)){
            UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "item", param)
                    .praseToUrl(sb, "level", "1");/*.praseToUrl(sb,"year","2016").praseToUrl(sb,"month","06");*/
        }else{
            UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "item", param)
                    .praseToUrl(sb, "level", "2").praseToUrl(sb, "province", provice)
                    .praseToUrl(sb, "code", provice);
        }

        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<CoreDetail>(getContext(), CoreDetail.class,refreshLayout) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, CoreDetail c, Request request, @Nullable Response response) {
                        Log.i("TAG", c.toString());
                        if (refreshLayout.isRefreshing()){
                            refreshLayout.setRefreshing(false);
                        }
                        detail = c;
                        FragmentDataUtil.map1.put("key" + param, c);
//                        if (c.tableData.size()>0){
                        titles.clearAllViews();
                        if (oldBarTool != null)
                            oldBarTool.setData(c.CoreChart1);
                        if (newBarTool != null)
                            newBarTool.setData(c.CoreChart2);
                        String[] tableTitles = detail.tableTitle.split("\\|");
                        final String[] titleDesc = detail.tableTitleDesc.split("\\|");
                        if (titles != null) {
                            titles.initView("地区");
                            titles.initView(tableTitles,
                                    titleDesc);
                        }
                        if (adapter != null)
                            adapter.TransData(detail.tableData);
                    }

                });
    }

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
    public void lodaData(SaleComparisonLoadDataEvent1 event) {
            if (event.position == currentPos) {
                conditionLayout.initViewByParam();
                extraParam = conditionLayout.getAllConditions();
                if ( FragmentDataUtil.map1.get("key" + param)==null/*||
                    !extraParam.equals(oldParmas.toString())*/){
                    getData();
                }
            }
    }

}
