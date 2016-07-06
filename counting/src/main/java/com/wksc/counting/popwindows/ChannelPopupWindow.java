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
import com.wksc.framwork.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class ChannelPopupWindow extends BasePopupWindow {
    List<BaseWithCheckBean> listChains = new ArrayList<>();

    ListView list ,platform;
    Button sure;
    CheckBoxListAdapter channelListAdapter,platformListAdapter;
    Activity mContext;

    public StringBuilder sbChannel = new StringBuilder();
    public StringBuilder sbPlatform = new StringBuilder();
    public StringBuilder sbChannelCode = new StringBuilder();
    public StringBuilder sbPlatformCode = new StringBuilder();
//    private MarqueeText area;

    public ChannelPopupWindow(Activity context){
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
        this.setAnimationStyle(R.style.channelAnimation);
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
                int i = 0,j=0;


                if (sbChannel.length()>0)
                sbChannel.delete(0,sbChannel.length());
                if (sbChannelCode.length()>0)
                    sbChannelCode.delete(0,sbChannelCode.length());
                for (BaseWithCheckBean bean:channelListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbChannel.append(bean.name).append(",");
                        sbChannelCode.append(bean.code).append(",");
                        i++;
                    }
                }
                if (sbChannel.length()>0){
                    sbChannel.deleteCharAt(sbChannel.length()-1);
                }
                if (sbChannelCode.length()>0){
                    sbChannelCode.deleteCharAt(sbChannelCode.length()-1);
                }

                if (sbPlatform.length()>0)
                sbPlatform.delete(0,sbPlatform.length());
                if (sbPlatformCode.length()>0)
                    sbPlatformCode.delete(0,sbPlatformCode.length());
                for (BaseWithCheckBean bean:platformListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbPlatform.append(bean.name).append(",");
                        sbPlatformCode.append(bean.code).append(",");
                        j++;
                    }
                }
                if (sbPlatform.length()>0){
                    sbPlatform.deleteCharAt(sbPlatform.length()-1);
                }
                if (sbPlatformCode.length()>0){
                    sbPlatformCode.deleteCharAt(sbPlatformCode.length()-1);
                }

                if (i>=1&&j==0){
                    mListener.conditionSelect(sbChannelCode.toString(),sbChannel.toString(),0);
                }else if(i == 0&&j>=1){
                    mListener.conditionSelect(sbPlatformCode.toString(),sbPlatform.toString(),1);
                }else if(i == 0&&j==0){
                    mListener.conditionSelect(sbPlatformCode.toString(),sbPlatform.toString(),-1);
                }else{
                    ToastUtil.showShortMessage(mContext,"筛选条件的格式不正确" );
                }


//                area.setText(sbChannel+" "+sbPlatform);
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
//        this.area = area;
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