package com.wksc.counting.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.widget.RadioGroup;

import com.wksc.counting.R;
import com.wksc.counting.fragment.CoreIndexFragment;
import com.wksc.counting.fragment.MoreFragment;
import com.wksc.counting.fragment.NewsFragment;
import com.wksc.counting.fragment.TelescopeFragment;
import com.wksc.counting.fragment.ThematicAnalysisFragment;
import com.wksc.framwork.baseui.ActivityManager;
import com.wksc.framwork.baseui.activity.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/26.
 *
 * @
 */
public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;

    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        // 删除窗口背景
        getWindow().setBackgroundDrawable(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment(new CoreIndexFragment());
        transFragment();
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initFragment(Fragment fragment) {
        mContent = fragment;
    }

    private void transFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContent)
                .commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.btn_core_index:
                mContent=new CoreIndexFragment();
                break;
            case R.id.btn_thematic_analysis:
                mContent=new ThematicAnalysisFragment();
                break;
            case R.id.btn_telescope:
                mContent=new TelescopeFragment();
                break;
            case R.id.btn_news:
                mContent=new NewsFragment();
                break;
            case R.id.btn_more:
                mContent=new MoreFragment();
                break;
        }
        transFragment();
    }
}
