package com.wksc.counting.activity;

import android.os.Bundle;

import com.wksc.counting.R;
import com.wksc.counting.fragment.CompareFragment;
import com.wksc.counting.fragment.SalesComparisonFragment;
import com.wksc.framwork.activity.CommonActivity;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SalesComparisonActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);


        pushFragmentToBackStack(CompareFragment.class, getIntent().getExtras());
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}
