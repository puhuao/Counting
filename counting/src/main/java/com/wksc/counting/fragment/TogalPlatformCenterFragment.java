package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.PlatFormLastItemAdapter;
import com.wksc.counting.adapter.PlatFormListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.PlatFormAnaEvent;
import com.wksc.counting.model.SaleAnaModel.PeiModel;
import com.wksc.counting.model.platFormModel.PlatFormModel;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.ConditionLayout;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.counting.widegit.PieChartTool;
import com.wksc.counting.widegit.TableTitleLayout;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;

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
public class TogalPlatformCenterFragment extends CommonFragment {
    @Bind(R.id.lv1)
    NestedListView lv1;
    @Bind(R.id.lv)
    NestedListView lv;

    @Bind(R.id.condition)
    ConditionLayout conditionLayout;

    @Bind(R.id.pie)
    PieChart pieChart;

    @Bind(R.id.table_titles)
    TableTitleLayout tableTitle;

    @Bind(R.id.title)
    TextView title;
    PieChartTool pieChartTool;

    PlatFormListAdapter platFormListAdapter;
    PlatFormLastItemAdapter platFormLastItemAdapter;
    private IConfig config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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
        platFormLastItemAdapter = new PlatFormLastItemAdapter(getContext());
        lv.setAdapter(platFormLastItemAdapter);
        platFormListAdapter = new PlatFormListAdapter(getContext());
        lv1.setAdapter(platFormListAdapter);
        conditionLayout.hideGoods(false);
        conditionLayout.paramsDeliver = true;
        conditionLayout.setView(false);
        pieChartTool = new PieChartTool(pieChart);
//        getListData();
        conditionLayout.setConditionSelect(new ConditionLayout.OnConditionSelect() {
            @Override
            public void postParams() {
                getListData();
            }
        });
    }

    private void getListData() {
        conditionLayout.getAllConditions(true);
        extraParam = conditionLayout.prams.toString();
        StringBuilder sb = new StringBuilder(Urls.TOPICINDEX);
        config = BaseApplication.getInstance().getCurrentConfig();
        UrlUtils.getInstance().addSession(sb,config).praseToUrl(sb,"class","10")
                .praseToUrl(sb,"level","1").praseToUrl(sb,"item","30");
        sb.append(extraParam);
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<PlatFormModel>(getContext(), PlatFormModel.class) {

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, PlatFormModel c, Request request, @Nullable Response response) {
                       if (c.tableData.size()>0){
                           Log.i("TAG", c.tableData.toString());
                           tableTitle.clearAllViews();
                           platFormListAdapter.setList(c.tableData);

                           String[] titles = c.memberData.tableTitle.split("\\|");
                           String[] desc = c.memberData.tableTitleDesc.split("\\|");
                           tableTitle.initView(titles, desc);
                           platFormLastItemAdapter.setItemCloums(titles.length);
                           platFormLastItemAdapter.setList(c.memberData.tableData);
                           title.setText(c.memberData.title);
                           StringBuilder sb1 = new StringBuilder();
                           StringBuilder sb2 = new StringBuilder();
                           for (int i = 0; i < c.memberData.tableData.size(); i++) {
                               String[] array = c.memberData.tableData.get(i).split("\\|");
                               sb1.append(array[1]).append("|");
                               sb2.append(array[0]).append("|");
                           }

                           if (sb1.length() > 0) {
                               sb1.deleteCharAt(sb1.length() - 1);
                               sb2.deleteCharAt(sb2.length() - 1);
                           }
                           PeiModel peiModel = new PeiModel();
                           peiModel.chartPoint1 = sb2.toString();
                           peiModel.chartValue1 = sb1.toString();
                           peiModel.chartTitle1 = c.memberData.title;
                           pieChartTool.setData(peiModel);
                           pieChartTool.setPiechart();
                       }

                    }

                });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void changeChart(PlatFormAnaEvent event) {
        getListData();
    }
}
