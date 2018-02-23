package com.cczcrv.jack.cczcrv.Tools.mTabLayout.TabLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.R;
import com.cczcrv.jack.cczcrv.View.FitHeightViewPager;
import com.zzhoujay.richtext.RichText;

/**
 * Created by jack on 2018/1/16.
 */


public class PageFragment extends Fragment {

    public static final String ARGS_PAGE = "args_page";
    public static final String ARGS_Text = "args_text";
    private int mPage;
    private String mText;
    private static FitHeightViewPager customViewpager;


    public static PageFragment newInstance(int page, String text, FitHeightViewPager mViewPager) {
        Bundle args = new Bundle();

        args.putInt(ARGS_PAGE, page);
        args.putString(ARGS_Text, text);

        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        customViewpager=mViewPager;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
        mText = getArguments().getString(ARGS_Text);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_details_tab_content, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        //textView.setText("第"+mPage+"页");



        // 设置为Html
        RichText
                .fromHtml(mText)
                .into(textView);
        //textView.setText(Html.fromHtml(mText));

        customViewpager.setObjectForPosition(view,mPage);
        return view;
    }
}
