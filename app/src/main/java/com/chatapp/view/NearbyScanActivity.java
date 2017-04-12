package com.chatapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.chatapp.R;
import com.chatapp.common.PulsatorLayout;


public class NearbyScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_scan);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();
        ImageView imageView = (ImageView) findViewById(R.id.img_heart);
        customInterpolator i = new customInterpolator();
        //Create an Scale Animation(fromX,toX,fromY,toY,pivotX,pivotY)
        ScaleAnimation anim = new ScaleAnimation(1, 1.2f, 1, 1.2f, imageView.getWidth() / 2, imageView.getHeight() / 2);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(750);
        anim.setInterpolator(i);
        //Attach our Custom Scale Animation to the imageview
        imageView.startAnimation(anim);

    }

    public class customInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float v) {
            float x = v < 1 / 3f ? 2 * v : (1 + v) / 2;
            return (float) Math.sin(x * Math.PI);
        }
    }
}
