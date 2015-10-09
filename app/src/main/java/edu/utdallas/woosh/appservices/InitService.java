package edu.utdallas.woosh.appservices;

import android.content.Context;

/**
 * Created by Dustin on 10/8/2015.
 */
public class InitService {

    public static void init(Context context){

        LocationService.getInstance().init(context);

        //TODO: init other services if needed

        DBManager.getInstance().init();
    }
}
