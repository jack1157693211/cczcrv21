package com.cczcrv.jack.cczcrv;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.ApiModel.AbpViewModel;
import com.cczcrv.jack.cczcrv.ApiModel.LineProudctDetailsApi;
import com.cczcrv.jack.cczcrv.View.FitHeightViewPager;
import com.cczcrv.jack.cczcrv.Tools.GlideImageLoader;
import com.cczcrv.jack.cczcrv.Tools.mTabLayout.TabLayout.MyFragmentPagerAdapter;
import com.cczcrv.jack.cczcrv.Tools.okHttp.callback.DialogCallback;
import com.cczcrv.jack.cczcrv.Tools.util.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.zzhoujay.richtext.RichText;

public class ProductDetails extends AppCompatActivity {

    private String productId = null;
    private FitHeightViewPager viewPager;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        productId = bundle.getString("productId");

        Initialize();

        TextView tv_selectTime = findViewById(R.id.selectTime);
        tv_selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productId.trim() != null && productId.trim() != "") {
                    Intent intent = new Intent(ProductDetails.this, SubmitOrder.class);
                    intent.putExtra("productId", productId);
                    startActivity(intent);
                }
            }
        });
        Button button = findViewById(R.id.proDetailsNext);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this, SubmitOrder.class);
                intent.putExtra("productId", productId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Initialize() {
        OkGo.<AbpViewModel<LineProudctDetailsApi>>post(Urls.URL_API + "products/GetLineDetail?id=" + productId)
                .tag(this)
                .execute(new DialogCallback<AbpViewModel<LineProudctDetailsApi>>(this) {
                    @Override
                    public void onSuccess(Response<AbpViewModel<LineProudctDetailsApi>> response) {
                        LineProudctDetailsApi lineProudctDetails = response.body().getResult();
                        TextView productName = findViewById(R.id.productName);
                        TextView productPrice = findViewById(R.id.productPrice);
                        TextView savePrice = findViewById(R.id.savePrice);
                        TextView startCity = findViewById(R.id.startCity);
                        productName.setText(lineProudctDetails.getName());
                        productPrice.setText(lineProudctDetails.getPrice());
                        if (lineProudctDetails.getPrice() != null && lineProudctDetails.getOriginPrice() != null) {
                            int saveMoney =(int) (Float.parseFloat(lineProudctDetails.getPrice()) - Float.parseFloat(lineProudctDetails.getOriginPrice()));
                            savePrice.setText("已省"+Math.abs(saveMoney) + "元");
                        }
                        startCity.setText(lineProudctDetails.getStartCity());

                        //-------选项卡
                        RichText.initCacheDir(ProductDetails.this);
                        String[] contents = new String[]{
                                lineProudctDetails.getLineDetail() == null ? "" : lineProudctDetails.getLineDetail(),
                                lineProudctDetails.getTravelProfile() == null ? "" : lineProudctDetails.getTravelProfile(),
                                lineProudctDetails.getCarIntroduce() == null ? "" : lineProudctDetails.getCarIntroduce(),
                                lineProudctDetails.getLineReserveNotice() == null ? "" : lineProudctDetails.getLineReserveNotice()};
                        viewPager = (FitHeightViewPager) findViewById(R.id.viewPager);
                        linearLayout = findViewById(R.id.constraintLayout6);
                        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), ProductDetails.this, contents, viewPager);
                        viewPager.setAdapter(adapter);
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                viewPager.resetHeight(position);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                                int a = 1;
                            }
                        });
                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
                        tabLayout.setupWithViewPager(viewPager);

                        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                        tabLayout.setTabMode(TabLayout.MODE_FIXED);

                        // Fresco.initialize(ProductDetails.this);
                        Banner banner = (Banner) findViewById(R.id.proDetail_Banner);
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        banner.setImages(lineProudctDetails.getAlbums());
                        //banner设置方法全部调用完毕时最后调用
                        banner.start();
                    }
                });
    }
}
