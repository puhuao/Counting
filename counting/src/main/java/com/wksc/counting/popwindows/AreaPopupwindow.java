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
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.AreaCheckModel;
import com.wksc.counting.model.AreaModel;
import com.wksc.counting.widegit.MarqueeText;

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
    ListView list,province,lvCity ;
    Button sure;
    CheckBoxListAdapter areaListAdapter,provinceListAdapter,cityListAdapter;
    List<AreaCheckModel> areas = new ArrayList<>();
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


        areas.add(new AreaCheckModel("全国"));
        areas.add(new AreaCheckModel("东北大区"));
        areas.add(new AreaCheckModel("东南大区"));
        areas.add(new AreaCheckModel("西北大区"));
        areas.add(new AreaCheckModel("西南大区"));

        areaListAdapter = new CheckBoxListAdapter(context);
        areaListAdapter.isAll = true;
        areaListAdapter.setList(areas);
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb= new StringBuilder();
                    sb.append(areaListAdapter.sb).append(provinceListAdapter.sb).append(cityListAdapter.sb);
                dissmisPopupwindow();
                if (sb.length()>0){
                    area.setText(sb.toString());
                }else{
                    area.setText("地区");
                }

            }
        });

        provinces.add(new AreaCheckModel("四川省"));
        provinces.add(new AreaCheckModel("重庆市"));
        provinces.add(new AreaCheckModel("云南省"));
        provinces.add(new AreaCheckModel("贵州省"));


        city.add(new AreaCheckModel("成都"));
        city.add(new AreaCheckModel("绵阳"));
        city.add(new AreaCheckModel("德阳"));
        city.add(new AreaCheckModel("资阳"));
        city.add(new AreaCheckModel("遂宁"));
        city.add(new AreaCheckModel("泸州"));
        provinceListAdapter = new CheckBoxListAdapter(context);
        provinceListAdapter.setList(provinces);
        province.setAdapter(provinceListAdapter);
        cityListAdapter = new CheckBoxListAdapter(context);
        cityListAdapter.setList(city);
        lvCity.setAdapter(cityListAdapter);
        province.setVisibility(View.GONE);
        lvCity.setVisibility(View.GONE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    if (areas.get(position).isCheck == CheckBoxListAdapter.NORMAL)
                        areaListAdapter.setAllCheck();
                    else if(areas.get(position).isCheck == CheckBoxListAdapter.ALL)
                        areaListAdapter.setAllNormal();
//                    else
//                        areaListAdapter.moveToNextStatus(position);
                    areaListAdapter.getCheckedNumber();
                    areaListAdapter.notifyDataSetChanged();
                    province.setVisibility(View.GONE);
                    lvCity.setVisibility(View.GONE);
                }else{
//                    areas.get(position).isCheck = !areas.get(position).isCheck;
                    areaListAdapter.moveToNextStatus(position);
                   int checkecNumber =  areaListAdapter.getCheckedNumber();
                    if (checkecNumber >1&&checkecNumber<areas.size()-1){
                        areas.get(0).isCheck = CheckBoxListAdapter.HALF;
                        province.setVisibility(View.GONE);
//                        provinceListAdapter.setAllCheck(false);
                        provinceListAdapter.notifyDataSetChanged();
                        lvCity.setVisibility(View.GONE);
//                        cityListAdapter.setAllCheck(false);
                        cityListAdapter.notifyDataSetChanged();
                        provinceListAdapter.getCheckedNumber();
//                        lvCity.setVisibility(View.GONE);
                    }else if(checkecNumber==1){
                        provinceListAdapter.setAllCheck();
                        provinceListAdapter.notifyDataSetChanged();
                        province.setVisibility(View.VISIBLE);
//                        lvCity.setVisibility(View.GONE);
                        provinceListAdapter.getCheckedNumber();
                    }
                    areaListAdapter.notifyDataSetChanged();
                    if(checkecNumber == areas.size()-1){
                        areas.get(0).isCheck = CheckBoxListAdapter.ALL;
                        areaListAdapter.notifyDataSetChanged();
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
//                provinces.get(position).isCheck = !provinces.get(position).isCheck;
                provinceListAdapter.moveToNextStatus(position);
                int checkedNumber = provinceListAdapter.getCheckedNumber();

                if (checkedNumber>1){
//                    cityListAdapter.setAllCheck();
                    cityListAdapter.notifyDataSetChanged();
                    cityListAdapter.getCheckedNumber();
                    lvCity.setVisibility(View.GONE);
                }else{
                    cityListAdapter.setAllCheck();
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
//                city.get(position).isCheck = !city.get(position).isCheck;
                cityListAdapter.moveToNextStatus(position);
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
