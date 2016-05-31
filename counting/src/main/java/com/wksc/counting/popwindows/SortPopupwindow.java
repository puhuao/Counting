package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wksc.counting.R;
import com.wksc.counting.adapter.SortListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SortPopupwindow extends PopupWindow implements AdapterView.OnItemClickListener {
    Activity mContext;
    ListView list ,lvsort;
    SortListAdapter listAdapter,sortAdapter;
    public SortPopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_sort,null);
        list = (ListView) view.findViewById(R.id.lv_month_data);
        lvsort = (ListView) view.findViewById(R.id.sort);
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
        listAdapter = new SortListAdapter(context);
        list.setAdapter(listAdapter);
        sortAdapter = new SortListAdapter(context);
        List<String> sorts = new ArrayList<>();
        sorts.add("升序");
        sorts.add("降序");
        sortAdapter.setList(sorts);
        lvsort.setAdapter(sortAdapter);
        list.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dissmisPopupwindow();
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
