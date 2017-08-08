package com.mp.dk.cwyd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.dk.mp.core.entity.PageMsg;
import com.dk.mp.core.http.HttpUtil;
import com.dk.mp.core.http.request.HttpListener;
import com.dk.mp.core.ui.BaseFragment;
import com.dk.mp.core.util.AdapterInterface;
import com.dk.mp.core.util.BroadcastUtil;
import com.dk.mp.core.util.DeviceUtil;
import com.dk.mp.core.util.SnackBarUtil;
import com.dk.mp.core.view.MyListView;
import com.dk.mp.core.view.RecycleViewDivider;
import com.dk.mp.core.widget.ErrorLayout;
import com.google.gson.reflect.TypeToken;
import com.mp.dk.cwyd.adapter.AdapterBedMove;
import com.mp.dk.cwyd.entity.BedMoveEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cobb on 2017/8/2.
 */

public class BedMoveFragment extends BaseFragment implements View.OnClickListener{

    public static final String ARGS_TABS = "args_tabs";
    private ErrorLayout mError;
    private RecyclerView myListView;
    private String mType;
    private List<BedMoveEntity> mData = new ArrayList<>();
    private AdapterBedMove ada;

    public static BedMoveFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ARGS_TABS,type);
        BedMoveFragment fragment = new BedMoveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fr_bed_move;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = getArguments().getString(ARGS_TABS);
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);

        mError = (ErrorLayout) view.findViewById(R.id.error_layout);
        myListView = (RecyclerView) view.findViewById(R.id.mListview);

//        mError.setOnLayoutClickListener(this);
//        initViews();
        initpage();

        BroadcastUtil.registerReceiver(getActivity(), mRefreshBroadcastReceiver, "zssdjgl_refresh");
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("zssdjgl_refresh")) {
               /* mData.clear();
                myListView.setPageNo(1);
                getData();*/
            }
        }
    };

    private void initpage() {

        mData.add(new BedMoveEntity("2304-43","3829-90","eweqw","q223","kdsiojios"));
        mData.add(new BedMoveEntity("2304-43","3829-90","eweqw","q223","kdsiojios"));
        mData.add(new BedMoveEntity("2304-43","3829-90","eweqw","q223","kdsiojios"));
        mData.add(new BedMoveEntity("2304-43","3829-90","eweqw","q223","kdsiojios"));
        mData.add(new BedMoveEntity("2304-43","3829-90","eweqw","q223","kdsiojios"));

        myListView.setHasFixedSize(true);
        myListView.setLayoutManager(new LinearLayoutManager(mContext));
        ada = new AdapterBedMove(mContext,mData,mType);
        myListView.setAdapter(ada);
        myListView.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.HORIZONTAL, 20, Color.rgb(244,244,244)));//添加分割线

    }

    @Override
    public void onClick(View v) {
        getData();
    }

    private void initViews() {
//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        myListView.setLayoutManager(manager);
//        myListView.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.HORIZONTAL, 1, Color.rgb(201, 201, 201)));//添加分割线
//        myListView.setAdapterInterface(mData, new AdapterInterface() {
//            @Override
//            public RecyclerView.ViewHolder setItemView(ViewGroup parent, int viewType) {
//                View view =  LayoutInflater.from(mContext).inflate(R.layout.bed_move_list, parent, false);// 设置要转化的layout文件
//                return new BedMoveFragment.MyView(view);
//            }
//
//            @Override
//            public void setItemValue(RecyclerView.ViewHolder holder, int position) {
//                BedMoveEntity ba = mData.get(position);
//                ((MyView)holder).xm.setText(ba.getXm());
//                ((MyView)holder).xh.setText("(" + ba.getXm() + ")");
//                ((MyView)holder).time.setText(ba.getKssj() + "-" + ba.getJssj());
//                ((MyView)holder).classs.setText(ba.getBj());
//
//            }
//
//            @Override
//            public void loadDatas() {
//                getData();
//            }
//        });
    }

    private class MyView extends RecyclerView.ViewHolder{
        private TextView xm,xh,time,classs;
        public MyView(View itemView) {
            super(itemView);
           /* xm = (TextView) itemView.findViewById(R.id.xm);// 取得实例
            xh = (TextView) itemView.findViewById(R.id.xh);// 取得实例
            time = (TextView) itemView.findViewById(R.id.time);// 取得实例
            classs = (TextView) itemView.findViewById(R.id.classs);// 取得实例*/

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Zssdjgl zssgl = mData.get(getLayoutPosition());
//                    Intent intent = new Intent(getActivity(), ZssdjglDetailActivity.class);
//                    intent.putExtra("detailid",zssgl.getId());
//                    intent.putExtra("type",mType);
//                    startActivity(intent);
//                }
//            });
        }
    }

    public void getData(){
        /*if(DeviceUtil.checkNet()) {
            getList();
        }else{
            if(myListView.pageNo == 1) {
                mError.setErrorType(ErrorLayout.NETWORK_ERROR);
            }else{
                SnackBarUtil.showShort(myListView,R.string.net_no2);
            }
        }*/
    }

    public void getList(){
//        myListView.startRefresh();
//        Map<String,Object> map = new HashMap<>();
//        map.put("pageNo",myListView.pageNo);
//        map.put("type",mType);
//        map.put("role", "1");
//        HttpUtil.getInstance().gsonRequest(new TypeToken<PageMsg<Zssdjgl>>(){}, "apps/zsdjgl/xsList", map, new HttpListener<PageMsg<Zssdjgl>>() {
//            @Override
//            public void onSuccess(PageMsg<Zssdjgl> result) {
//                myListView.stopRefresh(true);
//                mError.setErrorType(ErrorLayout.HIDE_LAYOUT);
//                if(result.getList() != null && result.getList().size()>0) {//是否获取到数据
//                    mData.addAll(result.getList());
//                    if(myListView.getAdapter()!=null) {
//                        myListView.getAdapter().notifyDataSetChanged();
//                        myListView.flish();
//                    }
//                }else{
//                    if(myListView.pageNo == 1) {//是否是第一页
//                        mError.setErrorType(ErrorLayout.NODATA);
//                    }else{
//                        SnackBarUtil.showShort(myListView,R.string.nodata);
//                    }
//                }
//            }
//            @Override
//            public void onError(VolleyError error) {
//                if(myListView.pageNo == 1) {
//                    mError.setErrorType(ErrorLayout.DATAFAIL);
//                }else{
//                    SnackBarUtil.showShort(myListView,R.string.data_fail);
//                }
//            }
//        });
    }

}
