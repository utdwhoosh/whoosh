package com.utdreqeng.whoosh.whoosh;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.parse.Parse;

import edu.utdallas.whoosh.appservices.InitService;

public class MainActivity extends AppCompatActivity  {

    //Parse App ID and client key
    public static final String PARSE_APPLICATION_ID = "YB7DqZB3O0LfmIKzd9NzMQ6uhIXRhoawgK7p5B1M";
    public static final String PARSE_CLIENT_KEY = "kA1LGDmyML7rhXEDkQjgNIKw8cNM5VoDpsO7Drxl";

    private View lastTopBar = null;

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

        setContentView(R.layout.activity_main);
        setTopBar(0);
    }

    private void setTopBar(int id){

        View temp;

        if(lastTopBar != null){
            ((RelativeLayout)findViewById(R.id.mainLayout)).removeView(lastTopBar);
        }

        switch(id){
            default: temp = getLayoutInflater().inflate(R.layout.searchbar_layout, null); break;
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
