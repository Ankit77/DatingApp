package com.chatapp.tutorial.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;

/**
 * Created on 14/03/17.
 */

public class InnerItemsTransformer implements ViewPager.PageTransformer {

    private Context context;

    public InnerItemsTransformer(Context context) {
        this.context = context;
    }

    @Override
    public void transformPage(View view, float position) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.fragment_my_badges_ivBadgeImage);
        final TextView tvTitle = (TextView) view.findViewById(R.id.tv1);
        final TextView tvMessages = (TextView) view.findViewById(R.id.tv2);

        final Animation ivAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_to_bottom);
        final Animation tvTitleAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_right_to_left);
        final Animation tvMessagesAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_right_to_left);

        imageView.startAnimation(ivAnimation);
        tvTitle.startAnimation(tvTitleAnimation);
        tvMessages.startAnimation(tvMessagesAnimation);
    }
}
