package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.wksc.counting.R;
import com.wksc.counting.adapter.ArticleListAdapter;
import com.wksc.counting.callBack.DialogCallback;
import com.wksc.counting.config.Constans;
import com.wksc.counting.config.Urls;
import com.wksc.counting.model.Notice;
import com.wksc.counting.model.NoticeResult;
import com.wksc.counting.tools.UrlUtils;
import com.wksc.counting.widegit.LoadMoreListView;
import com.wksc.framwork.BaseApplication;
import com.wksc.framwork.baseui.fragment.CommonFragment;
import com.wksc.framwork.platform.config.IConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by puhua on 2016/7/12.
 *
 * @
 */
public class ArticleListFragment extends CommonFragment {

    @Bind(R.id.lmlv_post)
    LoadMoreListView lmlvPost;
    @Bind(R.id.empty)
    TextView empty;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private IConfig config;
    private List<Notice> list = new ArrayList<>();
    private int pageNum = 1;
    ArticleListAdapter adapter;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_articles, null);
        setHeaderTitle("公告通知");
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        config = BaseApplication.getInstance().getCurrentConfig();
        initView();
        return v;
    }

    private void initView() {
        lmlvPost.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getArticles();
            }
        });
        lmlvPost.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (lmlvPost == null || lmlvPost.getChildCount() == 0) ?
                                0 : lmlvPost.getChildAt(0).getTop();
                swipeContainer.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum =1;
                getArticles();
            }
        });
        adapter = new ArticleListAdapter(getContext());
        lmlvPost.setAdapter(adapter);
        adapter.setList(list);
        lmlvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", adapter.getList().get(position).id);
                getContext().pushFragmentToBackStack(ArticleDetailFragment.class, bundle);
            }
        });
        getArticles();
    }

    private void getArticles() {
        StringBuilder sb = new StringBuilder(Urls.GET_ARTICLES);
        UrlUtils.getInstance().addSession(sb, config).praseToUrl(sb, "categaryName", "doc").praseToUrl(sb, "rows", "20")
                .praseToUrl(sb, "page", String.valueOf(pageNum));
        OkHttpUtils.post(sb.toString())//
                .tag(this)//
                .execute(new DialogCallback<NoticeResult>(getContext(), NoticeResult.class) {

                             @Override
                             public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                                 super.onError(isFromCache, call, response, e);
                             }

                             @Override
                             public void onResponse(boolean isFromCache, NoticeResult o, Request request, @Nullable Response response) {
                                 lmlvPost.onLoadMoreComplete();
                                 if (o != null) {
                                     if (o.rows != null && o.rows.size() > 0) {
                                         if (pageNum == 1){
                                             list.clear();
                                         }
                                         list.addAll(o.rows);
                                         adapter.notifyDataSetChanged();
                                         pageNum++;
                                         if (o.rows.size() < 20) {
                                             lmlvPost.setCanLoadMore(false);
                                         }
                                     } else {
                                         lmlvPost.setCanLoadMore(false);
                                     }
                                 }
                                 if (list.size() == 0) {
                                     lmlvPost.setEmptyView(empty);
                                 }
                             }
                         }
                );
    }

    @Override
    protected void lazyLoad() {

    }
}
