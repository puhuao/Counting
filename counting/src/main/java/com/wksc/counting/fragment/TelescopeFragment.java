package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wksc.counting.R;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/27.
 *
 * @
 */
public class TelescopeFragment extends CommonFragment {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_telescope, null);
        hideLeftButton();
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void lazyLoad() {

    }
}
