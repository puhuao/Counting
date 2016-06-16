package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.fragment.CustomerServiceFragment;
import com.wksc.counting.fragment.MarktingCenterFragment;
import com.wksc.counting.fragment.SupplyChainCenterFragment;
import com.wksc.counting.widegit.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by puhua on 2016/6/16.
 *
 * @
 */
public class TitleSelectPopupWindow extends PopupWindow{
    PagerSlidingTabStrip mIndicator;
    private final Activity mContext;
    private ArrayList<FragmentEntity> indicatorFragmentEntityList;
    private TextView titleView;

    public TitleSelectPopupWindow(Activity context){
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_title_select,null);
        mIndicator = (PagerSlidingTabStrip) view.findViewById(R.id.indicator);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                dismiss();
            }
        });


        initview();
    }

    private void initview() {
        indicatorFragmentEntityList = new ArrayList<>();

        for (int i =0 ;i < 3;i++) {
            String name = null;
            Fragment fragment = null;
            FragmentEntity fragmentEntity = null;
            if (i == 0){
                fragment = new MarktingCenterFragment();
                name = "营销中心";
            }else if(i == 1){
                fragment = new SupplyChainCenterFragment();
                name = "供应链中心";
            }else if(i ==2){
                fragment = new CustomerServiceFragment();
                name = "客服中心";
            }
//            else if(i ==3){
//                fragment = new PlatformCenterFragment();
//                name = "平台中心";
//            }
            if (fragment != null) {
                fragmentEntity = new FragmentEntity(name, fragment);
                indicatorFragmentEntityList.add(fragmentEntity);
            }
        }
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.slide_block_shape);
        mIndicator.setSlidingBlockDrawable(drawable);

        mIndicator.setTabViewFactory(new PagerSlidingTabStrip.TabViewFactory() {
            @Override
            public void addTabs(ViewGroup parent, int defaultPosition) {
                parent.removeAllViews();
                for (int i = 0; i < indicatorFragmentEntityList.size(); i++) {
                    TextView tab = new TextView(mContext);
                    tab.setGravity(Gravity.CENTER);
                    tab.setTextSize(15);
                    tab.setText(indicatorFragmentEntityList.get(i).name);
                    tab.setPadding(8, 8, 8, 8);

                    if (indicatorFragmentEntityList.size() == 2) {
                        if (i == 0) {
                            tab.setTextColor(mContext.getResources().getColor(R.color.bg_color));
                            tab.setBackgroundResource(R.drawable.tab_left_select);
                        } else {
                            tab.setTextColor(mContext.getResources().getColor(R.color.white));
                            tab.setBackgroundResource(R.drawable.tab_right_notselect);
                        }
                    } else if (indicatorFragmentEntityList.size() == 1) {
                        tab.setTextColor(mContext.getResources().getColor(R.color.white));
                        tab.setBackgroundResource(R.drawable.transparent);
                    }

                    parent.addView(tab);
                }
            }
        });

    }

    public void initListener(){
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (titleView != null) {
                    titleView.setText(indicatorFragmentEntityList.get(position).name);
                }
                dissmisPopupwindow();
                if (indicatorFragmentEntityList.size() == 2) {
                    if (position == 0) {
                        TextView tvTab0 = (TextView) mIndicator.getTab(0);
                        tvTab0.setBackgroundResource(R.drawable.tab_left_select);
                        tvTab0.setTextColor(mContext.getResources().getColor(R.color.bg_color));

                        TextView tvTab1 = (TextView) mIndicator.getTab(1);
                        tvTab1.setBackgroundResource(R.drawable.tab_right_notselect);
                        tvTab1.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else if (position == 1) {
                        TextView tvTab0 = (TextView) mIndicator.getTab(0);
                        tvTab0.setBackgroundResource(R.drawable.tab_left_notselect);
                        tvTab0.setTextColor(mContext.getResources().getColor(R.color.white));

                        TextView tvTab1 = (TextView) mIndicator.getTab(1);
                        tvTab1.setBackgroundResource(R.drawable.tab_right_select);
                        tvTab1.setTextColor(mContext.getResources().getColor(R.color.bg_color));
                    }

                } else if (indicatorFragmentEntityList.size() == 1) {
                    TextView tvTab = (TextView) mIndicator.getTab(position);
                    tvTab.setTextColor(mContext.getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public ArrayList<FragmentEntity> getIndicatorList(){
        return indicatorFragmentEntityList;
    }

    public void setViewPager(ViewPager mViewPager){
        mIndicator.setViewPager(mViewPager);
    }

    public void showPopupwindow(View view){
        backgroundAlpha(0.5f);
        this.showAsDropDown(view);
    }
    public void dissmisPopupwindow(){
        this.dismiss();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0

        mContext.getWindow().setAttributes(lp);
    }

    public void setTitleView(TextView titleTextView) {
        this.titleView = titleTextView;
    }

    public class FragmentEntity {
        public String name;
        public Fragment fragment;

        public FragmentEntity( String name, Fragment fragment) {
            this.name = name;
            this.fragment = fragment;
        }
    }
}
