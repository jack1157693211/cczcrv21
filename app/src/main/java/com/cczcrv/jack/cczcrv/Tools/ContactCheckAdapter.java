package com.cczcrv.jack.cczcrv.Tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cczcrv.jack.cczcrv.Model.ContactModel;
import com.cczcrv.jack.cczcrv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/1/30.
 */

public class ContactCheckAdapter extends RecyclerView.Adapter<ContactCheckAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<ContactModel> mDatas;
    public Context mContext;

    public List<ContactModel> getmResults() {
        return mResults;
    }

    public List<ContactModel> mResults;

    public ContactCheckAdapter(Context context, List<ContactModel> datas) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDatas = datas;
        mResults = new ArrayList<>();
    }

    @Override
    public ContactCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contactcheck, parent, false);
        ContactCheckAdapter.ViewHolder viewHolder = new ContactCheckAdapter.ViewHolder(view);

        viewHolder.checkBox = view.findViewById(R.id.checkBox);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContactModel mModel = null;
                if (mResults.size() > 0) {
                    for (ContactModel i : mResults) {
                        if (i.getName().equals(buttonView)) {
                            mModel = i;
                        }
                    }
                }
                if (isChecked) {
                    mModel = (ContactModel) buttonView.getTag();
                    mResults.add(mModel);
                } else {
                    if (mModel != null) {
                        mResults.remove(mModel);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactCheckAdapter.ViewHolder holder, int position) {
        holder.checkBox.setText(mDatas.get(position).getName());
        holder.checkBox.setTag(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
