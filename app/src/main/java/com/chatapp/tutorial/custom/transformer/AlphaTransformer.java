package com.chatapp.tutorial.custom.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.chatapp.R;


/**
 * Created on 15/03/17.
 */

public class AlphaTransformer implements ViewPager.PageTransformer {
    private boolean isSliderAnimation = false;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        View imageView = view.findViewById(R.id.fragment_my_badges_ivBadgeImage);
        View contentView = view.findViewById(R.id.tv2);
        View txt_title = view.findViewById(R.id.tv1);

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left
        } else if (position <= 0) { // [-1,0]
            // This page is moving out to the left

            // Counteract the default swipe
            setTranslationX(view,pageWidth * -position);
            if (contentView != null) {
                // But swipe the contentView
                setTranslationX(contentView,pageWidth * position);
                setTranslationX(txt_title,pageWidth * position);

                setAlpha(contentView,1 + position);
                setAlpha(txt_title,1 + position);
            }

            if (imageView != null) {
                // Fade the image in
                setAlpha(imageView,1 + position);
            }

        } else if (position <= 1) { // (0,1]
            // This page is moving in from the right

            // Counteract the default swipe
            setTranslationX(view, pageWidth * -position);
            if (contentView != null) {
                // But swipe the contentView
                setTranslationX(contentView,pageWidth * position);
                setTranslationX(txt_title,pageWidth * position);

                setAlpha(contentView, 1 - position);
                setAlpha(txt_title, 1 - position);

            }
            if (imageView != null) {
                // Fade the image out
                setAlpha(imageView,1 - position);
            }

        }
    }

    /**
     * Sets the alpha for the view. The alpha will be applied only if the running android device OS is greater than honeycomb.
     * @param view - view to which alpha to be applied.
     * @param alpha - alpha value.
     */
    private void setAlpha(View view, float alpha) {

        if(! isSliderAnimation) {
            view.setAlpha(alpha);
        }
    }

    /**
     * Sets the translationX for the view. The translation value will be applied only if the running android device OS is greater than honeycomb.
     * @param view - view to which alpha to be applied.
     * @param translationX - translationX value.
     */
    private void setTranslationX(View view, float translationX) {
        if(! isSliderAnimation) {
            view.setTranslationX(translationX);
        }
    }

}
