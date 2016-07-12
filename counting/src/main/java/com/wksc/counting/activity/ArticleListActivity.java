package com.wksc.counting.activity;

import android.os.Bundle;

import com.wksc.counting.R;
import com.wksc.counting.fragment.ArticleListFragment;
import com.wksc.framwork.activity.CommonActivity;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ArticleListActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        pushFragmentToBackStack(ArticleListFragment.class,"" );
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}
