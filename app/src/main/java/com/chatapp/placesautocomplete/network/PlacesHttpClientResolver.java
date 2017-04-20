package com.chatapp.placesautocomplete.network;

import com.chatapp.placesautocomplete.json.JsonParserResolver;
import com.chatapp.placesautocomplete.json.PlacesApiJsonParser;
import com.chatapp.placesautocomplete.network.*;
import com.chatapp.placesautocomplete.network.OkHttpPlacesHttpClient;

public final class PlacesHttpClientResolver {
    public static final com.chatapp.placesautocomplete.network.PlacesHttpClient PLACES_HTTP_CLIENT;

    static {
        boolean hasOkHttp;

        try {
            Class.forName("com.squareup.okhttp.OkHttpClient");
            hasOkHttp = true;
        } catch (ClassNotFoundException e) {
            hasOkHttp = false;
        }

        PlacesApiJsonParser parser = JsonParserResolver.JSON_PARSER;

        PLACES_HTTP_CLIENT = hasOkHttp ? new com.chatapp.placesautocomplete.network.OkHttpPlacesHttpClient(parser) : new HttpUrlConnectionMapsHttpClient(parser);
    }

    private PlacesHttpClientResolver() {
        throw new RuntimeException("No Instances!");
    }
}
