package com.mp.dk.cwyd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mp.dk.cwyd.R;
import com.mp.dk.cwyd.RoomDetailActivity;
import com.mp.dk.cwyd.entity.BedMoveEntity;

import java.util.List;

/**
 * Created by cobb on 2017/8/2.
 */

public class AdapterBedMove extends RecyclerView.Adapter<AdapterBedMove.MyViewHolder>{

    private Context mContext;
    private List<BedMoveEntity> list;
    private String mType;

    public AdapterBedMove(Context mContext, List<BedMoveEntity> list, String type) {
        this.mContext = mContext;
        this.list = list;
        this.mType = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bed_move_list, parent, false);
        return new AdapterBedMove.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BedMoveEntity ba = list.get(position);
        holder.xm.setText(ba.getXm());
        holder.xh.setText("(" + ba.getXm() + ")");
        holder.time.setText(ba.getKssj() + "-" + ba.getJssj());
        holder.classs.setText(ba.getBj());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView xm,xh,time,classs;

        public MyViewHolder(final View itemView) {
            super(itemView);

            xm = (TextView) itemView.findViewById(R.id.xm);// 取得实例
            xh = (TextView) itemView.findViewById(R.id.xh);// 取得实例
            time = (TextView) itemView.findViewById(R.id.time);// 取得实例
            classs = (TextView) itemView.findViewById(R.id.classs);// 取得实例

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RoomDetailActivity.class);
                    intent.putExtra("type",mType);
                    intent.putExtra("id",list.get(getLayoutPosition()).getId());
                    mContext.startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }
            });
        }
    }

}
