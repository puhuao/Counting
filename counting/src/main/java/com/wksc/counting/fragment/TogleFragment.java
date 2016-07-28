package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesCompareListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.model.coreDetail.CoreDetail;
import com.wksc.counting.tools.NetWorkTool;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.BarChartTool;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.TableTitleLayout;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/1.
 */
public class TogleFragment extends CommonFragment {
    @Bind(R.id.list_view)
    NestedListView list;
//    @Bind(R.id.chart1)
//    LinearLayout chart1;
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
    private String provice;
    private IConfig config;

    String extraParam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sales_comparison, null);
        getTitleHeaderBar().setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
//        showRightButton();
//        getRightButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getContext().pushFragmentToBackStack(MoreFragment.class,"");
//            }
//        });
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        config = BaseApplication.getInstance().getCurrentConfig();
        oldBarTool = new BarChartTool(barChartOld, getContext());

        newBarTool = new BarChartTool(barChartNew, getContext());

        Bundle bundle = (Bundle) getmDataIn();
        param = bundle.getString("param");
        provice = bundle.getString("provice");
        extraParam = bundle.getString("extra");
        setHeaderTitle(bundle.getString("title"));
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
        conditionLayout.initViewByParam();
        conditionLayout.setConditionSelect(new ConditionLayout.OnConditionSelect() {
            @Override
            public void postParams() {
//                conditionLayout.getAllConditions()
                getData(provice);
            }
        });
        adapter = new SalesCompareListAdapter(getActivity());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                getData("423");
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(provice);
            }
        });
        getData(provice);

    }

    StringBuilder originExtraParam = new StringBuilder();

    private void getData(String provice) {
        if (flag>0){
            extraParam = conditionLayout.getAllConditions();
        }
        StringBuilder sb = new StringBuilder(Urls.COREDETAIL);
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "item", param)
                .praseToUrl(sb, "level", "2").praseToUrl(sb, "province", provice)
        .praseToUrl(sb, "code", provice);
        if (!extraParam.equals(originExtraParam))
        sb.append(extraParam);
        if(!NetWorkTool.isNetworkAvailable(getActivity())){
            ToastUtil.showShortMessage(getActivity(),"网络错误");
            return;
        }
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<CoreDetail>(getContext(), CoreDetail.class,refreshLayout) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, CoreDetail c, Request request, @Nullable Response response) {
//                        if (c.tableData.size() > 0) {
                        if (refreshLayout.isRefreshing()){
                            refreshLayout.setRefreshing(false);
                        }
                        flag++;
                            Log.i("TAG", c.toString());
                            titles.clearAllViews();
                            detail = c;
                            oldBarTool.setData(c.CoreChart1);
                            newBarTool.setData(c.CoreChart2);
                            String[] tableTitles = detail.tableTitle.split("\\|");
                            final String[] titleDesc = detail.tableTitleDesc.split("\\|");
                            titles.initView("地区");
                            titles.initView(tableTitles,
                                    titleDesc);
                            adapter.TransData(detail.tableData);

//                        }

                    }
                });
    }

//    @Subscribe
//    public void changeChart(ChangeChartEvent event) {
//        if (!mChart.isShown()) {
//            chart1.setVisibility(View.GONE);
//            mChart.setVisibility(View.VISIBLE);
//        } else {
//            chart1.setVisibility(View.VISIBLE);
//            mChart.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void lazyLoad() {
//        getData(provice);
    }
}

