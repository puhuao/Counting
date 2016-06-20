package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wksc.counting.R;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.util.ToastUtil;
import com.wksc.framwork.widget.LocusPassWordView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/20.
 */
public class LocusPassFragment extends CommonFragment {
    @Bind(R.id.mLocusPassWordView)
    LocusPassWordView mLocusPassView;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lacus_pass, null);
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        mLocusPassView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {

            @Override
            public void onComplete(String password) {
                // TODO Auto-generated method stub
                ToastUtil.showLongMessage(getContext(), "密码是"+password);
                getActivity().finish();
            }
        });
        return v;
    }
}
