package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.AreaCheckModel;
import com.wksc.counting.model.AreaModel;
import com.wksc.counting.widegit.MarqueeText;
import com.wksc.counting.widegit.unionPickListView.PickListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class AreaPopupwindow extends PopupWindow {
    List<AreaCheckModel> provinces = new ArrayList<>();
    List<AreaCheckModel> city = new ArrayList<>();
    List<AreaModel> temp = new ArrayList<>();
    List<AreaModel> cTemp = new ArrayList<>();
    Activity mContext;
    PickListView regionListView, cityListView,countyListView ;
    Button sure;
//    CheckBoxListAdapter areaListAdapter,provinceListAdapter,cityListAdapter;
CheckBoxListAdapter reginListAdapter;
    CheckBoxListAdapter cityListAdapter;
    CheckBoxListAdapter countyListAdapter;

    List<AreaCheckModel> areas = new ArrayList<>();
    private MarqueeText area;

    public AreaPopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_area,null);
        regionListView = (PickListView) view.findViewById(R.id.diriction_area);
        cityListView = (PickListView) view.findViewById(R.id.diriction_province);
        countyListView = (PickListView) view.findViewById(R.id.diriction_city);
        sure = (Button) view.findViewById(R.id.sure);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                dismiss();
            }
        });
        reginListAdapter = new CheckBoxListAdapter(context);
        reginListAdapter.isAll = true;
        reginListAdapter.setList(BaseDataUtil.regions());
        regionListView.setAdapter(reginListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb= new StringBuilder();
                    sb.append(reginListAdapter.sb).append(cityListAdapter.sb).append(countyListAdapter.sb);
                dissmisPopupwindow();
                if (sb.length()>0){
                    area.setText(sb.toString());
                }else{
                    area.setText("地区");
                }

            }
        });
        cityListAdapter = new CheckBoxListAdapter(context);
        cityListAdapter.setList(BaseDataUtil.citys(0));
        cityListView.setAdapter(cityListAdapter);
        countyListAdapter = new CheckBoxListAdapter(context);
        countyListAdapter.setList(BaseDataUtil.countys(0,0));
        countyListView.setAdapter(countyListAdapter);
        regionListView.initView(null,cityListView);
        cityListView.initView(regionListView,countyListView);
        countyListView.initView(cityListView,null);
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

    public void bindTextView(MarqueeText area) {
        this.area = area;
    }




}
