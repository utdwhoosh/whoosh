package edu.utdallas.woosh.appservices;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.NodeType;

//So we can use parse to init objects of this class
import com.parse.ParseObject;

/**
 * Created by Marie on 9/30/2015.
 */
public class Node implements INode {
    private String id;
    private LatLng coordinates;
    private String subgroup;
    private String name;
    private NodeType type;
    private List<Node> adjacentNodes;
    private Integer floor;
    private NodeGroup group;


    /**A Constructor that takes all Node attributes as an argument
     * @param id
     * @param coordinates
     * @param group
     * @param subgroup
     * @param name
     * @param type
     * @param adjacentNodes
     * @param floor
     */
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

    /**A constructor that takes a ParseObject object as an argument
     * converts the object attributes to Node attributes,
     * then calls the constructor that takes all Node attributes.
     * @param object
     */
    Node(ParseObject object){

        String id;
        LatLng coordinates;
        NodeGroup group;
        String subgroup;
        String name;
        NodeType type;
        List<Node> adjacentNodes;
        Integer floor;

        id = object.getString("id");
        coordinates = new LatLng(object.getParseGeoPoint("coordinates").getLatitude(), object.getParseGeoPoint("coordinates").getLongitude());
        group = resolveNodeGroup(object.getString("nodegroup"));
        subgroup = object.getString("subgroup");
        name = object.getString("name");
        type = NodeType.valueOf(object.getString("type"));
        //this.adjacentNodes = ;
        floor = object.getInt("floor");

        this(id, coordinates, group, subgroup,name, type, adjacentNodes, floor);
    }


    ////TODO: write resolveNodeGroup() to return a list of Nodes corresponding to the ids in object.group
    /**Converter helpers for json shit*/
    private NodeGroup resolveNodeGroup(String group){

    }

    /**Getters*/
    public LatLng getCoordinates(){
        return coordinates;
    }

    public String getId(){
        return id;
    }

    public NodeGroup getGroup(){
        return group;
    }

    public NodeType getType(){
        return type;
    }

    public List<Node> getAdjacentNodes(){
        return adjacentNodes;
    }

    public String getSubgroup(){
        return subgroup;
    }

    public String getName(){
        return name;
    }

    public Integer getFloor(){
        return floor;
    }

}
