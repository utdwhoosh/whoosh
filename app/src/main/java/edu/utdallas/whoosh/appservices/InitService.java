package edu.utdallas.whoosh.appservices;

import android.content.Context;

/**
 * Created by Dustin on 10/8/2015.
 */
public class InitService {

    public static void init(final Context context, final Callback callback){

        DBManager.getInstance().init(new Callback() {
            @Override
            public void call(boolean success) {

                if (success) {
                    LocationService.getInstance().init(context);

                    //TODO: for each group and floor, create a relevant MapImage object and add to LocationService
                    /*LocationService.getInstance().putMapImage(new MapImage(R.drawable.atc1, new LatLng(), new LatLng(),
                        "ATC", 1));*/
                }

                callback.call(success);
            }
        });
    }

    public static boolean isReady(){
        return DBManager.getInstance().isReady;
    }
}