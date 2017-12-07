package com.dk.mp.gzbx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：janabo on 2017/12/6 17:00
 */
public class MyListView extends ListView{
    public MyListView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
