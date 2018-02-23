package com.cczcrv.jack.cczcrv.Tools;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.Model.SubCalculateDetailModel;
import com.cczcrv.jack.cczcrv.R;

import java.util.List;

/**
 * Created by jack on 2018/2/1.
 */

public class SubmitCalculateDetailAdapter extends RecyclerView.Adapter<SubmitCalculateDetailAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<SubCalculateDetailModel> mDatas;
    public Context mContext;

    public SubmitCalculateDetailAdapter(Context context, List<SubCalculateDetailModel> datas) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDatas = datas;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.submit_calculate_detail, parent, false);
        SubmitCalculateDetailAdapter.ViewHolder viewHolder = new SubmitCalculateDetailAdapter.ViewHolder(view);
        viewHolder.orderCalculateName = view.findViewById(R.id.orderCalculateName);
        viewHolder.orderCalculateValue = view.findViewById(R.id.orderCalculateValue);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.orderCalculateName.setText(mDatas.get(position).getName());
        holder.orderCalculateValue.setText(mDatas.get(position).getValue());
        if(mDatas.size()==position+1)
        {
            holder.orderCalculateValue.setTextColor(Color.parseColor("#FFB366"));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderCalculateName;
        public TextView orderCalculateValue;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
