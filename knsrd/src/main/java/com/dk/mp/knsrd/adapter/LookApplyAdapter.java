package com.dk.mp.knsrd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.mp.knsrd.R;
import com.dk.mp.knsrd.entity.LookApplyEntity;

import java.util.List;

/**
 * Created by cobb on 2017/8/3.
 */

public class LookApplyAdapter extends RecyclerView.Adapter<LookApplyAdapter.MyViewHolder>{

    private Context mContext;
    private List<LookApplyEntity> list;


    public LookApplyAdapter(Context mContext, List<LookApplyEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ad_look_apply,parent,false);
        return new LookApplyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        LookApplyEntity applyEntity = list.get(position);

        holder.sqdc.setText(applyEntity.getSqdc());
        holder.sqsj.setText(applyEntity.getSqsj());
        holder.sqzt.setText(applyEntity.getSqzt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView sqdc,sqsj,sqzt;

        public MyViewHolder(View itemView) {
            super(itemView);

            sqdc = (TextView) itemView.findViewById(R.id.sqdc);
            sqsj = (TextView) itemView.findViewById(R.id.sqsj);
            sqzt = (TextView) itemView.findViewById(R.id.sqzt);
        }
    }
}
