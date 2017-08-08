package com.dk.mp.knsrd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.mp.knsrd.R;
import com.dk.mp.knsrd.entity.SqjxEntity;

import java.util.List;

/**
 * Created by cobb on 2017/8/8.
 */

public class SqjxAdapter  extends RecyclerView.Adapter<SqjxAdapter.MyViewHolder>{

    private Context context;
    private List<SqjxEntity> list;

    public SqjxAdapter(Context context, List<SqjxEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ad_sqjx,parent,false);
        return new SqjxAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SqjxEntity sqjxEntity = list.get(position);

        holder.xmmc.setText(sqjxEntity.getXmmc());
        holder.xmje.setText(sqjxEntity.getXmje());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xmmc, xmje;

        public MyViewHolder(View itemView) {
            super(itemView);

            xmmc = (TextView) itemView.findViewById(R.id.xmmc);
            xmje = (TextView) itemView.findViewById(R.id.xmje);
        }
    }
}
