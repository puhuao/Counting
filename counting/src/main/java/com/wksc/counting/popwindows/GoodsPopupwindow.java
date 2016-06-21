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
import com.wksc.counting.adapter.GoodsListAdapter;
import com.wksc.counting.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class GoodsPopupwindow extends PopupWindow {
    List<AreaModel> scendTyep = new ArrayList<>();
    List<AreaModel> type = new ArrayList<>();
    List<AreaModel> names = new ArrayList<>();
    Activity mContext;
    ListView list ,lvwineYype,lvwineTaste,lvname;
    Button sure;
    GoodsListAdapter areaListAdapter,scendListTypeAdapter,typeListAdapter,nameListAdapter;
    public GoodsPopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_goods,null);
        list = (ListView) view.findViewById(R.id.wine_condition);
        lvwineYype = (ListView) view.findViewById(R.id.wine_type);
        lvwineTaste = (ListView) view.findViewById(R.id.wine_taste);
        lvname = (ListView) view.findViewById(R.id.wine_name);
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
//        areaListAdapter = new GoodsListAdapter(context);
//        reginListView.setAdapter(areaListAdapter);
//        reginListView.setVisibility(View.GONE);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
            }
        });
        scendTyep.add(new AreaModel("全部"));
        scendTyep.add(new AreaModel("白酒"));
        scendTyep.add(new AreaModel("黄酒"));
        scendTyep.add(new AreaModel("进口葡萄酒"));
        scendTyep.add(new AreaModel("国产葡萄酒"));
        scendTyep.add(new AreaModel("啤酒"));
        scendTyep.add(new AreaModel("器具"));
        type.add(new AreaModel("酱香型"));
        type.add(new AreaModel("浓香型"));
        names.add(new AreaModel("五粮液"));
        names.add(new AreaModel("泸州老窖"));
        names.add(new AreaModel("茅台"));
        names.add(new AreaModel("贡酒"));
        scendListTypeAdapter = new GoodsListAdapter(context);
        scendListTypeAdapter.setList(scendTyep);
        lvwineYype.setAdapter(scendListTypeAdapter);

        typeListAdapter = new GoodsListAdapter(context);
        typeListAdapter.setList(type);
        lvwineTaste.setAdapter(typeListAdapter);

        nameListAdapter = new GoodsListAdapter(context);
        nameListAdapter.setList(names);
        lvname.setAdapter(nameListAdapter);

        lvwineTaste.setVisibility(View.GONE);
        lvname.setVisibility(View.GONE);
        lvwineYype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    scendListTypeAdapter.setAllCheck(!scendTyep.get(position).isCheck);
                    scendListTypeAdapter.notifyDataSetChanged();
                    lvwineTaste.setVisibility(View.GONE);
                    lvname.setVisibility(View.GONE);
                }else{
                    scendTyep.get(0).isCheck = false;
                    scendTyep.get(position).isCheck = !scendTyep.get(position).isCheck;
                    scendListTypeAdapter.notifyDataSetChanged();
                    int checkecNumber =  scendListTypeAdapter.getCheckedNumber();
                    if (checkecNumber >1){
                        lvwineTaste.setVisibility(View.GONE);
                        typeListAdapter.setAllCheck(false);
                        typeListAdapter.notifyDataSetChanged();
                        lvname.setVisibility(View.GONE);
                        nameListAdapter.setAllCheck(false);
                        nameListAdapter.notifyDataSetChanged();
//                        lvCity.setVisibility(View.GONE);
                    }else if(checkecNumber==1){
                        typeListAdapter.setAllCheck(true);
                        typeListAdapter.notifyDataSetChanged();
                        lvwineTaste.setVisibility(View.VISIBLE);
//                        lvCity.setVisibility(View.GONE);
                    }
                    if(checkecNumber == scendTyep.size()-1){
                        scendTyep.get(0).isCheck = true;
                    }
                }
            }
        });

        lvwineTaste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type.get(position).isCheck = !type.get(position).isCheck;
                int checkedNumber = typeListAdapter.getCheckedNumber();
                if (checkedNumber>1){
                    nameListAdapter.setAllCheck(false);
                    nameListAdapter.notifyDataSetChanged();
                    lvname.setVisibility(View.GONE);
                }else{
                    nameListAdapter.setAllCheck(true);
                    nameListAdapter.notifyDataSetChanged();
                    lvname.setVisibility(View.VISIBLE);
                }
                typeListAdapter.notifyDataSetChanged();
            }
        });
        lvname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                names.get(position).isCheck = !names.get(position).isCheck;
                nameListAdapter.notifyDataSetChanged();
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
