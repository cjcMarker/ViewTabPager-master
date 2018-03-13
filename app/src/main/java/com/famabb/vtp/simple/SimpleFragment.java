package com.famabb.vtp.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ${ChenJC} on 2018/3/12.
 */

public class SimpleFragment extends Fragment {
    private View mView;
    private String mTip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fgm, container, false);
        ((TextView) mView.findViewById(R.id.tv)).setText(mTip);
        return mView;
    }

    public void setTest(String tip) {
        mTip = tip;
    }
}
