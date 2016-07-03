package com.wksc.counting.activity;

import android.os.Bundle;

import com.wksc.counting.R;
import com.wksc.counting.fragment.CompareFragment;
import com.wksc.counting.fragment.ToglSaleGoalAnalysisFragment;
import com.wksc.counting.fragment.TogleGoodsAnalysisFragment;
import com.wksc.counting.fragment.TogleSaleChainAnalysisFragment;
import com.wksc.counting.fragment.TogleSaveAnalysisFragment;
import com.wksc.framwork.activity.CommonActivity;

/**
 * Created by Administrator on 2016/5/29.
 */
public class TogleActivity extends CommonActivity {

    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        Bundle bundle = getIntent().getExtras();

        flag = bundle.getInt("flag");
        switch (flag){
            case 1:
                pushFragmentToBackStack(ToglSaleGoalAnalysisFragment.class, bundle);
                break;
            case 2:
                pushFragmentToBackStack(TogleSaleChainAnalysisFragment.class, bundle);
                break;
            case 4:
                pushFragmentToBackStack(TogleSaveAnalysisFragment.class, bundle);
                break;
            case 3:
                pushFragmentToBackStack(TogleGoodsAnalysisFragment.class, bundle);
                break;
        }


    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.id_fragment;
    }
}
