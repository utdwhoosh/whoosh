package edu.utdallas.woosh.appservices;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.NodeGroup;
import edu.utdallas.whoosh.api.NodeType;

//So we can use parse to init objects of this class
import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("Node")

/**
 * Created by Marie on 9/30/2015.
 * Note 10/3/2015:
 * Tutorials recommend that Parse data models not use member instance variables, and instead have getters and setters rely
 * directly on getX(key) Parse methods to retrieve the values of db properties - this helps keep the variable values accurate to what i
 * is stored in the database. However, this requires some extra work when it comes to retrieving certain variables such as LatLng,
 * NodeGroup, and NodeType, which are custom/enum variables that Parse has no built in getter for -they must be retrieved from Parse
 * as a generic object and manually casted or converted into an object type that can be returned by the getter function.
 * This takes some extra parsing time that may not be exactly practical for the app's needs since all of the application logic depends
 * on getters (and performance issues are why we are caching the whole database in the first place) so for now I've settled on a compromise.
 */
public class Node  extends ParseObject implements edu.utdallas.whoosh.api.Node {
    private String id;
    private LatLng coordinates;
    private String subgroup;
    private String name;
    private NodeType type;
    private List<Node> adjacentNodes;
    private Integer floor;
    NodeGroup group;

    Node( String id, LatLng coordinates, NodeGroup group, String subgroup,
          String name, NodeType type, List<Node> adjacentNodes, Integer floor){
        this.id = id;
        this.coordinates = coordinates;
        this.group = group;
        this.subgroup = subgroup;
        this.name = name;
        this.type = type;
        this.adjacentNodes = adjacentNodes;
        this.floor = floor;
    }

    Node(){
        super();
    }

    /** geographic coordinates represented by this node*/
    /*Had to make this bc Parse doesn't have a build in getter for LatLng types*/
    public LatLng getCoordinates(){
        String latlong = getString("coordinates");
        String [] splitlatlong = latlong.split(",");
        double lat = Double.parseDouble(splitlatlong[0]);
        double lon = Double.parseDouble(splitlatlong[1]);

        return new LatLng(lat, lon); //will this create memory issues?
    }

    /** a natural key*/
    public String getId(){
        //return id;
        return getString("id");
    }

    /**TODO: Write Parse getter*/
    /**the {@link edu.utdallas.whoosh.api.NodeGroup} this node belongs to, such as the {@link GroupType#Building} "ECSS */
    public NodeGroup getGroup(){
        return group;
    }

    /**TODO: Write Parse getter*/
    /**the type of geographic point this node represents, such as {@link NodeType#Room}*/
    public NodeType getType(){
        return type;
    }

    /**TODO: Write Parse getter*/
    /**a {@link List} of nodes directly routable from this one*/
    public List<Node> getAdjacentNodes(){
        return adjacentNodes;
    }

    /**a second-level grouping this node belongs to, such as {@link NodeType#Room} "2.123"*/
    public String getSubgroup(){
        //return subgroup;
        return getString("NodeGroup");
    };

    /**a colloquial name given this geographic point, or {@link #getSubgroup()} if no such name exists*/
    public String getName(){
        return getString("name");
    }

    /**a vertical qualifier for this geographic point, expressed in terms of its floor id within that building;*/
    public Integer getFloor(){
        return getInt("floor");
    };

}
