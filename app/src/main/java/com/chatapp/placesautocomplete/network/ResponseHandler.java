package com.chatapp.placesautocomplete.network;

import com.chatapp.placesautocomplete.json.JsonParsingException;

import java.io.InputStream;

interface ResponseHandler<T> {
    T handleStreamResult(InputStream is) throws JsonParsingException;
}
