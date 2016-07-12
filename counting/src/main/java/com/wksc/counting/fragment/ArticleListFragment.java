package com.wksc.counting.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.wksc.counting.R;
import com.wksc.counting.widegit.LoadMoreListView;
import com.wksc.framwork.baseui.fragment.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_articles, null);
        hideLeftButton();
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        lmlvPost.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                loadData();
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
//                doRefresh();
            }
        });


//        lmlvPost.onLoadMoreComplete();
//        if (result != null) {
//            List<ClassPostInfo> storesList = result.data.elements;
//            if (storesList != null && storesList.size() > 0) {
//                pageNum++;
//                isFirstLoad = false;
//                list.addAll(storesList);
//                adapter.notifyDataSetChanged();
//                if (storesList.size() < pageSize) {
//                    lmlvPost.setCanLoadMore(false);
//                }
//            } else {
//                lmlvPost.setCanLoadMore(false);
//            }
//        }
//        if (list.size() == 0) {
//            lmlvPost.setEmptyView(empty);
//        }
//    }
    }

    @Override
    protected void lazyLoad() {

    }
}
