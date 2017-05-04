package com.chatapp.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.placesautocomplete.DetailsCallback;
import com.chatapp.placesautocomplete.OnPlaceSelectedListener;
import com.chatapp.placesautocomplete.PlacesAutocompleteTextView;
import com.chatapp.placesautocomplete.model.PlaceDetails;
import com.chatapp.util.Constants;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.chatapp.view.HomeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by indianic on 11/04/17.
 */

public class SelectLocationFragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap map;
    //    private TextView tvSearch;
    private static final int REQUEST_SELECT_PLACE = 1000;
    private HomeActivity homeActivity;
    private PlacesAutocompleteTextView mAutocomplete;
    private LatLng crntLocationLatLng;
    private String location = "";
    private String title = "";
    private String userid = "";
    private String receverid = "";
    private String fulladdress = "";
    private String smallAddress = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String zipcode = "";
    private String poiId = "";
    private String seachtext = "";
    private String profilePath = "";
    private GetLocationAsync mGetLocationAsync;
    private FrameLayout flmain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selectlocation, container, false);
        homeActivity = (HomeActivity) getActivity();
        homeActivity.setActionBarTitle(getString(R.string.title_selectlocation));
        homeActivity.isBackEnable(true);
        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.fragment_selectlocation_mapview);
        mapView.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());
        init(v);
        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_location, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_done) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
        }
        return true;
    }

    private void init(View view) {
        flmain = (FrameLayout) view.findViewById(R.id.fragment_selectlocation_flmain);
        mAutocomplete = (PlacesAutocompleteTextView) view.findViewById(R.id.autocomplete);
        mAutocomplete.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {

            @Override
            public void onPlaceSelected(@NonNull com.chatapp.placesautocomplete.model.Place place) {
                mAutocomplete.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(PlaceDetails details) {
                        Location targetLocation = new Location("");//provider name is unnecessary
                        targetLocation.setLatitude(details.geometry.location.lat);//your coords of course
                        targetLocation.setLongitude(details.geometry.location.lng);
                        DatingApp.getsInstance().setCurrentLocation(targetLocation);
                        DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_PLACE_NAME, details.name.toString()).commit();
                        DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_PLACE_LAT, String.valueOf(details.geometry.location.lng)).commit();
                        DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_PLACE_LONG, String.valueOf(details.geometry.location.lng)).commit();
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(details.geometry.location.lat, details.geometry.location.lng)));
                        // Zoom in the Google Map
                        map.animateCamera(CameraUpdateFactory.zoomTo(15));
                        Utils.hideSoftKeyBoard(getActivity());
                    }

                    @Override
                    public void onFailure(Throwable failure) {

                    }
                });


            }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_IS_CURRENT_LOCATION, Constants.SHOW_CURRENT_LOCATION).equalsIgnoreCase(Constants.SHOW_CURRENT_LOCATION)) {
            crntLocationLatLng = new LatLng(DatingApp.getsInstance().getCurrentLocation().getLatitude(), DatingApp.getsInstance().getCurrentLocation().getLongitude());
        } else {
            Double lat = Double.parseDouble(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_PLACE_LAT, ""));
            Double lang = Double.parseDouble(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_PLACE_LONG, ""));

            crntLocationLatLng = new LatLng(lat, lang);
        }
        CameraPosition cameraPosition = new CameraPosition.Builder().target(crntLocationLatLng).zoom(14.5f).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {

                crntLocationLatLng = map.getCameraPosition().target;
                map.clear();

                try {
                    if (crntLocationLatLng.latitude != 0 && crntLocationLatLng.longitude != 0) {
                        Location targetLocation = new Location("");//provider name is unnecessary
                        targetLocation.setLatitude(crntLocationLatLng.latitude);//your coords of course
                        targetLocation.setLongitude(crntLocationLatLng.longitude);
                        DatingApp.getsInstance().setCurrentLocation(targetLocation);
                        DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_PLACE_LAT, String.valueOf(crntLocationLatLng.latitude)).commit();
                        DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_PLACE_LONG, String.valueOf(crntLocationLatLng.longitude)).commit();
                        latlongToAddress(crntLocationLatLng.latitude, crntLocationLatLng.longitude);
                    }

                } catch (Exception e) {
                }
            }
        });

    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SELECT_PLACE) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void latlongToAddress(double latitude, double longitude) {

        if (mGetLocationAsync != null && mGetLocationAsync.getStatus() == AsyncTask.Status.PENDING) {
            mGetLocationAsync.execute();
        } else if (mGetLocationAsync == null || mGetLocationAsync.getStatus() == AsyncTask.Status.FINISHED) {
            mGetLocationAsync = new GetLocationAsync(latitude, longitude, getActivity());
            mGetLocationAsync.execute();
        }


    }

    private class GetLocationAsync extends AsyncTask<String, Void, String> {

        // boolean duplicateResponse;
        double x, y;
        private Context context;

        public GetLocationAsync(double latitude, double longitude, final Context context) {
            x = latitude;
            y = longitude;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            location = String.valueOf(x + "," + y);
        }

        @Override
        protected String doInBackground(String... params) {

            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                List<Address> addressList = geocoder.getFromLocation(x, y, 1);
                StringBuilder sb = new StringBuilder();

                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);

                    fulladdress = "";
                    smallAddress = "";

                    if (address.getLocality() != null) {
                        fulladdress = address.getLocality();
                    }

                    if (address.getAdminArea() != null) {
                        if (TextUtils.isEmpty(fulladdress)) {
                            fulladdress = address.getAdminArea();
                        } else {
                            fulladdress = fulladdress + "," + address.getAdminArea();
                        }
                        DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_PLACE_NAME, fulladdress).commit();
                    }

//                    if (sb.append(address.getAddressLine(0)) != null) {
//                        smallAddress = address.getAddressLine(0);
//                        //Log.e("", "smallAddress:-" + smallAddress);
//                        fulladdress = smallAddress;
//                    }
//
//                    if (address.getLocality() != null) {
//                        city = address.getLocality();
//                        if (!fulladdress.contains(city))
//                            fulladdress = fulladdress + " " + city;
//
//                    }
//                    if (address.getAdminArea() != null) {
//                        state = address.getAdminArea();
//                        if (!fulladdress.contains(state))
//                            fulladdress = fulladdress + " " + state;
//
//                    }
//                    if (address.getPostalCode() != null) {
//                        zipcode = address.getPostalCode();
//                        if (!fulladdress.contains(zipcode))
//                            fulladdress = fulladdress + " " + zipcode;
//
//                    }
//                    if (address.getCountryName() != null) {
//                        country = address.getCountryName();
//                        if (!fulladdress.contains(country))
//                            fulladdress = fulladdress + " " + country;
//
//                    }

                }
            } catch (IOException e) {
                // Log.e("", "Unable connect to Geocoder", e);
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (fulladdress != null && !fulladdress.isEmpty()) {

                    DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_PLACE_NAME, fulladdress).commit();
                    Snackbar snackbar;
                    snackbar = Snackbar.make(flmain, fulladdress, Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                    snackbar.show();
                    // fulladdress=deDup(fulladdress);
                    // Log.e("", "fulladdress:-" + fulladdress);
//                    tvStartDest.setText("" + fulladdress);
//                    tvStartDest.dismissDropDown();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}
