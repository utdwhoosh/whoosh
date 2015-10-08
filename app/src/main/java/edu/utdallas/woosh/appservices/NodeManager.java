package edu.utdallas.woosh.appservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utdallas.whoosh.api.Node;
import edu.utdallas.whoosh.api.NodeGroup;
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
    private static HashMap<String, Node> nodes = new HashMap<String, Node>();

    /**
     * Puts a single node into the node manager.
     * @param n - Node
     */
    public void putNode(Node n){
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
    public void putNodes(List<Node> nodes){
        for(Node n: nodes){
            putNode(n);
        }
    }

    /**
     * Get a node by its id
     * @param id - id of node to retrieve
     * @return Node
     */
    public Node getNode(String id) {
        return nodes.get(id);
    }

    /**
     * Get a list of nodes in a particular group
     * @param group - NodeGroup to retrieve nodes from
     * @return list of nodes
     */
    public List<Node> getNodesFromGroup(NodeGroup group){
        return getNodes(nodeGroups.get(group.getName()));
    }

    /**
     * Get a list of nodes in a particular subgroup
     * @param subgroup - Name of subgroup to retrieve nodes from
     * @return list of nodes
     */
    public List<Node> getNodesFromSubgroup(String subgroup){
        return getNodes(nodeSubgroups.get(subgroup));
    }

    /**
     * Get all nodes of a particular type
     * @param type - value of type of node to retrieve
     * @return list of nodes
     */
    public List<Node> getNodesFromType(NodeType type){
        return getNodes(nodeTypes.get(type));
    }
    
    private List<Node> getNodes(ArrayList<String> nodeList){
        ArrayList<Node> temp = new ArrayList<Node>();

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
