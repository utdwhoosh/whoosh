package edu.utdallas.whoosh.api;

/**
 * Service interface that provides functionality to convert a {@link Route} (technical representation)
 * to {@link Directions} (human-oriented representation).
 *
 * Created by sasha on 9/22/15.
 */
public interface DirectionsService {

    /**
     * given a {@link Route}, calculates and returns the {@link Directions}
     */
    Directions getDirections(Route route);

}