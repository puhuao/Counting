package com.wksc.counting.fragment;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wksc.counting.R;
import com.wksc.counting.event.GoodsAnaEvent;
import com.wksc.counting.event.SaleGoalAnaEvent;
import com.wksc.counting.event.TurnToMoreFragmentEvent;
import com.wksc.counting.popwindows.TitleSelectPopupWindow;
import com.wksc.counting.widegit.CustomViewPager;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import org.greenrobot.eventbus.EventBus;

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
//        showRightButton();
//        getRightButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new TurnToMoreFragmentEvent(false));
//            }
//        });
        setHeaderTitle("专题分析");
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.rectangle);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        getTitleHeaderBar().getTitleTextView().setCompoundDrawables(null,null,drawable,null);
        getTitleHeaderBar().getTitleTextView().getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        getTitleHeaderBar().getTitleTextView().getPaint().setAntiAlias(true);
        getTitleHeaderBar().getTitleTextView().setCompoundDrawablePadding(5);
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
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    EventBus.getDefault().post(new GoodsAnaEvent());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    protected void lazyLoad() {

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

    public void loadFirst(){
        EventBus.getDefault().post(new SaleGoalAnaEvent());
    }

}
