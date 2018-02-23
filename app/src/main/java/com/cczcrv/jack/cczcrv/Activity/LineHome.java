package com.cczcrv.jack.cczcrv.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cczcrv.jack.cczcrv.Base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jack on 2018/2/22.
 */

public class LineHome extends BaseActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //绑定控件
        unbinder = ButterKnife.bind(this);


    }



}
