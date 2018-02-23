package com.cczcrv.jack.cczcrv.Tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.Model.home_product_model;
import com.cczcrv.jack.cczcrv.R;

import java.util.List;

/**
 * Created by jack on 2018/1/11.
 */

public class HomeProductAdapter1 extends RecyclerView.Adapter<HomeProductAdapter1.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<home_product_model> mDatas;
    private OnItemClickLitener mOnItemClickLitener;
    public  HomeProductAdapter1(Context context, List<home_product_model> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
    @Override
    public HomeProductAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_vertical_product,
                parent, false);
        HomeProductAdapter1.ViewHolder viewHolder = new HomeProductAdapter1.ViewHolder(view);
        viewHolder.iv_product_pic = view.findViewById(R.id.product_pic);

        viewHolder.tv_product_name = view.findViewById(R.id.product_name);
        viewHolder.tv_product_describe = view.findViewById(R.id.product_describe);
        viewHolder.tv_product_money = view.findViewById(R.id.product_money);
        viewHolder.tv_product_start_city = view.findViewById(R.id.product_start_city);
        return  viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.iv_product_pic.setImageResource(mDatas.get(position).getProductPic());
        holder.tv_product_name.setText(mDatas.get(position).getProductName());
        holder.tv_product_describe.setText(mDatas.get(position).getProductDescribe());
        holder.tv_product_money.setText(mDatas.get(position).getProductMoney());
        holder.tv_product_start_city.setText(mDatas.get(position).getProductStartCity());
        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView iv_product_pic;
        TextView tv_product_name;
        TextView tv_product_describe;
        TextView tv_product_money;
        TextView tv_product_start_city;
    }
}
