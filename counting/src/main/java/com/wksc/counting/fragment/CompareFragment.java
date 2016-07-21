package com.wksc.counting.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.Basedata.FragmentDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.activity.SearchActivity;
import com.wksc.counting.adapter.MyPagerAdapter;
import com.wksc.counting.event.CompareFragmentTransPagerEvent;
import com.wksc.counting.event.SaleComparisonLoadDataEvent;
import com.wksc.counting.event.VipComparisonLoadDataEvent;
import com.wksc.counting.model.FragmentEntity;
import com.wksc.counting.model.baseinfo.CoreItem;
import com.wksc.counting.popwindows.ComparePopupWindow;
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
 * Created by Administrator on 2016/6/15.
 */
public class CompareFragment extends CommonFragment {
    @Bind(R.id.indicator)
    PagerSlidingTabStrip mIndicator;
    @Bind(R.id.viewPager_history)
    CustomViewPager mViewPager;

    private ArrayList<FragmentEntity> indicatorFragmentEntityList;
    private MyPagerAdapter adapter;
    private String param;
    private String provice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comparison, null);
        setHeaderTitle("销售额对比");
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.title_rectangle_down);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        getTitleHeaderBar().getTitleTextView().setCompoundDrawablePadding(10);
        getTitleHeaderBar().getTitleTextView().setCompoundDrawables(null,null,drawable,null);

        showRightButton();
        getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("flag",pos);
                getActivity().startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        Bundle bundle = (Bundle) getmDataIn();
        param = bundle.getString("param");
        extraParam = bundle.getString("extraParam");
        provice = bundle.getString("provice");
        initView();
        return v;
    }

    private int pos;

    private void initView() {
        indicatorFragmentEntityList = new ArrayList<>();

        for (int i = 0; i < BaseDataUtil.coreItems.size(); i++) {
            CoreItem coreItem = BaseDataUtil.coreItems.get(i);
            FragmentDataUtil.map.put("key"+coreItem.code,null);
            Bundle bundle = new Bundle();
            if (param.equals(coreItem.code)) {
                pos = i;
                bundle.putBoolean("isFirstShow",true);
            }else{
                bundle.putBoolean("isFirstShow",false);
            }
            String name = coreItem.name;
            Fragment fragment = null;
            if (coreItem.code.equals("60") || coreItem.code.equals("70")) {
//                fragment = new VipComparisonFragment();
                break;
            } else {
                fragment = new SalesComparisonFragment();
            }

            bundle.putString("param", coreItem.code);
            bundle.putString("extraParam",extraParam);
            bundle.putInt("position",i);
            bundle.putString("provice",provice);
            fragment.setArguments(bundle);
            FragmentEntity fragmentEntity = new FragmentEntity(name, fragment);
            if (fragment != null) {
                indicatorFragmentEntityList.add(fragmentEntity);
            }
        }

        getTitleHeaderBar().getTitleTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComparePopupWindow comparePopupWindow = new ComparePopupWindow(getContext(),indicatorFragmentEntityList,pos);
                comparePopupWindow.showPopupwindow(v);
            }
        });

//        Drawable drawable = getResources().getDrawable(R.drawable.slide_block_shape);
//        mIndicator.setSlidingBlockDrawable(drawable);
//
//        mIndicator.setTabViewFactory(new PagerSlidingTabStrip.TabViewFactory() {
//            @Override
//            public void addTabs(ViewGroup parent, int defaultPosition) {
//                parent.removeAllViews();
//                for (int i = 0; i < indicatorFragmentEntityList.size(); i++) {
//                    TextView tab = new TextView(getContext());
//                    tab.setGravity(Gravity.CENTER);
//                    tab.setTextSize(14);
//                    tab.setText(indicatorFragmentEntityList.get(i).name);
//                    tab.setPadding(10, 8, 10, 8);
//
//                    if (indicatorFragmentEntityList.size() == 2) {
//                        if (i == 0) {
//                            tab.setTextColor(getResources().getColor(R.color.bg_color));
//                            tab.setBackgroundResource(R.drawable.tab_left_select);
//                        } else {
//                            tab.setTextColor(getResources().getColor(R.color.white));
//                            tab.setBackgroundResource(R.drawable.tab_right_notselect);
//                        }
//                    } else if (indicatorFragmentEntityList.size() == 1) {
//                        tab.setTextColor(getResources().getColor(R.color.white));
//                        tab.setBackgroundResource(R.drawable.transparent);
//                    }
//
//                    parent.addView(tab);
//                }
//            }
//        });
        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) fragments.clear();

        adapter = new MyPagerAdapter(fm,
                indicatorFragmentEntityList);
        mViewPager.setAdapter(adapter);
        mViewPager.setPagingEnabled(false);
        mViewPager.setOffscreenPageLimit(1);
//        mIndicator.setViewPager(mViewPager);
        mIndicator.setVisibility(View.GONE);
//        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                setHeaderTitle(indicatorFragmentEntityList.get(position).name+"对比");
//                if (indicatorFragmentEntityList.size() == 2) {
//                    if (position == 0) {
//                        TextView tvTab0 = (TextView) mIndicator.getTab(0);
//                        tvTab0.setBackgroundResource(R.drawable.tab_left_select);
//                        tvTab0.setTextColor(getResources().getColor(R.color.bg_color));
//
//                        TextView tvTab1 = (TextView) mIndicator.getTab(1);
//                        tvTab1.setBackgroundResource(R.drawable.tab_right_notselect);
//                        tvTab1.setTextColor(getResources().getColor(R.color.white));
//                    } else if (position == 1) {
//                        TextView tvTab0 = (TextView) mIndicator.getTab(0);
//                        tvTab0.setBackgroundResource(R.drawable.tab_left_notselect);
//                        tvTab0.setTextColor(getResources().getColor(R.color.white));
//
//                        TextView tvTab1 = (TextView) mIndicator.getTab(1);
//                        tvTab1.setBackgroundResource(R.drawable.tab_right_select);
//                        tvTab1.setTextColor(getResources().getColor(R.color.bg_color));
//                    }
//
//                } else if (indicatorFragmentEntityList.size() == 1) {
//                    TextView tvTab = (TextView) mIndicator.getTab(position);
//                    tvTab.setTextColor(getResources().getColor(R.color.white));
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

//        btnRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new ChangeChartEvent());
//
//            }
//        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setHeaderTitle(indicatorFragmentEntityList.get(position).name+"对比");
                SaleComparisonLoadDataEvent event = new SaleComparisonLoadDataEvent();
                event.item = BaseDataUtil.coreItems.get(position).code;
                event.position = position;
                if (event.item.equals("60")||event.item.equals("70")){
                    VipComparisonLoadDataEvent vipComparisonLoadDataEvent = new VipComparisonLoadDataEvent();
                    vipComparisonLoadDataEvent.item = BaseDataUtil.coreItems.get(position).code;
                    vipComparisonLoadDataEvent.position = position;
                    EventBus.getDefault().post(vipComparisonLoadDataEvent);
                }else{
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (pos!=0){
            mViewPager.setCurrentItem(pos);
//            mIndicator.selectedTab(pos);
        }else{
            mViewPager.setCurrentItem(0);
//            mIndicator.selectedTab(0);
//            VipComparisonLoadDataEvent vipComparisonLoadDataEvent = new VipComparisonLoadDataEvent();
//            vipComparisonLoadDataEvent.item = BaseDataUtil.coreItems.get(0).code;
//            vipComparisonLoadDataEvent.position = 0;
//            EventBus.getDefault().post(vipComparisonLoadDataEvent);
        }
//        barLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentDataUtil.clearCoreDetailData(FragmentDataUtil.map);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(CompareFragmentTransPagerEvent event) {
        mViewPager.setCurrentItem(event.pos);
        pos = event.pos;
    }

    @Override
    protected void lazyLoad() {

    }
}
