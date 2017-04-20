package com.chatapp.placesautocomplete.network;

import android.net.Uri;

import com.chatapp.placesautocomplete.json.JsonParsingException;
import com.chatapp.placesautocomplete.json.PlacesApiJsonParser;
import com.chatapp.placesautocomplete.model.PlacesApiResponse;
import com.chatapp.placesautocomplete.model.PlacesAutocompleteResponse;
import com.chatapp.placesautocomplete.model.PlacesDetailsResponse;
import com.chatapp.placesautocomplete.network.*;
import com.chatapp.placesautocomplete.network.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;

abstract class AbstractPlacesHttpClient implements com.chatapp.placesautocomplete.network.PlacesHttpClient {

    protected final PlacesApiJsonParser placesApiJsonParser;

    protected AbstractPlacesHttpClient(PlacesApiJsonParser parser) {
        placesApiJsonParser = parser;
    }

    @Override
    public PlacesAutocompleteResponse executeAutocompleteRequest(final Uri uri) throws IOException {
        return executeNetworkRequest(uri, new com.chatapp.placesautocomplete.network.ResponseHandler<PlacesAutocompleteResponse>() {

            @Override
            public PlacesAutocompleteResponse handleStreamResult(final InputStream is) throws JsonParsingException {
                return placesApiJsonParser.autocompleteFromStream(is);
            }
        });
    }

    @Override
    public PlacesDetailsResponse executeDetailsRequest(final Uri uri) throws IOException {
        return executeNetworkRequest(uri, new com.chatapp.placesautocomplete.network.ResponseHandler<PlacesDetailsResponse>() {

            @Override
            public PlacesDetailsResponse handleStreamResult(final InputStream is) throws JsonParsingException {
                return placesApiJsonParser.detailsFromStream(is);
            }
        });
    }

    protected abstract <T extends PlacesApiResponse> T executeNetworkRequest(Uri uri, com.chatapp.placesautocomplete.network.ResponseHandler<T> responseHandler) throws IOException;
}
