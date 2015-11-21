package edu.utdallas.whoosh.appservices;

import android.content.Context;

/**
 * Created by Dustin on 10/8/2015.
 */
public class InitService {

    public static void init(Context context){

        LocationService.getInstance().init(context);
        DBManager.getInstance().init();
    }

    public static boolean isReady(){
        return DBManager.getInstance().isReady;
    }
}
