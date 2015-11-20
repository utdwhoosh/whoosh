package edu.utdallas.whoosh.appservices;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marie on 10/3/2015.
 * This class is intended to be a entry point for ParseDB and the application
 */
public class DBManager {


    private static DBManager instance = null;

    /**init(): initializes the data variables for the application*/
    public void init(){
        Log.d(getClass().getName(), "DBManager.init() : Initializing Nodes and NodeGroups..");
        initNodeGroups();
        //initNodes();

        Log.d(getClass().getName(), "DBManager.init() : Finished");
    }

    /**initNodeGroups(): retrieves all NodeGroup ParseObjects from Parse
     *  & adds them to NodeManager's list of NodeGroups
     */
    private void initNodeGroups(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("NodeGroup");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseNodeGroups, ParseException e) {
                if (e == null) {
                    for (ParseObject object : parseNodeGroups) {
                        NodeGroup nodegroup = new NodeGroup(object);
                        NodeManager.getInstance().addNodeGroup(nodegroup);
                    }
                    initNodes();
                } else {
                    //something went wrong
                }
            }
        });
    }

    /**initNodes(): retrieves all Node ParseObjects from Parse
     * creates a list of nodes with them, and adds the list to ModeManager
     */
    private void initNodes(){
        Log.d("DBManager", "Trying to init nodes...");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Node");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseNodes, ParseException e) {

                if (e == null) {
                    List<Node> nodes = new ArrayList<Node>();
                    //instantiate each node
                    for(ParseObject object: parseNodes){
                        Node node = new Node(object);
                        nodes.add(node);

                        Log.d("DBManager", "added " + node.getName() + " to node list.");
                    }
                    //set each node's adjacent nodes
                    int i = 0;
                    for(ParseObject object: parseNodes){
                        nodes.get(i).setAdjacentNodes(object);
                        i++;
                    }

                    NodeManager.getInstance().putNodes(nodes);
                } else {
                    //something went wrong
                    System.out.println("DBManager.initNodes() : Parsenodes could not be found()");
                }
            }
        });
    }

    /**
     * getInstance() : returns an instance of DBManger*/
    public static DBManager getInstance(){

        if(instance==null){
            instance = new DBManager();
        }

        return instance;
    }
}

