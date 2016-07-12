package com.wksc.counting.activity;

import android.os.Bundle;

import com.wksc.counting.R;
import com.wksc.counting.fragment.ModifyPasswordFragment;
import com.wksc.framwork.activity.CommonActivity;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ModifyPasswordActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        pushFragmentToBackStack(ModifyPasswordFragment.class,"" );
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}
