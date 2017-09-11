/**
 * 
 */
package de.slux.mcoc.admin.ui.model;

import java.util.Objects;

/**
 * @author slux
 *
 */
public class MapLink
{
    public enum MapLinkType
    {
        UnknownType, PathLinkType, PortalLinkType, BoostLinkType;
    }

    private MapNode source;
    private MapNode destination;
    private MapLinkType linkType;

    /**
     * Ctor
     *
     * @param linkType
     * @param source
     * @param destination
     */
    public MapLink(MapLinkType linkType, MapNode source, MapNode destination) {
        this.linkType = Objects.requireNonNull(linkType);
        this.source = Objects.requireNonNull(source);
        this.destination = Objects.requireNonNull(destination);
    }

    /**
     * @return the source
     */
    public MapNode getSource()
    {
        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(MapNode source)
    {
        this.source = source;
    }

    /**
     * @return the destination
     */
    public MapNode getDestination()
    {
        return destination;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(MapNode destination)
    {
        this.destination = destination;
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
        result = prime * result + ((destination == null) ? 0 : destination.getNodeId().hashCode());
        result = prime * result + ((source == null) ? 0 : source.getNodeId().hashCode());
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
        MapLink other = (MapLink) obj;
        if (destination == null)
        {
            if (other.destination != null)
                return false;
        }
        else if (!destination.getNodeId().equals(other.destination.getNodeId()))
            return false;
        if (source == null)
        {
            if (other.source != null)
                return false;
        }
        else if (!source.getNodeId().equals(other.source.getNodeId()))
            return false;
        return true;
    }

    /**
     * @return the linkType
     */
    public MapLinkType getLinkType()
    {
        return linkType;
    }

    /**
     * @param linkType
     *            the linkType to set
     */
    public void setLinkType(MapLinkType linkType)
    {
        this.linkType = linkType;
    }

}
