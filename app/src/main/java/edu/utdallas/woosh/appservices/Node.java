package edu.utdallas.woosh.appservices;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
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
     * @param adjacentNodesSize
     * @param floor
     */
    Node( String id, LatLng coordinates, NodeGroup group, String subgroup,
          String name, NodeType type, Integer floor, int adjacentNodesSize){
        this.id = id;
        this.coordinates = coordinates;
        this.group = group;
        this.subgroup = subgroup;
        this.name = name;
        this.type = type;
        this.adjacentNodes = new ArrayList<Node>(adjacentNodesSize); //initalized w/ setAdjacent Nodes after initial instantiation
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
//^NodeGroups HAS to be initialized first by DBManager
        this(   object.getString("id"),
                new LatLng(object.getParseGeoPoint("coordinates").getLatitude(), object.getParseGeoPoint("coordinates").getLongitude()),
                NodeManager.getInstance().getNodeGroup(object.getString("nodegroup")),
                object.getString("subgroup"),
                object.getString("name"),
                NodeType.valueOf(object.getString("type")),
                object.getInt("floor"),
                object.getList("adjacentNodes").size()
        );
    }

    /**Setter(s?)*/
    public void setAdjacentNodes(ParseObject object){
        //list of adjacent node IDS
        List <String> neighbors = object.getList("adjacentNodes");

        for(int i = 0; i < neighbors.size(); i++ ){
            //
            adjacentNodes.add(NodeManager.getInstance().getNode(neighbors.get(i).toString()));
        }
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
