package edu.utdallas.woosh.appservices;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

import edu.utdallas.whoosh.api.GroupType;
import edu.utdallas.whoosh.api.ILocationService;
import edu.utdallas.whoosh.api.IMapImage;
import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.INodeGroup;
import edu.utdallas.whoosh.api.NodeType;

/**
 * Created by Dustin on 10/7/2015.
 */
public class LocationService implements ILocationService
{
    private HashMap<String, IMapImage> mapImages = new HashMap<String, IMapImage>();
    private IMapImage campusMap;
    private Context context;
    private static LocationService instance = null;

    @Override
    public INode getClosestNode(LatLng coordinates) {
        return null;
    }

    @Override
    public List<INode> searchNodesByTypes(String query, List<NodeType> types) {
        return null;
    }

    @Override
    public List<INode> searchNodesByTypesAndGroup(String query, List<NodeType> types, INodeGroup group) {
        return null;
    }

    @Override
    public List<INode> getNodesByTypesAndGroupAndFloor(List<NodeType> types, INodeGroup group, Integer floor) {
        return null;
    }

    @Override
    public INodeGroup getClosestGroup(LatLng coordinates) {
        return null;
    }

    @Override
    public List<INodeGroup> getGroupsByType(GroupType type) {
        return null;
    }

    @Override
    public IMapImage getCampusMap() {
        return campusMap;
    }

    @Override
    public IMapImage getGroupMap(INodeGroup group, Integer floor) {
        return mapImages.get(getImageKey(group.getName(), floor));
    }

    public static LocationService getInstance(){
        if(instance == null){
            instance = new LocationService();
        }
        return instance;
    }

    public void init(Context c){
        this.context = c;
    }

    public void putMapImage(MapImage image){

        image.init(context);
        mapImages.put(getImageKey(image.getGroupName(), image.getFloor()), image);
    }

    private String getImageKey(String groupName, int floor){
        return groupName + floor;
    }
}
