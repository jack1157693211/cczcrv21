package com.cczcrv.jack.cczcrv.Tools.mTabLayout.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cczcrv.jack.cczcrv.ProductDetails;
import com.cczcrv.jack.cczcrv.View.FitHeightViewPager;

/**
 * Created by jack on 2018/1/16.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 4;
    private String[] titles = new String[]{"商品详情", "行程概况", "房车介绍", "预订须知"};
    private String[] mContents = new String[4];
    FitHeightViewPager mViewPager;

    public MyFragmentPagerAdapter(FragmentManager supportFragmentManager, ProductDetails productDetails, String[] contents, FitHeightViewPager viewPager) {
        super(supportFragmentManager);
        mContents = contents;
        mViewPager = viewPager;

    }

    @Override
    public Fragment getItem(int position) {
        PageFragment pageFragment = PageFragment.newInstance(position, mContents[position], mViewPager);
        return pageFragment;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}