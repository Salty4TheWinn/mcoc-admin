package de.slux.mcoc.admin.ui.views.provider;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.viewers.IFigureProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.model.ChampionNode;
import de.slux.mcoc.admin.ui.model.EmptyNode;
import de.slux.mcoc.admin.ui.model.MapLink;
import de.slux.mcoc.admin.ui.model.MapLink.MapLinkType;
import de.slux.mcoc.admin.ui.model.MapNode;
import de.slux.mcoc.admin.ui.model.PortalNode;
import de.slux.mcoc.admin.ui.views.figure.ChampionNodeFigure;
import de.slux.mcoc.admin.ui.views.figure.EmptyNodeFigure;
import de.slux.mcoc.admin.ui.views.figure.PortalNodeFigure;

/**
 * @author slux
 *
 */
public class MapGraphLabelProvider extends LabelProvider
        implements IEntityStyleProvider, IEntityConnectionStyleProvider, IFigureProvider
{
    private static final Logger LOG = Logger.getLogger(MapGraphContentProvider.class.getName());

    private static final Image INFO_ICON = McocAdminUiPlugin.getImageDescriptor("icons/info.png").createImage();
    private static final Color PORTAL_LINK_COLOR = new Color(Display.getCurrent(), 255, 33, 33);
    private static final Color BOOST_LINK_COLOR = new Color(Display.getCurrent(), 252, 128, 240);
    private static final Color PATH_LINK_COLOR = new Color(Display.getCurrent(), 229, 255, 252);

    /**
     * Constructor
     */
    public MapGraphLabelProvider() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
     */
    @Override
    public void dispose()
    {
        INFO_ICON.dispose();

        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element)
    {
        return super.getImage(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element)
    {

        if (element instanceof EntityConnectionData)
            return ""; // No label on the connection

        if (element instanceof MapNode)
        {
            return ((MapNode) element).getNodeId().toString();
        }

        return "???"; // FIXME
    }

    @Override
    public Color getNodeHighlightColor(Object entity)
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderColor(java.
     * lang.Object)
     */
    @Override
    public Color getBorderColor(Object entity)
    {
        return null;
    }

    @Override
    public Color getBorderHighlightColor(Object entity)
    {
        return null;
    }

    @Override
    public int getBorderWidth(Object entity)
    {
        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.zest.core.viewers.IEntityStyleProvider#getBackgroundColour(
     * java.lang.Object)
     */
    @Override
    public Color getBackgroundColour(Object entity)
    {
        return null;
    }

    @Override
    public Color getForegroundColour(Object entity)
    {
        return null;
    }

    @Override
    public IFigure getTooltip(Object entity)
    {
        /*
         * if (!(entity instanceof AgentModelItem)) return null;
         * 
         * AgentModelItem item = (AgentModelItem) entity;
         * 
         * if (!item.getAlarms().isEmpty()) return new Label(String.format(
         * "%d alarm(s)", item.getAlarms().size()), INFO_ICON);
         */
        return null;
    }

    @Override
    public boolean fisheyeNode(Object entity)
    {
        return false;
    }

    @Override
    public IFigure getFigure(Object element)
    {
        IFigure figure = null;
        if (element instanceof EmptyNode)
        {
            figure = new EmptyNodeFigure((EmptyNode) element);
            figure.setSize(-1, -1);
        }

        if (element instanceof PortalNode)
        {
            figure = new PortalNodeFigure((PortalNode) element);
            figure.setSize(-1, -1);
        }

        if (element instanceof ChampionNode)
        {
            figure = new ChampionNodeFigure((ChampionNode) element);
            figure.setSize(-1, -1);
        }

        return figure;
    }

    @Override
    public int getConnectionStyle(Object src, Object dest)
    {
        if (src instanceof MapNode && dest instanceof MapNode)
        {
            MapLink link = getLink((MapNode) src, (MapNode) dest);

            if (link == null)
            {
                LOG.warning("Cannot find the link for src=" + src + " dest=" + dest);
            }
            else
            {
                if (link.getLinkType() == MapLinkType.PathLinkType)
                {
                    return ZestStyles.CONNECTIONS_SOLID;
                }
                if (link.getLinkType() == MapLinkType.PortalLinkType)
                {
                    return ZestStyles.CONNECTIONS_DIRECTED | ZestStyles.CONNECTIONS_DOT;
                }

                if (link.getLinkType() == MapLinkType.BoostLinkType)
                {
                    return ZestStyles.CONNECTIONS_DIRECTED | ZestStyles.CONNECTIONS_DOT;
                }
            }
        }

        return ZestStyles.CONNECTIONS_SOLID;
    }

    /**
     * Get the link which matches src and dest
     * 
     * @param src
     * @param dest
     * @return the link or null if none
     */
    private MapLink getLink(MapNode src, MapNode dest)
    {
        List<MapLink> links = AWDDataUIModelManager.getInstance().getAwMapLinks();
        for (MapLink link : links)
        {
            if (link.equals(new MapLink(MapLinkType.UnknownType, src, dest)))
            {
                // Found the link
                return link;
            }
        }

        return null;
    }

    @Override
    public Color getColor(Object src, Object dest)
    {
        if (src instanceof MapNode && dest instanceof MapNode)
        {
            MapLink link = getLink((MapNode) src, (MapNode) dest);

            if (link == null)
            {
                LOG.warning("Cannot find the link for src=" + src + " dest=" + dest);
            }
            else
            {
                if (link.getLinkType() == MapLinkType.PathLinkType)
                {
                    return PATH_LINK_COLOR;
                }
                if (link.getLinkType() == MapLinkType.PortalLinkType)
                {
                    return PORTAL_LINK_COLOR;
                }

                if (link.getLinkType() == MapLinkType.BoostLinkType)
                {
                    return BOOST_LINK_COLOR;
                }
            }
        }

        return null;
    }

    @Override
    public Color getHighlightColor(Object src, Object dest)
    {
        return null;
    }

    @Override
    public int getLineWidth(Object src, Object dest)
    {
        if (src instanceof MapNode && dest instanceof MapNode)
        {
            MapLink link = getLink((MapNode) src, (MapNode) dest);

            if (link == null)
            {
                LOG.warning("Cannot find the link for src=" + src + " dest=" + dest);
            }
            else
            {
                if (link.getLinkType() == MapLinkType.PathLinkType)
                {
                    return 2;
                }
                if (link.getLinkType() == MapLinkType.PortalLinkType)
                {
                    return 2;
                }

                if (link.getLinkType() == MapLinkType.BoostLinkType)
                {
                    return 3;
                }
            }
        }

        return 0;
    }

}
