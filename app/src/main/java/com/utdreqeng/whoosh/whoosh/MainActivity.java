package com.utdreqeng.whoosh.whoosh;

import android.app.ActionBar;
import android.content.Context;
import android.opengl.Visibility;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.Parse;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.NodeType;
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

    private View lastTopBar = null;
    private int lastTopId = 0;
    private RouteMap map;
    //private Route currentRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Enable Local Datastore
        /*Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        //Initialize the backend services
        InitService.init(this.getApplicationContext());*/

        // Register your parse models
        //ParseObject.registerSubclass(Node.class);

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
                List<NodeType> types = new ArrayList<NodeType>();

                for(NodeType t: NodeType.values()){
                    if(t != NodeType.Stair){
                        types.add(t);
                    }
                }

                //for testing RoutingService & LocationService
                Node start = LocationService.getInstance().getClosestNode(new LatLng(33.071865f, -96.750301f));
                Node end = null;
                List<INode> results = LocationService.getInstance().searchNodesByTypes("ATC 1.91", types);

                if(results.size() != 0){
                    end = (Node)results.get(0);
                }
                RoutingService rs = new RoutingService();

                Log.d(getClass().getName(), "trying to build path");

                String temp = "";
                for(INode n: rs.getRoute(start, end, RouteType.Walking).getPath()){
                    temp+=n.getId()+",";
                }
                Log.d(getClass().getName(),"Path="+temp);
            }
        }).start();

        setContentView(R.layout.activity_main);
        map = new RouteMap(this);

        ((FloatingActionButton)findViewById(R.id.navButtonTop)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    map.locateUser();
                }
                return false;
            }
        });

        ((FloatingActionButton)findViewById(R.id.navButtonBottom)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setTopBar(2);
                }
                return false;
            }
        });

        setTopBar(0);
        map.locateUser();
    }

    private void setTopBar(int id){

        View temp = null;

        if(lastTopBar != null){
            lastTopBar.clearFocus();
            ((RelativeLayout)findViewById(R.id.mainLayout)).removeView(lastTopBar);
        }

        switch(id){
            case 0:
                    temp = getLayoutInflater().inflate(R.layout.searchbar_layout, null);

                    ((Button)temp.findViewById(R.id.searchButton)).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                setTopBar(1);
                            }
                            return false;
                        }
                    }); break;
            case 1:
                    temp = getLayoutInflater().inflate(R.layout.searchbar_layout2, null);
                    temp.findViewById(R.id.searchBox).requestFocus();

                    ((EditText)temp.findViewById(R.id.searchBox)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                setTopBar(2);
                            }
                            return false;
                        }
                    });
                    openKeyboard();

                    ((Button)temp.findViewById(R.id.searchExitButton)).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                setTopBar(0);
                            }
                            return false;
                        }
                    }); break;

            case 2:
                    String startText = "My Location", endText = "";

                    if(lastTopId == 1){
                        endText = ((EditText)lastTopBar.findViewById(R.id.searchBox)).getText().toString();
                    }

                    temp = getLayoutInflater().inflate(R.layout.navigation_layout, null);
                    ((EditText)temp.findViewById(R.id.nav_fieldStart)).setText(startText);
                    ((EditText)temp.findViewById(R.id.nav_fieldEnd)).setText(endText);

                    ((Button)temp.findViewById(R.id.nav_screen_backButton)).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                setTopBar(0);
                            }
                            return false;
                        }
                    }); break;

            default: break;
        }


        if(id == 0 || id == 1){
            ((FloatingActionButton)findViewById(R.id.navButtonBottom)).setVisibility(View.VISIBLE);
            ((FloatingActionButton)findViewById(R.id.navButtonTop)).setVisibility(View.VISIBLE);
        }
        else{
            ((FloatingActionButton)findViewById(R.id.navButtonBottom)).setVisibility(View.INVISIBLE);
            ((FloatingActionButton)findViewById(R.id.navButtonTop)).setVisibility(View.INVISIBLE);
        }

        if(id == 2){
            ((FloatingActionButton)findViewById(R.id.navButtonBottom2)).setVisibility(View.VISIBLE);
        }
        else{
            ((FloatingActionButton)findViewById(R.id.navButtonBottom2)).setVisibility(View.INVISIBLE);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        ((RelativeLayout)findViewById(R.id.mainLayout)).addView(temp, params);
        lastTopBar = temp;
        lastTopId = id;
    }

    private void openKeyboard(){
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        findViewById(R.id.action_directory);
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
