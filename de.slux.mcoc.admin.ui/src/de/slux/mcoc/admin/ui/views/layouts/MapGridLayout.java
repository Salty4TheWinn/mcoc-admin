/**
 * 
 */
package de.slux.mcoc.admin.ui.views.layouts;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;

import de.slux.mcoc.admin.ui.model.MapNode;
import de.slux.mcoc.admin.ui.model.NodeId;
import de.slux.mcoc.admin.ui.model.NodeId.NodeType;
import de.slux.mcoc.admin.ui.views.figure.ChampionNodeFigure;

/**
 * Custom grid layout for an AQ/AW map
 * 
 * @author Slux
 */
public class MapGridLayout extends AbstractLayoutAlgorithm
{
    private static final Logger LOG = Logger.getLogger(MapGridLayout.class.getName());

    private int columns;
    private int rows;
    private Collection<MapNode> model;
    private Map<NodeId, NodeCellPosition> positionModel;

    /**
     * @param styles
     * @param model
     */
    public MapGridLayout(int styles, int rows, int columns, Collection<MapNode> model,
            Map<NodeId, NodeCellPosition> positionModel) {
        super(styles);

        this.rows = rows;
        this.columns = columns;
        this.model = model;
        this.positionModel = positionModel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#
     * applyLayoutInternal(org.eclipse.zest.layouts.dataStructures.InternalNode[
     * ], org.eclipse.zest.layouts.dataStructures.InternalRelationship[],
     * double, double, double, double)
     */
    @Override
    protected void applyLayoutInternal(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider,
            double boundsX, double boundsY, double boundsWidth, double boundsHeight)
    {
        double rowSize = boundsHeight / this.rows;
        double colSize = boundsWidth / this.columns;
        
        // set a minimum so that we force the scroll bars keeping the map
        // nicely drown
        /*
        if (rowSize < ChampionNodeFigure.HEIGHT)
            rowSize = ChampionNodeFigure.HEIGHT;
        if (colSize < ChampionNodeFigure.WIDTH)
            colSize = ChampionNodeFigure.WIDTH;
         */
        for (InternalNode node : entitiesToLayout)
        {
            // Zest sucks, we need to find the data in the model using this hack
            MapNode mapNode = findMapNode(node);

            NodeCellPosition cellPos = this.positionModel.get(mapNode.getNodeId());
            if (cellPos != null)
            {
                // We align smaller figures in the middle (vertically &
                // horizontally)
                double fixAlignVertical = 0;
                double fixAlignHorizontal = 0;
                if (!mapNode.getNodeId().getType().equals(NodeType.ChampionNode))
                {
                    fixAlignHorizontal = (ChampionNodeFigure.WIDTH / 2) - (node.getWidthInLayout() / 2);
                    fixAlignVertical = (ChampionNodeFigure.HEIGHT / 2) - (node.getHeightInLayout() / 2);
                }

                node.setLocation((cellPos.getCol() * colSize) + fixAlignHorizontal,
                        (cellPos.getRow() * rowSize) + fixAlignVertical);
            }

        }
    }

    /**
     * s This is a really ugly way to do that but there are no alternatives
     * offered by Zest
     * 
     * @param node
     *            the internal graph node
     * @return the model node
     * @throws a
     *             RuntimeException if we can't find the {@link MapNode}
     */
    private MapNode findMapNode(InternalNode node)
    {
        for (MapNode mapNode : this.model)
        {
            if (mapNode.getNodeId().toString().equals(node.toString()))
            {
                return mapNode;
            }
        }

        LOG.severe("Cannot find the model node for the Graph Internal Node: " + node);

        throw new RuntimeException("Cannot find the model node for the Graph Internal Node: " + node);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#
     * preLayoutAlgorithm(org.eclipse.zest.layouts.dataStructures.InternalNode[]
     * , org.eclipse.zest.layouts.dataStructures.InternalRelationship[], double,
     * double, double, double)
     */
    @Override
    protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider,
            double x, double y, double width, double height)
    {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#
     * postLayoutAlgorithm(org.eclipse.zest.layouts.dataStructures.InternalNode[
     * ], org.eclipse.zest.layouts.dataStructures.InternalRelationship[])
     */
    @Override
    protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#
     * getTotalNumberOfLayoutSteps()
     */
    @Override
    protected int getTotalNumberOfLayoutSteps()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#
     * getCurrentLayoutStep()
     */
    @Override
    protected int getCurrentLayoutStep()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#setLayoutArea
     * (double, double, double, double)
     */
    @Override
    public void setLayoutArea(double x, double y, double width, double height)
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#
     * isValidConfiguration(boolean, boolean)
     */
    @Override
    protected boolean isValidConfiguration(boolean asynchronous, boolean continuous)
    {
        return (this.rows > 0 && this.columns > 0);
    }

}
