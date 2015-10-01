package edu.utdallas.whoosh.api;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Data interface that aggregates a graphical asset with its metadata (right now, just the latitude/longitude
 * bounds).
 *
 * Created by sasha on 9/27/15.
 */
public interface IMapImage {

    /**
     * the graphical asset of this map
     */
    BitmapDescriptor getImage();

    /**
     * the latitude/longitude bounds covered by this map
     */
    LatLngBounds getBounds();

}