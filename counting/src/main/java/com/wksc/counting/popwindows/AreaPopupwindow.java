package com.wksc.counting.popwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wksc.counting.R;
import com.wksc.counting.adapter.AreaListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class AreaPopupwindow extends PopupWindow {
    List<String> provinces = new ArrayList<>();
    List<String> city = new ArrayList<>();
    Activity mContext;
    ListView list,province,lvCity ;
    Button sure;
    AreaListAdapter areaListAdapter,provinceListAdapter,cityListAdapter;
    public AreaPopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_area,null);
        list = (ListView) view.findViewById(R.id.diriction_area);
        province = (ListView) view.findViewById(R.id.diriction_province);
        lvCity = (ListView) view.findViewById(R.id.diriction_city);
        sure = (Button) view.findViewById(R.id.sure);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
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
        areaListAdapter = new AreaListAdapter(context);
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
            }
        });

        provinces.add("四川省");
        provinces.add("重庆市");
        provinces.add("云南省");
        provinces.add("贵州省");

        city.add("成都");
        city.add("绵阳");
        city.add("德阳");
        city.add("资阳");
        city.add("遂宁");
        city.add("泸州");
        provinceListAdapter = new AreaListAdapter(context);
        provinceListAdapter.setList(provinces);
        province.setAdapter(provinceListAdapter);
        cityListAdapter = new AreaListAdapter(context);
        cityListAdapter.setList(city);
        lvCity.setAdapter(cityListAdapter);
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

}
