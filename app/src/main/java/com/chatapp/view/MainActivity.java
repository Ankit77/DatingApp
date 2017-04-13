package com.chatapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chatapp.R;
import com.chatapp.common.ResideMenu;
import com.chatapp.fragment.HomeFragment;

import static com.chatapp.fragment.HomeFragment.resideMenu;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnMenuIcon;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMenuIcon = (Button) findViewById(R.id.activity_main_navigationMenuIcon);
        if (savedInstanceState == null)
            replaceFragment(new HomeFragment(), R.id.main_container);
        setUpMenu();
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(MainActivity.this);
        resideMenu.setUse3D(false);
        resideMenu.setBackground(R.drawable.ic_background);
        resideMenu.attachToActivity(MainActivity.this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;


        btnMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    private final ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {
        }
    };

    /***
     * This method will replace current fragment with new fragment.
     *
     * @param fragment
     */
    private void replaceFragment(final Fragment fragment, int containerId) {
        getFragmentManager()
                .beginTransaction()
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(containerId, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    /***
     * This method will add new fragment on current fragment
     * @param currentFragment
     * @param newFragment
     */
    public void addFragment(final Fragment currentFragment, final Fragment newFragment) {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, newFragment, newFragment.getClass().getSimpleName())
                .hide(currentFragment)
                .addToBackStack(currentFragment.getClass().getSimpleName())
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        android.app.FragmentManager fm = getFragmentManager();
//        Fragment myFragment = (Fragment) fm.findFragmentById(R.id.main_container);
//        if (myFragment instanceof StaticScreens) {
//            getFragmentManager().popBackStack();
//        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//            resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public void onClick(View view) {

    }
}
