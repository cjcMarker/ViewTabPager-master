package com.famabb.vtp;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by ${ChenJC} on 2018/1/25.
 */

public class ViewTabPager extends LinearLayout {
    private Context mContext;
    private OnTabSelectedListener mTabListener;
    private View mView;
    private FragmentManager mFgmManager;
    private TabPager mPager;
    private TabLayoutAdapter mTabAdapter;
    private TabLayout mTabLayout;
    private List<Fragment> mFgms;
    private List<String> mTitle;
    private List<Integer> mResIds;
    private List<View> mTabView;
    private int mFontSelectColor = -1;
    private int mFontNormalColor = -1;
    private float mFontSize = 0;
    private int mResWidth = -1;
    private int mResHeight = -1;
    private int mMsgResId = -1;
    private Map<Integer, Boolean> mMsgMap;

    public ViewTabPager(Context context) {
        this(context, null);
    }

    public ViewTabPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewTabPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mFgmManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_tab_pager, null, false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mView, params);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        mFgms = new ArrayList<>();
        mTitle = new ArrayList<>();
        mResIds = new ArrayList<>();
        mMsgMap = new Hashtable<>();
        mTabView = new ArrayList<>();
    }

    private void initView() {
        mPager = mView.findViewById(R.id.view_pager);
        mTabLayout = mView.findViewById(R.id.tab_layout);
        mTabAdapter = new TabLayoutAdapter();
        mPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mPager);
    }

    private void initListener() {
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mTabView.size() == 0) {
                    changeTabStatus(tab, true);
                }
                if (mTabListener != null) {
                    mTabListener.onTabSelected(tab.getCustomView());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (mTabView.size() == 0) {
                    changeTabStatus(tab, false);
                }
                if (mTabListener != null) {
                    mTabListener.onTabUnselected(tab.getCustomView());
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (mTabListener != null) {
                    mTabListener.onTabReselected(tab.getCustomView());
                }
            }
        });
    }

    //自定义底部item切换监听
    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mTabListener = listener;
    }

    /**
     * 加入页面切换监听
     *
     * @param listener
     */
    public void addPagerChangeListener(ViewPager.OnPageChangeListener listener) {
        mPager.addOnPageChangeListener(listener);
    }

    //改变选择状态
    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        final View view = tab.getCustomView();
        if (view != null) {
            final TextView txtTitle = view.findViewById(R.id.tv_tab);
            final ImageView imgTitle = view.findViewById(R.id.iv_tab);
            imgTitle.setSelected(selected);
            if (mFontSelectColor != -1 && mFontNormalColor != -1) {
                txtTitle.setTextColor(selected ? mFontSelectColor : mFontNormalColor);
            }
        }
    }

    //单个item
    private View getTabView(final int position) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_tab_item, null);
        final TextView txtTitle = view.findViewById(R.id.tv_tab);
        final ImageView imgTitle = view.findViewById(R.id.iv_tab);
        if (mResIds.size() > 0 && mFgms.size() == mResIds.size()) {
            imgTitle.setImageResource(mResIds.get(position));
            imgTitle.setSelected(position == 0);
            imgTitle.setVisibility(VISIBLE);
            if (mResWidth > 0 && mResHeight > 0) {
                LayoutParams params = new LayoutParams(mResWidth, mResHeight);
                params.gravity = Gravity.CENTER;
                imgTitle.setLayoutParams(params);
            }
        } else {
            imgTitle.setVisibility(GONE);
        }

        if (mMsgResId != -1) {
            ((ImageView) view.findViewById(R.id.iv_msg)).setImageResource(mMsgResId);
        }
        if (mTitle.size() > 0 && mFgms.size() == mTitle.size()) {
            if (mFontSize > 0) {
                txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mFontSize);
            }
            txtTitle.setText(mTitle.get(position));
            if (mFontSelectColor != -1 && mFontNormalColor != -1) {
                txtTitle.setTextColor(position == 0 ? mFontSelectColor : mFontNormalColor);
            } else if (mFontNormalColor != -1) {
                txtTitle.setTextColor(mFontNormalColor);
            }
            txtTitle.setVisibility(VISIBLE);
        } else {
            txtTitle.setVisibility(GONE);
        }

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(position);
            }
        });
        return view;
    }

    //字体颜色
    public void setFontColor(int normalResId, int selectResId) {
        mFontNormalColor = ContextCompat.getColor(mContext, normalResId);
        mFontSelectColor = ContextCompat.getColor(mContext, selectResId);
    }

    //item 图片大小
    public void setItemImageSize(int width, int height) {
        mResWidth = width;
        mResHeight = height;
    }

    /**
     * @param fgm
     * @param title
     * @param resId selector res id
     */
    public void addItem(Fragment fgm, String title, int resId) {
        mFgms.add(fgm);
        mTitle.add(title);
        mResIds.add(resId);
    }

    /**
     * @param fgm
     * @param resId selector resid
     */
    public void addItem(Fragment fgm, int resId) {
        mFgms.add(fgm);
        mResIds.add(resId);
    }

    /**
     * @param fgm
     * @param tabView 底部item view
     */
    public void addItem(Fragment fgm, View tabView) {
        mFgms.add(fgm);
        mTabView.add(tabView);
    }

    //改变index 的消息状态
    public void setMsgState(int index, boolean hasMsg) {
        if (mMsgMap.containsKey(index)) {
            boolean oldState = mMsgMap.get(index);
            if (oldState == hasMsg) {
                return;
            }
        }
        mMsgMap.put(index, hasMsg);
        TabLayout.Tab tab = mTabLayout.getTabAt(index);
        if (tab != null) {
            View view = tab.getCustomView();
            if (view != null) {
                view.findViewById(R.id.iv_msg).setVisibility(hasMsg ? VISIBLE : GONE);
            }
        }
    }

    //刷新view
    public void notifyViewChanger() {
        mTabAdapter.notifyDataSetChanged();
        for (int position = 0; position < mFgms.size(); position++) {
            mTabLayout.getTabAt(position)
                    .setCustomView(mTabView.size() == mFgms.size()
                            ? mTabView.get(position) : getTabView(position));
            mMsgMap.put(position, false);
        }
    }

    //tabLayout背景颜色
    public void setTabLayoutBgColor(int colorId) {
        mTabLayout.setBackgroundColor(ContextCompat.getColor(mContext, colorId));
    }

    //tab高度
    public void setTabItemHeight(int height) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mTabLayout.setLayoutParams(params);
    }

    //底部线背景颜色
    public void setLineBackground(int colorId) {
        mView.findViewById(R.id.view_line).setBackgroundColor(ContextCompat.getColor(mContext, colorId));
    }

    //设置字体大小
    public void setFontDipSize(float size) {
        mFontSize = size;
    }

    //默认字体颜色
    public void setFontColor(int colorId) {
        mFontNormalColor = ContextCompat.getColor(mContext, colorId);
    }

    //隐藏底部线
    public void hideLine() {
        mView.findViewById(R.id.view_line).setVisibility(GONE);
    }

    //是否可以滑动
    public void setScroll(boolean scroll) {
        mPager.setScroll(scroll);
    }

    //提示有消息的小图标
    public void setMsgResId(int resId) {
        mMsgResId = resId;
    }

    //跳转到index页
    public void jumpItem(int index) {
        mPager.setCurrentItem(index);
    }

    private class TabLayoutAdapter extends FragmentPagerAdapter {

        private TabLayoutAdapter() {
            super(mFgmManager);
        }

        public Fragment getItem(int position) {
            return mFgms.get(position);
        }

        public int getCount() {
            return mFgms.size();
        }
    }

}
