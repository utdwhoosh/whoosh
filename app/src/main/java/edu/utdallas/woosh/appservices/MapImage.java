package edu.utdallas.woosh.appservices;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.parse.ParseObject;

import edu.utdallas.whoosh.api.IMapImage;

/**
 * Created by Dustin on 10/7/2015.
 */
public class MapImage extends ParseObject implements IMapImage
{
    private int resId;
    private LatLngBounds bounds;

    public MapImage(Context ct, String resName, LatLng bottomLeft, LatLng topRight){
        this.resId = ct.getResources().getIdentifier(resName, "id", ct.getPackageName());
        this.bounds = new LatLngBounds(bottomLeft, topRight);
    }

    @Override
    public BitmapDescriptor getImage() {
        return BitmapDescriptorFactory.fromResource(resId);
    }

    @Override
    public LatLngBounds getBounds() {
        return bounds;
    }
}
