package edu.utdallas.woosh.appservices;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import edu.utdallas.whoosh.api.NodeType;

/**
 * Created by Marie on 10/3/2015.
 * This class is intended to be a entry point for ParseDB and the application
 */
public class DBManager {


    private static DBManager instance = null;

    /**
     * Saves a node into Parse
     * @param node - Node

    public void saveNode(Node node){
        node.saveInBackground();
    }*/


    /**
     * Save a node into Parse using all the params

    public void saveNode(String id, LatLng coordinates, NodeGroup group, String subgroup,
                         String name, NodeType type, List<Node> adjacentNodes, Integer floor) {
        Node node = new Node();
        node.put("id", id);
        node.put("coordinates", coordinates);
        node.put("group", group);
        node.put("subgroup", subgroup);
        node.put("name", name);
        node.put("type", type);
        node.put("adjacentNodes", adjacentNodes);
        node.put("floor", floor);
        node.saveInBackground();
    }*/

    /**query for single node by Parse Object ID
    public void getNode(String objID){
        ParseQuery<Node> query = ParseQuery.getQuery("Node");
        query.getInBackground(objID, new GetCallback<Node>() {
            public void done(Node node, ParseException e) {
                if (e == null) {
                    //node contains node data
                    NodeManager.getInstance().putNode(node);
                } else {
                    // something went wrong
                }
            }
        });
    }*/

    /**Queries for all objects of type Node
     * creates a Node node object for each ParseObject object and adds it to a list of nodes
     * then uses NodeManager to store the list.
     */
    public void init(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Node");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseNodes, ParseException e) {
                if (e == null) {
                    List<Node> nodes = new ArrayList<Node>();
                    for(ParseObject object: parseNodes){
                        Node node = new Node(object);
                        nodes.add(node);
                    }
                    NodeManager.getInstance().putNodes(nodes);
                } else {
                    //something went wrong
                }
            }
        });
    }


    public static DBManager getInstance(){

        if(instance==null){
            instance = new DBManager();
        }

        return instance;
    }
}

