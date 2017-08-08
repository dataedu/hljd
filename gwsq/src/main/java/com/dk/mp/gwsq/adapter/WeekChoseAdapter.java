package com.dk.mp.gwsq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.mp.gwsq.R;
import com.dk.mp.gwsq.entity.WeekEntity;

import java.util.List;

/**
 * Created by cobb on 2017/8/4.
 */

public class WeekChoseAdapter  extends RecyclerView.Adapter<WeekChoseAdapter.MyViewHolder>{

    private Context mContext;
    private List<WeekEntity> list;

    int selected;

    public WeekChoseAdapter(Context mContext, List<WeekEntity> list, int selected) {
        this.mContext = mContext;
        this.list = list;
        this.selected = selected;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ad_week_chose,parent,false);
        return new WeekChoseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        WeekEntity weekEntity = list.get(position);
        holder.week.setText(weekEntity.getWeek());

        if (selected == position){
            holder.week.setTextColor(mContext.getResources().getColor(R.color.weekblue));
            holder.week.setBackground(mContext.getResources().getDrawable(R.drawable.circlr_round_blue));
        }else {
            holder.week.setTextColor(mContext.getResources().getColor(R.color.weekblack));
            holder.week.setBackground(mContext.getResources().getDrawable(R.drawable.circlr_round_black));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView week;

        public MyViewHolder(View itemView) {
            super(itemView);

            week = (TextView) itemView.findViewById(R.id.week);

            final OnItemClickListener itemClickListener = (OnItemClickListener) mContext;
            if(itemClickListener!=null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListener.onItemClick(view,getLayoutPosition());
                    }
                });
            }
        }
    }
}
