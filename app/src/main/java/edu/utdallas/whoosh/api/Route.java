package edu.utdallas.whoosh.api;

import java.util.List;

/**
 * Data interface that represents a path between two {@link Node}s as a collection of intermediate
 * {@link Node}s, as well as the parameters used to generate it.
 *
 * Created by sasha on 9/21/15.
 */
public interface Route {

    /**
     * the "from" {@link Node}; should be the first element in {@link #getPath()}
     */
    Node getOrigin();

    /**
     * the "to" {@link Node}; should be the last element in {@link #getPath()}
     */
    Node getDestination();

    /**
     * the {@link RouteType mode of transit}
     */
    RouteType getType();

    /**
     * ordered list of {@link Node}s that describes the path between this route's origin and destination
     */
    List<Node> getPath();

    /**
     * the estimated length of time in minutes to complete this route, given its {@link #getType() mode of transit}
     */
    int getTimeInMinutes();

    /**
     * the calculated distance in feet of this route
     */
    int getDistanceInFeet();

}