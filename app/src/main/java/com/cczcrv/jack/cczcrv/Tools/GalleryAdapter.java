package com.cczcrv.jack.cczcrv.Tools;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.ApiModel.LineProudctApi;
import com.cczcrv.jack.cczcrv.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jack on 2018/1/10.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<LineProudctApi> mDatas;
    private DisplayMetrics displayMetrics;
    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;

    public GalleryAdapter(Context context, List<LineProudctApi> datats, DisplayMetrics displayMetrics) {
        mContext=context;
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        this.displayMetrics = displayMetrics;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * ItemClick的回调接口
     *
     * @author zhy
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_horizontal_product,
                parent, false);

        ConstraintLayout cl = view.findViewById(R.id.one_product_show_Layout);
        ViewGroup.LayoutParams params = cl.getLayoutParams();
        params.width = (int) (displayMetrics.widthPixels * (4.0 / 5));
        cl.setLayoutParams(params);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.iv_product_pic = view.findViewById(R.id.product_pic);

        viewHolder.tv_product_name = view.findViewById(R.id.product_name);
        viewHolder.tv_product_describe = view.findViewById(R.id.product_describe);
        viewHolder.tv_product_money = view.findViewById(R.id.product_money);
        viewHolder.tv_product_start_city = view.findViewById(R.id.product_start_city);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //holder.mImg.setImageResource(mDatas.get(position));
        //holder.mImg.setTag(position + "");

        Picasso.with(mContext).load(mDatas.get(position).getCover()).into(holder.iv_product_pic);

        holder.tv_product_name.setText(mDatas.get(position).getName());
        holder.tv_product_describe.setText(mDatas.get(position).getListExplain());
        holder.tv_product_money.setText("¥ "+mDatas.get(position).getPrice());
        holder.tv_product_start_city.setText(mDatas.get(position).getCityName());
        holder.itemView.setTag(mDatas.get(position).getId());
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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
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