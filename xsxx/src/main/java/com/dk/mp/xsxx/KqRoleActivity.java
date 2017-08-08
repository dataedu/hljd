package com.dk.mp.xsxx;

import android.content.Intent;
import android.view.View;

import com.dk.mp.core.ui.MyActivity;

/**
 * Created by cobb on 2017/8/3.
 */

public class KqRoleActivity extends MyActivity {

    private String type;

    @Override
    protected int getLayoutID() {
        return R.layout.ac_kq_role;
    }

    public void xs_btn(View view){
        type = "0";
        intent(type);
    }

    public void ls_btn(View view){
        type = "1";
        intent(type);
    }

    public void bzr_btn(View view){
        type = "2";
        intent(type);
    }


    private void intent(String t) {
        Intent i = new Intent(this,KqInfomationActivity.class);
        i.putExtra("type",t);
        startActivity(i);
    }

}
