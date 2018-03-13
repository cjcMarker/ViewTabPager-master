package com.famabb.vtp.simple;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.famabb.vtp.ViewTabPager;

public class MainActivity extends FragmentActivity {
    private ViewTabPager mTabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabPager = findViewById(R.id.view_tab_pager);
        initTabPager();
    }

    private void initTabPager() {
        SimpleFragment fragment1 = new SimpleFragment();
        fragment1.setTest("0");
        SimpleFragment fragment2 = new SimpleFragment();
        fragment2.setTest("1");
        SimpleFragment fragment3 = new SimpleFragment();
        fragment3.setTest("2");
        SimpleFragment fragment4 = new SimpleFragment();
        fragment4.setTest("3");
        mTabPager.addItem(fragment1, getString(R.string.app_m_home), R.drawable.item_home_selector);
        mTabPager.addItem(fragment2, getString(R.string.app_m_cp), R.drawable.item_home_selector);
        mTabPager.addItem(fragment3, getString(R.string.app_m_mes), R.drawable.item_home_selector);
        mTabPager.addItem(fragment4, getString(R.string.app_m_my), R.drawable.item_home_selector);

        mTabPager.setMsgResId(R.drawable.icon_f);
        mTabPager.setLineBackground(R.color.line_title);
        mTabPager.setTabLayoutBgColor(R.color.color_w);
        mTabPager.setFontColor(R.color.colorAccent, R.color.colorPrimaryDark);

        mTabPager.setFontDipSize(10);

        mTabPager.notifyViewChanger();
        mTabPager.setMsgState(1, true);

    }
}
