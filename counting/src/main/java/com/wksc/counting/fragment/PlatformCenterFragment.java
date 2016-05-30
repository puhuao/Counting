package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wksc.counting.R;
import com.wksc.counting.adapter.ChainVipListAdapter;
import com.wksc.counting.adapter.VipNormalCustomerListAdapter;
import com.wksc.counting.adapter.VipPurchaseListAdapter;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class PlatformCenterFragment extends CommonFragment {
    @Bind(R.id.lv_vip_purchase)
    NestedListView lvVipListView;
    @Bind(R.id.lv_vip_compare)
    NestedListView lvVipNormalListView;
    @Bind(R.id.lv_chain_vip_grow)
    NestedListView lvVipChainListView;

    VipPurchaseListAdapter vipPurchaseListAdapter;
    VipNormalCustomerListAdapter vipNormalCustomerListAdapter;
    ChainVipListAdapter chainVipListAdapter;
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
        vipPurchaseListAdapter = new VipPurchaseListAdapter(getActivity());
        lvVipListView.setAdapter(vipPurchaseListAdapter);
        vipNormalCustomerListAdapter = new VipNormalCustomerListAdapter(getActivity());
        lvVipNormalListView.setAdapter(vipNormalCustomerListAdapter);
        chainVipListAdapter = new ChainVipListAdapter(getActivity());
        lvVipChainListView.setAdapter(chainVipListAdapter);
    }
}
