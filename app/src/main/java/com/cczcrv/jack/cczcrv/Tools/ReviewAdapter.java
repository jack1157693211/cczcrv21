package com.cczcrv.jack.cczcrv.Tools;


import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cczcrv.jack.cczcrv.Model.review_item_model;
import com.cczcrv.jack.cczcrv.R;
import com.cczcrv.jack.cczcrv.Tools.util.ToastUtils;
import com.cczcrv.jack.cczcrv.Tools.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/1/18.
 */

public class ReviewAdapter extends BaseQuickAdapter<review_item_model, BaseViewHolder> {
    private DisplayMetrics dm;

    public ReviewAdapter(DisplayMetrics dm) {
        super(R.layout.review_item);
        this.dm = dm;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, review_item_model item) {
        viewHolder
                //.setText(R.id.head_pic, item.getHead_pic())
                .setText(R.id.name, item.getName())
                .setText(R.id.time, item.getTime())
                .setText(R.id.content, item.getContent());
        //.setVisible(R.id.tweetRT, item.isRetweet())
        //.linkify(R.id.tweetText);
        ImageView score_img1 = viewHolder.getView(R.id.score_img1);
        ImageView score_img2 = viewHolder.getView(R.id.score_img2);
        ImageView score_img3 = viewHolder.getView(R.id.score_img3);
        ImageView score_img4 = viewHolder.getView(R.id.score_img4);
        ImageView score_img5 = viewHolder.getView(R.id.score_img5);
        int score = (int) (item.getScore() / 0.5);
        switch (score) {
            case 0:
                score_img1.setImageResource(R.mipmap.star3);
                score_img2.setImageResource(R.mipmap.star3);
                score_img3.setImageResource(R.mipmap.star3);
                score_img4.setImageResource(R.mipmap.star3);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 1:
                score_img1.setImageResource(R.mipmap.star2);
                score_img2.setImageResource(R.mipmap.star3);
                score_img3.setImageResource(R.mipmap.star3);
                score_img4.setImageResource(R.mipmap.star3);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 2:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star3);
                score_img3.setImageResource(R.mipmap.star3);
                score_img4.setImageResource(R.mipmap.star3);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 3:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star2);
                score_img3.setImageResource(R.mipmap.star3);
                score_img4.setImageResource(R.mipmap.star3);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 4:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star1);
                score_img3.setImageResource(R.mipmap.star3);
                score_img4.setImageResource(R.mipmap.star3);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 5:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star1);
                score_img3.setImageResource(R.mipmap.star2);
                score_img4.setImageResource(R.mipmap.star3);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 6:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star1);
                score_img3.setImageResource(R.mipmap.star1);
                score_img4.setImageResource(R.mipmap.star3);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 7:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star1);
                score_img3.setImageResource(R.mipmap.star1);
                score_img4.setImageResource(R.mipmap.star2);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 8:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star1);
                score_img3.setImageResource(R.mipmap.star1);
                score_img4.setImageResource(R.mipmap.star1);
                score_img5.setImageResource(R.mipmap.star3);
                break;
            case 9:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star1);
                score_img3.setImageResource(R.mipmap.star1);
                score_img4.setImageResource(R.mipmap.star1);
                score_img5.setImageResource(R.mipmap.star2);
                break;
            case 10:
                score_img1.setImageResource(R.mipmap.star1);
                score_img2.setImageResource(R.mipmap.star1);
                score_img3.setImageResource(R.mipmap.star1);
                score_img4.setImageResource(R.mipmap.star1);
                score_img5.setImageResource(R.mipmap.star1);
                break;
        }
        ImageView imageView10 = viewHolder.getView(R.id.imageView10);
        ImageView imageView11 = viewHolder.getView(R.id.imageView11);
        ImageView imageView12 = viewHolder.getView(R.id.imageView12);
        ImageView imageView13 = viewHolder.getView(R.id.imageView13);


        //Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) viewHolder.getView(R.id.iv));
    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#00ddb6"));
            ds.setUnderlineText(true);
        }
    };
}
