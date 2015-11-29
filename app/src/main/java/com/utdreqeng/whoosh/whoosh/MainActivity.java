package com.utdreqeng.whoosh.whoosh;

import android.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.AdapterView; //x
import android.widget.ArrayAdapter; //x
import android.widget.ListView; //x
import android.widget.Toast; //x

import com.parse.Parse;

import edu.utdallas.whoosh.appservices.InitService;

public class MainActivity extends AppCompatActivity  {

    //Parse App ID and client key
    public static final String PARSE_APPLICATION_ID = "YB7DqZB3O0LfmIKzd9NzMQ6uhIXRhoawgK7p5B1M";
    public static final String PARSE_CLIENT_KEY = "kA1LGDmyML7rhXEDkQjgNIKw8cNM5VoDpsO7Drxl";

    private View lastTopBar = null;
    private RouteMap map;

    private ListView mDrawerList; //x
    private ArrayAdapter<String> mAdapter; //x

    private void addDrawerItems() { //x
        String[] osArray = { "Directory", "Maps", "Help" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //x
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView)findViewById(R.id.navList); //x
        addDrawerItems(); //x
        // Enable Local Datastore
        /*Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        //Initialize the backend services
        InitService.init(this.getApplicationContext());*/

        // Register your parse models
        //ParseObject.registerSubclass(Node.class);

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

                    ((Button)temp.findViewById(R.id.searchExitButton)).setOnTouchListener(new View.OnTouchListener() {
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

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        ((RelativeLayout)findViewById(R.id.mainLayout)).addView(temp, params);
        lastTopBar = temp;
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
