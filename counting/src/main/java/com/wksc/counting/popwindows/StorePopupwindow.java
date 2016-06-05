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
import com.wksc.counting.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class StorePopupwindow extends PopupWindow {
    List<AreaModel> provinces = new ArrayList<>();
    List<AreaModel> temp = new ArrayList<>();
    List<AreaModel> cTemp = new ArrayList<>();
    Activity mContext;
    ListView list,province,lvCity ;
    Button sure;
    AreaListAdapter areaListAdapter,provinceListAdapter;
    List<AreaModel> areas = new ArrayList<>();
    public StorePopupwindow(Activity context){
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


        areas.add(new AreaModel("成都"));
        areas.add(new AreaModel("德阳"));
        areas.add(new AreaModel("绵阳"));
        areas.add(new AreaModel("南充"));
        areas.add(new AreaModel("泸州"));

        areaListAdapter = new AreaListAdapter(context);
        areaListAdapter.isAll = true;
        areaListAdapter.setList(areas);
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
            }
        });

        provinces.add(new AreaModel("门店1"));
        provinces.add(new AreaModel("门店2"));
        provinces.add(new AreaModel("门店3"));
        provinces.add(new AreaModel("门店4"));



        provinceListAdapter = new AreaListAdapter(context);
        provinceListAdapter.setList(provinces);
        province.setAdapter(provinceListAdapter);
        lvCity.setVisibility(View.GONE);
        province.setVisibility(View.GONE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    areas.get(position).isCheck = !areas.get(position).isCheck;
                    areaListAdapter.notifyDataSetChanged();
                   int checkecNumber =  areaListAdapter.getCheckedNumber();
                    if (checkecNumber >1){
                        province.setVisibility(View.GONE);
                        provinceListAdapter.setAllCheck(false);
                        provinceListAdapter.notifyDataSetChanged();
                    }else if(checkecNumber==1){
                        provinceListAdapter.setAllCheck(true);
                        provinceListAdapter.notifyDataSetChanged();
                        province.setVisibility(View.VISIBLE);
//                        lvCity.setVisibility(View.GONE);
                    }
                    if(checkecNumber == areas.size()-1){
                        areas.get(0).isCheck = true;
                    }
            }
        });

        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinces.get(position).isCheck = !provinces.get(position).isCheck;
                provinceListAdapter.notifyDataSetChanged();
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

}
