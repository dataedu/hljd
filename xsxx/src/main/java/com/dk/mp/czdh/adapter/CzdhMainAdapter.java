package com.dk.mp.czdh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.mp.czdh.entity.CzdhEntity;
import com.dk.mp.xsxx.R;

import java.util.List;

/**
 * Created by cobb on 2017/8/8.
 */

public class CzdhMainAdapter extends RecyclerView.Adapter<CzdhMainAdapter.MyViewHolder>{

    private Context context;
    private List<CzdhEntity> list;

    public CzdhMainAdapter(Context context, List<CzdhEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ad_czdh,parent,false);
        return new CzdhMainAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CzdhEntity czdhEntity = list.get(position);
        holder.xm.setText(czdhEntity.getXm());
        holder.xh.setText(czdhEntity.getXh());
        holder.pm.setText(czdhEntity.getPm());
        holder.zf.setText(czdhEntity.getZf());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xm, xh, pm, zf;

        public MyViewHolder(View itemView) {
            super(itemView);

            xm = (TextView) itemView.findViewById(R.id.xm);
            xh = (TextView) itemView.findViewById(R.id.xh);
            pm = (TextView) itemView.findViewById(R.id.pm);
            zf = (TextView) itemView.findViewById(R.id.zf);
        }
    }
}
