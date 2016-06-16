package com.wksc.counting.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.event.ChangeChartEvent;
import com.wksc.counting.widegit.CustomViewPager;
import com.wksc.counting.widegit.PagerSlidingTabStrip;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/15.
 */
public class CompareFragment extends CommonFragment {
    @Bind(R.id.indicator)
    PagerSlidingTabStrip mIndicator;
    @Bind(R.id.viewPager_history)
    CustomViewPager mViewPager;
    @Bind(R.id.ib_right)
    ImageButton btnRight;
    @Bind(R.id.bar_left)
    ImageView barLeft;

    private ArrayList<FragmentEntity> indicatorFragmentEntityList;
    private MyPagerAdapter adapter;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comparison, null);
        hideTitleBar();
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
        indicatorFragmentEntityList = new ArrayList<>();

        for (int i =0 ;i < 5;i++) {
            String name = null;
            Fragment fragment = null;
            FragmentEntity fragmentEntity = null;
            if (i == 0){
                fragment = new SalesComparisonFragment();
                name = "销售额";
            }else if(i == 1){
                fragment = new SalesComparisonFragment();
                name = "毛利额";
            }else if(i ==2){
                fragment = new SalesComparisonFragment();
                name = "毛利率";
            }
            else if(i ==3){
                fragment = new SalesComparisonFragment();
                name = "客单数";
            }
            else if(i ==4){
                fragment = new SalesComparisonFragment();
                name = "客单价";
            }
            if (fragment != null) {
                fragmentEntity = new FragmentEntity(name, fragment);
                indicatorFragmentEntityList.add(fragmentEntity);
            }
        }
        Drawable drawable = getResources().getDrawable(R.drawable.slide_block_shape);
        mIndicator.setSlidingBlockDrawable(drawable);

        mIndicator.setTabViewFactory(new PagerSlidingTabStrip.TabViewFactory() {
            @Override
            public void addTabs(ViewGroup parent, int defaultPosition) {
                parent.removeAllViews();
                for (int i = 0; i < indicatorFragmentEntityList.size(); i++) {
                    TextView tab = new TextView(getContext());
                    tab.setGravity(Gravity.CENTER);
                    tab.setTextSize(15);
                    tab.setText(indicatorFragmentEntityList.get(i).name);
                    tab.setPadding(8, 8, 8, 8);

                    if (indicatorFragmentEntityList.size() == 2) {
                        if (i == 0) {
                            tab.setTextColor(getResources().getColor(R.color.bg_color));
                            tab.setBackgroundResource(R.drawable.tab_left_select);
                        } else {
                            tab.setTextColor(getResources().getColor(R.color.white));
                            tab.setBackgroundResource(R.drawable.tab_right_notselect);
                        }
                    } else if (indicatorFragmentEntityList.size() == 1) {
                        tab.setTextColor(getResources().getColor(R.color.white));
                        tab.setBackgroundResource(R.drawable.transparent);
                    }

                    parent.addView(tab);
                }
            }
        });
        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments =  fm.getFragments();
        if(fragments!=null)fragments.clear();

        adapter = new MyPagerAdapter(fm,
                indicatorFragmentEntityList);
        mViewPager.setAdapter(adapter);
        mViewPager.setPagingEnabled(false);
        mViewPager.setOffscreenPageLimit(3);
        mIndicator.setViewPager(mViewPager);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (indicatorFragmentEntityList.size() == 2) {
                    if (position == 0) {
                        TextView tvTab0 = (TextView) mIndicator.getTab(0);
                        tvTab0.setBackgroundResource(R.drawable.tab_left_select);
                        tvTab0.setTextColor(getResources().getColor(R.color.bg_color));

                        TextView tvTab1 = (TextView) mIndicator.getTab(1);
                        tvTab1.setBackgroundResource(R.drawable.tab_right_notselect);
                        tvTab1.setTextColor(getResources().getColor(R.color.white));
                    } else if (position == 1) {
                        TextView tvTab0 = (TextView) mIndicator.getTab(0);
                        tvTab0.setBackgroundResource(R.drawable.tab_left_notselect);
                        tvTab0.setTextColor(getResources().getColor(R.color.white));

                        TextView tvTab1 = (TextView) mIndicator.getTab(1);
                        tvTab1.setBackgroundResource(R.drawable.tab_right_select);
                        tvTab1.setTextColor(getResources().getColor(R.color.bg_color));
                    }

                } else if (indicatorFragmentEntityList.size() == 1) {
                    TextView tvTab = (TextView) mIndicator.getTab(position);
                    tvTab.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChangeChartEvent());

            }
        });
        barLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm, ArrayList<FragmentEntity> fragments) {
            super(fm);
            this.fragmentsList = fragments;

        }

        private ArrayList<FragmentEntity> fragmentsList;

        public void setFragmentsList(ArrayList<FragmentEntity> fragmentsList) {
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

    class FragmentEntity {
        public String name;
        public Fragment fragment;

        public FragmentEntity( String name, Fragment fragment) {
            this.name = name;
            this.fragment = fragment;
        }
    }
}
