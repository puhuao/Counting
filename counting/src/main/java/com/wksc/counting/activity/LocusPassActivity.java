package com.wksc.counting.activity;

import android.os.Bundle;

import com.wksc.counting.R;
import com.wksc.counting.fragment.LocusPassFragment;
import com.wksc.framwork.activity.CommonActivity;

/**
 * Created by Administrator on 2016/6/20.
 */
public class LocusPassActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        pushFragmentToBackStack(LocusPassFragment.class, false);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}
