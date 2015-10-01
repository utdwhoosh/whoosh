package edu.utdallas.whoosh.api;

import java.util.ArrayList;
import java.util.List;

/**
 * An enumeration listing the various types of {@link INode}s.
 *
 * Created by sasha on 9/21/15.
 */
public enum NodeType {

    Path,
    Curb,
    Ramp,
    Skybridge,
    Elevator,
    Door,
    Room(true),
    Restroom,
    Stair,
    ;

    boolean endpoint;

    NodeType() {
        this(false);
    }
    NodeType(boolean endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * a flag indicating whether this type is a valid origin/destination (see {@link IRoute#getOrigin()}
     * and {@link IRoute#getDestination()})
     */
    public boolean isEndpoint() {
        return this.endpoint;
    }

    /**
     * a {@link List} of types valid as origins/destinations (see {@link ILocationService#searchNodesByTypes(String, List)})
     */
    public static List<NodeType> getEndpointTypes() {

        List<NodeType> results = new ArrayList<>();

        for (NodeType type : NodeType.values()) {
            if (type.isEndpoint()) {
                results.add(type);
            }
        }

        return results;
    }

}