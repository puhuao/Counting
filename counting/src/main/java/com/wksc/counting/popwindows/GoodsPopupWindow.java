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
public class GoodsPopupWindow extends PopupWindow {

    public StringBuilder sbChannel = new StringBuilder();
    public StringBuilder sbPlatform = new StringBuilder();
    List<BaseWithCheckBean> type = new ArrayList<>();
    List<BaseWithCheckBean> names = new ArrayList<>();
    Activity mContext;
    ListView lvGoodsType,lvGoodsName;
    Button sure;
    MarqueeText goods;
    CheckBoxListAdapter typeListAdapter,nameListAdapter;
    public int superPosition;
    public GoodsPopupWindow(Activity context){
        super();
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout_goods,null);
        lvGoodsType = (ListView) view.findViewById(R.id.wine_type);
        lvGoodsName = (ListView) view.findViewById(R.id.wine_name);
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
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmisPopupwindow();
                backgroundAlpha(1f);

                if (sbChannel.length()>0)
                    sbChannel.delete(0,sbChannel.length());
                for (BaseWithCheckBean bean:typeListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbChannel.append(bean.name).append(",");
                    }
                }
                if (sbChannel.length()>0){
                    sbChannel.deleteCharAt(sbChannel.length()-1);
                }
                if (sbPlatform.length()>0)
                    sbPlatform.delete(0,sbPlatform.length());
                for (BaseWithCheckBean bean:nameListAdapter.getList()){
                    if (bean.isCheck == CheckBoxListAdapter.ALL){
                        sbPlatform.append(bean.name).append(",");
                    }
                }
                if (sbPlatform.length()>0){
                    sbPlatform.deleteCharAt(sbPlatform.length()-1);
                }
                if(sbChannel.length()>0||sbPlatform.length()>0)
                goods.setText(sbChannel+" "+sbPlatform);
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
                if (typeListAdapter.getCheckedNumber()>1){
                    lvGoodsName.setVisibility(View.INVISIBLE);
                }else if(typeListAdapter.getCheckedNumber()==1){
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
        this.goods = goods;
    }

}
