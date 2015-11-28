package com.utdreqeng.whoosh.whoosh;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.INodeGroup;
import edu.utdallas.whoosh.api.NodeType;
import edu.utdallas.whoosh.appservices.LocationService;

/**
 * Created by Dustin on 11/5/2015.
 */

public class RouteMap {

    private GoogleMap map;
    private LocationManager locationManager;
    private LatLng loc;

    private LocationService locationService;

    private Map<GroupType, BitmapDescriptor> groupIcons;
    private Map<NodeType, BitmapDescriptor> nodeIcons;



    public RouteMap(Activity activity) {
        map = ((MapFragment) activity.getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        locationManager = (LocationManager) activity.getSystemService(activity.getApplicationContext().LOCATION_SERVICE);

        locationService = LocationService.getInstance();

        // load NodeGroup icons
        groupIcons = new HashMap<>();
        groupIcons.put(GroupType.Building, BitmapDescriptorFactory.fromResource(R.drawable.grouptype_building));
        for (GroupType type : GroupType.values()) {
            if (groupIcons.containsKey(type) != true) {
                groupIcons.put(type, BitmapDescriptorFactory.defaultMarker());
            }
        }

        // load Node icons
        nodeIcons = new HashMap<>();
        nodeIcons.put(NodeType.Ramp, BitmapDescriptorFactory.fromResource(R.drawable.nodetype_ramp));
        nodeIcons.put(NodeType.Elevator, BitmapDescriptorFactory.fromResource(R.drawable.nodetype_elevator));
        for (NodeType type : NodeType.values()) {
            if (nodeIcons.containsKey(type) != true) {
                nodeIcons.put(type, BitmapDescriptorFactory.defaultMarker());
            }
        }
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
        // drop pins for all NodeGroup's (buildings), and zoom to encompass them all
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (INodeGroup group : this.locationService.getGroupsByType(GroupType.Building)) {
            map.addMarker(new MarkerOptions()
                            .position(group.getCenterCoordinates())
                            .title(group.getId())
                            .snippet(group.getName())
                            .icon(groupIcons.get(group.getType()))
                            .anchor(0.5f, 0.5f)
            );
            builder.include(group.getCenterCoordinates());
        }
        map.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                        builder.build(),
                        200
                )
        );
    }

    public LatLng getLastLocation(){
        return loc;
    }
}
