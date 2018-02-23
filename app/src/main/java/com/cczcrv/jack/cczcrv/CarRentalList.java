package com.cczcrv.jack.cczcrv;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.cczcrv.jack.cczcrv.Adapter.CarRentalItemAdapter;
import com.cczcrv.jack.cczcrv.ApiModel.AbpViewModel;
import com.cczcrv.jack.cczcrv.ApiModel.CarProductApi;
import com.cczcrv.jack.cczcrv.Tools.okHttp.callback.DialogCallback;
import com.cczcrv.jack.cczcrv.Tools.util.Urls;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarRentalList extends AppCompatActivity {
    private static final int PAGE_SIZE = 6;
    private int mNextRequestPage = 1;
    CarRentalItemAdapter carRentalItemAdapter;

    @BindView(R.id.car_rental_swipeLayout)
    SwipeRefreshLayout mCarRentalSwipeLayout;
    @BindView(R.id.car_rental_list)
    android.support.v7.widget.RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental_list);
        ButterKnife.bind(this);

        mCarRentalSwipeLayout.setColorSchemeColors(Color.rgb(225, 0, 0));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        carRentalItemAdapter = new CarRentalItemAdapter();
        carRentalItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);
        carRentalItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(carRentalItemAdapter);
        mRecyclerView.addOnItemTouchListener(
                new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                        //页面跳转
                    }
                }
        );
        initRefreshLayout();
        mCarRentalSwipeLayout.setRefreshing(true);
        refresh();

    }

    private void initRefreshLayout() {
        mCarRentalSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        mNextRequestPage = 1;
        carRentalItemAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        loadMore();
    }

    private void loadMore() {
        OkGo.<AbpViewModel<List<CarProductApi>>>post(Urls.URL_API + "products/GetCarList")
                .tag(this)
                .upJson("{\n" +
                        "  \"pageIndex\": "+mNextRequestPage+",\n" +
                        "  \"pageSize\": "+PAGE_SIZE+",\n" +
                        "  \"regionId\": 334,\n" +
                        "  \"classifyId\": 1\n" +
                        "}")
                .execute(new DialogCallback<AbpViewModel<List<CarProductApi>>>(this){
                    @Override
                    public void onSuccess(Response<AbpViewModel<List<CarProductApi>>> response) {
                        setData(false, response.body().getResult());
                        carRentalItemAdapter.setEnableLoadMore(true);
                        mCarRentalSwipeLayout.setRefreshing(false);
                    }
                    @Override
                    public void onError(Response<AbpViewModel<List<CarProductApi>>> response) {
                        carRentalItemAdapter.loadMoreFail();
                        carRentalItemAdapter.setEnableLoadMore(true);
                        mCarRentalSwipeLayout.setRefreshing(false);
                        Toast.makeText(CarRentalList.this, "加载错误!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setData(boolean b, List<CarProductApi> result) {
        mNextRequestPage++;
        final int size = result == null ? 0 : result.size();
        if (b) {
            carRentalItemAdapter.setNewData(result);
        } else {
            if (size > 0) {
                carRentalItemAdapter.addData(result);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            carRentalItemAdapter.loadMoreEnd(b);
            Toast.makeText(this, "没有更多了!", Toast.LENGTH_SHORT).show();
        } else {
            carRentalItemAdapter.loadMoreComplete();
        }
    }
}
