package com.utdreqeng.whoosh.whoosh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;

import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.RouteType;
import edu.utdallas.whoosh.appservices.InitService;
import edu.utdallas.whoosh.appservices.DBManager;
import edu.utdallas.whoosh.appservices.LocationService;
import edu.utdallas.whoosh.appservices.Node;
import edu.utdallas.whoosh.appservices.NodeManager;
import edu.utdallas.whoosh.appservices.RoutingService;

public class MainActivity extends AppCompatActivity  {

    //Parse App ID and client key
    public static final String PARSE_APPLICATION_ID = "YB7DqZB3O0LfmIKzd9NzMQ6uhIXRhoawgK7p5B1M";
    public static final String PARSE_CLIENT_KEY = "kA1LGDmyML7rhXEDkQjgNIKw8cNM5VoDpsO7Drxl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        //Initialize the backend services
        InitService.init(this.getApplicationContext());

        // Register your parse models
        //ParseObject.registerSubclass(Node.class);

        //for testing RoutingService
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!InitService.isReady()){
                    try {
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Node start = NodeManager.getInstance().getNode("1");
                Node end = NodeManager.getInstance().getNode("20");
                RoutingService rs = new RoutingService();

                Log.d(getClass().getName(), "trying to build path");

                String temp = "";
                for(INode n: rs.getRoute(start, end, RouteType.Walking).getPath()){
                    temp+=","+n.getId();
                }
                Log.d(getClass().getName(),"Path="+temp);
            }
        }).start();

        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
