package com.chatapp.tutorial.custom;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.tutorial.adapter.OnBoardingAdapter;
import com.chatapp.tutorial.custom.transformer.AlphaTransformer;
import com.chatapp.tutorial.custom.transformer.CubeInTransformer;
import com.chatapp.tutorial.custom.transformer.CubeOutTransformer;
import com.chatapp.tutorial.custom.transformer.DepthPageTransformer;
import com.chatapp.tutorial.custom.transformer.FlipHorizontalTransformer;
import com.chatapp.tutorial.custom.transformer.FlipVerticalTransformer;
import com.chatapp.tutorial.custom.transformer.RotationPageTransformer;
import com.chatapp.tutorial.custom.transformer.TempTransformer;
import com.chatapp.tutorial.custom.transformer.ZoomInTransformer;
import com.chatapp.tutorial.custom.transformer.ZoomOutSlideTransformer;
import com.chatapp.tutorial.custom.transformer.ZoomOutTranformer;
import com.chatapp.tutorial.indicator.RoundCornerIndicator;
import com.chatapp.util.PREF;
import com.chatapp.view.HomeActivity;


/**
 * OnBoardingView : This is a custom view that you can set for showing onboarding screen,
 * you can set properties based on your requirement
 */

public class OnBoardingView extends RelativeLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView tvSkip;
    private TextView tvPrevious;
    private ViewPager viewPager;

    private OnBoardingAdapter badgesWalkthroughAdapter;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int[] colors;
    private TypedArray images;
    private String[] titles;
    private String[] messages;
    private AppCompatActivity appCompatActivity;

    private static final String TAG = "OnBoarding";

    public OnBoardingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    /**
     * @param context context
     * @param attrs   attrs
     */
    private void init(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.custom_onboarding, this);
        appCompatActivity = (AppCompatActivity) context;
        tvSkip = (TextView) findViewById(R.id.btn_skip);
        tvPrevious = (TextView) findViewById(R.id.btn_previous);
        viewPager = (ViewPager) findViewById(R.id.activity_walkthrough_view_pager);
        final RoundCornerIndicator roundCornerIndicator = (RoundCornerIndicator) findViewById(R.id.dialog_badges_description_indicator);

        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OnBoardingView,
                0, 0);

        try {
            final boolean mShowPrevNext = a.getBoolean(R.styleable.OnBoardingView_showPreNextView, false);
            final boolean mColorChangeRequired = a.getBoolean(R.styleable.OnBoardingView_showPreNextView, false);
            final int mTypeOfTransformer = a.getInteger(R.styleable.OnBoardingView_pageTransformer, 10);

            final int imageId = a.getResourceId(R.styleable.OnBoardingView_imagesForOnboarding, 0);
            if (imageId != 0) {
                images = getResources().obtainTypedArray(imageId);
            }

            final int titleId = a.getResourceId(R.styleable.OnBoardingView_titlesForOnboarding, 0);
            if (titleId != 0) {
                titles = getResources().getStringArray(titleId);
            }
            final int messageId = a.getResourceId(R.styleable.OnBoardingView_messagesForOnboarding, 0);
            if (messageId != 0) {
                messages = getResources().getStringArray(messageId);
            }
            final int colorId = a.getResourceId(R.styleable.OnBoardingView_colorsForOnboarding, 0);
            if (colorId != 0) {
                colors = getResources().getIntArray(colorId);
            }

            setPreNextView(mShowPrevNext);
            setPageTransformer(mTypeOfTransformer);
            setOnPageChnageListner(mColorChangeRequired);

        } finally {
            a.recycle();
        }

        badgesWalkthroughAdapter = new OnBoardingAdapter(context, images, titles, messages);

        viewPager.setAdapter(badgesWalkthroughAdapter);
        viewPager.setCurrentItem(0);
        roundCornerIndicator.setViewPager(viewPager);

        //viewPager.setClipToPadding(false);
        //viewPager.setPadding(20,0,20,0);

        tvSkip.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        hidePreAndNext();
        if (position == images.length() - 1) {
            tvSkip.setText(R.string.got_it);
            tvPrevious.setVisibility(GONE);
        } else {
            tvSkip.setText(R.string.next);
            tvPrevious.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_skip) {
            if (tvSkip.getText().equals(getContext().getString(R.string.got_it))) {

                if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
                    appCompatActivity.startActivity(new Intent(getContext(), HomeActivity.class));
                    appCompatActivity.finish();
                    appCompatActivity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                    DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_SHOW_TUTORIAL, false).commit();

                } else {
                    appCompatActivity.overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
                    appCompatActivity.finish();
                }
            } else if (tvSkip.getText().equals(getContext().getString(R.string.next))) {
                viewPager.setCurrentItem(getItem(+1), true);
            }
        } else if (view.getId() == R.id.btn_previous) {
            if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
                appCompatActivity.startActivity(new Intent(getContext(), HomeActivity.class));
                appCompatActivity.finish();
                appCompatActivity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_SHOW_TUTORIAL, false).commit();
            } else {
                appCompatActivity.overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
                appCompatActivity.finish();
            }
        }
    }

    /**
     * @param isPrevNextShow true if you want to show previous and next button
     *                       false only skip button will shown
     */
    private void setPreNextView(boolean isPrevNextShow) {
        if (isPrevNextShow) {
            hidePreAndNext();
        } else {
            tvSkip.setText(getContext().getString(R.string.skip));
            tvPrevious.setVisibility(View.GONE);
        }
    }

    /**
     * hidePreandNext : at first position previous button should not show and at last postion we should not show next button
     */
    public void hidePreAndNext() {
        if (viewPager.getCurrentItem() == 0) {
            tvPrevious.setVisibility(View.GONE);
        } else {
            tvPrevious.setVisibility(View.VISIBLE);
        }

        if (viewPager.getCurrentItem() == images.length() - 1) {
            tvSkip.setText(getContext().getString(R.string.got_it));
        } else {
            tvSkip.setText(getContext().getString(R.string.next));
        }
    }

    /**
     * CustomOnPageChangeListener : for chnaging color gradually
     */
    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            final Window window = ((Activity) getContext()).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if (position < (badgesWalkthroughAdapter.getCount() - 1) && position < (colors.length - 1)) {

                viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                //indicaor.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                window.setStatusBarColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));

            } else {
                // the last page color
                viewPager.setBackgroundColor(colors[colors.length - 1]);
                //indicaor.setBackgroundColor(colors[colors.length - 1]);
                window.setStatusBarColor(colors[colors.length - 1]);
            }
        }

        @Override
        public void onPageSelected(int position) {
            hidePreAndNext();
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }

    }

    /**
     * @param i current item of view pager
     * @return
     */
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    /**
     * setPageTransformer : This method is to set pagetransformer to view pager accordingly which type user pass
     *
     * @param type user will give type which type of transformer he/she wants
     */
    public void setPageTransformer(final int type) {
        switch (type) {
            case 0:
                viewPager.setPageTransformer(true, new AlphaTransformer());
                break;

            case 1:
                viewPager.setPageTransformer(true, new CubeInTransformer());
                break;

            case 2:
                viewPager.setPageTransformer(true, new CubeOutTransformer());
                break;

            case 3:
                viewPager.setPageTransformer(true, new DepthPageTransformer());
                break;

            case 4:
                viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
                break;

            case 5:
                viewPager.setPageTransformer(true, new FlipVerticalTransformer());
                break;

            case 6:
                viewPager.setPageTransformer(true, new RotationPageTransformer());
                break;

            case 7:
                viewPager.setPageTransformer(true, new ZoomInTransformer());
                break;

            case 8:
                viewPager.setPageTransformer(true, new ZoomOutTranformer());
                break;

            case 9:
                viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                break;

            case 10:
                viewPager.setPageTransformer(true, new TempTransformer());
                break;

            default:
                viewPager.setPageTransformer(true, new TempTransformer());
                break;
        }

    }

    /**
     * @param isColorChangeRequired if true then custom listner will set and colors will gradullay change
     *                              if false then defalut listner will set
     */
    public void setOnPageChnageListner(boolean isColorChangeRequired) {
        if (isColorChangeRequired) {
            viewPager.addOnPageChangeListener(new CustomOnPageChangeListener());
        } else {
            viewPager.addOnPageChangeListener(this);
        }
    }

}
