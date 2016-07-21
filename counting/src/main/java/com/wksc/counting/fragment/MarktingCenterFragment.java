package com.wksc.counting.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.adapter.MyPagerAdapter;
import com.wksc.counting.event.ChangeTitleEvent;
import com.wksc.counting.event.MarketingTransPagerEvent;
import com.wksc.counting.event.PlatFormAnaEvent;
import com.wksc.counting.event.SaleChannelAnaEvent;
import com.wksc.counting.event.SaleGoalAnaEvent;
import com.wksc.counting.model.FragmentEntity;
import com.wksc.counting.popwindows.MarketingPopupWindow;
import com.wksc.counting.widegit.CustomViewPager;
import com.wksc.counting.widegit.PagerSlidingTabStrip;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/29.
 */
@SuppressLint("ValidFragment")
public class MarktingCenterFragment extends CommonFragment {
    @Bind(R.id.indicator)
    PagerSlidingTabStrip mIndicator;
    @Bind(R.id.viewPager_history)
    CustomViewPager mViewPager;
    int position;
    View view;

    private ArrayList<FragmentEntity> indicatorFragmentEntityList;
    private MyPagerAdapter adapter;

    public MarktingCenterFragment(View view){
        super();
        this.view = view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_marketing_center, null);
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

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        indicatorFragmentEntityList = new ArrayList<>();

        for (int i =0 ;i < 3;i++) {
            String name = null;
            Fragment fragment = null;
            FragmentEntity fragmentEntity = null;
            if (i == 0){
                fragment = new SaleGoalAnalysisFragment();
                name = "销售达成分析";
            }else if(i == 1){
                fragment = new SaleChainAnalysisFragment();
                name = "销售渠道分析";
            }else if(i ==2){
                fragment = new PlatformCenterFragment();
                name = "会员分析";
            }
            if (fragment != null) {
                fragmentEntity = new FragmentEntity(name, fragment);
                indicatorFragmentEntityList.add(fragmentEntity);
            }
        }

        ChangeTitleEvent changeTitleEvent = new ChangeTitleEvent();
        changeTitleEvent.pos = 1;
        changeTitleEvent.title = indicatorFragmentEntityList.get(0).name;
        EventBus.getDefault().post(changeTitleEvent);

        mIndicator.setTabViewFactory(new PagerSlidingTabStrip.TabViewFactory() {
            @Override
            public void addTabs(ViewGroup parent, int defaultPosition) {
                parent.removeAllViews();
                for (int i = 0; i < indicatorFragmentEntityList.size(); i++) {
                    LinearLayout layout = new LinearLayout(getActivity());
                    layout.setGravity(Gravity.CENTER);
                    TextView tab = new TextView(getContext());
                    tab.setGravity(Gravity.CENTER);
                    tab.setTextSize(15);
                    tab.setText(indicatorFragmentEntityList.get(i).name);
                    tab.setPadding(8, 8, 8, 8);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,0,10,0);
                    tab.setLayoutParams(params);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tab.setBackground(getContext().getResources().getDrawable(R.drawable.tab_text_selector));
                    }else{
                        tab.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.tab_text_selector));
                    }
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
                    layout.addView(tab);
                    parent.addView(layout);
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
        mViewPager.setOffscreenPageLimit(1);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setVisibility(View.GONE);
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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        position = 0;
                        EventBus.getDefault().post(new SaleGoalAnaEvent());
                        break;
                    case 1:
                        position = 1;
                        EventBus.getDefault().post(new SaleChannelAnaEvent());
                        break;
                    case 2:
                        position = 2;
                        EventBus.getDefault().post(new PlatFormAnaEvent());
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Integer flag) {
        if (flag == 1){
            MarketingPopupWindow marketingPopupWindow = new MarketingPopupWindow(getActivity(),
                    indicatorFragmentEntityList,position);
            marketingPopupWindow.showPopupwindow(view);
        }
    }
    @Subscribe
    public void onChange(MarketingTransPagerEvent event){
        mViewPager.setCurrentItem(event.pos);
        position = event.pos;
        ChangeTitleEvent changeTitleEvent = new ChangeTitleEvent();
        changeTitleEvent.pos = 1;
        changeTitleEvent.title = indicatorFragmentEntityList.get(position).name;
        EventBus.getDefault().post(changeTitleEvent);
    }
}
