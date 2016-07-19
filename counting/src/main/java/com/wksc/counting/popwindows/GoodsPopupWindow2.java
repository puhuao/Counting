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

import com.wksc.counting.Contorner.Condition;
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
public class GoodsPopupWindow2 extends BasePopupWindow {

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
    private boolean isFromList =false;
    private Condition mCondition;
    public void setCondition( Condition condition){
        this.mCondition = condition;
    }
    public GoodsPopupWindow2(Activity context,Condition condition){
        super();
        mContext = context;
        mCondition =condition;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_goods,null);
        lvGoodsType = (ListView) view.findViewById(R.id.wine_type);
        lvGoodsName = (ListView) view.findViewById(R.id.wine_name);
        sure = (Button) view.findViewById(R.id.sure);
        checkBox1 = (CheckBox) view.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkbox2);
        layout_names = (LinearLayout) view.findViewById(R.id.wine_names);
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
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (typeListAdapter.getCheckedNumber()==0&&nameListAdapter.getCheckedNumber()==0){
                        ToastUtil.showShortMessage(mContext,"请选择商品");
                        return;
                }
                dissmisPopupwindow();
                backgroundAlpha(1f);

                List<BaseWithCheckBean> scend = mCondition.checkGoodsClassScend();
                List<BaseWithCheckBean> first = mCondition.checkGoodsClassFirst();
            if (first.size()>=1&&scend.size()==0){
                mListener.conditionSelect(mCondition.sbGoodsClassFirstCode.toString(),
                        mCondition.sbGoodsClassFirst.toString(),0);
            }else if(first.size()==0&&scend.size()>=1){
                mListener.conditionSelect(mCondition.sbGoodsClassFirstCode.toString(),
                        mCondition.sbGoodsClassScend.toString(),1);
            }else if (first.size()==0&&scend.size()==0){
                mListener.conditionSelect(mCondition.sbGoodsClassFirstCode.toString(),
                        mCondition.sbGoodsClassScend.toString(),-1);
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
        type = mCondition.goodsClassFirst();
        typeListAdapter.setList(type);
        lvGoodsType.setAdapter(typeListAdapter);

        nameListAdapter = new CheckBoxListAdapter(context);
        names = mCondition.goodsClassScend(0);
        nameListAdapter.setList(names);
        lvGoodsName.setAdapter(nameListAdapter);

        lvGoodsType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCondition.updateGoodsStatus(position,-1,
                        typeListAdapter.moveToNextStatus(position));
                typeListAdapter.notifyDataSetChanged();
                superPosition = position;
                nameListAdapter.setList(mCondition.goodsClassScend(position));
                nameListAdapter.notifyDataSetChanged();
                if (typeListAdapter.getCheckedNumber()>1){
                    lvGoodsName.setVisibility(View.INVISIBLE);
                }else if(typeListAdapter.getCheckedNumber()==1){
                    if (nameListAdapter.getList().size()>0)
                    lvGoodsName.setVisibility(View.VISIBLE);
                }
                isFromList = true;
                if (typeListAdapter.getCheckedNumber()==typeListAdapter.getList().size()){
                    checkBox1.setChecked(true);
                }else{
                    checkBox1.setChecked(false);
                }
                isFromList = false;

            }
        });

        lvGoodsName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCondition.updateGoodsStatus(superPosition,position,
                        nameListAdapter.moveToNextStatus(position));
                nameListAdapter.notifyDataSetChanged();
                isFromList = true;
                if (nameListAdapter.getCheckedNumber()==nameListAdapter.getList().size()){
                    checkBox2.setChecked(true);
                }else{
                    checkBox2.setChecked(false);
                }
                isFromList = false;
            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isFromList)
                if (isChecked){
                    typeListAdapter.setAllCheck();
                }else{
                    typeListAdapter.setAllNormal();
                }
                isFromList = false;
                layout_names.setVisibility(View.INVISIBLE);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isFromList)
                if (isChecked){
                    nameListAdapter.setAllCheck();
                }else{
                    nameListAdapter.setAllNormal();
                }
                isFromList = false;
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

}
