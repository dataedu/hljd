package com.dk.mp.xsjf;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.view.RecycleViewDivider;
import com.dk.mp.core.widget.ErrorLayout;
import com.dk.mp.xsjf.adapter.AdapterStudrntPayCost;
import com.dk.mp.xsjf.entity.PayCostEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cobb on 2017/8/2.
 */

public class ActivityStudentPayCost extends MyActivity{

    private ErrorLayout errorLayout;
    private RecyclerView recyclerView;

    private AdapterStudrntPayCost adapterStudrntPayCost;
    private List<PayCostEntity> list = new ArrayList<>();

    @Override
    protected int getLayoutID() {
        return R.layout.ac_student_paycost;
    }

    @Override
    protected void initialize() {
        super.initialize();

        setTitle(getIntent().getStringExtra("title"));

        errorLayout = (ErrorLayout) findViewById(R.id.error_layout);
        recyclerView = (RecyclerView) findViewById(R.id.pay_cost);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapterStudrntPayCost = new AdapterStudrntPayCost(mContext,list);
        recyclerView.setAdapter(adapterStudrntPayCost);
//        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 10, ContextCompat.getColor(mContext, R.color.bg)));

        getData();
    }

    private void getData() {
        errorLayout.setErrorType(ErrorLayout.HIDE_LAYOUT);
        list.add(new PayCostEntity("122","2222","123","100","334","23"));
        list.add(new PayCostEntity("122","2222","123","100","334","23"));
        list.add(new PayCostEntity("122","2222","123","100","334","23"));
        list.add(new PayCostEntity("122","2222","123","100","334","23"));
        list.add(new PayCostEntity("122","2222","123","100","334","23"));
        adapterStudrntPayCost.notifyDataSetChanged();
    }
}
