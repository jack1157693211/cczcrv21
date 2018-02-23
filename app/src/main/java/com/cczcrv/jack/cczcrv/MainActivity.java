package com.cczcrv.jack.cczcrv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cczcrv.jack.cczcrv.ApiModel.AbpViewModel;
import com.cczcrv.jack.cczcrv.ApiModel.BannerApi;
import com.cczcrv.jack.cczcrv.ApiModel.LineProudctApi;
import com.cczcrv.jack.cczcrv.Base.BaseActivity;
import com.cczcrv.jack.cczcrv.Tools.GalleryAdapter;
import com.cczcrv.jack.cczcrv.Tools.GlideImageLoader;
import com.cczcrv.jack.cczcrv.Tools.HomeVerticalAdapter;
import com.cczcrv.jack.cczcrv.Tools.okHttp.callback.DialogCallback;
import com.cczcrv.jack.cczcrv.Tools.util.Urls;
import com.cczcrv.jack.cczcrv.wxapi.WXEntryActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;

import java.util.List;

interface RecyclerRequestCallBack {
    void success(List<LineProudctApi> data);

    void fail(Exception e);
}

class RecyclerRequest extends Thread {
    private static final int PAGE_SIZE = 6;
    private int mPage;
    private RecyclerRequestCallBack mCallBack;
    private Handler mHandler;

    private static boolean mFirstPageNoMore;
    private static boolean mFirstError = true;

    public RecyclerRequest(int page, RecyclerRequestCallBack callBack) {
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
                mCallBack.success(GetHomeHorizontalData());
            }
        });
    }

    List<LineProudctApi> GetHomeHorizontalData() {
        return null;
    }
}

public class MainActivity extends BaseActivity {
    private static final int PAGE_SIZE = 6;
    private DisplayMetrics dm;
    private int mNextRequestPage = 1;
    private HomeVerticalAdapter homeVerticalAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (OpenID == "") {
            Intent intent = new Intent(MainActivity.this, WXEntryActivity.class);
            startActivity(intent);
        }
        //-------------------toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Navigation Icon 要設定在 setSupoortActionBar 才有作用
        //否則會出現 back bottom
        toolbar.setNavigationIcon(R.mipmap.cc_login);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //--------------------toolbar

        //----轮播测试
        InitializeBanner();
        //----轮播测试

        //得到控件
        dm = getResources().getDisplayMetrics();

        //-----垂直
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(225, 0, 0));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //数据
        homeVerticalAdapter = new HomeVerticalAdapter();
        homeVerticalAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);
        homeVerticalAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//      mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(homeVerticalAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);
                String productId = view.getTag() + "";
                intent.putExtra("productId", productId);
                startActivity(intent);
            }
        });
        //end数据
        initRefreshLayout();
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();


        GetHomeHorizontalData();
        //-----垂直

        //------选项卡

        //------



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
        homeVerticalAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        loadMore();
    }

    private void loadMore() {
        OkGo.<AbpViewModel<List<LineProudctApi>>>post(Urls.URL_API + "products/GetLineList")
                .tag(this)
                .upJson("{\n" +
                        "  \"pageIndex\": " + mNextRequestPage + ",\n" +
                        "  \"pageSize\": " + PAGE_SIZE + ",\n" +
                        "  \"regionId\": 334,\n" +
                        "  \"classifyId\": 2\n" +
                        "}")
                .execute(new DialogCallback<AbpViewModel<List<LineProudctApi>>>(this) {
                    @Override
                    public void onSuccess(Response<AbpViewModel<List<LineProudctApi>>> response) {
                        setData(false, response.body().getResult());
                        homeVerticalAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Response<AbpViewModel<List<LineProudctApi>>> response) {
                        homeVerticalAdapter.loadMoreFail();
                        homeVerticalAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "加载错误!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            homeVerticalAdapter.setNewData(data);
        } else {
            if (size > 0) {
                homeVerticalAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            homeVerticalAdapter.loadMoreEnd(isRefresh);
            Toast.makeText(this, "没有更多了!", Toast.LENGTH_SHORT).show();
        } else {
            homeVerticalAdapter.loadMoreComplete();
        }
    }

    private void InitializeBanner() {
        //设置图片集合
        OkGo.<AbpViewModel<List<BannerApi>>>post(Urls.URL_API + "cms/GetBanners?bannerClassifyId=43")
                .tag(this)
                .execute(new DialogCallback<AbpViewModel<List<BannerApi>>>(this) {
                    @Override
                    public void onSuccess(Response<AbpViewModel<List<BannerApi>>> response) {
                        Fresco.initialize(MainActivity.this);
                        Banner banner = (Banner) findViewById(R.id.banner);
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        banner.setImages(response.body().getResult());
                        //banner设置方法全部调用完毕时最后调用
                        banner.start();
                    }
                });
    }

    private void GetHomeHorizontalData() {
        OkGo.<AbpViewModel<List<LineProudctApi>>>post(Urls.URL_API + "products/GetLineList")
                .tag(this)
                .upJson("{\n" +
                        "  \"pageIndex\": 1,\n" +
                        "  \"pageSize\": 6,\n" +
                        "  \"regionId\": 334,\n" +
                        "  \"classifyId\": 2,\n" +
                        "  \"attributes\": [\n" +
                        "    {\n" +
                        "      \"attributeId\": 3,\n" +
                        "      \"optionIds\": \"2\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .execute(new DialogCallback<AbpViewModel<List<LineProudctApi>>>(this) {
                    @Override
                    public void onSuccess(Response<AbpViewModel<List<LineProudctApi>>> response) {
                        //--水平滚动
                        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerView);
                        //设置布局管理器
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        //设置适配器
                        GalleryAdapter mAdapter = new GalleryAdapter(MainActivity.this, response.body().getResult(), dm);

                        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(MainActivity.this, ProductDetails.class);
                                String productId = view.getTag() + "";
                                intent.putExtra("productId", productId);
                                startActivity(intent);
                            }
                        });
                        mRecyclerView.setAdapter(mAdapter);
                        //--水平滚动
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, ProductDetails.class);
            startActivity(intent);
        } else if (id == R.id.tab_test_settings) {
            Intent intent = new Intent(MainActivity.this, TabTest.class);
            startActivity(intent);
        } else if (id == R.id.action_settings1) {
            Intent intent = new Intent(MainActivity.this, Review.class);
            startActivity(intent);
        } else if (id == R.id.action_settings2) {
            Intent intent = new Intent(MainActivity.this, LineCalendar.class);
            startActivity(intent);
        } else if (id == R.id.action_settings3) {
            Intent intent = new Intent(MainActivity.this, CarRentalList.class);
            startActivity(intent);
        }
        else if (id == R.id.action_settings4) {
            Intent intent = new Intent(MainActivity.this, CarRental.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
