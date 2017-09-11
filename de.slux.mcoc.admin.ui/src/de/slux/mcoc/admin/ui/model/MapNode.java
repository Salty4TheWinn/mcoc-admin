/**
 * 
 */
package de.slux.mcoc.admin.ui.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Slux
 */
public abstract class MapNode
{
    protected Collection<MapNode> connections;
    protected NodeId nodeId;

    /**
     * Constructor
     * 
     * @param nodeId
     */
    public MapNode(NodeId nodeId) {
        this.nodeId = nodeId;
        this.connections = new ArrayList<>();
    }

    /**
     * @return the nodeId
     */
    public NodeId getNodeId()
    {
        return nodeId;
    }

    /**
     * @param nodeId
     *            the nodeId to set
     */
    public void setNodeId(NodeId nodeId)
    {
        this.nodeId = nodeId;
    }

    /**
     * @return the connections
     */
    public Collection<MapNode> getConnections()
    {
        return connections;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "MapNode [nodeId=" + nodeId + "]";
    }

}
