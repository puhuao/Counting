package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wksc.counting.R;
import com.wksc.counting.adapter.GoodsSalesAnalysisListAdapter;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/30.
 *
 * @
 */
public class GoodsAnalysisFragment extends CommonFragment {
    @Bind(R.id.goods_sale_analysis_list)
    NestedListView lvGoodsSaleAnalysis;

    GoodsSalesAnalysisListAdapter goodsSalesAnalysisListAdapter;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goods_analysis, null);
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
        goodsSalesAnalysisListAdapter = new GoodsSalesAnalysisListAdapter(getActivity());
        lvGoodsSaleAnalysis.setAdapter(goodsSalesAnalysisListAdapter);
    }
}
