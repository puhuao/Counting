package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.adapter.ChainVipListAdapter;
import com.wksc.counting.adapter.VipNormalCustomerListAdapter;
import com.wksc.counting.adapter.VipPurchaseListAdapter;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.GoodsPopupwindow;
import com.wksc.counting.popwindows.IndexPopupwindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.counting.popwindows.TimePopupwindow;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class PlatformCenterFragment extends CommonFragment implements View.OnClickListener {
    @Bind(R.id.lv_vip_purchase)
    NestedListView lvVipListView;
    @Bind(R.id.lv_vip_compare)
    NestedListView lvVipNormalListView;
    @Bind(R.id.lv_chain_vip_grow)
    NestedListView lvVipChainListView;
    @Bind(R.id.goods)
    TextView goods;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.index)
    TextView index;
    @Bind(R.id.area)
    TextView area;
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
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        index.setOnClickListener(this);
        vipPurchaseListAdapter = new VipPurchaseListAdapter(getActivity());
        lvVipListView.setAdapter(vipPurchaseListAdapter);
        vipNormalCustomerListAdapter = new VipNormalCustomerListAdapter(getActivity());
        lvVipNormalListView.setAdapter(vipNormalCustomerListAdapter);
        chainVipListAdapter = new ChainVipListAdapter(getActivity());
        lvVipChainListView.setAdapter(chainVipListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
