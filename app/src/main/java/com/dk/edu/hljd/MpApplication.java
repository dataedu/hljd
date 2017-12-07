package com.dk.edu.hljd;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.dk.mp.core.application.MyApplication;

/**
 * 作者：janabo on 2017/12/4 16:44
 */
public class MpApplication extends MyApplication{

    /**
     * 突破64K问题，MultiDex构建
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
