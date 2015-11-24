package edu.utdallas.whoosh.appservices;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.IRoute;
import edu.utdallas.whoosh.api.RouteType;

/**
 * Created by Dustin on 11/1/2015.
 */
public class Route implements IRoute {

    private List<Node> nodes;
    private RouteType type;

    public Route(List<Node> nodes, RouteType type)
    {
        this.nodes = new ArrayList<Node>(nodes);
        this.type = type;
    }

    @Override
    public INode getOrigin() {
        return nodes.get(0);
    }

    @Override
    public INode getDestination() {
        return nodes.get(nodes.size()-1);
    }

    @Override
    public RouteType getType() {
        return type;
    }

    @Override
    public List<INode> getPath() {
        return new ArrayList<INode>(nodes);
    }

    @Override
    public int getTimeInMinutes() {

        //TODO: implement
        return 0;
    }

    @Override
    public int getDistanceInFeet() {

        //TODO: implement
        return 0;
    }
}
