package com.chatapp.placesautocomplete.model;

import com.chatapp.placesautocomplete.model.*;
import com.chatapp.placesautocomplete.model.PlaceDetails;
import com.chatapp.placesautocomplete.model.Status;

public final class PlacesDetailsResponse extends com.chatapp.placesautocomplete.model.PlacesApiResponse {

    public final com.chatapp.placesautocomplete.model.PlaceDetails result;

    public PlacesDetailsResponse(final Status status, final String error_message, final PlaceDetails result) {
        super(status, error_message);
        this.result = result;
    }
}
