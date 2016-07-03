package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wksc.counting.R;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
public class CustomerServiceFragment extends CommonFragment {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_customer_service, null);
        View v = inflater.inflate(R.layout.fragment_expecting, null);
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

    }

    @Override
    protected void lazyLoad() {

    }
}
