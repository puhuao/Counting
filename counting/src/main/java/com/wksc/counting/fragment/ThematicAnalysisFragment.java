package com.wksc.counting.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wksc.counting.R;
import com.wksc.counting.popwindows.TitleSelectPopupWindow;
import com.wksc.counting.widegit.CustomViewPager;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/27.
 *
 * @
 */
public class ThematicAnalysisFragment extends CommonFragment {

    @Bind(R.id.viewPager_history)
    CustomViewPager mViewPager;


    private MyPagerAdapter adapter;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thematic_analysis, null);
        hideLeftButton();
        setHeaderTitle("专题分析");
        getTitleHeaderBar().getTitleTextView().setCompoundDrawables(null,null,
                getContext().getResources().getDrawable(R.drawable.rectangle),null);
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {

        final TitleSelectPopupWindow titleSelectPopupWindow = new TitleSelectPopupWindow(getContext());

        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments =  fm.getFragments();
        if(fragments!=null)fragments.clear();

        adapter = new MyPagerAdapter(fm,
                titleSelectPopupWindow.getIndicatorList());
        mViewPager.setAdapter(adapter);
        mViewPager.setPagingEnabled(false);
        mViewPager.setOffscreenPageLimit(3);
        titleSelectPopupWindow.setViewPager(mViewPager);
        titleSelectPopupWindow.initListener();
        titleSelectPopupWindow.setTitleView(getTitleHeaderBar().getTitleTextView());
        getTitleHeaderBar().setCenterOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleSelectPopupWindow.showPopupwindow(v);
            }
        });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm, ArrayList<TitleSelectPopupWindow.FragmentEntity> fragments) {
            super(fm);
            this.fragmentsList = fragments;

        }

        private ArrayList<TitleSelectPopupWindow.FragmentEntity> fragmentsList;

        public void setFragmentsList(ArrayList<TitleSelectPopupWindow.FragmentEntity> fragmentsList) {
            this.fragmentsList = fragmentsList;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position).fragment;
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

    }

}
