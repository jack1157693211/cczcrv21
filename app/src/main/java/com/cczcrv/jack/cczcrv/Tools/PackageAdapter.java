package com.cczcrv.jack.cczcrv.Tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cczcrv.jack.cczcrv.Model.PackageReturnModel;
import com.cczcrv.jack.cczcrv.R;

import java.util.List;

/**
 * Created by jack on 2018/1/23.
 */

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    public interface PackageCountChangeEvent
    {
        void CountChange(List<PackageReturnModel> changeData);
    }
    private  PackageCountChangeEvent mPackageCountChangeEvent;
    private LayoutInflater mInflater;
    private List<PackageReturnModel> mDatas;
    public Context mContext;

    public List<PackageReturnModel> getmPackageReturnModels() {
        return mDatas;
    }



    public PackageAdapter(Context context, List<PackageReturnModel> datas,PackageCountChangeEvent packageCountChangeEvent) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mContext=context;
        mPackageCountChangeEvent=packageCountChangeEvent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.package_item, parent, false);
        PackageAdapter.ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.name = view.findViewById(R.id.name);
        viewHolder.price = view.findViewById(R.id.price);
        viewHolder.count = view.findViewById(R.id.count);
        viewHolder.left = view.findViewById(R.id.left);
        viewHolder.right = view.findViewById(R.id.right);

        viewHolder.left.setTag(viewHolder);
        viewHolder.right.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDatas.get(position).getName());
        holder.price.setText(mDatas.get(position).getPrice());
        holder.count.setText("0");
        holder.id = mDatas.get(position).getId();
        holder.maxCount=mDatas.get(position).getMaxCount();

        holder.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton imageButton = (ImageButton) v;
                PackageAdapter.ViewHolder viewHolder = (PackageAdapter.ViewHolder) imageButton.getTag();
                Integer count1 = Integer.parseInt(viewHolder.count.getText().toString());
                count1 = count1 > 0 ? count1 - 1 : count1;
                viewHolder.count.setText(count1.toString());
                if (mDatas != null && mDatas.size() > 0) {
                    PackageReturnModel mself = null;
                    for (PackageReturnModel i : mDatas) {
                        if (i.getId().equals(viewHolder.id)) {
                            mself = i;
                            break;
                        }
                    }
                    if (mself != null) {
                        mself.setCount(count1);
                    }
                    else
                    {
                        mself=new  PackageReturnModel();
                        mself.setId(viewHolder.id);
                        mself.setCount(count1);
                        mDatas.add(mself);
                    }
                }
                mPackageCountChangeEvent.CountChange(getmPackageReturnModels());
            }
        });
        holder.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton imageButton = (ImageButton) v;
                PackageAdapter.ViewHolder viewHolder = (PackageAdapter.ViewHolder) imageButton.getTag();
                Integer count1 = Integer.parseInt(viewHolder.count.getText().toString());
                count1 += 1;

                if(viewHolder.maxCount>0 && count1>viewHolder.maxCount)
                {
                    count1=viewHolder.maxCount;
                    Toast.makeText(mContext,"没有更多了",Toast.LENGTH_SHORT).show();
                }
                viewHolder.count.setText(count1.toString());
                if (mDatas != null && mDatas.size() > 0) {
                    PackageReturnModel mself = null;
                    for (PackageReturnModel i : mDatas) {
                        if (i.getId().equals(viewHolder.id)) {
                            mself = i;
                            break;
                        }
                    }
                    if (mself != null) {
                        mself.setCount(count1);
                    }
                    else
                    {
                        mself=new  PackageReturnModel();
                        mself.setId(viewHolder.id);
                        mself.setCount(count1);
                        mDatas.add(mself);
                    }
                }

                mPackageCountChangeEvent.CountChange(getmPackageReturnModels());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public Long id;
        public Integer maxCount;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView name;
        TextView price;
        TextView count;
        ImageButton left;
        ImageButton right;
    }
}
