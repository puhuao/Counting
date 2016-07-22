package com.wksc.counting.popwindows;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.wksc.counting.R;
import com.wksc.counting.tools.Params;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by puhua on 2016/6/5.
 */
public class DateSelectPopupWindow extends BasePopupWindow {

    private Activity mContext;
    private Button id_btn_date_ok;
    private DateSelectPopupWindow dateSelectPopupWindow;
    private DatePicker datePick1;
    RadioGroup radioGroup;
    public int flag=3;
    private Button reset;

    public DateSelectPopupWindow(Activity context) {
        super();
        mContext = context;
        initView();
        dateSelectPopupWindow=this;
        init();
    }

    private void setDatePickerDividerColor(DatePicker datePicker){
        // Divider changing:

        // 获取 mSpinners
        LinearLayout llFirst       = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners      = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView= inflater.inflate(R.layout.layout_popupwindow_datepick, null);

        datePick1= (DatePicker) contentView.findViewById(R.id.datePick1);
        radioGroup = (RadioGroup) contentView.findViewById(R.id.rg);
        reset = (Button) contentView.findViewById(R.id.reset);
        setDatePickerDividerColor(datePick1);
        final DatePicker.OnDateChangedListener dcl=new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Params.y=year;
                Params.m=monthOfYear;
                Params.d=dayOfMonth;
            }
        };
//        hide(0);
        if (Params.dateFlag == 2){
            hide(2);
            radioGroup.check(R.id.rb2);
            flag =2;

        }else if(Params.dateFlag==3){
            show(2);
            flag =3;
            radioGroup.check(R.id.rb3);
        }
        datePick1.init( Params.y,Params.m,Params.d,dcl);

        id_btn_date_ok= (Button) contentView.findViewById(R.id.id_btn_date_ok);
        id_btn_date_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
                backgroundAlpha(1f);
                String date = "";
                String y = "";
                String m = "";
                if (flag ==1){
                    y = String.valueOf(Params.y);
                }else if (flag ==2){
                    y = String.valueOf(Params.y);
                    if (Params.m<9){
                        m ="0"+(Params.m+1);
                    }else{
                        m = String.valueOf(Params.m+1);
                    }
                }else if (flag ==3){
                    y = String.valueOf(Params.y);
                    if (Params.m<9){
                        m ="0"+(Params.m+1);
                    }else{
                        m = String.valueOf(Params.m+1);
                    }
                    if (Params.d<10){
                        date = "0"+(Params.d);
                    }else{
                        date =String.valueOf(Params.d);
                    }
                }
                mOnDateSelectListener.onDateSelect(y,m,date,flag);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        Params.dateFlag = 1;
                        flag = 1;
                        break;
                    case R.id.rb2:
                        Params.dateFlag = 2;
                        flag = 2;
                        hide(2);
                        break;
                    case R.id.rb3:
                        Params.dateFlag = 3;
                        flag = 3;
                        show(1);
                        show(2);
                        break;

                }
            }
        });
        //设置PopupWindow的View
        this.setContentView(contentView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));//必须设置  ps:xml bg和这个不冲突
        this.setAnimationStyle(R.style.selectDataViewAnimation);
        this.setFocusable(true);//设置后  达到返回按钮先消失popupWindow
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                datePick1.init( calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH),dcl);
            }
        });
    }


    public void hideDay(Boolean hideDay) {
        radioGroup.findViewById(R.id.rb3).setVisibility(View.GONE);
        hide(2);
        flag=2;
    }




    public interface OnDateSelectListener {
        void onDateSelect(String year,String month,String date,int flag);
    }

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        mOnDateSelectListener = onDateSelectListener;
    }

    private OnDateSelectListener mOnDateSelectListener;


    public void showPopupwindow(View view){
        backgroundAlpha(0.5f);
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
    }
    public void dissmisPopupwindow(){
        this.dismiss();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0

        mContext.getWindow().setAttributes(lp);
    }

    private void hide(int i){
        ((ViewGroup)((ViewGroup)datePick1.getChildAt(0)).
                getChildAt(0)).getChildAt(i).setVisibility(View.GONE);
    }
    private void show(int i){
        ((ViewGroup)((ViewGroup)datePick1.getChildAt(0)).
                getChildAt(0)).getChildAt(i).setVisibility(View.VISIBLE);
    }
}
