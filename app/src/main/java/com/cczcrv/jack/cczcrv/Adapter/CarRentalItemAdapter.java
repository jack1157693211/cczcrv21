package com.cczcrv.jack.cczcrv.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.ApiModel.CarProductApi;
import com.cczcrv.jack.cczcrv.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by jack on 2018/2/8.
 */

public class CarRentalItemAdapter extends BaseQuickAdapter<CarProductApi, BaseViewHolder> {


    public CarRentalItemAdapter() {
        super(R.layout.car_rental_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarProductApi item) {
        Picasso.with(mContext).load(item.getCover()).into((ImageView) helper.getView(R.id.car_rental_cover));
        helper.itemView.setTag(item.getId());
        ((TextView) helper.getView(R.id.car_rental_name)).setText(item.getName());
        ((TextView) helper.getView(R.id.car_rental_price)).setText("¥ "+item.getPrice());
        if (item.getGearType()!=""&&item.getGearType()!=null) {
            ((TextView) helper.getView(R.id.car_rental_gearType)).setVisibility(View.VISIBLE);
            ((TextView) helper.getView(R.id.car_rental_gearType)).setText(item.getPrice());
        }
        if (item.getBeds()!=""&&item.getBeds()!=null) {
            ((TextView) helper.getView(R.id.car_rental_beds)).setVisibility(View.VISIBLE);
            ((TextView) helper.getView(R.id.car_rental_beds)).setText(item.getBeds());
        }
        if (item.getNumberLoad()!=""&&item.getNumberLoad()!=null) {
            ((TextView) helper.getView(R.id.car_rental_numberLoad)).setVisibility(View.VISIBLE);
            ((TextView) helper.getView(R.id.car_rental_numberLoad)).setText(item.getNumberLoad());
        }
        if (item.getDrivingLicense()!=""&&item.getDrivingLicense()!=null) {
            ((TextView) helper.getView(R.id.car_rental_drivingLicense)).setVisibility(View.VISIBLE);
            ((TextView) helper.getView(R.id.car_rental_drivingLicense)).setText(item.getDrivingLicense());
        }
    }
}
