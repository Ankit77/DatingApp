package com.chatapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.chatapp.R;
import com.chatapp.fragment.SuggestionFragment;

/**
 * Created by ANKIT on 3/31/2017.
 */

public class HomeActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_home_drawer_layout);
        initToolbar();
        SuggestionFragment suggestionFragment = new SuggestionFragment();
        getFragmentManager().beginTransaction().add(R.id.activity_home_container, suggestionFragment, SuggestionFragment.class.getSimpleName()).commit();
    }

    @Override
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_home_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        setDrawerToggle(new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        });
        drawerLayout.addDrawerListener(getDrawerToggle());
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(HomeActivity.this, "Navigation call", Toast.LENGTH_LONG).show();
//            }
//        });
        closeNavDrawer();
    }

    /**
     * Checks if side menu is open
     *
     * @return true if open
     */
    public boolean isNavDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    /**
     * Closes side menu
     */
    public void closeNavDrawer() {
        if (drawerLayout != null && isNavDrawerOpen()) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Opens side menu
     */
    public void openNavDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * Set side menu swipe lock
     *
     * @param isEnabled lock
     */
    public void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}
