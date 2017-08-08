package com.dk.mp.xsxx.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.mp.xsxx.R;
import com.dk.mp.xsxx.entity.KqInfimationEntity;

import java.util.List;

/**
 * Created by cobb on 2017/8/8.
 */

public class KqInfomationAdapter extends RecyclerView.Adapter<KqInfomationAdapter.MyViewHolder>{

    private List<KqInfimationEntity> list;
    private Context context;
    private String type;

    public KqInfomationAdapter(List<KqInfimationEntity> list, Context context, String type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ad_kq_infomation,parent,false);
        return new KqInfomationAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        KqInfimationEntity kq = list.get(position);

        if (type.equals("0")){
            holder.title1.setText(kq.getTitle1());
            holder.title2.setVisibility(View.GONE);
            holder.title3.setText(kq.getTitle3());
        }else if (type.equals("1")){
            holder.title1.setText(kq.getTitle1());
            holder.title2.setText(kq.getTitle2());
            holder.title3.setText(kq.getTitle3());
        }else {
            holder.title1.setText(kq.getTitle1());
            holder.title2.setText(kq.getTitle2());
            holder.title3.setVisibility(View.GONE);
        }

        holder.cdcs.setText(kq.getCdcs());
        holder.kkcs.setText(kq.getKkcs());
        holder.qjcs.setText(kq.getQjcs());
        holder.ztcs.setText(kq.getZtcs());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title1, title2, title3, cdcs, kkcs, qjcs, ztcs;

        public MyViewHolder(View itemView) {
            super(itemView);

            title1 = (TextView) itemView.findViewById(R.id.title1);
            title2 = (TextView) itemView.findViewById(R.id.title2);
            title3 = (TextView) itemView.findViewById(R.id.title3);
            cdcs = (TextView) itemView.findViewById(R.id.cdcs);
            kkcs = (TextView) itemView.findViewById(R.id.kkcs);
            qjcs = (TextView) itemView.findViewById(R.id.qjcs);
            ztcs = (TextView) itemView.findViewById(R.id.ztcs);
        }
    }
}
