package com.chatapp.tutorial.custom.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created on 14/03/17.
 */

public class RotationPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(final View page, float position) {
        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        page.setScaleX(normalizedposition / 2 + 0.5f);
        page.setScaleY(normalizedposition / 2 + 0.5f);
    }
}
