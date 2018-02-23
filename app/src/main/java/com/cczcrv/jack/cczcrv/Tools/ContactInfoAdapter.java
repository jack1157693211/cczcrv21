package com.cczcrv.jack.cczcrv.Tools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cczcrv.jack.cczcrv.Model.ContactModel;
import com.cczcrv.jack.cczcrv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/1/30.
 */

public class ContactInfoAdapter extends RecyclerView.Adapter<ContactInfoAdapter.ViewHolder> {
    private LayoutInflater mInflater;

    public void setmDatas(List<ContactModel> mDatas) {
        this.mDatas = mDatas;
    }

    private List<ContactModel> mDatas;
    public Context mContext;


    private List<ContactInfoAdapter.ViewHolder> mViewHolders;

    public List<ContactModel> GetResults()
    {
        List<ContactModel> result=new ArrayList<>();
        if(mViewHolders.size()>0)
        {
            for (ContactInfoAdapter.ViewHolder i:mViewHolders) {
                ContactModel mContactModel=new ContactModel();
                mContactModel.setName(i.name.getText().toString().trim());
                mContactModel.setPhone(i.idCard.getText().toString().trim());
                result.add(mContactModel);
            }
        }
        return  result;
    }

    public ContactInfoAdapter(Context context, List<ContactModel> datas) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDatas = datas;
        mViewHolders=new ArrayList<>();
    }

    @Override
    public ContactInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contact_info, parent, false);
        ContactInfoAdapter.ViewHolder viewHolder = new ContactInfoAdapter.ViewHolder(view);
        viewHolder.name=view.findViewById(R.id.editText5);
        viewHolder.idCard=view.findViewById(R.id.editText6);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactInfoAdapter.ViewHolder holder, int position) {

        mViewHolders.add(holder);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView idCard;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
