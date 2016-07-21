package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
import com.wksc.counting.event.CompareFragmentTransPagerEvent1;
import com.wksc.counting.model.FragmentEntity;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.platform.config.IConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by puhua on 2016/6/16.
 *
 * @
 */
public class ComparePopupWindow1 extends PopupWindow {
    private IConfig config;
    private final Activity mContext;
    private TextView titleView;
    private int positon=0;

    public ComparePopupWindow1(Activity context,
                               ArrayList<FragmentEntity> indicatorFragmentEntityList,
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
                CompareFragmentTransPagerEvent1 event = new CompareFragmentTransPagerEvent1();
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

    }

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



}
