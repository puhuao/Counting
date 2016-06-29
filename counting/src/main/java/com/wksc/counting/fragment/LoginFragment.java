package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wksc.counting.R;
import com.wksc.counting.activity.MainActivity;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by puhua on 2016/5/26.
 *
 * @
 */
public class LoginFragment extends CommonFragment {
    @Bind(R.id.fab)
    Button button;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTitleHeaderBar.setVisibility(View.GONE);
        final View view = inflater.inflate(R.layout.activity_login,null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @OnClick({R.id.fab})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.fab:
                startActivity(MainActivity.class);
                getActivity().finish();
                break;
        }
    }
}
