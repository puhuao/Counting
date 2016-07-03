package com.wksc.counting.popwindows;

import android.widget.PopupWindow;

import com.wksc.counting.model.baseinfo.BaseWithCheckBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 */
public class BasePopupWindow extends PopupWindow {
    public OnConditionSelectListener mListener;
    public void setOnConditionSelectListener(OnConditionSelectListener mListener) {
        this.mListener = mListener;
    }
    public interface OnConditionSelectListener{
        public void conditionSelect(String ragions,String name,int tag);
    }
}
