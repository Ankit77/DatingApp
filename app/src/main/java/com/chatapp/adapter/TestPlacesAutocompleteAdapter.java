package com.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.placesautocomplete.PlacesApi;
import com.chatapp.placesautocomplete.adapter.AbstractPlacesAutocompleteAdapter;
import com.chatapp.placesautocomplete.history.AutocompleteHistoryManager;
import com.chatapp.placesautocomplete.model.AutocompleteResultType;
import com.chatapp.placesautocomplete.model.Place;

/**
 * Created by indianic on 20/04/17.
 */

public class TestPlacesAutocompleteAdapter extends AbstractPlacesAutocompleteAdapter {

    public TestPlacesAutocompleteAdapter(final Context context, final PlacesApi api, final AutocompleteResultType resultType, final AutocompleteHistoryManager history) {
        super(context, api, resultType, history);
    }

    @Override
    protected View newView(final ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.place_autocomplete_item, parent, false);
    }

    @Override
    protected void bindView(final View view, final Place item) {
        ((TextView) view).setText(item.description);
    }

}
