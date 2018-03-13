package com.famabb.vtp;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TabPager extends ViewPager {
    private boolean isScroll = true;

    //是否可以滑动
    public void setScroll(boolean scroll) {
        this.isScroll = scroll;
    }

    public TabPager(Context context) {
        super(context);
    }

    public TabPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScroll && super.onTouchEvent(ev);
    }
}