package com.wksc.counting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.Basedata.BaseDataUtil;
import com.wksc.counting.R;
import com.wksc.counting.adapter.CheckBoxListAdapter;
import com.wksc.counting.adapter.StoreHistoryAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Urls;
import com.wksc.counting.event.CompareDetailRefreshEvent;
import com.wksc.counting.event.CoreIndextRefreshEvent;
import com.wksc.counting.model.MCU;
import com.wksc.counting.model.baseinfo.BaseWithCheckBean;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.NestedListView;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.platform.config.IConfig;
import com.wksc.framwork.util.StringUtils;
import com.wksc.framwork.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/20.
 */
public class SearchActivity1 extends Activity {
    @Bind(R.id.edit_query)
    EditText edit_query;
    @Bind(R.id.cancel)
    TextView canel;
    @Bind(R.id.empty)
    TextView empty;
    @Bind(R.id.stores)
    NestedListView stores;
    @Bind(R.id.reset)
    Button reset;
    @Bind(R.id.sure)
    Button sure;
    @Bind(R.id.history)
    NestedListView history;
    private CheckBoxListAdapter storsAdapter;
    private IConfig config;
    int flag;
    StringBuilder store = new StringBuilder();
    private StoreHistoryAdapter historyAdapter;
    public static List<String> historys = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        flag = getIntent().getIntExtra("flag", -1);
        initView();
    }

    private void initView() {
        historyAdapter = new StoreHistoryAdapter(this);
        history.setAdapter(historyAdapter);
        historyAdapter.setList(historys);
        storsAdapter = new CheckBoxListAdapter(this);
        if (BaseDataUtil.stores!=null);{
            storsAdapter.setList(BaseDataUtil.stores);
        }
        stores.setAdapter(storsAdapter);
        edit_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getData();
            }
        });
        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_query.setText("");
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storsAdapter.setAllNormal();
                storsAdapter.notifyDataSetChanged();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==-1){
                    finish();
                    CoreIndextRefreshEvent event = new CoreIndextRefreshEvent();
                    event.sb = store;
                    event.names = storsAdapter.sb.toString();
                    EventBus.getDefault().post(event);
                }else{
                    finish();
                    CompareDetailRefreshEvent compareDetailRefreshEvent = new CompareDetailRefreshEvent();
                    compareDetailRefreshEvent.sb = store;
                    compareDetailRefreshEvent.names = storsAdapter.sb.toString();
                    compareDetailRefreshEvent.pos = flag;
                    EventBus.getDefault().post(compareDetailRefreshEvent);
                }
            }
        });
        stores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                storsAdapter.moveToNextStatus(position);
                storsAdapter.notifyDataSetChanged();
                if (store.length() > 0) {
                    store.delete(0, store.length());
                }
                if (storsAdapter.getCheckedNumber(position) > 0)
                    for (int i = 0; i < storsAdapter.getList().size(); i++) {
                        BaseWithCheckBean bean = storsAdapter.getList().get(i);
                        if (bean.isCheck == CheckBoxListAdapter.ALL)
                            store.append(bean.code).append(",");
                    }
                if (store.length() > 0)
                    store.deleteCharAt(store.length() - 1);
            }
        });
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit_query.setText(historys.get(position));
            }
        });
    }

    private void getData() {
        String param = edit_query.getText().toString();

        if (StringUtils.isBlank(param)) {
            ToastUtil.showShortMessage(SearchActivity1.this, "请输入要查询门店所在的地区名");
            return;
        }
        config = BaseApplication.getInstance().getCurrentConfig();
        StringBuilder sb = new StringBuilder(Urls.STORS);
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "name", param);
        DialogCallback<MCU> callback = new DialogCallback<MCU>(SearchActivity1.this, MCU.class) {
            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
            }

            @Override
            public void onResponse(boolean isFromCache, MCU c, Request request, @Nullable Response response) {
                if (c != null)
                    historys.add(edit_query.getText().toString());
                    if (c.MCU.size() > 0) {
                        BaseDataUtil.stores = c.MCU;
                        storsAdapter.setList(c.MCU);
                        empty.setVisibility(View.GONE);
                        stores.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.VISIBLE);
                        stores.setVisibility(View.GONE);
                    }
            }
        };
        callback.setDialogHide();
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(callback);
    }
}
