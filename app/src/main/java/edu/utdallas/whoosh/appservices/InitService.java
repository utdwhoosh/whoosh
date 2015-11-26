package edu.utdallas.whoosh.appservices;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.utdreqeng.whoosh.whoosh.R;

/**
 * Created by Dustin on 10/8/2015.
 */
public class InitService {

    public static void init(Context context){

        LocationService.getInstance().init(context);
        DBManager.getInstance().init();

        //TODO: for each group and floor, create a relevant MapImage object and add to LocationService
        /*LocationService.getInstance().putMapImage(new MapImage(R.drawable.atc1, new LatLng(), new LatLng(),
                "ATC", 1));*/
    }

    public static boolean isReady(){
        return DBManager.getInstance().isReady;
    }
}
