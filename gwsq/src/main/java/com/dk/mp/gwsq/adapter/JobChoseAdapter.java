package com.dk.mp.gwsq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dk.mp.core.entity.News;
import com.dk.mp.gwsq.R;
import com.dk.mp.gwsq.entity.JobChooseEntity;
import com.dk.mp.gwsq.entity.WeekEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cobb on 2017/8/4.
 */

public class JobChoseAdapter extends RecyclerView.Adapter<JobChoseAdapter.MyViewHolder>{

    private Context mContext;
    private List<JobChooseEntity> list;

    private HashMap<String,Object> isSelected = new HashMap<>();

    public JobChoseAdapter(Context mContext, List<JobChooseEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public HashMap<String, Object> getIsSelected() {
        return isSelected;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ad_job_choose,parent,false);
        return new JobChoseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        JobChooseEntity weekEntity = list.get(position);
        holder.job.setText(weekEntity.getJcmc());

        if (isSelected.get(weekEntity.getJcid()) != null){
            holder.job.setTextColor(mContext.getResources().getColor(R.color.weekblue));
            holder.job.setBackgroundColor(mContext.getResources().getColor(R.color.jobblue));
        }else {
            holder.job.setTextColor(mContext.getResources().getColor(R.color.jobblack));
            holder.job.setBackground(mContext.getResources().getDrawable(R.drawable.square_round_gary));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView job;

        public MyViewHolder(View itemView) {
            super(itemView);

            job = (TextView) itemView.findViewById(R.id.jobtext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSelected.get(list.get(getLayoutPosition()).getJcid()) != null){
                        isSelected.remove((list.get(getLayoutPosition()).getJcid()));
                    }else{
                        isSelected.put(list.get(getLayoutPosition()).getJcid(),list.get(getLayoutPosition()));
                    }

                    notifyDataSetChanged();
                }
            });
        }
    }
}
