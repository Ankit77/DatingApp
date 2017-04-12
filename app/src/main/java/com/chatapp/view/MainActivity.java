package com.chatapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chatapp.R;
import com.chatapp.common.ResideMenu;
import com.chatapp.fragment.HomeFragment;

import static com.chatapp.fragment.HomeFragment.resideMenu;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            replaceFragment(new HomeFragment(),R.id.main_container);
    }


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
