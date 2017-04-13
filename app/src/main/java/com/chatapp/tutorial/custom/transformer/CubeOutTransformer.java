package com.chatapp.tutorial.custom.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created on 15/03/17.
 */

public class CubeOutTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        view.setPivotX(position < 0f ? view.getWidth() : 0f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(90f * position);
    }
}
