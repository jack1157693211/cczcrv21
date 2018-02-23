package com.cczcrv.jack.cczcrv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.cczcrv.jack.cczcrv.ApiModel.AbpViewModel;
import com.cczcrv.jack.cczcrv.ApiModel.LinePriceApi;
import com.cczcrv.jack.cczcrv.ApiModel.ReturnGenerateOrderApi;
import com.cczcrv.jack.cczcrv.ApiModel.SubmitOrderAPI;
import com.cczcrv.jack.cczcrv.Base.BaseActivity;
import com.cczcrv.jack.cczcrv.Model.ContactModel;
import com.cczcrv.jack.cczcrv.Model.PackageReturnModel;
import com.cczcrv.jack.cczcrv.Model.SubCalculateDetailModel;
import com.cczcrv.jack.cczcrv.Tools.ContactCheckAdapter;
import com.cczcrv.jack.cczcrv.Tools.ContactInfoAdapter;
import com.cczcrv.jack.cczcrv.Tools.PackageAdapter;
import com.cczcrv.jack.cczcrv.Tools.SubmitCalculateDetailAdapter;
import com.cczcrv.jack.cczcrv.Tools.okHttp.callback.DialogCallback;
import com.cczcrv.jack.cczcrv.Tools.util.Urls;
import com.cczcrv.jack.cczcrv.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitOrder extends BaseActivity {
    private String SubmitSataSaveCode = "SSSC";
    private String productId;
    public int CALENDAR_CODE = 10001;
    public static int RETURN_CALENDAR_CODE = 20001;
    private String startTime;
    private List<LinePriceApi.Package> mPackages;
    private List<SubmitOrderAPI.Package> mSubmitOrderAPIPackage;
    private PackageAdapter mPackageAdapter;
    private ContactCheckAdapter contactCheckAdapter;
    private ContactInfoAdapter contactInfoAdapter;

    private SubmitCalculateDetailAdapter submitCalculateDetailAdapter;
    //需要提交的数据
    SubmitOrderAPI submitOrderAPI;

    @BindView(R.id.proName)
    TextView mproName;
    @BindView(R.id.startTime)
    TextView mstartTime;
    @BindView(R.id.packages)
    TextView mpackages;
    @BindView(R.id.couponName)
    TextView mcouponName;

    @BindView(R.id.contactName1)
    EditText mcontactName;
    @BindView(R.id.contactPhone1)
    EditText mcontactPhone;
    @BindView(R.id.leaveMessage)
    MultiAutoCompleteTextView mleaveMessage;

    @BindView(R.id.addService)
    RecyclerView maddService;
    @BindView(R.id.passengersCheck)
    RecyclerView mpassengersCheck;
    @BindView(R.id.passengers)
    RecyclerView mpassengers;
    @BindView(R.id.orderDetails)
    RecyclerView morderDetails;

    @OnClick(R.id.proName)
    void testweixin() {
        Intent intent = new Intent(SubmitOrder.this, WXEntryActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.startTime, R.id.textView7, R.id.packages, R.id.textView9})
    void openCalendar() {
        Intent intent = new Intent(SubmitOrder.this, LineCalendar.class);
        intent.putExtra("productId", productId);
        startActivityForResult(intent, CALENDAR_CODE);
    }

    public void aaaa(Response<AbpViewModel<ReturnGenerateOrderApi>> response) {
        final Gson gson = new Gson();
        Intent intent = new Intent(SubmitOrder.this, OrderSuccess.class);
        intent.putExtra("orderNo",response.body().getResult().getOrderNo());
        intent.putExtra("startTime",startTime);
        intent.putExtra("proName",mproName.getText()+"");
        intent.putExtra("packageName",mpackages.getText()+"");
        startActivity(intent);
    }

    @OnClick(R.id.orderSubmitButton)
    void orderSubmitButtonEvent() {
        //List<PackageReturnModel> da = mPackageAdapter.getmPackageReturnModels();

        List<ContactModel> a = contactCheckAdapter.getmResults();
        List<ContactModel> b = contactInfoAdapter.GetResults();
        if (mcontactName.getText() + "" == "" || mcontactPhone.getText() + "" == "") {
            Toast.makeText(this, "联系人不能为空", Toast.LENGTH_SHORT);
        }
        submitOrderAPI.setContactName(mcontactName.getText() + "");
        submitOrderAPI.setContactNo(mcontactPhone.getText() + "");
        submitOrderAPI.setLeavingMessage(mleaveMessage.getText() + "");

        submitOrderAPI.setOpenid(OpenID);
        submitOrderAPI.setUnionid(Unionid);
        submitOrderAPI.setEndDate(submitOrderAPI.getStartDate());
        final Gson gson = new Gson();
        OkGo.<AbpViewModel<ReturnGenerateOrderApi>>post(Urls.URL_API + "orders/Generate_LineOrder")
                .upJson(gson.toJson(submitOrderAPI))
                .tag(this)
                .execute(new DialogCallback<AbpViewModel<ReturnGenerateOrderApi>>(this) {
                    @Override
                    public void onSuccess(Response<AbpViewModel<ReturnGenerateOrderApi>> response) {
                        aaaa(response);
                    }

                    @Override
                    public void onError(Response<AbpViewModel<ReturnGenerateOrderApi>> response) {
                        super.onError(response);
                    }
                });
    }


    public void RefreshBill() {
        List<SubCalculateDetailModel> subCalculateDetailModels = new ArrayList<>();
        SubCalculateDetailModel subCalculateDetailModel;
        for (SubmitOrderAPI.Package i : submitOrderAPI.getPackages()) {
            subCalculateDetailModel = new SubCalculateDetailModel();
            subCalculateDetailModel.setName(i.getName());
            subCalculateDetailModel.setPrice(i.getPrice());
            subCalculateDetailModel.setCount(i.getCount());
            subCalculateDetailModels.add(subCalculateDetailModel);
        }
        for (SubmitOrderAPI.Extra i : submitOrderAPI.getExtras()) {
            subCalculateDetailModel = new SubCalculateDetailModel();
            subCalculateDetailModel.setName(i.getName());
            subCalculateDetailModel.setPrice(i.getPrice());
            subCalculateDetailModel.setCount(i.getCount());
            subCalculateDetailModels.add(subCalculateDetailModel);
        }
        Double totalMoney = 0.0;

        for (SubCalculateDetailModel i : subCalculateDetailModels) {
            Double money = i.getCount() * Double.parseDouble(i.getPrice());
            totalMoney = totalMoney + money;
            String st = "¥" + i.getPrice() + " X " + i.getCount() + " = " + money;
            i.setValue(st);
        }
        subCalculateDetailModel = new SubCalculateDetailModel();
        subCalculateDetailModel.setName("应付金额");
        subCalculateDetailModel.setValue("¥" + totalMoney);
        subCalculateDetailModels.add(subCalculateDetailModel);

        submitOrderAPI.setTotalPrice(totalMoney);
        submitCalculateDetailAdapter = new SubmitCalculateDetailAdapter(SubmitOrder.this, subCalculateDetailModels);
        morderDetails.setAdapter(submitCalculateDetailAdapter);
        submitCalculateDetailAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALENDAR_CODE && resultCode == RETURN_CALENDAR_CODE) {
            Gson gson = new Gson();
            startTime = data.getStringExtra("startTime");

            productId = data.getStringExtra("productId");

            String mPackagesStr = data.getStringExtra("mPackages");

            if (startTime != null && productId != null && mPackagesStr != null) {
                Type type = new TypeToken<List<SubmitOrderAPI.Package>>() {
                }.getType();

                mSubmitOrderAPIPackage = gson.fromJson(mPackagesStr, type);

                if (startTime.trim() != "") {
                    mstartTime.setText(startTime);
                }

                if (mSubmitOrderAPIPackage.size() > 0) {
                    String packageInfo = "";
                    for (SubmitOrderAPI.Package i : mSubmitOrderAPIPackage) {
                        packageInfo += i.getName() + "X" + i.getCount() + " ";
                    }
                    if (packageInfo == "") {
                        packageInfo = "未选择";
                    }
                    mpackages.setText(packageInfo);
                }

                submitOrderAPI.setStartDate(startTime);
                for (SubmitOrderAPI.Package i : mSubmitOrderAPIPackage) {
                    i.setStartDate(startTime);
                }
                submitOrderAPI.setPackages(mSubmitOrderAPIPackage);
                RefreshBill();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        submitOrderAPI = new SubmitOrderAPI();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        productId = bundle.getString("productId");

        if (savedInstanceState != null) {
            Gson gson = new Gson();
            submitOrderAPI = gson.fromJson(savedInstanceState.getString(SubmitSataSaveCode), SubmitOrderAPI.class);
            productId = submitOrderAPI.getProductId() + "";
        }
        Initialize();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        super.onSaveInstanceState(outState);
        outState.putString(SubmitSataSaveCode, gson.toJson(submitOrderAPI));

    }

    private void Initialize() {
        if (productId.trim() != null && productId.trim() != "") {
            OkGo.<AbpViewModel<LinePriceApi>>post(Urls.URL_API + "products/GetAndroidLinePrice?id=" + productId)
                    .tag(this)
                    .execute(new DialogCallback<AbpViewModel<LinePriceApi>>(this) {
                        @Override
                        public void onSuccess(Response<AbpViewModel<LinePriceApi>> response) {
                            if (response.body() == null) {
                                return;
                            }
                            mproName.setText(response.body().getResult().getProductName());
                            //提交数据处理
                            submitOrderAPI.setProductId(Long.parseLong(productId));
                            submitOrderAPI.setProductName(response.body().getResult().getProductName());
                            submitOrderAPI.setProductCover(response.body().getResult().getCover());

                            //增值服务
                            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(SubmitOrder.this);
                            linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                            maddService.setLayoutManager(linearLayoutManager1);

                            List<PackageReturnModel> mExtrasDatas = new ArrayList<>();
                            for (LinePriceApi.Extra i : response.body().getResult().getExtras()) {
                                PackageReturnModel mself = new PackageReturnModel();
                                mself.setPrice(i.getPrice());
                                mself.setName(i.getExtraName());
                                mself.setMaxCount(i.getCount());
                                mself.setCount(0);
                                mself.setId(i.getId());
                                mExtrasDatas.add(mself);
                            }
                            //增值服务 适配器 以及 数量改变 回调事件
                            mPackageAdapter = new PackageAdapter(SubmitOrder.this, mExtrasDatas, new PackageAdapter.PackageCountChangeEvent() {
                                @Override
                                public void CountChange(List<PackageReturnModel> changeData) {
                                    if (changeData != null && changeData.size() > 0) {
                                        List<SubmitOrderAPI.Extra> extras = new ArrayList<>();
                                        SubmitOrderAPI.Extra extra;
                                        for (PackageReturnModel i : changeData) {
                                            extra = new SubmitOrderAPI.Extra();
                                            extra.setName(i.getName());
                                            extra.setId(i.getId());
                                            extra.setCount(i.getCount());
                                            extra.setPrice(i.getPrice());
                                            extras.add(extra);
                                        }
                                        submitOrderAPI.setExtras(extras);
                                        RefreshBill();
                                    }
                                }
                            });
                            maddService.setAdapter(mPackageAdapter);
                            //end增值服务
                            //复选框联系人
                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(SubmitOrder.this);
                            linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                            mpassengersCheck.setLayoutManager(linearLayoutManager2);

                            List<ContactModel> contactModels = new ArrayList<>();
                            ContactModel contactModel = new ContactModel();
                            contactModel.setName("带个v");
                            contactModel.setPhone("6666666");
                            contactModels.add(contactModel);
                            contactCheckAdapter = new ContactCheckAdapter(SubmitOrder.this, contactModels);
                            mpassengersCheck.setAdapter(contactCheckAdapter);
                            //联系人
                            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(SubmitOrder.this);
                            linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
                            mpassengers.setLayoutManager(linearLayoutManager3);
                            List<ContactModel> contactModels1 = new ArrayList<>();
                            ContactModel contactModel1 = new ContactModel();
                            contactModels1.add(contactModel1);
                            contactInfoAdapter = new ContactInfoAdapter(SubmitOrder.this, contactModels1);
                            mpassengers.setAdapter(contactInfoAdapter);

                            //订单计算
                            LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(SubmitOrder.this);
                            linearLayoutManager4.setOrientation(LinearLayoutManager.VERTICAL);
                            morderDetails.setLayoutManager(linearLayoutManager4);

                        }
                    });
        }
    }
}
