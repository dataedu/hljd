package com.dk.mp.gzbx.ui;

import android.support.v4.view.ViewPager;

import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.gzbx.Adapter.ImagePagerAdapter;
import com.dk.mp.gzbx.R;

import java.util.List;

/**
 * 作者：janabo on 2017/12/4 20:41
 */
public class ImagePreviewActivity extends MyActivity {
    private ViewPager mViewPager;
    private List<String> mList;
    private int index;
    private ImagePagerAdapter mAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.image_preview;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("查看图片");
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
    }

    @Override
    protected void initialize() {
        super.initialize();
        mList = getIntent().getStringArrayListExtra("list");
        index = getIntent().getIntExtra("index", 0);
        mAdapter = new ImagePagerAdapter(ImagePreviewActivity.this, mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int arg0) {
            }
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        mViewPager.setCurrentItem(index);
    }
}
