package com.chatapp.view;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chatapp.R;


/**
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private long mLastClickTime = 0;
    protected Toolbar toolbar;
    protected TextView toolbarTitle;

    public boolean isBackEnabled = false;

    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    public void setDrawerToggle(ActionBarDrawerToggle mDrawerToggle) {
        this.mDrawerToggle = mDrawerToggle;

        this.mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackEnabled) {
                    onBackPressed();
                }
            }
        });
    }

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getResources().getBoolean(R.bool.portrait_only)) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
    }

    @Override
    public void onClick(View v) {
        /**
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && isBackEnabled) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract void initToolbar();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    /**
     * Common toolbar setup method for activity
     *
     * @param title         title to set
     * @param isBackEnabled true if back function is available or not
     */
    public void setUpToolbar(String title, boolean isBackEnabled) {
        this.isBackEnabled = isBackEnabled;
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
        if (mDrawerToggle != null)
            mDrawerToggle.setDrawerIndicatorEnabled(!isBackEnabled);

        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(isBackEnabled ? R.drawable.ic_back : R.drawable.ic_menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
