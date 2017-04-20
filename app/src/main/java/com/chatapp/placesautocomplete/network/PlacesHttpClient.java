package com.chatapp.placesautocomplete.network;

import android.net.Uri;

import com.chatapp.placesautocomplete.model.PlacesAutocompleteResponse;
import com.chatapp.placesautocomplete.model.PlacesDetailsResponse;

import java.io.IOException;

public interface PlacesHttpClient {
    PlacesAutocompleteResponse executeAutocompleteRequest(Uri uri) throws IOException;

    PlacesDetailsResponse executeDetailsRequest(Uri uri) throws IOException;
}
