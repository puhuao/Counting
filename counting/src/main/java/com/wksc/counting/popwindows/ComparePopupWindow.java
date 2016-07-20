package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.event.CompareFragmentTransPagerEvent;
import com.wksc.counting.fragment.CompareFragment;
import com.wksc.counting.fragment.CustomerServiceFragment;
import com.wksc.counting.fragment.MarktingCenterFragment;
import com.wksc.counting.fragment.NoRightFragment;
import com.wksc.counting.fragment.SupplyChainCenterFragment;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.platform.config.IConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by puhua on 2016/6/16.
 *
 * @
 */
public class ComparePopupWindow extends PopupWindow {
    private IConfig config;
    private final Activity mContext;
    private ArrayList<FragmentEntity> indicatorFragmentEntityList;
    private TextView titleView;
    private int positon=0;

    public ComparePopupWindow(Activity context,
                              ArrayList<CompareFragment.FragmentEntity> indicatorFragmentEntityList,
                              int pos) {
        positon = pos;
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_compare, null);
        RadioGroup group = (RadioGroup) view.findViewById(R.id.radio_group);
        for (int i = 0;i < indicatorFragmentEntityList.size();i++){
            RadioButton button = new RadioButton(mContext);
            button.setText(indicatorFragmentEntityList.get(i).name);
            button.setTextColor(mContext.getResources().getColor(R.color.white));
            button.setButtonDrawable(android.R.color.transparent);
            button.setPadding(10,10,10,10);
            button.setTag(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    button.setBackground(mContext.getResources().getDrawable(R.drawable.selector_radio));
            }else{
                button.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_radio));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.setMargins(10,5,10,5);
            button.setGravity(Gravity.CENTER);
            group.addView(button,
                    params);
            if (i==positon){
                button.setChecked(true);
            }else{
                button.setChecked(false);
            }
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                CompareFragmentTransPagerEvent event = new CompareFragmentTransPagerEvent();
                event.pos = (int) radioButton.getTag();
                EventBus.getDefault().post(event);
                dissmisPopupwindow();
            }
        });
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setWidth(400);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
//                backgroundAlpha(1f);
                dismiss();
            }
        });
        config = BaseApplication.getInstance().getCurrentConfig();

//        initview();
    }

    private Boolean checkHasRight(String s){
        String[] rights =  config.getString("topicrule", "").split(",");
        if (rights.length>0)
        for (int i=0;i<rights.length;i++){
            if (rights[i].equals(s)){
                return true;
            }
        }
        return false;
    }

    private void initview() {
        indicatorFragmentEntityList = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            String name = null;
            Fragment fragment = null;
            FragmentEntity fragmentEntity = null;
            if (i == 0) {
                if (checkHasRight("10"))
                fragment = new MarktingCenterFragment();
                else fragment = new NoRightFragment();
                name = "营销中心";
            } else if (i == 1) {
                if (checkHasRight("20"))
                fragment = new SupplyChainCenterFragment();
                else fragment = new NoRightFragment();
                name = "供应链中心";
            } else if (i == 2) {
                if (checkHasRight("30"))
                fragment = new CustomerServiceFragment();
                else fragment = new NoRightFragment();
                name = "客服中心";
            }
            if (fragment != null) {
                fragmentEntity = new FragmentEntity(name, fragment);
                indicatorFragmentEntityList.add(fragmentEntity);
            }
        }
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.slide_block_shape);
//        mIndicator.setSlidingBlockDrawable(drawable);
//
//        mIndicator.setTabViewFactory(new PagerSlidingTabStrip.TabViewFactory() {
//            @Override
//            public void addTabs(ViewGroup parent, int defaultPosition) {
//                parent.removeAllViews();
//                for (int i = 0; i < indicatorFragmentEntityList.size(); i++) {
//                    TextView tab = new TextView(mContext);
//                    tab.setGravity(Gravity.CENTER);
//                    tab.setTextSize(15);
//                    tab.setText(indicatorFragmentEntityList.get(i).name);
//                    tab.setPadding(8, 8, 8, 8);
//                    tab.setTextColor(mContext.getResources().getColor(R.color.main_tab_text_selector));
//
////                    if (indicatorFragmentEntityList.size() == 2) {
////                        if (i == 0) {
////                            tab.setTextColor(mContext.getResources().getColor(R.color.bg_color));
////                            tab.setBackgroundResource(R.drawable.tab_left_select);
////                        } else {
////                            tab.setTextColor(mContext.getResources().getColor(R.color.white));
////                            tab.setBackgroundResource(R.drawable.tab_right_notselect);
////                        }
////                    } else if (indicatorFragmentEntityList.size() == 1) {
////                        tab.setTextColor(mContext.getResources().getColor(R.color.white));
////                        tab.setBackgroundResource(R.drawable.transparent);
////                    }
//
//                    parent.addView(tab);
//                }
//            }
//        });

    }

//    public void initListener() {
//        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (titleView != null) {
//                    titleView.setText(indicatorFragmentEntityList.get(position).name);
//                }
//                dissmisPopupwindow();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }

//    public ArrayList<FragmentEntity> getIndicatorList() {
//        return indicatorFragmentEntityList;
//    }
//
//    public void setViewPager(ViewPager mViewPager) {
//        mIndicator.setViewPager(mViewPager);
//    }

    public void showPopupwindow(View view) {
//        backgroundAlpha(0.5f);
        this.showAsDropDown(view);
    }

    public void dissmisPopupwindow() {
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

        public FragmentEntity(String name, Fragment fragment) {
            this.name = name;
            this.fragment = fragment;
        }
    }
}
