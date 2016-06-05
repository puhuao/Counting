package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wksc.counting.R;
import com.wksc.counting.adapter.AreaListAdapter;
import com.wksc.counting.adapter.TimeListAdapter;

/**
 * Created by Administrator on 2016/5/29.
 */
public class TimePopupwindow extends PopupWindow implements AdapterView.OnItemClickListener {
    Activity mContext;
    public TimePopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_time,null);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setWidth(800);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dissmisPopupwindow();
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
