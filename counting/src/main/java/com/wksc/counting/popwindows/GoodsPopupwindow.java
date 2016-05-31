package com.wksc.counting.popwindows;

import android.app.Activity;
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
import com.wksc.counting.adapter.GoodsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class GoodsPopupwindow extends PopupWindow {
    List<String> scendTyep = new ArrayList<>();
    List<String> type = new ArrayList<>();
    List<String> names = new ArrayList<>();
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
        areaListAdapter = new GoodsListAdapter(context);
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
            }
        });
        scendTyep.add("白酒");
        scendTyep.add("葡萄酒");
        scendTyep.add("米酒");
        type.add("酱香型");
        type.add("浓香型");
        names.add("五粮液");
        names.add("泸州老窖");
        names.add("茅台");
        names.add("贡酒");
        scendListTypeAdapter = new GoodsListAdapter(context);
        scendListTypeAdapter.setList(scendTyep);
        lvwineYype.setAdapter(scendListTypeAdapter);

        typeListAdapter = new GoodsListAdapter(context);
        typeListAdapter.setList(type);
        lvwineTaste.setAdapter(typeListAdapter);

        nameListAdapter = new GoodsListAdapter(context);
        nameListAdapter.setList(names);
        lvname.setAdapter(nameListAdapter);

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
