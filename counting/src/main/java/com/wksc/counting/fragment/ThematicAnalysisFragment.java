package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wksc.counting.R;
import com.wksc.counting.adapter.MyPagerAdapter;
import com.wksc.counting.event.ChangeTitleEvent;
import com.wksc.counting.event.GoodsAnaEvent;
import com.wksc.counting.event.SaleGoalAnaEvent;
import com.wksc.counting.model.FragmentEntity;
import com.wksc.counting.widegit.CustomViewPager;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    @Bind(R.id.title_left)
    RadioButton titleLeft;
    @Bind(R.id.title_center)
    RadioButton titleCenter;
    @Bind(R.id.title_right)
    RadioButton titleRight;
    @Bind(R.id.title_group)
    RadioGroup titleGroup;

    private MyPagerAdapter adapter;
    private ArrayList<FragmentEntity> indicatorFragmentEntityList;
    private IConfig config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thematic_analysis, null);
//        hideLeftButton();
        hideTitleBar();
//        showRightButton();
//        getRightButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new TurnToMoreFragmentEvent(false));
//            }
//        });
//        setHeaderTitle("专题分析");
//        Drawable drawable = getContext().getResources().getDrawable(R.drawable.title_rectangle_down);
//        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
//        getTitleHeaderBar().getTitleTextView().setCompoundDrawables(null,null,drawable,null);
////        getTitleHeaderBar().getTitleTextView().getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
////        getTitleHeaderBar().getTitleTextView().getPaint().setAntiAlias(true);
//        getTitleHeaderBar().getTitleTextView().setCompoundDrawablePadding(10);
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
        config = BaseApplication.getInstance().getCurrentConfig();
        indicatorFragmentEntityList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String name = null;
            Fragment fragment = null;
            FragmentEntity fragmentEntity = null;
            if (i == 0) {
                if (checkHasRight("10"))
                    fragment = new MarktingCenterFragment(titleLeft);
                else fragment = new NoRightFragment();
                name = "营销中心";
            } else if (i == 1) {
                if (checkHasRight("20"))
                    fragment = new SupplyChainCenterFragment(titleCenter);
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
        titleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.title_left:
//                        EventBus.getDefault().post(1);
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.title_center:
//                        EventBus.getDefault().post(2);
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.title_right:
                        mViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(1);
            }
        });
        titleCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(2);
            }
        });

//        final TitleSelectPopupWindow titleSelectPopupWindow = new TitleSelectPopupWindow(getContext());

        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments =  fm.getFragments();
        if(fragments!=null)fragments.clear();

        adapter = new MyPagerAdapter(fm,
                indicatorFragmentEntityList);
        mViewPager.setAdapter(adapter);
        mViewPager.setPagingEnabled(false);
        mViewPager.setOffscreenPageLimit(3);
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
//        titleSelectPopupWindow.setViewPager(mViewPager);
//        titleSelectPopupWindow.initListener();
//        titleSelectPopupWindow.setTitleView(getTitleHeaderBar().getTitleTextView());
//        getTitleHeaderBar().setCenterOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                titleSelectPopupWindow.showPopupwindow(v);
//            }
//        });
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

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void loadFirst(){
        EventBus.getDefault().post(1);
        EventBus.getDefault().post(new SaleGoalAnaEvent());
    }
    @Subscribe
    public void onEvent(ChangeTitleEvent event){
        if (event.pos == 1){
            titleLeft.setText(event.title);
        }else  if(event.pos == 2){
            titleCenter.setText(event.title);
        }
    }

}
