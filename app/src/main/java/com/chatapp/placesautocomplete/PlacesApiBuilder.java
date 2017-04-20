package com.chatapp.placesautocomplete;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chatapp.placesautocomplete.*;
import com.chatapp.placesautocomplete.network.PlacesHttpClient;
import com.chatapp.placesautocomplete.network.PlacesHttpClientResolver;

public class PlacesApiBuilder {

    @Nullable
    private PlacesHttpClient apiClient;

    @Nullable
    private String googleApiKey;

    public PlacesApiBuilder setApiClient(@NonNull final PlacesHttpClient apiClient) {
        this.apiClient = apiClient;
        return this;
    }

    public PlacesApiBuilder setGoogleApiKey(@NonNull final String googleApiKey) {
        if (TextUtils.isEmpty(googleApiKey)) {
            throw new IllegalArgumentException("googleApiKey cannot be null or empty!");
        }

        this.googleApiKey = googleApiKey;
        return this;
    }

    @NonNull
    public com.chatapp.placesautocomplete.PlacesApi build() {
        if (apiClient == null) {
            apiClient = PlacesHttpClientResolver.PLACES_HTTP_CLIENT;
        }

        if (googleApiKey == null) {
            throw new IllegalArgumentException("googleApiKey cannot be null when building " + com.chatapp.placesautocomplete.PlacesApi.class.getSimpleName());
        }

        return new com.chatapp.placesautocomplete.PlacesApi(apiClient, googleApiKey);
    }
}
