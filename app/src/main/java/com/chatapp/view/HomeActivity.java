package com.chatapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chatapp.R;
import com.chatapp.common.ResideMenu;
import com.chatapp.common.ResideMenuItem;
import com.chatapp.fragment.ProfileDetailFragment;
import com.chatapp.fragment.SettingFragment;
import com.chatapp.fragment.SuggestionFragment;
import com.chatapp.tutorial.activity.TutorialActivity;
import com.chatapp.util.Constants;
import com.chatapp.util.Utils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
    private ImageView imgProfile;
    private TextView tvProfileName;
    private TextView tvLogout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpMenu();
        initToolBar();
        init();

        SuggestionFragment suggestionFragment = new SuggestionFragment();
        getFragmentManager().beginTransaction().add(R.id.activity_home_container, suggestionFragment, SuggestionFragment.class.getSimpleName()).commit();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void init() {
        imgMenu = (ImageView) findViewById(R.id.activity_home_img_menu);
        tvTitle = (TextView) findViewById(R.id.activity_home_tv_title);
        imgProfile = (ImageView) resideMenu.getSideMenuView().findViewById(R.id.residemenu_custom_left_scrollview_img_userphoto);
        tvProfileName = (TextView) resideMenu.getSideMenuView().findViewById(R.id.residemenu_custom_left_scrollview_tv_username);
        tvLogout = (TextView) resideMenu.getSideMenuView().findViewById(R.id.residemenu_custom_left_scrollview_tv_logout);
        imgMenu.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        loadUserData();
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

    private void loadUserData() {
        Glide.with(HomeActivity.this).load("").placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile)
                .bitmapTransform(new RoundedCornersTransformation(HomeActivity.this, getResources().getDimensionPixelSize(R.dimen._2sdp), 0))
                .into(imgProfile);
        tvProfileName.setText("Ankit,28");
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(HomeActivity.this);
        resideMenu.setUse3D(false);
        resideMenu.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.white));
        resideMenu.attachToActivity(HomeActivity.this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemSetting = new ResideMenuItem(HomeActivity.this, R.drawable.menu_settings, "SETTING");
        itemProfile = new ResideMenuItem(HomeActivity.this, R.drawable.menu_user, "PROFILE");
        itemChat = new ResideMenuItem(HomeActivity.this, R.drawable.menu_chat, "CHAT");
        itemSuggetion = new ResideMenuItem(HomeActivity.this, R.drawable.menu_share, "Share");
        itemTutorial = new ResideMenuItem(HomeActivity.this, R.drawable.menu_information, "HOW ITS WORK");
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
        } else if (view == tvLogout) {
            Toast.makeText(HomeActivity.this, "Logout click", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (resideMenu.isOpened()) {
            resideMenu.closeMenu();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }


}
