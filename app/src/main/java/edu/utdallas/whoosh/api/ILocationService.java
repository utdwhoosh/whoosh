package edu.utdallas.whoosh.api;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Service interface that provides functionality to search and retrieve {@link Node}s,
 * {@link NodeGroup}s, and map graphics (as {@link BitmapDescriptor}s).
 *
 * Created by sasha on 9/22/15.
 */
public interface ILocationService {

    /**
     * given a set of coordinates, returns the closest {@link Node} by distance
     */
    Node getClosestNode(LatLng coordinates);

    /**
     * Given a search string and list of {@link NodeType}s, returns a list of matching {@link Node}s.
     * If the intent is to locate origin/destination nodes for routing, an example call could be
     * {@code searchNodesByTypes("2.2", {@link NodeType#getEndpointTypes()})}.
     */
    List<Node> searchNodesByTypes(String query, List<NodeType> types);

    /**
     * Given a search string, a list of {@link NodeType}s, and a {@link NodeGroup}, returns a list
     * of matching {@link Node}s. If the intent is to locate origin/destination nodes for routing
     * within a certain building, an example call could be
     * {@code searchNodesByTypes("2.2", {@link NodeType#getEndpointTypes()})}.
     */
    List<Node> searchNodesByTypesAndGroup(String query, List<NodeType> types, NodeGroup group);

    /**
     * given a set of coordinates, returns the closest {@link NodeGroup} by distance
     */
    NodeGroup getClosestGroup(LatLng coordinates);

    /**
     * given a {@link GroupType}, returns all {@link NodeGroup}s of that type
     */
    List<NodeGroup> getGroupsByType(GroupType type);

    /**
     * returns a {@link IMapImage} for the top-level map
     */
    IMapImage getCampusMap();

    /**
     * given a {@link NodeGroup} and {@code floor} index, returns a {@link IMapImage} for the
     * map of that building/floor
     */
    IMapImage getGroupMap(NodeGroup group, Integer floor);

}