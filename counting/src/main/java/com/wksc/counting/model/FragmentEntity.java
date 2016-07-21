package com.wksc.counting.model;

import android.support.v4.app.Fragment;

/**
 * Created by puhua on 2016/7/21.
 *
 * @
 */
public class FragmentEntity {
    public String name;
    public Fragment fragment;

    public FragmentEntity(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }
}
