package com.cczcrv.jack.cczcrv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderSuccess extends AppCompatActivity {

    private String orderNo;
    private String startTime;
    private String proName;
    private String packageName;

    @BindView(R.id.proName)
    TextView mproName;
    @BindView(R.id.orderNo)
    TextView morderNo;
    @BindView(R.id.startTime)
    TextView mstartTime;
    @BindView(R.id.packageName)
    TextView mpackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderNo = bundle.getString("orderNo");
        startTime = bundle.getString("startTime");
        proName = bundle.getString("proName");
        packageName = bundle.getString("packageName");

        mproName.setText(proName);
        morderNo.setText(orderNo);
        mstartTime.setText(startTime);
        mpackageName.setText(packageName);
    }
}
