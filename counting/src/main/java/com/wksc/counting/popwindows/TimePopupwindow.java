package com.wksc.counting.popwindows;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wksc.counting.R;
import com.wksc.counting.widegit.MarqueeText;
import com.wksc.framwork.util.StringUtils;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by Administrator on 2016/5/29.
 */
public class TimePopupwindow extends PopupWindow implements AdapterView.OnItemClickListener {
    RadioGroup radioGroup;
    RadioButton rbForMonth;
    Activity mContext;
    private MarqueeText area;
    TextView input;
    ImageButton search;

    public TimePopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_time,null);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        rbForMonth = (RadioButton) view.findViewById(R.id.for_month);
        input = (TextView) view.findViewById(R.id.input);
        search = (ImageButton) view.findViewById(R.id.search);
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.for_month:
                        area.setText("当月");
                        backgroundAlpha(1f);
                        dismiss();
                        break;
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isBlank(input.getText().toString())){

                }
                area.setText(input.getText().toString());
                backgroundAlpha(1f);
                dismiss();
            }
        });
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1f);
                dismiss();
                final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                dialog.show();
                DatePicker picker = new DatePicker(mContext);
                picker.setDate(2016, 6);
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
//                        Toast.makeText(mContext, date, Toast.LENGTH_LONG).show();
                        area.setText(date);
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }


    public void bindTextView(MarqueeText area) {
        this.area = area;
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
