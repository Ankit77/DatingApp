package com.chatapp.placesautocomplete.network;

import android.net.Uri;

import com.chatapp.placesautocomplete.json.PlacesApiJsonParser;
import com.chatapp.placesautocomplete.model.PlacesApiException;
import com.chatapp.placesautocomplete.model.PlacesApiResponse;
import com.chatapp.placesautocomplete.model.Status;
import com.chatapp.placesautocomplete.network.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class OkHttpPlacesHttpClient extends AbstractPlacesHttpClient {
    private final OkHttpClient okHttpClient;

    OkHttpPlacesHttpClient(PlacesApiJsonParser parser) {
        super(parser);
        okHttpClient = new OkHttpClient();
//        okHttpClient.setConnectTimeout(15L, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(15L, TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(15L, TimeUnit.SECONDS);
    }

    @Override
    protected <T extends PlacesApiResponse> T executeNetworkRequest(final Uri uri, final com.chatapp.placesautocomplete.network.ResponseHandler<T> responseHandler) throws IOException {
        final Request request = new Request.Builder()
                .url(uri.toString())
                .build();

        Response response = okHttpClient.newCall(request).execute();

        T body = responseHandler.handleStreamResult(response.body().byteStream());

        Status status = body.status;

        if (status != null && !status.isSuccessful()) {
            String err = body.error_message;
            throw new PlacesApiException(err != null ? err : "Unknown Places Api Error");
        } else {
            return body;
        }
    }
}
