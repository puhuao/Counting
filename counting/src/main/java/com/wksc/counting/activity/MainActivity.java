package com.wksc.counting.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.wksc.counting.R;
import com.wksc.counting.fragment.HomeFragment;
import com.wksc.framwork.baseui.ActivityManager;
import com.wksc.framwork.baseui.activity.BaseFragmentActivity;

import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/26.
 *
 * @
 */
public class MainActivity extends BaseFragmentActivity {
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        // 删除窗口背景
        getWindow().setBackgroundDrawable(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initFragment();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void initFragment() {
        mContent = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();
    }
}
