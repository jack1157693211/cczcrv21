package com.cczcrv.jack.cczcrv.Adapter;

import com.cczcrv.jack.cczcrv.ApiModel.LineProudctApi;
import com.cczcrv.jack.cczcrv.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by jack on 2018/2/23.
 */

public class LineHomeAdapter extends BaseQuickAdapter<List<LineProudctApi>, BaseViewHolder> {

    public LineHomeAdapter() {
        super(R.layout.home_horizontal_product);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<LineProudctApi> item) {

    }
}
