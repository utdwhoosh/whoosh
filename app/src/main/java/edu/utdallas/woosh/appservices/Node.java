package edu.utdallas.woosh.appservices;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.INodeGroup;
import edu.utdallas.whoosh.api.NodeType;

//So we can use parse to init objects of this class
import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("Node")

/**
 * Created by Marie on 9/30/2015.
 */
public class Node  extends ParseObject implements INode {
    private String id;
    private LatLng coordinates;
    private INodeGroup group;
    private String subgroup;
    private String name;
    private NodeType type;
    private List<Node> adjacentNodes;
    private Integer floor;

    Node(){}

    Node( String id, LatLng coordinates, INodeGroup group, String subgroup,
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

    /** a natural key*/
    public String getId(){
        return id;
    }

    /** geographic coordinates represented by this node*/
    public LatLng getCoordinates(){
        return coordinates;
    }

    /**the {@link INodeGroup} this node belongs to, such as the {@link GroupType#Building} "ECSS */
    public INodeGroup getGroup(){
        return group;
    }

    /**a second-level grouping this node belongs to, such as {@link NodeType#Room} "2.123"*/
    public String getSubgroup(){
        return subgroup;
    };

    /**a colloquial name given this geographic point, or {@link #getSubgroup()} if no such name exists*/
    public String getName(){
        return name;
    }

    /**the type of geographic point this node represents, such as {@link NodeType#Room}*/
    public NodeType getType(){
        return type;
    }

    /**a {@link List} of nodes directly routable from this one*/
    public List<Node> getAdjacentNodes(){
        return adjacentNodes;
    }

    /**a vertical qualifier for this geographic point, expressed in terms of its floor id within that building;
     * {@link GroupType#OutdoorArea} nodes should indicate a value of {@code 1}*/
    public Integer getFloor(){
        return floor;
    }
}
