package com.utdreqeng.whoosh.whoosh;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Dustin on 11/5/2015.
 */

public class RouteMap {

    private GoogleMap map;
    private LocationManager locationManager;
    private LatLng loc;

    public RouteMap(Activity activity) {
        map = ((MapFragment) activity.getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        locationManager = (LocationManager) activity.getSystemService(activity.getApplicationContext().LOCATION_SERVICE);
    }

    public void locateUser() {

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));

        if(location==null){
            return;
        }
        loc = new LatLng(location.getLatitude(), location.getLongitude());

        map.addMarker(new MarkerOptions().position(loc));
        if(map != null){
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        }
    }

    public LatLng getLastLocation(){
        return loc;
    }
}
