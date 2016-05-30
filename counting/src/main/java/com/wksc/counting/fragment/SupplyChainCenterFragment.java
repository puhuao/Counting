package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wksc.counting.R;
import com.wksc.counting.adapter.GoodsSalesAnalysisListAdapter;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SupplyChainCenterFragment extends CommonFragment {

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_supply_chain, null);
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
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.goods_analysis_fram, new GoodsAnalysisFragment())
                .commit();
        fm.beginTransaction().replace(R.id.save_analysis_fram, new SaveAnalysisFragment())
                .commit();
        fm.beginTransaction().replace(R.id.purchase_analysis_fram, new PurchaseAnalysisFragment())
                .commit();
    }
}
