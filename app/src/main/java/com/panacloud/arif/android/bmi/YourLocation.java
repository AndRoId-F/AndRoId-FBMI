package com.panacloud.arif.android.bmi;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.panacloud.arif.android.R;

/**
 * Created by Arif on 10/21/2014.
 */
public class YourLocation extends Activity implements com.google.android.gms.location.LocationListener {
    static final LatLng TutorialsPoint = new LatLng(21 , 57);
    private GoogleMap googleMap;

    com.google.android.gms.maps.MapFragment aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapyourlocation);
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // Marker TP = googleMap.addMarker(new MarkerOptions().position(TutorialsPoint).title("TutorialsPoint"));

        } catch (Exception e) {
            e.printStackTrace();
        }


        googleMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        Log.d("getLastKnownLocation", "getLastKnownLocation = " + location);

        double longi = location.getLongitude();
        double lati = location.getLatitude();

        LatLng myLoLatLng = new LatLng(lati, longi);

        MarkerOptions marker = new MarkerOptions();
        marker.position(myLoLatLng);
        marker.title("You are here...");
        marker.alpha(.5f);

        // marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(marker);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 12));




/*
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.d("onMyLocationChange ", "myLocation = " + location);
                double longi = location.getLongitude();
                double lati = location.getLatitude();

                LatLng myLoLatLng = new LatLng(lati, longi);

                MarkerOptions marker = new MarkerOptions().position(myLoLatLng).title("You are here...");
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                googleMap.addMarker(marker);


            }
        });
*/












/*


        Location myLocation = googleMap.getMyLocation();


        Log.d("","googleMap = " + googleMap);
        Log.d("","myLocation = " + myLocation);


        double longi = myLocation.getLongitude();
        double lati = myLocation.getLatitude();

        LatLng myLoLatLng = new LatLng(lati, longi);
        //Marker myLocationMarker = googleMap.addMarker(new MarkerOptions().position(myLoLatLng).title("You are here..."));
        MarkerOptions marker = new MarkerOptions().position(myLoLatLng).title("You are here...");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(marker);

        */





    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d("", "onLocationChanged  = " + location);
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

    }




}