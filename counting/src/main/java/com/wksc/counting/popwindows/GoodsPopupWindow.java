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
public class GoodsPopupWindow extends BasePopupWindow {

    public StringBuilder sbChannel = new StringBuilder();
    public StringBuilder sbPlatform = new StringBuilder();
    List<BaseWithCheckBean> type = new ArrayList<>();
    List<BaseWithCheckBean> names = new ArrayList<>();
    Activity mContext;
    ListView lvGoodsType,lvGoodsName;
    Button sure;
    LinearLayout layout_names;
    CheckBox checkBox1,checkBox2;
//    MarqueeText goods;
    CheckBoxListAdapter typeListAdapter,nameListAdapter;
    public int superPosition;
    public GoodsPopupWindow(Activity context){
        super();
        mContext = context;
        contentView= LayoutInflater.from(context).inflate(R.layout.pop_layout_goods,null);
        lvGoodsType = (ListView) contentView.findViewById(R.id.wine_type);
        lvGoodsName = (ListView) contentView.findViewById(R.id.wine_name);
        sure = (Button) contentView.findViewById(R.id.sure);
        checkBox1 = (CheckBox) contentView.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) contentView.findViewById(R.id.checkbox2);
        layout_names = (LinearLayout) contentView.findViewById(R.id.wine_names);
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
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
                backgroundAlpha(1f);

                List<BaseWithCheckBean> scend = BaseDataUtil.checkGoodsClassScend();
                List<BaseWithCheckBean> first = BaseDataUtil.checkGoodsClassFirst();
            if (first.size()>=1&&scend.size()==0){
                mListener.conditionSelect(BaseDataUtil.sbGoodsClassFirstCode.toString(),
                        BaseDataUtil.sbGoodsClassFirst.toString(),0);
            }else if(first.size()==0&&scend.size()>=1){
                mListener.conditionSelect(BaseDataUtil.sbGoodsClassFirstCode.toString(),
                        BaseDataUtil.sbGoodsClassScend.toString(),1);
            }else if (first.size()==0&&scend.size()==0){
                mListener.conditionSelect(BaseDataUtil.sbGoodsClassFirstCode.toString(),
                        BaseDataUtil.sbGoodsClassScend.toString(),-1);
            }

                if (sbChannel.length()>0)
                    sbChannel.delete(0,sbChannel.length());
                for (BaseWithCheckBean bean:typeListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbChannel.append(bean.code).append(",");

                    }
                }
                if (sbChannel.length()>0){
                    sbChannel.deleteCharAt(sbChannel.length()-1);
                }
                if (sbPlatform.length()>0)
                    sbPlatform.delete(0,sbPlatform.length());
                for (BaseWithCheckBean bean:nameListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbPlatform.append(bean.code).append(",");
                    }
                }
                if (sbPlatform.length()>0){
                    sbPlatform.deleteCharAt(sbPlatform.length()-1);
                }
//                if(sbChannel.length()>0||sbPlatform.length()>0)
//                goods.setText(sbChannel+" "+sbPlatform);
            }
        });

        typeListAdapter = new CheckBoxListAdapter(context);
        type = BaseDataUtil.goodsClassFirst();
        typeListAdapter.setList(type);
        lvGoodsType.setAdapter(typeListAdapter);

        nameListAdapter = new CheckBoxListAdapter(context);
        names = BaseDataUtil.goodsClassScend(0);
        nameListAdapter.setList(names);
        lvGoodsName.setAdapter(nameListAdapter);

        lvGoodsType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseDataUtil.updateGoodsStatus(position,-1,
                        typeListAdapter.moveToNextStatus(position));
                typeListAdapter.notifyDataSetChanged();
                superPosition = position;
                nameListAdapter.setList(BaseDataUtil.goodsClassScend(position));
                nameListAdapter.notifyDataSetChanged();
                if (typeListAdapter.getCheckedNumber(position)>1){
                    lvGoodsName.setVisibility(View.INVISIBLE);
                }else if(typeListAdapter.getCheckedNumber(position)==1){
                    lvGoodsName.setVisibility(View.VISIBLE);
                }

            }
        });

        lvGoodsName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseDataUtil.updateGoodsStatus(superPosition,position,
                        nameListAdapter.moveToNextStatus(position));
                nameListAdapter.notifyDataSetChanged();
            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    typeListAdapter.setAllCheck();
                    checkBox1.setText("反选");
                }else{
                    typeListAdapter.setAllNormal();
                    checkBox1.setText("全选");
                }
                layout_names.setVisibility(View.INVISIBLE);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    nameListAdapter.setAllCheck();
                    checkBox2.setText("反选");
                }else{
                    nameListAdapter.setAllNormal();
                    checkBox2.setText("全选");
                }
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

    public void bindTextView(MarqueeText goods) {
//        this.goods = goods;
    }

    public void setFirstSelect() {
        typeListAdapter.setAllCheck();
        checkBox1.setChecked(true);
    }
}
