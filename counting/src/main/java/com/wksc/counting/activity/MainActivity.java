package com.wksc.counting.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wksc.counting.R;
import com.wksc.counting.event.CoreIndextLoadDataEvent;
import com.wksc.counting.event.SaleGoalAnaEvent;
import com.wksc.counting.event.TurnToMoreFragmentEvent;
import com.wksc.counting.fragment.CoreIndexFragment;
import com.wksc.counting.fragment.LocusPassFragment;
import com.wksc.counting.fragment.MoreFragment;
import com.wksc.counting.fragment.NewsFragment;
import com.wksc.counting.fragment.TelescopeFragment;
import com.wksc.counting.fragment.ThematicAnalysisFragment;
import com.wksc.counting.widegit.CustomDialog;
import com.wksc.counting.widegit.CustomViewPager;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.ActivityManager;
import com.wksc.framwork.baseui.activity.BaseFragmentActivity;
import com.wksc.framwork.platform.config.IConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/26.
 *
 * @
 */
public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.btn_more)
    RadioButton btn_more;
    @Bind(R.id.viewPager_history)
    CustomViewPager mViewPager;
    List<Fragment> fragments = new ArrayList<>();

    IConfig config;
    private Boolean isSetLocusPassword;

    private Fragment coreIndexFragment = new CoreIndexFragment();
    private Fragment thematicAnalysisFragment = new ThematicAnalysisFragment();
    private Fragment telescopeFragment = new TelescopeFragment();
    private Fragment newsFragment = new NewsFragment();
    private Fragment moreFragment = new MoreFragment();
    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        // 删除窗口背景
        getWindow().setBackgroundDrawable(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
//        transFragment(coreIndexFragment);
        fragments.add(coreIndexFragment);
        fragments.add(thematicAnalysisFragment);
        fragments.add(telescopeFragment);
        fragments.add(newsFragment);
        fragments.add(moreFragment);
        fragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), (ArrayList<Fragment>) fragments);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setPagingEnabled(false);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
//                    EventBus.getDefault().post(CoreIndextLoadDataEvent.class);
                }
                else if(position==1){
                    EventBus.getDefault().post(SaleGoalAnaEvent.class);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(this);
        config = BaseApplication.getInstance().getCurrentConfig();
        isSetLocusPassword = config.getBoolean("setLocusPassword", false);
        if (!isSetLocusPassword) {
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setMessage("你还没有设置手势密码，是否设置？");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(LocusPassActivity.class);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

    }

    private void transFragment(Fragment fragment){
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//        mViewPager.setCurrentItem();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.btn_core_index:
//                transFragment(coreIndexFragment);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.btn_thematic_analysis:
//                transFragment(thematicAnalysisFragment);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.btn_telescope:
//                transFragment(telescopeFragment);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.btn_news:
//                transFragment(newsFragment);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.btn_more:
//                transFragment(moreFragment);
                mViewPager.setCurrentItem(4);
                break;
        }

    }

    @Subscribe
    public void changeChart(TurnToMoreFragmentEvent event) {
        transFragment(moreFragment);
        btn_more.setChecked(true);
        if (event.isDestroyAll()){
            ActivityManager.getInstance().finishAllActivity();
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragmentsList = fragments;

        }

        private ArrayList<Fragment> fragmentsList;

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

    }
}
