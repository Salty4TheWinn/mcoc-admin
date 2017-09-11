/**
 * 
 */
package de.slux.mcoc.admin.ui.model;

import java.util.Objects;

/**
 * @author Slux
 *
 */
public class NodeId
{
    public enum NodeType
    {
        ChampionNode, EmptyNode, PortalNode
    }

    private NodeType type;
    private Integer nodeNumber;

    /**
     * Ctor
     * 
     * @param type
     * @param nodeNumber
     */
    public NodeId(NodeType type, Integer nodeNumber) {
        this.type = Objects.requireNonNull(type);
        this.nodeNumber = Objects.requireNonNull(nodeNumber);
    }

    /**
     * @return the type
     */
    public NodeType getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(NodeType type)
    {
        this.type = type;
    }

    /**
     * @return the nodeNumber
     */
    public Integer getNodeNumber()
    {
        return nodeNumber;
    }

    /**
     * @param nodeNumber
     *            the nodeNumber to set
     */
    public void setNodeNumber(Integer nodeNumber)
    {
        this.nodeNumber = nodeNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nodeNumber == null) ? 0 : nodeNumber.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NodeId other = (NodeId) obj;
        if (nodeNumber == null)
        {
            if (other.nodeNumber != null)
                return false;
        }
        else if (!nodeNumber.equals(other.nodeNumber))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String typeValue = null;
        switch (this.type)
        {
        case ChampionNode:
            typeValue = "CN";
            break;
        case EmptyNode:
            typeValue = "EN";
            break;
        case PortalNode:
            typeValue = "PN";
            break;
        }
        return typeValue + "-" + nodeNumber;
    }

}
