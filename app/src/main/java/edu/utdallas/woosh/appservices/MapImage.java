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
    private String groupName, resName;
    private int floor;

    public MapImage(String resName, LatLng bottomLeft, LatLng topRight, String groupName, int floor){

        this.bounds = new LatLngBounds(bottomLeft, topRight);
        this.groupName = groupName;
        this.floor = floor;
    }

    /**
     * Must be called before the image can be used.
     * @param ct - context retrieved from the application Activity
     */
    public void init(Context ct){
        this.resId = ct.getResources().getIdentifier(resName, "id", ct.getPackageName());
    }

    @Override
    public BitmapDescriptor getImage() {
        return BitmapDescriptorFactory.fromResource(resId);
    }

    @Override
    public LatLngBounds getBounds() {
        return bounds;
    }

    public String getGroupName(){
        return groupName;
    }

    public int getFloor(){
        return floor;
    }
}
