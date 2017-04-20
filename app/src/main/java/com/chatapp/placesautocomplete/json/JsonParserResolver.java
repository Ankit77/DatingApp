package com.chatapp.placesautocomplete.json;

import com.chatapp.placesautocomplete.json.AndroidPlacesApiJsonParser;
import com.chatapp.placesautocomplete.json.GsonPlacesApiJsonParser;
import com.chatapp.placesautocomplete.json.PlacesApiJsonParser;

public final class JsonParserResolver {
    public static final PlacesApiJsonParser JSON_PARSER;

    static {
        boolean hasGson;
        try {
            Class.forName("com.google.gson.Gson");
            hasGson = true;
        } catch (ClassNotFoundException e) {
            hasGson = false;
        }

        JSON_PARSER = hasGson ? new GsonPlacesApiJsonParser() : new AndroidPlacesApiJsonParser();
    }

    private JsonParserResolver() {
        throw new RuntimeException("No instances");
    }
}
