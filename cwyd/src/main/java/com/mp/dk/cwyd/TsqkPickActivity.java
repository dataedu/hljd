package com.mp.dk.cwyd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.widget.ArrayWheelAdapter;
import com.dk.mp.core.widget.WheelView;
import com.mp.dk.cwyd.entity.TsqkEntity;

import java.util.List;

/**
 * Created by cobb on 2017/8/3.
 */

public class TsqkPickActivity extends Activity {

    private String[] PLANETM;
    private WheelView mInfo;
    List<TsqkEntity> kfs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_tsqk_pick);
        initialize();
    }

    protected void initialize() {
        Bundle bundle = getIntent().getExtras();
        kfs = (List<TsqkEntity>) bundle.getSerializable("kfs");
        PLANETM = new String[kfs.size()];
        if(kfs != null && kfs.size()>0){
            int i =0;
            for(TsqkEntity j : kfs){
                PLANETM[i] = j.getTsqkmc();
                i++;
            }
        }
        findView();
    }

    private void findView(){
        mInfo = (WheelView) findViewById(R.id.info);
        mInfo.setAdapter(new ArrayWheelAdapter<>(PLANETM));
        mInfo.setCyclic(false);

        Button bt = (Button)findViewById(R.id.set);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = PLANETM[mInfo.getCurrentItem()];
                Intent in = new Intent();
                in.putExtra("tsqkmc", str);
                in.putExtra("tsqkid", kfs.get(mInfo.getCurrentItem()).getTsqkid());
                setResult(RESULT_OK, in);
                back();
            }
        });
        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    public void back() {
        finish();
        overridePendingTransition(0, R.anim.push_down_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
