package com.chatapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chatapp.R;
import com.chatapp.fragment.SuggestionFragment;
import com.chatapp.util.Utils;

/**
 * Created by ANKIT on 3/31/2017.
 */

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SuggestionFragment suggestionFragment = new SuggestionFragment();
        getFragmentManager().beginTransaction().add(R.id.activity_home_container, suggestionFragment, SuggestionFragment.class.getSimpleName()).commit();
    }

}
