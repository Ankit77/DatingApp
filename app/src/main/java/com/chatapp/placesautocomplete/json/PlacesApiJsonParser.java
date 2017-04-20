package com.chatapp.placesautocomplete.json;

import com.chatapp.placesautocomplete.json.JsonParsingException;
import com.chatapp.placesautocomplete.json.JsonWritingException;
import com.chatapp.placesautocomplete.model.Place;
import com.chatapp.placesautocomplete.model.PlacesAutocompleteResponse;
import com.chatapp.placesautocomplete.model.PlacesDetailsResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface PlacesApiJsonParser {
    PlacesAutocompleteResponse autocompleteFromStream(InputStream is) throws JsonParsingException;

    PlacesDetailsResponse detailsFromStream(InputStream is) throws JsonParsingException;

    List<Place> readHistoryJson(InputStream in) throws JsonParsingException;

    void writeHistoryJson(OutputStream os, List<Place> places) throws JsonWritingException;
}
