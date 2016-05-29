package com.wksc.counting.popwindows;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.wksc.counting.R;
import com.wksc.counting.adapter.AreaListAdapter;

/**
 * Created by Administrator on 2016/5/29.
 */
public class TimePopupwindow extends PopupWindow {
    ListView list ;
    Button sure;
    AreaListAdapter areaListAdapter;
    public TimePopupwindow(Activity context){
        super();
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_area,null);
        list = (ListView) view.findViewById(R.id.diriction_area);
        sure = (Button) view.findViewById(R.id.sure);
        this.setContentView(view);
        this.setHeight(400);
        this.setWidth(600);
        this.setOutsideTouchable(true);
        areaListAdapter = new AreaListAdapter(context);
        list.setAdapter(areaListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
            }
        });
    }

    public void showPopupwindow(View view){
        this.showAsDropDown(view);
    }
    public void dissmisPopupwindow(){
        this.dismiss();
    }

}
