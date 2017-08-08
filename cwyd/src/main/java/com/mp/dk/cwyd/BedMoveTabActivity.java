package com.mp.dk.cwyd;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.dk.mp.core.adapter.MyFragmentPagerAdapter;
import com.dk.mp.core.ui.BaseFragment;
import com.dk.mp.core.ui.MyActivity;
import com.dk.mp.core.widget.MyViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cobb on 2017/8/2.
 */

public class BedMoveTabActivity extends MyActivity {

    private TabLayout mTabLayout;
    private MyViewpager mViewpager;

    @Override
    protected int getLayoutID() {
        return R.layout.ac_bad_move;
    }

    @Override
    protected void initView() {
        super.initView();

        setTitle(getIntent().getStringExtra("title"));
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager = (MyViewpager) findViewById(R.id.viewpager);
    }

    @Override
    protected void initialize() {
        super.initialize();

        initViewPager();
    }

    private void initViewPager(){
        List<String> titles = new ArrayList<>();
        titles.add("退宿申请");
        titles.add("调宿申请");

        for(int i=0;i<titles.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<BaseFragment> fragments = new ArrayList<>();

        fragments.add(BedMoveFragment.newInstance("1"));
        fragments.add(BedMoveFragment.newInstance("2"));

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,titles);
        mViewpager.setOffscreenPageLimit ( fragments.size ( ) );
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    /**
     * 新增退宿学生
     * @param
     */
    public void addTuisu(View view){
        Intent intent = new Intent(this,RoomaApplicationActivity.class);
        intent.putExtra("type","1");
        startActivity(intent);
    }

    /**
     * 新增调宿学生
     * @param
     */
    public void addTiaosu(View view){
        Intent intent = new Intent(this,RoomaApplicationActivity.class);
        intent.putExtra("type","2");
        startActivity(intent);
    }

}
