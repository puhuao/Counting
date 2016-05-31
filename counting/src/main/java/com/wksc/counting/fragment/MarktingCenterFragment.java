package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesFinishListAdapter;
import com.wksc.counting.adapter.SalesSupplyListAdapter;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.GoodsPopupwindow;
import com.wksc.counting.popwindows.IndexPopupwindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.counting.popwindows.TimePopupwindow;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class MarktingCenterFragment extends CommonFragment implements View.OnClickListener {
    @Bind(R.id.sales_analysis)
    ListView lvSalesAnalysis;
    @Bind(R.id.supply_analysis)
    ListView lvSupplyAnalysis;
    @Bind(R.id.area)
    TextView area;
    @Bind(R.id.goods)
    TextView goods;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.index)
    TextView index;
    SalesFinishListAdapter salesFinishListAdapter;
    SalesSupplyListAdapter salesSupplyListAdapter;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_marketing_center, null);
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
        salesFinishListAdapter = new SalesFinishListAdapter(getActivity());
        lvSalesAnalysis.setAdapter(salesFinishListAdapter);
        salesSupplyListAdapter = new SalesSupplyListAdapter(getActivity());
        lvSupplyAnalysis.setAdapter(salesSupplyListAdapter);
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        index.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
