package com.famabb.vtp;

import android.support.design.widget.TabLayout;
import android.view.View;

/**
 * Created by ${ChenJC} on 2018/3/12.
 *
 * @see TabLayout.OnTabSelectedListener
 */

public interface OnTabSelectedListener {
    public void onTabSelected(View view);

    public void onTabUnselected(View view);

    public void onTabReselected(View view);
}
