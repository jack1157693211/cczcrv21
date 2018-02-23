package com.cczcrv.jack.cczcrv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cczcrv.jack.cczcrv.ApiModel.AbpViewModel;
import com.cczcrv.jack.cczcrv.ApiModel.LinePriceApi;
import com.cczcrv.jack.cczcrv.ApiModel.SubmitOrderAPI;
import com.cczcrv.jack.cczcrv.Model.PackageReturnModel;
import com.cczcrv.jack.cczcrv.Model.ProductDatePrice;
import com.cczcrv.jack.cczcrv.Tools.PackageAdapter;
import com.cczcrv.jack.cczcrv.Tools.okHttp.callback.DialogCallback;
import com.cczcrv.jack.cczcrv.Tools.util.StringUtils;
import com.cczcrv.jack.cczcrv.View.CommonCalendarView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cczcrv.jack.cczcrv.Tools.util.Urls.URL_API;

public class LineCalendar extends AppCompatActivity {

    private static CommonCalendarView calendarView;
    private Map<String, List> mYearMonthMap = new HashMap<>();
    private String startTime = null;
    private String endTime = null;
    private String mStart;
    private String mEnd;

    //返回数据
    private String productId;
    private List<SubmitOrderAPI.Package> mPackages;


    @OnClick(R.id.timeNextButton)
    void SelectTimeNextButton() {
        Intent intent = new Intent();
        intent.putExtra("productId", productId);
        intent.putExtra("startTime", startTime);
        Gson gson = new Gson();
        intent.putExtra("mPackages", mPackages == null ? null : gson.toJson(mPackages));
        setResult(SubmitOrder.RETURN_CALENDAR_CODE, intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_calendar);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        productId = bundle.getString("productId");

        Initialize();
    }

    private void Initialize() {
        if (productId.trim() != null & productId.trim() != "") {
            OkGo.<AbpViewModel<LinePriceApi>>post(URL_API + "products/GetAndroidLinePrice?id=" + productId)
                    .tag(LineCalendar.this)
                    .execute(new DialogCallback<AbpViewModel<LinePriceApi>>(this) {
                        @Override
                        public void onSuccess(Response<AbpViewModel<LinePriceApi>> response) {
                            //---套餐数据
                            RecyclerView recyclerView = findViewById(R.id.package_recyclerView);
                            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(LineCalendar.this);
                            linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

                            recyclerView.setLayoutManager(linearLayoutManager1);


                            List<PackageReturnModel> datas = new ArrayList<>();
                            for (LinePriceApi.Package i : response.body().getResult().getPackages()) {
                                PackageReturnModel mself = new PackageReturnModel();
                                mself.setId(i.getId());
                                mself.setCount(0);
                                mself.setMaxCount(i.getCount());
                                mself.setName(i.getPackageName());
                                mself.setPrice(i.getTodayPrice());
                                datas.add(mself);
                            }


                            PackageAdapter packageAdapter = new PackageAdapter(LineCalendar.this, datas, new PackageAdapter.PackageCountChangeEvent() {
                                @Override
                                public void CountChange(List<PackageReturnModel> changeData) {
                                    if (mStart != null && mStart.trim() != "") {
                                        Toast.makeText(LineCalendar.this, "请选择出发时间", Toast.LENGTH_SHORT);
                                    }
                                    if (changeData != null && changeData.size() > 0) {
                                        mPackages = new ArrayList<>();
                                        SubmitOrderAPI.Package mPackage;
                                        for (PackageReturnModel i : changeData) {
                                            mPackage = new SubmitOrderAPI.Package();
                                            mPackage.setId(i.getId());
                                            mPackage.setCount(i.getCount());
                                            mPackage.setStartDate(mStart);
                                            mPackage.setName(i.getName());
                                            mPackage.setPrice(i.getPrice());
                                            mPackages.add(mPackage);
                                        }
                                    }
                                }
                            });

                            recyclerView.setAdapter(packageAdapter);
                            //---

                            List<ProductDatePrice> mDatePriceList = new ArrayList<>();
                            LinePriceApi.Package mPackage = null;
                            if (response.body().getResult().getPackages().size() > 0) {
                                mPackage = response.body().getResult().getPackages().get(0);
                                for (LinePriceApi.DatePrice i : mPackage.getDatePrices()) {
                                    ProductDatePrice price = new ProductDatePrice();
                                    price.setPriceDate(i.getDate());
                                    price.setPrice(i.getPrice());
                                    mDatePriceList.add(price);
                                }
                                for (ProductDatePrice productDatePrice : mDatePriceList) {//把价格数据改为同一个月的list 在一个key value里，减少渲染界面时循环判断数量
                                    productDatePrice.getPriceDate();
                                    String yearMonth = TextUtils.substring(productDatePrice.getPriceDate(), 0, TextUtils.lastIndexOf(productDatePrice.getPriceDate(), '-'));
                                    List list = mYearMonthMap.get(yearMonth);
                                    if (list == null) {
                                        list = new ArrayList();
                                        list.add(productDatePrice);
                                        mYearMonthMap.put(yearMonth, list);
                                    } else {
                                        list.add(productDatePrice);
                                    }
                                }

                                LineCalendar.calendarView = (CommonCalendarView) findViewById(R.id.calendarView);

                                LineCalendar.calendarView.init(new CommonCalendarView.DatePickerController() {
                                    @Override
                                    public int getMaxYear() {
                                        return 2020;
                                    }

                                    @Override
                                    public void onDayOfMonthSelected(int year, int month, int day) {
//                                        Toast.makeText(LineCalendar.this, String.format("%s-%s-%s", year, StringUtils.leftPad(String.valueOf(month), 2, "0"),
//                                                StringUtils.leftPad(String.valueOf(day), 2, "0")), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onDayOfMonthAndDataSelected(int year, int month, int day, List obj) throws ParseException {
                                        if (obj == null) {
                                            return;
                                        }
                                        String priceDate = String.format("%s-%s-%s", year,
                                                StringUtils.leftPad(month + "", 2, "0"), StringUtils.leftPad(String.valueOf(day), 2, "0"));
                                        for (int i = 0; i < obj.size(); i++) {
                                            ProductDatePrice datePrice = (ProductDatePrice) obj.get(i);
                                            if (datePrice == null) {
                                                continue;
                                            }
                                            if (TextUtils.equals(datePrice.getPriceDate(), priceDate)) {
                                                startTime = priceDate;
//                                                if (startTime != null && startTime.equals(endTime) && startTime.equals(priceDate)) {
//                                                    startTime = null;
//                                                    endTime = null;
//                                                } else if (startTime == null) {
//                                                    startTime = priceDate;
//                                                } else if (endTime == null) {
//                                                    endTime = priceDate;
//                                                } else if (startTime.equals(priceDate)) {
//                                                    startTime = null;
//                                                } else if (endTime.equals(priceDate)) {
//                                                    endTime = null;
//                                                } else {
//                                                    startTime = priceDate;
//                                                    endTime = null;
//                                                }
//                                                if (startTime != null && endTime != null) {
//                                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                                                    if (format.parse(startTime).getTime() > format.parse(endTime).getTime()) {
//                                                        String t = startTime;
//                                                        startTime = endTime;
//                                                        endTime = t;
//                                                    }
//                                                }
//                                                Toast.makeText(LineCalendar.this, datePrice.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void showOtherFields(Object obj, View view, int gridItemYear, int gridItemMonth, int gridItemDay, List<CommonCalendarView.GridViewHolder> views) throws ParseException {
//                                        for (CommonCalendarView.GridViewHolder i:views) {
//                                                i.mLineLayout.setBackgroundColor(Color.parseColor("#fdfdfd"));
//                                        }

                                        //当你设置了数据源之后，界面渲染会循环调用showOtherFields方法，在该方法中实现同一日期设置界面显示效果。
                                        ProductDatePrice productDatePrice = (ProductDatePrice) obj;

                                        String time = String.format("%s-%s-%s", gridItemYear,
                                                StringUtils.leftPad(gridItemMonth + "", 2, "0"), StringUtils.leftPad(String.valueOf(gridItemDay), 2, "0"));
                                        if (TextUtils.equals(productDatePrice.getPriceDate(), time)) {
                                            CommonCalendarView.GridViewHolder viewHolder = (CommonCalendarView.GridViewHolder) view.getTag();
                                            viewHolder.mPriceTv.setText(String.format("¥ %s", productDatePrice.getPrice()));
                                            viewHolder.mPriceTv.setTag(time);
                                            view.setEnabled(true);
                                            viewHolder.mTextView.setEnabled(true);

                                            if (startTime != null && endTime != null) {
                                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                                Calendar ca = Calendar.getInstance();
                                                Date curDate = format.parse(startTime);
                                                Date curEndtime = format.parse(endTime);
                                                while (curDate.compareTo(curEndtime) <= 0) {
                                                    ca.setTime(curDate);

                                                    if (format.format(curDate).equals(time)) {
                                                        viewHolder.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
                                                    }

                                                    ca.add(ca.DATE, 1);
                                                    curDate = ca.getTime();
                                                }
                                            } else if (startTime != null && startTime.equals(time)) {
                                                viewHolder.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
                                            } else if (endTime != null && endTime.equals(time)) {
                                                viewHolder.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
                                            } else {
                                                viewHolder.mLineLayout.setBackgroundColor(Color.parseColor("#fdfdfd"));
                                            }
                                        }
                                    }

                                    @Override
                                    public void PageChangeEvent(List<CommonCalendarView.GridViewHolder> views) throws ParseException {

                                        if (startTime != null && endTime != null) {
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            Calendar ca = Calendar.getInstance();
                                            Date curDate = format.parse(startTime);
                                            Date curEndtime = format.parse(endTime);
                                            while (curDate.compareTo(curEndtime) <= 0) {
                                                ca.setTime(curDate);
                                                for (CommonCalendarView.GridViewHolder i : views) {
                                                    if (format.format(curDate).equals(i.mTextView.getText())) {
                                                        i.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
                                                    } else {
                                                        i.mLineLayout.setBackgroundColor(Color.parseColor("#fdfdfd"));
                                                    }
                                                }
                                                ca.add(ca.DATE, 1);
                                                curDate = ca.getTime();
                                            }
                                        } else {
                                            for (CommonCalendarView.GridViewHolder i : views) {
                                                String mtag = i.mPriceTv.getTag() + "";
                                                if (mtag != null && mtag.trim() != "") {
                                                    if (startTime != null && startTime.trim() != "" && startTime.equals(mtag)) {
                                                        i.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
                                                    } else if (endTime != null && endTime.trim() != "" && endTime.equals(mtag)) {
                                                        i.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
                                                    } else {
                                                        i.mLineLayout.setBackgroundColor(Color.parseColor("#fdfdfd"));
                                                    }
                                                }
                                            }
                                        }


//                                        else if (startTime != null && startTime.equals(time)) {
//                                            viewHolder.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
//                                        } else if (endTime != null && endTime.equals(time)) {
//                                            viewHolder.mLineLayout.setBackgroundColor(Color.parseColor("#ffb366"));
//                                        } else {
//                                            viewHolder.mLineLayout.setBackgroundColor(Color.parseColor("#fdfdfd"));
//                                        }
                                    }

                                    @Override
                                    public Map<String, List> getDataSource() {
                                        return mYearMonthMap;
                                    }
                                });
                            }
                        }
                    });
        }
    }
}
