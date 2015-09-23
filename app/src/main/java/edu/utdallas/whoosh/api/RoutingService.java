package edu.utdallas.whoosh.api;

/**
 * Service interface that provides functionality to calculate a {@link Route} between two {@link Node}s.
 *
 * Created by sasha on 9/21/15.
 */
public interface RoutingService {

    /**
     * Given an {@link Node origin}, {@link Node destination}, and {@link RouteType mode of transit},
     * calculates and returns a {@link Route} describing the shortest distance (in terms of time).
     */
    Route getRoute(Node origin, Node destination, RouteType type);

}