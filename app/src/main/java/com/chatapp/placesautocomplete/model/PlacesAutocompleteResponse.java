package com.chatapp.placesautocomplete.model;

import com.chatapp.placesautocomplete.model.*;
import com.chatapp.placesautocomplete.model.Place;
import com.chatapp.placesautocomplete.model.Status;

import java.util.List;

/**
 * Model representing the places api autocomplete response; complete with status codes and error messages
 */
public final class PlacesAutocompleteResponse extends com.chatapp.placesautocomplete.model.PlacesApiResponse {

    /**
     * A list of predicted places from the api based on the given input
     */
    public final List<com.chatapp.placesautocomplete.model.Place> predictions;

    public PlacesAutocompleteResponse(final Status status, final String error_message, final List<Place> predictions) {
        super(status, error_message);
        this.predictions = predictions;
    }
}
