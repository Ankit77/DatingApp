package com.chatapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chatapp.R;
import com.chatapp.adapter.SuggetionAdapter;
import com.chatapp.model.SuggetionModel;
import com.chatapp.swipestack.SwipeStack;
import com.chatapp.swipeview.CardStackView;
import com.chatapp.swipeview.Direction;
import com.chatapp.util.Utils;
import com.chatapp.util.WriteLog;
import com.chatapp.view.HomeActivity;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ANKIT on 4/1/2017.
 */

public class SuggestionFragment extends Fragment implements SwipeStack.SwipeStackListener {
    private View view;
    private SuggetionAdapter suggetionAdapter;
    private ArrayList<SuggetionModel> list;
    private HomeActivity homeActivity;
    private RippleBackground rippleBackground;
    private SwipeStack swipeStack;
    private ArrayList<String> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_suggetion, null);
        setHasOptionsMenu(true);
        homeActivity = (HomeActivity) getActivity();
//        cardStackView = (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
//        cardStackView.setCardStackEventListener(this);
        swipeStack = (SwipeStack) view.findViewById(R.id.activity_main_card_stack_view);
        init(view);
        return view;
    }

    private void init(View view) {
//        rippleBackground = (RippleBackground) view.findViewById(R.id.fragment_suggestion_radarview);
//        cardStackView = (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
        loadData();
        homeActivity.setActionBarTitle("Matches");
        homeActivity.isBackEnable(false);
    }

    private void loadData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                rippleBackground.startRippleAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        rippleBackground.stopRippleAnimation();
                        list = new ArrayList<>();
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));
                        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"));

                        suggetionAdapter = new SuggetionAdapter(getActivity(), list);
//                        cardStackView.setAdapter(suggetionAdapter);

                        fillWithTestData();
                        swipeStack.setAdapter(new SwipeStackAdapter(mData));
                    }
                }, 5000);
            }
        }, 1000);


    }

    private void fillWithTestData() {
        for (int x = 0; x < 5; x++) {
            mData.add("test" + " " + (x + 1));
        }
    }

    public class SwipeStackAdapter extends BaseAdapter {

        private List<String> mData;

        public SwipeStackAdapter(List<String> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.card, parent, false);
            }

            TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
            textViewCard.setText(mData.get(position));

            return convertView;
        }
    }

    private void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onBeginSwipe(int index, Direction direction) {
//        WriteLog.E(SuggestionFragment.class.getSimpleName(), direction.name());
//    }
//
//    @Override
//    public void onEndSwipe(Direction direction) {
//
//    }
//
//    @Override
//    public void onSwiping(float positionX) {
//    }
//
//    @Override
//    public void onDiscarded(int index, Direction direction) {
//        WriteLog.E(SuggestionFragment.class.getSimpleName(), direction.name());
//    }
//
//    @Override
//    public void onTapUp(int index) {
//        ProfileDetailFragment profileDetailFragment = new ProfileDetailFragment();
//        Utils.addNextFragment(R.id.activity_home_container, homeActivity, profileDetailFragment, SuggestionFragment.this, false);
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            homeActivity.setActionBarTitle("Matches");
            homeActivity.isBackEnable(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat:
                Toast.makeText(getActivity(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewSwipedToLeft(int position) {
        WriteLog.E(SuggestionFragment.class.getSimpleName(), "" + position);
    }

    @Override
    public void onViewSwipedToRight(int position) {
        WriteLog.E(SuggestionFragment.class.getSimpleName(), "" + position);
    }

    @Override
    public void onStackEmpty() {
        WriteLog.E(SuggestionFragment.class.getSimpleName(), "");
    }
}
