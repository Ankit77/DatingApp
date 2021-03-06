package com.chatapp.tutorial.indicator;

import android.support.v4.view.ViewPager;

/**
 * PageIndicator: to provide page indicator
 */
public interface PageIndicator extends ViewPager.OnPageChangeListener {
    /**
     * bind ViewPager
     */
    void setViewPager(ViewPager vp);

    /**
     * for special viewpager,such as LooperViewPager
     */
    void setViewPager(ViewPager vp, int realCount);

    void setCurrentItem(int item);
}
