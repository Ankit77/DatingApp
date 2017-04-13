package com.chatapp.tutorial.custom.transformer;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;

/**
 * Created on 16/03/17.
 */

public class TempTransformer implements ViewPager.PageTransformer {
    private static final String TAG = "TempTransformer";

    @Override
    public void transformPage(View view, float position) {
        Log.e(TAG, "position" + position);
        final ImageView imageView = (ImageView) view.findViewById(R.id.fragment_my_badges_ivBadgeImage);
        final TextView tvTitle = (TextView) view.findViewById(R.id.tv1);
        final TextView tvMessages = (TextView) view.findViewById(R.id.tv2);

        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        imageView.setScaleX(normalizedposition / 2 + 0.5f);
        imageView.setScaleY(normalizedposition / 2 + 0.5f);

    }
}
