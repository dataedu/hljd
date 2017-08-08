package com.dk.mp.xsjf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.mp.xsjf.R;
import com.dk.mp.xsjf.entity.PayCostEntity;

import java.util.List;

/**
 * Created by cobb on 2017/8/2.
 */

public class AdapterStudrntPayCost extends RecyclerView.Adapter<AdapterStudrntPayCost.MyViewHolder>{

    private Context mContext;
    private List<PayCostEntity> list;

    public AdapterStudrntPayCost(Context mContext, List<PayCostEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ad_pay_cost, parent, false);
        return new AdapterStudrntPayCost.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PayCostEntity payCostEntity = list.get(position);

        holder.yingjiao.setText(payCostEntity.getYingjiao()+"元");
        holder.yijiao.setText(payCostEntity.getYijiao()+"元");
        holder.mianjiao.setText(payCostEntity.getMianjiao()+"元");
        holder.hjje.setText(payCostEntity.getHjje()+"元");
        holder.wnqf.setText(payCostEntity.getWnqf()+"元");
        holder.dsh.setText(payCostEntity.getDsh()+"元");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView yingjiao, yijiao, mianjiao, hjje ,wnqf, dsh;

        public MyViewHolder(View itemView) {
            super(itemView);

            yingjiao = (TextView) itemView.findViewById(R.id.yingjiao);
            yijiao = (TextView) itemView.findViewById(R.id.yijiao);
            mianjiao = (TextView) itemView.findViewById(R.id.mianjiao);
            hjje = (TextView) itemView.findViewById(R.id.hjje);
            wnqf = (TextView) itemView.findViewById(R.id.wnqf);
            dsh = (TextView) itemView.findViewById(R.id.dsh);
        }
    }
}
