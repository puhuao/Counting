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
import com.wksc.counting.adapter.SupplyListAdapter;
import com.wksc.counting.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SupplyChianPopupwindow extends PopupWindow {
    List<AreaModel> listChains = new ArrayList<>();

    ListView list ;
    Button sure;
    SupplyListAdapter areaListAdapter;
    Activity mContext;
    public SupplyChianPopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_supply,null);
        list = (ListView) view.findViewById(R.id.supply);
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

        listChains.add(new AreaModel("全部"));
        listChains.add(new AreaModel("渠道A"));
        listChains.add(new AreaModel("渠道B"));
        listChains.add(new AreaModel("渠道C"));
        listChains.add(new AreaModel("渠道D"));

        areaListAdapter = new SupplyListAdapter(context);
        areaListAdapter. setList(listChains);
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listChains.get(position).isCheck = !listChains.get(position).isCheck;
                areaListAdapter.notifyDataSetChanged();
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
