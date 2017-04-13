package com.chatapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.tutorial.activity.TutorialActivity;
import com.chatapp.util.PREF;
import com.skyfishjy.library.RippleBackground;


public class NearbyScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_scan);
        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        ImageView imageView = (ImageView) findViewById(R.id.centerImage);
        rippleBackground.startRippleAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rippleBackground.stopRippleAnimation();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = null;
                                if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
                                    intent = new Intent(NearbyScanActivity.this, TutorialActivity.class);
                                } else {
                                    intent = new Intent(NearbyScanActivity.this, HomeActivity.class);
                                    DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_SHOW_TUTORIAL, true).commit();
                                }
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                            }
                        }, 1000);

                    }
                }, 5000);
            }
        }, 2000);

    }
}
