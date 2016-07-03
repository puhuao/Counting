package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.adapter.AreaListAdapter;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.adapter.IndexListAdapter;
import com.wksc.counting.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class IndexPopupWindow extends PopupWindow {
    ListView list ;
    Button sure;
    CheckBoxListAdapter areaListAdapter;
    Activity mContext;

    public IndexPopupWindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_index,null);
        list = (ListView) view.findViewById(R.id.diriction_area);
        sure = (Button) view.findViewById(R.id.sure);
        this.setContentView(view);
        this.setOutsideTouchable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setAnimationStyle(R.style.selectDataViewAnimation);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                dismiss();
            }
        });
        areaListAdapter = new CheckBoxListAdapter(context);
        areaListAdapter. setList(BaseDataUtil.coreItems());
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
                BaseDataUtil.updateCoreItemsStatus(position,areaListAdapter.moveToNextStatus(position));
                areaListAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showPopupwindow(View view){
        backgroundAlpha(0.5f);
        showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
        mContext.overridePendingTransition(com.wksc.framwork.R.anim.push_left_in,
                com.wksc.framwork.R.anim.push_left_out);
    }
    public void dissmisPopupwindow(){
        this.dismiss();
        mContext.overridePendingTransition(com.wksc.framwork.R.anim.push_left_in,
                com.wksc.framwork.R.anim.push_left_out);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0

        mContext.getWindow().setAttributes(lp);
    }

}
