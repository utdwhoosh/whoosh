package edu.utdallas.woosh.appservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utdallas.whoosh.api.INode;
import edu.utdallas.whoosh.api.INodeGroup;
import edu.utdallas.whoosh.api.NodeType;

/**
 * Singleton used to store and retrieve nodes in the application. Intended to be used by
 * all service classes for simple node management.
 *
 * @author Dustin Grannemann
 */
public class NodeManager {

    private static NodeManager instance = null;
    private static HashMap<String, ArrayList<String>> nodeGroups = new HashMap<String, ArrayList<String>>();
    private static HashMap<String, ArrayList<String>> nodeSubgroups = new HashMap<String, ArrayList<String>>();
    private static HashMap<NodeType, ArrayList<String>> nodeTypes = new HashMap<NodeType, ArrayList<String>>();
    private static HashMap<String, INode> nodes = new HashMap<String, INode>();

    /**
     * Puts a single node into the node manager.
     * @param n - Node
     */
    public void putNode(INode n){
        nodes.put(n.getId(), n);

        if(!nodeGroups.containsKey(n.getGroup().getName())){
            nodeGroups.put(n.getGroup().getName(), new ArrayList<String>());
        }
        if(!nodeSubgroups.containsKey(n.getSubgroup())){
            nodeSubgroups.put(n.getSubgroup(), new ArrayList<String>());
        }
        if(!nodeTypes.containsKey(n.getType())){
            nodeTypes.put(n.getType(), new ArrayList<String>());
        }

        nodeTypes.get(n.getType()).add(n.getId());
        nodeGroups.get(n.getGroup().getName()).add(n.getId());
        nodeSubgroups.get(n.getSubgroup()).add(n.getId());
    }

    /**
     * Puts several nodes into the node manager.
     * @param nodes - list of nodes
     */
    public void putNodes(List<INode> nodes){
        for(INode n: nodes){
            putNode(n);
        }
    }

    /**
     * Get a node by its id
     * @param id - id of node to retrieve
     * @return Node
     */
    public INode getNode(String id) {
        return nodes.get(id);
    }

    /**
     * Get a list of nodes in a particular group
     * @param group - NodeGroup to retrieve nodes from
     * @return list of nodes
     */
    public List<INode> getNodesFromGroup(INodeGroup group){
        return getNodes(nodeGroups.get(group.getName()));
    }

    /**
     * Get a list of nodes in a particular subgroup
     * @param subgroup - Name of subgroup to retrieve nodes from
     * @return list of nodes
     */
    public List<INode> getNodesFromSubgroup(String subgroup){
        return getNodes(nodeSubgroups.get(subgroup));
    }

    /**
     * Get all nodes of a particular type
     * @param type - value of type of node to retrieve
     * @return list of nodes
     */
    public List<INode> getNodesFromType(NodeType type){
        return getNodes(nodeTypes.get(type));
    }
    
    private List<INode> getNodes(ArrayList<String> nodeList){
        ArrayList<INode> temp = new ArrayList<INode>();

        for(String s: nodeList){
            temp.add(nodes.get(s));
        }
        return temp;
    }

    public static NodeManager getInstance(){

        if(instance==null){
            instance = new NodeManager();
        }

        return instance;
    }
}
