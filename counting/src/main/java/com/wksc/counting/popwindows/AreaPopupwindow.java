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
import com.wksc.counting.widegit.MarqueeText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class AreaPopupwindow extends PopupWindow {
    List<AreaModel> provinces = new ArrayList<>();
    List<AreaModel> city = new ArrayList<>();
    List<AreaModel> temp = new ArrayList<>();
    List<AreaModel> cTemp = new ArrayList<>();
    Activity mContext;
    ListView list,province,lvCity ;
    Button sure;
    AreaListAdapter areaListAdapter,provinceListAdapter,cityListAdapter;
    List<AreaModel> areas = new ArrayList<>();
    private MarqueeText area;

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


        areas.add(new AreaModel("全国"));
        areas.add(new AreaModel("东北大区"));
        areas.add(new AreaModel("东南大区"));
        areas.add(new AreaModel("西北大区"));
        areas.add(new AreaModel("西南大区"));

        areaListAdapter = new AreaListAdapter(context);
        areaListAdapter.isAll = true;
        areaListAdapter.setList(areas);
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb= new StringBuilder();
                    sb.append(areaListAdapter.sb).append(provinceListAdapter.sb).append(cityListAdapter.sb);
                dissmisPopupwindow();
                if (area.length()>0){
                    area.setText(sb.toString());
                }else{
                    area.setText("地区");
                }

            }
        });

        provinces.add(new AreaModel("四川省"));
        provinces.add(new AreaModel("重庆市"));
        provinces.add(new AreaModel("云南省"));
        provinces.add(new AreaModel("贵州省"));


        city.add(new AreaModel("成都"));
        city.add(new AreaModel("绵阳"));
        city.add(new AreaModel("德阳"));
        city.add(new AreaModel("资阳"));
        city.add(new AreaModel("遂宁"));
        city.add(new AreaModel("泸州"));
        provinceListAdapter = new AreaListAdapter(context);
        provinceListAdapter.setList(provinces);
        province.setAdapter(provinceListAdapter);
        cityListAdapter = new AreaListAdapter(context);
        cityListAdapter.setList(city);
        lvCity.setAdapter(cityListAdapter);
        province.setVisibility(View.GONE);
        lvCity.setVisibility(View.GONE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    areaListAdapter.setAllCheck(!areas.get(position).isCheck);
                    areaListAdapter.getCheckedNumber();
                    areaListAdapter.notifyDataSetChanged();
                    province.setVisibility(View.GONE);
                    lvCity.setVisibility(View.GONE);
                }else{
                    areas.get(0).isCheck = false;
                    areas.get(position).isCheck = !areas.get(position).isCheck;
                    areaListAdapter.notifyDataSetChanged();
                   int checkecNumber =  areaListAdapter.getCheckedNumber();
                    if (checkecNumber >1){
                        province.setVisibility(View.GONE);
                        provinceListAdapter.setAllCheck(false);
                        provinceListAdapter.notifyDataSetChanged();
                        lvCity.setVisibility(View.GONE);
                        cityListAdapter.setAllCheck(false);
                        cityListAdapter.notifyDataSetChanged();
                        provinceListAdapter.getCheckedNumber();
//                        lvCity.setVisibility(View.GONE);
                    }else if(checkecNumber==1){
                        provinceListAdapter.setAllCheck(true);
                        provinceListAdapter.notifyDataSetChanged();
                        province.setVisibility(View.VISIBLE);
//                        lvCity.setVisibility(View.GONE);
                        provinceListAdapter.getCheckedNumber();
                    }
                    if(checkecNumber == areas.size()-1){
                        areas.get(0).isCheck = true;
                    }
                    if (checkecNumber ==0 ){
                        province.setVisibility(View.GONE);
                        lvCity.setVisibility(View.GONE);
                    }
                }
            }
        });

        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinces.get(position).isCheck = !provinces.get(position).isCheck;
                int checkedNumber = provinceListAdapter.getCheckedNumber();
                if (checkedNumber>1){
                    cityListAdapter.setAllCheck(false);
                    cityListAdapter.notifyDataSetChanged();
                    cityListAdapter.getCheckedNumber();
                    lvCity.setVisibility(View.GONE);
                }else{
                    cityListAdapter.setAllCheck(true);
                    cityListAdapter.notifyDataSetChanged();
                    lvCity.setVisibility(View.VISIBLE);
                    cityListAdapter.getCheckedNumber();
                }
                provinceListAdapter.notifyDataSetChanged();
            }
        });
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city.get(position).isCheck = !city.get(position).isCheck;
                cityListAdapter.notifyDataSetChanged();
                cityListAdapter.getCheckedNumber();
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

    public void bindTextView(MarqueeText area) {
        this.area = area;
    }
}
