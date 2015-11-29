package com.utdreqeng.whoosh.whoosh;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.IMapImage;
import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.INodeGroup;
import edu.utdallas.whoosh.api.NodeType;
import edu.utdallas.whoosh.appservices.LocationService;

/**
 * Created by Dustin on 11/5/2015.
 */

public class RouteMap {

    private Activity activity;

    private GoogleMap map;
    private LocationManager locationManager;
    private LatLng loc;

    private LocationService locationService;

    private Map<GroupType, BitmapDescriptor> groupIcons = new HashMap<>();
    private Map<NodeType, BitmapDescriptor> nodeIcons = new HashMap<>();

    private Map<Marker, INodeGroup> markerGroups = new HashMap<>();
    private Map<Marker, INode> markerNodes = new HashMap<>();



    public RouteMap(final Activity activity) {
        this.activity = activity;

        map = ((MapFragment) activity.getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                // Getting view from the layout file info_window_layout
                View v = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.building_info_window, null);

                // Getting the position from the marker
                final INodeGroup group = markerGroups.get(marker);

                // Getting reference to the TextView to set latitude
                TextView tvNodeName = (TextView) v.findViewById(R.id.tvGroupName);
                tvNodeName.setText(group.getName());

                // Returning the view containing InfoWindow contents
                return v;

            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                INodeGroup group = markerGroups.get(marker);
                RouteMap.this.drawFloorPlan(group, group.getDefaultFloor());
            }
        });

        locationManager = (LocationManager) activity.getSystemService(activity.getApplicationContext().LOCATION_SERVICE);

        locationService = LocationService.getInstance();

        // load NodeGroup icons
        groupIcons.put(GroupType.Building, BitmapDescriptorFactory.fromResource(R.drawable.grouptype_building));
        for (GroupType type : GroupType.values()) {
            if (groupIcons.containsKey(type) != true) {
                groupIcons.put(type, BitmapDescriptorFactory.defaultMarker());
            }
        }

        // load Node icons
        nodeIcons.put(NodeType.Ramp, BitmapDescriptorFactory.fromResource(R.drawable.nodetype_ramp));
        nodeIcons.put(NodeType.Elevator, BitmapDescriptorFactory.fromResource(R.drawable.nodetype_elevator));
        for (NodeType type : NodeType.values()) {
            if (nodeIcons.containsKey(type) != true) {
                nodeIcons.put(type, BitmapDescriptorFactory.defaultMarker());
            }
        }
    }

    public void drawFloorPlan(INodeGroup group, Integer floor) {

        // clear building markers
        for (Marker marker : markerGroups.keySet()) {
            marker.setVisible(false);
        }

        // draw floor plan overlay
        IMapImage image = LocationService.getInstance().getGroupMap(group, floor);
        LatLngBounds bounds = new LatLngBounds(image.getBottomLeftCoordinates(), image.getTomRightCoordinates());
        map.addGroundOverlay(new GroundOverlayOptions()
                .image(image.getImage())
                .positionFromBounds(bounds)
        );
        map.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        100
                )
        );

        // draw handicap-relevant nodes
        for (INode node : this.locationService.getNodesByTypesAndGroupAndFloor(NodeType.getHandicapRelevantTypes(), group, floor)) {
            Marker marker = map.addMarker(new MarkerOptions()
                            .position(node.getCoordinates())
                            .title(node.getId())
                            .snippet(node.getName())
                            .icon(groupIcons.get(node.getType()))
                            .anchor(0.5f, 0.5f)
            );
            markerNodes.put(marker, node);
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
            Marker marker = map.addMarker(new MarkerOptions()
                            .position(group.getCenterCoordinates())
                            .title(group.getId())
                            .snippet(group.getName())
                            .icon(groupIcons.get(group.getType()))
                            .anchor(0.5f, 0.5f)
            );
            markerGroups.put(marker, group);
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
