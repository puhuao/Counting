package com.wksc.counting.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wksc.counting.model.FragmentEntity;

import java.util.ArrayList;

/**
 * Created by puhua on 2016/7/21.
 *
 * @
 */
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
