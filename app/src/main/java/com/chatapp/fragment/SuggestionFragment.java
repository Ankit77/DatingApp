package com.chatapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chatapp.R;
import com.chatapp.adapter.SuggetionAdapter;
import com.chatapp.model.SuggetionModel;
import com.chatapp.swipeview.CardStackView;
import com.chatapp.swipeview.Direction;

import java.util.ArrayList;

/**
 * Created by ANKIT on 4/1/2017.
 */

public class SuggestionFragment extends Fragment implements CardStackView.CardStackEventListener {
    private View view;
    private CardStackView cardStackView;
    private SuggetionAdapter suggetionAdapter;
    private ArrayList<SuggetionModel> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_suggetion, null);
        cardStackView = (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
        cardStackView.setCardStackEventListener(this);
        init(view);
        return view;
    }

    private void init(View view) {
        cardStackView = (CardStackView) view.findViewById(R.id.activity_main_card_stack_view);
        loadData();
    }

    private void loadData() {
        list = new ArrayList<>();
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        list.add(new SuggetionModel("Änkit Khatri", "28", "Ahmedabad", "Software Engineer", "2.5", 1, ""));
        suggetionAdapter = new SuggetionAdapter(getActivity(), list);
        cardStackView.setAdapter(suggetionAdapter);
    }


    private void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginSwipe(int index, Direction direction) {

    }

    @Override
    public void onEndSwipe(Direction direction) {

    }

    @Override
    public void onSwiping(float positionX) {

    }

    @Override
    public void onDiscarded(int index, Direction direction) {

    }

    @Override
    public void onTapUp(int index) {

    }
}
