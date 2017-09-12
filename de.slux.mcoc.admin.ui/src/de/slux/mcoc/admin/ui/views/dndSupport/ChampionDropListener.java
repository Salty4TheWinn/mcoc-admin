/**
 * 
 */
package de.slux.mcoc.admin.ui.views.dndSupport;

import java.util.Objects;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;

import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.model.ChampionNode;
import de.slux.mcoc.admin.ui.model.MapNode;
import de.slux.mcoc.admin.ui.model.NodeId.NodeType;
import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.model.PlayerChampion;
import de.slux.mcoc.admin.ui.views.figure.ChampionNodeFigure;

/**
 * @author Slux
 *
 */
public class ChampionDropListener extends ViewerDropAdapter
{
    private ChampionNode lastDroppedNode;

    /**
     * @param viewer
     */
    public ChampionDropListener(Viewer viewer) {
        super(viewer);
    }

    @Override
    public void drop(DropTargetEvent event)
    {
        int location = this.determineLocation(event);
        String translatedLocation = "";
        switch (location)
        {
        case 1:
            translatedLocation = "Dropped before the target ";
            break;
        case 2:
            translatedLocation = "Dropped after the target ";
            break;
        case 3:
            translatedLocation = "Dropped on the target ";
            break;
        case 4:
            translatedLocation = "Dropped into nothing ";
            break;
        }
        System.out.println(translatedLocation);

        Object target = determineTarget(event);
        System.out.println("The drop was done on the element: " + target);

        if (target == null)
            return;

        if (target instanceof ChampionNodeFigure)
        {
            // We got a valid drop
            ChampionNode cn = ((ChampionNodeFigure) target).getNode();
            this.lastDroppedNode = cn;
            System.out.println("Dropped on champion node: " + cn);

            super.drop(event);
        }
    }

    /**
     * This method performs the actual drop. We simply add the String we receive
     * to the model and trigger a refresh of the viewer by calling its setInput
     * method.
     * 
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#performDrop(java.lang.Object)
     */
    @Override
    public boolean performDrop(Object data)
    {
        String playerChampionUniqueId = data.toString();
        System.out.println("Drop performed: " + playerChampionUniqueId);

        // We have to make sure that the dropped champion wasn't
        // already somewhere else placed. If so, we remove from the
        // previous node
        for (MapNode p : AWDDataUIModelManager.getInstance().getAwMapNodes())
        {
            if (p.getNodeId().getType() == NodeType.ChampionNode)
            {
                ChampionNode cn = (ChampionNode) p;
                if (cn.getPlayerChampion() != null
                        && cn.getPlayerChampion().getUniqueId().equals(playerChampionUniqueId))
                {
                    cn.setPlayerChampion(null);
                }
            }
        }

        // Let's look for this champion and attach it to the target
        boolean found = false;
        for (Player p : AWDDataUIModelManager.getInstance().getTeamModel().getPlayers())
        {
            for (PlayerChampion pc : p.getChampions())
            {
                if (pc.getUniqueId().equals(playerChampionUniqueId))
                {
                    // Found it.
                    Objects.requireNonNull(this.lastDroppedNode).setPlayerChampion(pc);
                    System.out.println("DROPPED on " + this.lastDroppedNode);
                    found = true;
                }
            }
        }

        // Refresh the view
        if (found)
            getViewer().refresh();

        this.lastDroppedNode = null;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#validateDrop(java.lang.
     * Object, int, org.eclipse.swt.dnd.TransferData)
     */
    @Override
    public boolean validateDrop(Object target, int operation, TransferData transferType)
    {
        return true;
    }

    @Override
    protected Object determineTarget(DropTargetEvent event)
    {
        Graph g = ((GraphViewer) getViewer()).getGraphControl();
        Point p = g.getDisplay().map(null, g, event.x, event.y);
        IFigure fig = g.getFigureAt(p.x, p.y);
        return fig;
    }

}
