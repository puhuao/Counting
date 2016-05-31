package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wksc.counting.R;
import com.wksc.counting.adapter.SalesFinishListAdapter;
import com.wksc.counting.adapter.SalesSupplyListAdapter;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class MarktingCenterFragment extends CommonFragment {
    @Bind(R.id.sales_analysis)
    ListView lvSalesAnalysis;
    @Bind(R.id.supply_analysis)
    ListView lvSupplyAnalysis;

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
    }
}
