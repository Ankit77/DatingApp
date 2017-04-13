package com.chatapp.tutorial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.util.PREF;
import com.chatapp.view.HomeActivity;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_tutorial_toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
//        getSupportActionBar().setTitle("");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
            startActivity(new Intent(TutorialActivity.this, HomeActivity.class));
            finish();
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
            DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_SHOW_TUTORIAL, false).commit();

        } else {
            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
            finish();
        }

    }
}
