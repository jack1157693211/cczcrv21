package com.cczcrv.jack.cczcrv;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.cczcrv.jack.cczcrv.Model.review_item_model;
import com.cczcrv.jack.cczcrv.Tools.ReviewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

interface RequestCallBack {
    void success(List<review_item_model> data);

    void fail(Exception e);
}

class Request extends Thread {
    private static final int PAGE_SIZE = 3;
    private int mPage;
    private RequestCallBack mCallBack;
    private Handler mHandler;

    private static boolean mFirstPageNoMore;
    private static boolean mFirstError = true;

    public Request(int page, RequestCallBack callBack) {
        mPage = page;
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallBack.success(getSampleData(mPage, PAGE_SIZE));
            }
        });
    }

    private List<review_item_model> getSampleData(int mPage, int pageSize) {
        List<review_item_model> list = new ArrayList();
        review_item_model mode = new review_item_model();
        mode.setAllPicCount(8);
        mode.setContent("666666666666666");
        mode.setHead_pic(R.mipmap.head_pic + "");
        mode.setName("777777");
        mode.setPics(new String[]{R.mipmap.review_ioc1 + "", R.mipmap.review_ioc2 + "", R.mipmap.review_ioc3 + "", R.mipmap.review_ioc4 + ""});
        mode.setScore(4.8F);
        mode.setTime("2018-1-19");
        for (int i = 0; i < pageSize; i++) {
            list.add(mode);
        }
        return list;
    }

}

public class Review extends AppCompatActivity {
    private static final int PAGE_SIZE = 3;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ReviewAdapter mAdapter;

    private int mNextRequestPage = 1;
    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        dm = new DisplayMetrics();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(225, 0, 0));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        initRefreshLayout();
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        new Request(mNextRequestPage, new RequestCallBack() {
            @Override
            public void success(List<review_item_model> data) {
                setData(true, data);
                mAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void fail(Exception e) {
                mAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }).start();
    }

    private void initAdapter() {
        mAdapter = new ReviewAdapter(dm);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(Review.this, Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadMore() {
        new Request(mNextRequestPage, new RequestCallBack() {

            @Override
            public void success(List<review_item_model> data) {
                setData(false, data);
            }

            @Override
            public void fail(Exception e) {
                mAdapter.loadMoreFail();
            }
        }).start();
    }

    private void setData(boolean isRefresh, List<review_item_model> data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    public List<review_item_model> getSampleData(int page, int size) {
        List<review_item_model> list = new ArrayList();
        review_item_model mode = new review_item_model();
        mode.setAllPicCount(8);
        mode.setContent("666666666666666");
        mode.setHead_pic(R.mipmap.head_pic + "");
        mode.setName("777777");
        mode.setPics(new String[]{R.mipmap.review_ioc1 + "", R.mipmap.review_ioc2 + "", R.mipmap.review_ioc3 + "", R.mipmap.review_ioc4 + ""});
        mode.setScore(4.8F);
        mode.setTime("2018-1-19");
        for (int i = 0; i < size; i++) {
            list.add(mode);
        }
        return list;
    }
}
