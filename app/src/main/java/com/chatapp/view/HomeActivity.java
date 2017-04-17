package com.chatapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatapp.R;
import com.chatapp.common.ResideMenu;
import com.chatapp.common.ResideMenuItem;
import com.chatapp.fragment.ProfileDetailFragment;
import com.chatapp.fragment.SettingFragment;
import com.chatapp.fragment.SuggestionFragment;
import com.chatapp.tutorial.activity.TutorialActivity;
import com.chatapp.util.Constants;
import com.chatapp.util.Utils;

/**
 * Created by ANKIT on 3/31/2017.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ResideMenu resideMenu;
    private ImageView imgMenu;
    private ResideMenuItem itemSetting;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemChat;
    private ResideMenuItem itemSuggetion;
    private ResideMenuItem itemTutorial;
    private TextView tvTitle;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpMenu();
        SuggestionFragment suggestionFragment = new SuggestionFragment();
        getFragmentManager().beginTransaction().add(R.id.activity_home_container, suggestionFragment, SuggestionFragment.class.getSimpleName()).commit();
        initToolBar();
        init();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void init() {
        imgMenu = (ImageView) findViewById(R.id.activity_home_img_menu);
        tvTitle = (TextView) findViewById(R.id.activity_home_tv_title);
        imgMenu.setOnClickListener(this);
        isBackEnable(false);
    }

    public void setActionBarTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void isBackEnable(boolean isbackEnable) {
        if (isbackEnable) {
            imgMenu.setImageResource(R.drawable.ic_back);
            imgMenu.setTag(Constants.TAG_BACK);
        } else {
            imgMenu.setImageResource(R.drawable.menu);
            imgMenu.setTag(Constants.TAG_MENU);
        }
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(HomeActivity.this);
        resideMenu.setUse3D(false);
        resideMenu.setBackground(R.drawable.ic_background);
        resideMenu.attachToActivity(HomeActivity.this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemSetting = new ResideMenuItem(HomeActivity.this, R.drawable.ic_menu_rate_app, "SETTING");
        itemProfile = new ResideMenuItem(HomeActivity.this, R.drawable.ic_menu_share, "PROFILE");
        itemChat = new ResideMenuItem(HomeActivity.this, R.drawable.ic_menu_contatct_us, "CHAT");
        itemSuggetion = new ResideMenuItem(HomeActivity.this, R.drawable.ic_menu_followus, "SUGGETION");
        itemTutorial = new ResideMenuItem(HomeActivity.this, R.drawable.ic_menu_followus, "HOW ITS WORK");
        itemSetting.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemChat.setOnClickListener(this);
        itemSuggetion.setOnClickListener(this);
        itemTutorial.setOnClickListener(this);
        resideMenu.addMenuItem(itemSetting, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemChat, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSuggetion, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemTutorial, ResideMenu.DIRECTION_LEFT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
    }

    private final ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {
        }
    };

    @Override
    public void onClick(View view) {
        if (view == itemProfile) {
            ProfileDetailFragment profileDetailFragment = new ProfileDetailFragment();
            Utils.addNextFragment(R.id.activity_home_container, HomeActivity.this, profileDetailFragment, getFragmentManager().findFragmentById(R.id.activity_home_container), false);
            resideMenu.closeMenu();
        } else if (view == itemSetting) {
            SettingFragment settingFragment = new SettingFragment();
            Utils.addNextFragment(R.id.activity_home_container, HomeActivity.this, settingFragment, getFragmentManager().findFragmentById(R.id.activity_home_container), false);
            resideMenu.closeMenu();
        } else if (view == itemSuggetion) {

        } else if (view == itemSuggetion) {

        } else if (view == imgMenu) {

            if (imgMenu.getTag().toString().equalsIgnoreCase(Constants.TAG_BACK)) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                } else {
                    finish();
                }
            } else {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }

        } else if (view == itemTutorial) {
            startActivity(new Intent(HomeActivity.this, TutorialActivity.class));
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}
