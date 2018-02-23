package com.cczcrv.jack.cczcrv.Tools;

import android.widget.ImageView;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.ApiModel.LineProudctApi;
import com.cczcrv.jack.cczcrv.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by jack on 2018/1/25.
 */

public class HomeVerticalAdapter extends BaseQuickAdapter<LineProudctApi, BaseViewHolder> {

    public HomeVerticalAdapter() {
        super(R.layout.home_vertical_product,null);
    }

    @Override
    protected void convert(BaseViewHolder helper, LineProudctApi item) {

        Picasso.with(mContext).load(item.getCover()).into((ImageView)helper.getView(R.id.product_pic));
        helper.itemView.setTag(item.getId());
        ((TextView)helper.getView(R.id.product_name)).setText(item.getName());
        ((TextView)helper.getView(R.id.product_describe)).setText(item.getListExplain());
        ((TextView)helper.getView(R.id.product_money)).setText("¥ "+item.getPrice());
        ((TextView)helper.getView(R.id.product_start_city)).setText(item.getCityName());
    }
}
