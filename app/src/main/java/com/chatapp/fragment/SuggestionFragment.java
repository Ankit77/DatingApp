package com.chatapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chatapp.R;
import com.chatapp.adapter.SuggetionAdapter;
import com.chatapp.model.SuggetionModel;
import com.chatapp.util.Utils;
import com.chatapp.util.WriteLog;
import com.chatapp.view.HomeActivity;
import com.chatapp.wenchao.cardstack.CardAnimator;
import com.chatapp.wenchao.cardstack.CardStack;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;


/**
 * Created by ANKIT on 4/1/2017.
 */

public class SuggestionFragment extends Fragment implements CardStack.CardEventListener {
    private View view;
    private SuggetionAdapter suggetionAdapter;
    private ArrayList<SuggetionModel> list;
    private HomeActivity homeActivity;
    private RippleBackground rippleBackground;
    private CardStack cardStack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_suggetion, null);

        homeActivity = (HomeActivity) getActivity();
        cardStack = (CardStack) view.findViewById(R.id.container);
        cardStack.setStackGravity(CardAnimator.TOP);
        init(view);
        return view;
    }

    private void init(View view) {
        rippleBackground = (RippleBackground) view.findViewById(R.id.fragment_suggestion_radarview);
        loadData();
        homeActivity.setActionBarTitle("Matches");
        homeActivity.isBackEnable(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_chat, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat:
                Toast.makeText(getActivity(), "Chat is called", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


    private void loadData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rippleBackground.startRippleAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rippleBackground.stopRippleAnimation();
                        list = new ArrayList<>();
                        list.add(new SuggetionModel("Änkit Khatri 1", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 2", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 3", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 4", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 5", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 6", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 7", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 8", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 9", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 10", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 11", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 12", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 13", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 14", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri 15", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));

                        suggetionAdapter = new SuggetionAdapter(getActivity(), list);
                        cardStack.setAdapter(suggetionAdapter);
                        cardStack.setStackGravity(cardStack.getStackGravity() == CardAnimator.TOP ? CardAnimator.BOTTOM : CardAnimator.TOP);
                        cardStack.reset(true);
                        cardStack.setListener(SuggestionFragment.this);
                    }
                }, 5000);
            }
        }, 1000);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            homeActivity.setActionBarTitle("Matches");
            homeActivity.isBackEnable(false);
        }
    }

    @Override
    public boolean swipeEnd(int section, float distance) {
        if (section == 0) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Top Left");
        } else if (section == 1) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Top Right");
        } else if (section == 2) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Bottom Left");
        } else if (section == 3) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Bottom Right");
        }
        return (distance > 300) ? true : false;


    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return true;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void discarded(int mIndex, int direction) {
        WriteLog.E(SuggestionFragment.class.getSimpleName(), "Index is" + mIndex);
    }

    @Override
    public void topCardTapped(int mIndex) {
        ProfileDetailFragment profileDetailFragment = new ProfileDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.key_isloggedin_user), false);
        profileDetailFragment.setArguments(bundle);
        Utils.addNextFragment(R.id.activity_home_container, homeActivity, profileDetailFragment, SuggestionFragment.this, false);
        WriteLog.E(SuggestionFragment.class.getSimpleName(), list.get(mIndex).getName());
    }
}

