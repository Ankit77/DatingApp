package com.chatapp.tutorial.custom.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created on 15/03/17.
 */

public class CubeInTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        view.setPivotX(position > 0 ? 0 : view.getWidth());
        view.setPivotY(0);
        view.setRotationY(-90f * position);
    }
}
