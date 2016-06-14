package com.wksc.counting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.activity.SalesComparisonActivity;
import com.wksc.counting.adapter.CoreIndexListAdapter;
import com.wksc.counting.model.CoreIndexModel;
import com.wksc.counting.popwindows.AreaPopupwindow;
import com.wksc.counting.popwindows.GoodsPopupwindow;
import com.wksc.counting.popwindows.IndexPopupwindow;
import com.wksc.counting.popwindows.SupplyChianPopupwindow;
import com.wksc.counting.popwindows.TimePopupwindow;
import com.wksc.counting.widegit.MarqueeText;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puhua on 2016/5/27.
 *
 * @
 */
public class CoreIndexFragment extends CommonFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.area)
    MarqueeText area;
    @Bind(R.id.goods)
    TextView goods;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.channel)
    TextView channel;
    @Bind(R.id.index)
    TextView index;

    CoreIndexListAdapter coreIndexListAdapter;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_core_index, null);
        hideLeftButton();
        setHeaderTitle("核心指标");

        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        coreIndexListAdapter = new CoreIndexListAdapter(getActivity());
        coreIndexListAdapter.setList(CoreIndexModel.getData());
        list.setAdapter(coreIndexListAdapter);
        list.setOnItemClickListener(this);
        area.setOnClickListener(this);
        goods.setOnClickListener(this);
        time.setOnClickListener(this);
        channel.setOnClickListener(this);
        index.setOnClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        getContext().pushFragmentToBackStack(RegisterFragment.class, null);
        startActivity(SalesComparisonActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.area:
                AreaPopupwindow areaPopupwindow = new AreaPopupwindow(getActivity());
                areaPopupwindow.bindTextView(area);
                areaPopupwindow.showPopupwindow(v);
                break;
            case R.id.goods:
                GoodsPopupwindow goodsPopupwindow = new GoodsPopupwindow(getActivity());
                goodsPopupwindow.showPopupwindow(v);
                break;
            case R.id.time:
                TimePopupwindow timePopupwindow = new TimePopupwindow(getActivity());
                timePopupwindow.showPopupwindow(v);
                break;
            case R.id.channel:
                SupplyChianPopupwindow supplyChianPopupwindow = new SupplyChianPopupwindow(getActivity());
                supplyChianPopupwindow.showPopupwindow(v);
                break;
            case R.id.index:
                IndexPopupwindow indexPopupwindow = new IndexPopupwindow(getActivity());
                indexPopupwindow.showPopupwindow(v);
                break;

        }
    }
}
