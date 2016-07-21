package com.wksc.counting.popwindows;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wksc.counting.Basedata.BaseDataUtil2;
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
public class ChannelPopupWindow2 extends BasePopupWindow {
    List<BaseWithCheckBean> listChains = new ArrayList<>();

    ListView list ,platform;
    Button sure;
    LinearLayout layout_platforms;
    CheckBox checkBox1,checkBox2;
    CheckBoxListAdapter channelListAdapter,platformListAdapter;
    Activity mContext;

    public StringBuilder sbChannel = new StringBuilder();
    public StringBuilder sbPlatform = new StringBuilder();
    public StringBuilder sbChannelCode = new StringBuilder();
    public StringBuilder sbPlatformCode = new StringBuilder();
//    private MarqueeText area;

    public ChannelPopupWindow2(Activity context){
        super();
        mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.pop_layout_supply,null);
        list = (ListView) contentView.findViewById(R.id.supply);
        platform = (ListView) contentView.findViewById(R.id.platform);
        sure = (Button) contentView.findViewById(R.id.sure);
        checkBox1 = (CheckBox) contentView.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) contentView.findViewById(R.id.checkbox2);
        layout_platforms = (LinearLayout) contentView.findViewById(R.id.platforms);
        this.setContentView(contentView);
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
        init();
        channelListAdapter = new CheckBoxListAdapter(context);
        channelListAdapter. setList(BaseDataUtil2.channels());
        list.setAdapter(channelListAdapter);
        platformListAdapter = new CheckBoxListAdapter(context);
        platformListAdapter.setList(BaseDataUtil2.platforms(0));
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
                platformListAdapter.setList(BaseDataUtil2.platforms(position));
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
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    channelListAdapter.setAllCheck();
                    checkBox1.setText("反选");
                }else{
                    channelListAdapter.setAllNormal();
                    checkBox1.setText("全选");
                }
                layout_platforms.setVisibility(View.INVISIBLE);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    platformListAdapter.setAllCheck();
                    checkBox2.setText("反选");
                }else{
                    platformListAdapter.setAllNormal();
                    checkBox2.setText("全选");
                }
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
