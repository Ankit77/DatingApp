package com.chatapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.util.PREF;

/**
 * Created by ANKIT on 3/31/2017.
 */

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadSplash();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

    private void loadSplash() {
        handler.postDelayed(runnable, 3000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = null;
            if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_IS_LOGGED_IN, false)) {
                intent = new Intent(SplashActivity.this, HomeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
            finish();
        }
    };
}
