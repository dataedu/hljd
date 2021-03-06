package com.dk.mp.knsrd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dk.mp.knsrd.ImagePreviewActivity;
import com.dk.mp.knsrd.KnsrdMainActivity;
import com.dk.mp.knsrd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：janabo on 2017/1/17 14:07
 */
public class UploadImageAdapter extends RecyclerView.Adapter<UploadImageAdapter.MyViewHolder>{

    private KnsrdMainActivity activity;
    private Context mContext;
    private List<String> data;
    private LayoutInflater inflater;

    public UploadImageAdapter(Context context, KnsrdMainActivity activity, List<String> basicList) {
        this.mContext = context;
        this.data = basicList;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public UploadImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.ad_upload_image,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UploadImageAdapter.MyViewHolder holder, int position) {
        String imgurl = data.get(position);
        if("addImage".equals(imgurl)){
            Glide.with(mContext).load(R.mipmap.addfile).fitCenter().into(holder.img);
            holder.delete.setVisibility(View.GONE);
        }else{
            Glide.with(mContext).load(imgurl).fitCenter().into(holder.img);
            holder.delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private ImageButton delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.remove(getLayoutPosition());

                        for (int i=1;i<data.size();i++){
                            Log.e("--------------------",data.get(i).toString()+i);
                            if (data.get(i).toString().equals("addImage")){
//                                return;
                            }else {
                                data.add("addImage");
                            }
                        }
//                        data.add("addImage");

                    notifyDataSetChanged();
                    activity.clearImg();
                }
            });

            img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if("addImage".equals(data.get(getLayoutPosition()))){//添加按钮
                        activity.startReadWi();
                    }else{//预览图片
                        Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                        intent.putExtra("index", 0);
                        intent.putStringArrayListExtra("list", (ArrayList<String>) data);
                        mContext.startActivity(intent);
                    }
                }
            });

        }
    }
}
