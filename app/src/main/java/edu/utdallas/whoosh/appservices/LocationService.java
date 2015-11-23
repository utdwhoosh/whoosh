package edu.utdallas.whoosh.appservices;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.ILocationService;
import edu.utdallas.whoosh.api.IMapImage;
import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.INodeGroup;
import edu.utdallas.whoosh.api.NodeType;

/**
 * Created by Dustin on 10/7/2015.
 * Updated by Taber Hurst.
 */
public class LocationService implements ILocationService {
    private HashMap<String, IMapImage> mapImages = new HashMap<String, IMapImage>();
    private IMapImage campusMap;
    private Context context;
    private static LocationService instance = null;

    @Override
    public Node getClosestNode(LatLng coordinates) {
        NodeManager manager = NodeManager.getInstance();

        List<Node> possibleNodes = manager.getNodesFromGroup(getClosestGroup(coordinates));
        Node currentNode,shortestNode;
        shortestNode = null;
        double currDistance,shortestDistance,latDif,lngDif;

        shortestDistance = -1.0;

        Iterator<Node> iterator = possibleNodes.listIterator();
        while(iterator.hasNext())
        {
            currentNode = iterator.next();
            latDif = coordinates.latitude - currentNode.getCoordinates().latitude;
            lngDif = coordinates.longitude - currentNode.getCoordinates().longitude;

            currDistance = Math.sqrt((latDif*latDif) + (lngDif*lngDif));
            if(shortestDistance == -1.0)
            {
                shortestDistance = currDistance;
                shortestNode = currentNode;
            }
            else if(shortestDistance > currDistance)
            {
                shortestDistance = currDistance;
                shortestNode = currentNode;
            }
        }

        return shortestNode;
    }

    @Override
    public List<INode> searchNodesByTypes(String query, List<NodeType> types) {
        NodeManager manager = NodeManager.getInstance();
        HashSet<Node> tempSet = new HashSet<Node>();

        Iterator<NodeType> typeIterator = types.listIterator();
        while(typeIterator.hasNext())
        {
            //tempSet.addAll(manager.getNodesFromType(typeIterator.next()));
        }
        tempSet.addAll(manager.doNodeQuery(query));

        return new ArrayList<INode>(tempSet);
    }

    @Override
    public List<INode> searchNodesByTypesAndGroup(String query, List<NodeType> types, INodeGroup group) {
        NodeManager manager = NodeManager.getInstance();
        Node currNode;

        List<Node> nodesByGroup = manager.getNodesFromGroup((NodeGroup)group);
        Iterator<Node> groupIterator = nodesByGroup.listIterator();
        Iterator<NodeType> typeIterator;

        HashSet<Node> tempSet = new HashSet<Node>();

        while(groupIterator.hasNext())
        {
            currNode = groupIterator.next();
            typeIterator = types.listIterator();
            while(typeIterator.hasNext())
            {
                if(currNode.getType() == typeIterator.next())
                {
                    tempSet.add(currNode);
                }
            }
        }

        //TODO: possibly add from Parse query rather than by subgroup
        tempSet.addAll(manager.getNodesFromSubgroup(query));

        return new ArrayList<INode>(tempSet);
    }

    @Override
    public List<Node> getNodesByTypesAndGroupAndFloor(List<NodeType> types, NodeGroup group, Integer floor) {
        Node currNode;
        List<Node> result = new ArrayList<>();
        Iterator<NodeType> typeIterator;
        NodeManager manager = NodeManager.getInstance();

        List<Node> nodesByGroup = manager.getNodesFromGroup(group);
        Iterator<Node> groupIterator = nodesByGroup.listIterator();

        while(groupIterator.hasNext())
        {
            currNode = groupIterator.next();
            typeIterator = types.listIterator();
            while(typeIterator.hasNext())
            {
                if(currNode.getType() == typeIterator.next())
                {
                    if(currNode.getFloor().equals(floor))
                    {
                        result.add(currNode);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public NodeGroup getClosestGroup(LatLng coordinates) {
        NodeGroup currGroup,closestGroup;
        closestGroup = null;

        double currDistance,shortestDistance,latDif,lngDif;
        shortestDistance = -1.0;

        NodeManager manager = NodeManager.getInstance();

        List<NodeGroup> groups = manager.getNodeGroups();
        Iterator<NodeGroup> iterator = groups.listIterator();

        while(iterator.hasNext())
        {
            currGroup = iterator.next();
            latDif = coordinates.latitude - currGroup.getCenterCoordinates().latitude;
            lngDif = coordinates.longitude - currGroup.getCenterCoordinates().longitude;

            currDistance = Math.sqrt((latDif*latDif) + (lngDif*lngDif));
            if(shortestDistance == -1.0
                    || shortestDistance > currDistance)
            {
                shortestDistance = currDistance;
                closestGroup = currGroup;
            }
        }

        return closestGroup;
    }

    @Override
    public List<INodeGroup> getGroupsByType(GroupType type) {
        NodeManager manager = NodeManager.getInstance();
        List<NodeGroup> groups = manager.getNodeGroups();
        List<INodeGroup> groupsByType = new ArrayList<>();
        NodeGroup currGroup;
        Iterator<NodeGroup> iterator = groups.listIterator();
        while(iterator.hasNext())
        {
            currGroup = iterator.next();
            if(currGroup.getType() == type)
            {
                groupsByType.add(currGroup);
            }
        }

        return groupsByType;
    }

    @Override
    public IMapImage getCampusMap() {
        return campusMap;
    }

    @Override
    public IMapImage getGroupMap(INodeGroup group, Integer floor) {
        return mapImages.get(getImageKey(group.getName(), floor));
    }

    public static LocationService getInstance() {
        if (instance == null) {
            instance = new LocationService();
        }
        return instance;
    }

    public void init(Context c) {
        this.context = c;
    }

    public void putMapImage(MapImage image) {

        image.init(context);
        mapImages.put(getImageKey(image.getGroupName(), image.getFloor()), image);
    }

    private String getImageKey(String groupName, int floor) {
        return groupName + floor;
    }
}