package com.wksc.counting.activity;

import android.os.Bundle;

import com.wksc.counting.R;
import com.wksc.counting.fragment.LocusPassFragment;
import com.wksc.counting.fragment.LoginFragment;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.activity.CommonActivity;
import com.wksc.framwork.platform.config.IConfig;

public class LoginActivity extends CommonActivity {

    IConfig config;
    private Boolean isSetLocusPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);

        config = BaseApplication.getInstance().getCurrentConfig();
        isSetLocusPassword = config.getBoolean("setLocusPassword", false);
        if (isSetLocusPassword) {
           pushFragmentToBackStack(LocusPassFragment.class, true);
        }else {
            pushFragmentToBackStack(LoginFragment.class, null);
        }
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}
