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
    private HashMap<String, ArrayList<String>> nodesByGroup = new HashMap<String, ArrayList<String>>();
    private HashMap<String, ArrayList<String>> nodesBySubgroup = new HashMap<String, ArrayList<String>>();
    private HashMap<String, NodeGroup> nodeGroups = new HashMap<String, NodeGroup>();
    private HashMap<NodeType, ArrayList<String>> nodeTypes = new HashMap<NodeType, ArrayList<String>>();
    private static HashMap<String, Node> nodes = new HashMap<String, Node>();

    /**Puts a single node into the node manager.
     * @param n - Node*/
    public void putNode(Node n){
        nodes.put(n.getId(), n);

        if(!nodesByGroup.containsKey(n.getGroup().getName())){
            nodesByGroup.put(n.getGroup().getName(), new ArrayList<String>());
        }
        if(!nodesBySubgroup.containsKey(n.getSubgroup())){
            nodesBySubgroup.put(n.getSubgroup(), new ArrayList<String>());
        }
        if(!nodeTypes.containsKey(n.getType())){
            nodeTypes.put(n.getType(), new ArrayList<String>());
        }

        nodeTypes.get(n.getType()).add(n.getId());
        nodesByGroup.get(n.getGroup().getName()).add(n.getId());
        nodesBySubgroup.get(n.getSubgroup()).add(n.getId());
    }

    /**Puts several nodes into the node manager.
     * @param nodes - list of nodes*/
    public void putNodes(List<Node> nodes){
        for(Node n: nodes){
            putNode(n);
        }
    }

    /**Get a node by its id
     * @param id - id of node to retrieve
     * @return Node*/
    public Node getNode(String id) {
        return nodes.get(id);
    }

    /**
     * Get a list of nodes in a particular group
     * @param group - NodeGroup to retrieve nodes from
     * @return list of nodes
     */
    public List<Node> getNodesFromGroup(NodeGroup group){
        return getNodes(nodesByGroup.get(group.getName()));
    }

    /**Get a list of nodes in a particular subgroup
     * @param subgroup - Name of subgroup to retrieve nodes from
     * @return list of nodes*/
    public List<Node> getNodesFromSubgroup(String subgroup){
        return getNodes(nodesBySubgroup.get(subgroup));
    }

    /**Get all nodes of a particular type
     * @param type - value of type of node to retrieve
     * @return list of nodes*/
    public List<Node> getNodesFromType(NodeType type){
        return getNodes(nodeTypes.get(type));
    }

    public void addNodeGroup(NodeGroup n){
        nodeGroups.put(n.getId(), n);
    }

    public NodeGroup getNodeGroup(String id){
        return nodeGroups.get(id);
    }

    public List<NodeGroup> getNodeGroups(){
        return new ArrayList<NodeGroup>(nodeGroups.values());
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
