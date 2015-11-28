package com.utdreqeng.whoosh.whoosh;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.INodeGroup;
import edu.utdallas.whoosh.appservices.LocationService;

/**
 * Created by Dustin on 11/5/2015.
 */

public class RouteMap {

    private GoogleMap map;
    private LocationManager locationManager;
    private LatLng loc;

    private LocationService locationService;

    public RouteMap(Activity activity) {
        map = ((MapFragment) activity.getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        locationManager = (LocationManager) activity.getSystemService(activity.getApplicationContext().LOCATION_SERVICE);

        locationService = LocationService.getInstance();
    }

    public void locateUser() {

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));

        if(location!=null){
            loc = new LatLng(location.getLatitude(), location.getLongitude());

            // drop pin for current position
            map.addMarker(new MarkerOptions().position(loc));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        }
    }

    public void drawBuildings() {
        // drop pins for all NodeGroup's (buildings)
        for (INodeGroup group : this.locationService.getGroupsByType(GroupType.Building)) {
            map.addMarker(new MarkerOptions().position(group.getCenterCoordinates()));
        }
    }

    public LatLng getLastLocation(){
        return loc;
    }
}
