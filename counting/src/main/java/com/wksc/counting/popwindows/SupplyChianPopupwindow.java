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

import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.widegit.MarqueeText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class SupplyChianPopupwindow extends PopupWindow {
    List<BaseWithCheckBean> listChains = new ArrayList<>();

    ListView list ,platform;
    Button sure;
    CheckBoxListAdapter channelListAdapter,platformListAdapter;
    Activity mContext;

    public StringBuilder sbChannel = new StringBuilder();
    public StringBuilder sbPlatform = new StringBuilder();
    private MarqueeText area;

    public SupplyChianPopupwindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_supply,null);
        list = (ListView) view.findViewById(R.id.supply);
        platform = (ListView) view.findViewById(R.id.platform);
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

        channelListAdapter = new CheckBoxListAdapter(context);
        channelListAdapter. setList(BaseDataUtil.channels());
        list.setAdapter(channelListAdapter);
        platformListAdapter = new CheckBoxListAdapter(context);
        platformListAdapter.setList(BaseDataUtil.platforms(0));
        platform.setAdapter(platformListAdapter);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
                backgroundAlpha(1f);
                if (sbChannel.length()>0)
                sbChannel.delete(0,sbChannel.length());
                for (BaseWithCheckBean bean:channelListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbChannel.append(bean.name).append(",");
                    }
                }
                if (sbChannel.length()>0){
                    sbChannel.deleteCharAt(sbChannel.length()-1);
                }
                if (sbPlatform.length()>0)
                sbPlatform.delete(0,sbPlatform.length());
                for (BaseWithCheckBean bean:platformListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbPlatform.append(bean.name).append(",");
                    }
                }
                if (sbPlatform.length()>0){
                    sbPlatform.deleteCharAt(sbPlatform.length()-1);
                }
                area.setText(sbChannel+" "+sbPlatform);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                platformListAdapter.setList(BaseDataUtil.platforms(position));
                platformListAdapter.notifyDataSetChanged();
                channelListAdapter.moveToNextStatus(position);
                channelListAdapter.notifyDataSetChanged();
            }
        });
        platform.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                platformListAdapter.moveToNextStatus(position);
                platformListAdapter.notifyDataSetChanged();

            }
        });

    }

    public void bindTextView(MarqueeText area) {
        this.area = area;
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
