package de.slux.mcoc.admin.ui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.slux.mcoc.admin.data.model.Champion;
import de.slux.mcoc.admin.data.model.McocDataManager;
import de.slux.mcoc.admin.ui.model.MapLink.MapLinkType;
import de.slux.mcoc.admin.ui.model.NodeId.NodeType;
import de.slux.mcoc.admin.ui.model.listener.DataModelChangedListener;
import de.slux.mcoc.admin.ui.views.layouts.NodeCellPosition;

/**
 * Data UI Model Manager
 * 
 * @author slux
 */
public class AWDDataUIModelManager
{
    private static final int TOTAL_AW_MAP_CHAMP_NODES = 55;
    private static final int TOTAL_AW_MAP_PORTAL_NODES = 21;
    private static final int TOTAL_AW_MAP_EMPTY_NODES = 23;

    // TESTING
    // private static final int TOTAL_AW_MAP_CHAMP_NODES = 3;
    // private static final int TOTAL_AW_MAP_PORTAL_NODES = 1;
    // private static final int TOTAL_AW_MAP_EMPTY_NODES = 2;

    private static final Logger LOG = Logger.getLogger(AWDDataUIModelManager.class.getName());
    private static AWDDataUIModelManager INSTANCE = new AWDDataUIModelManager();

    private List<DataModelChangedListener> modelChangeListeners;
    private Team teamModel;
    private Map<String, Champion> championModel;
    private Map<NodeId, MapNode> awMapNodes;
    private List<MapLink> awMapLinks;
    private Map<NodeId, NodeCellPosition> awMapNodesPositions;

    /**
     * Private singleton constructor
     */
    private AWDDataUIModelManager() {
        this.modelChangeListeners = new CopyOnWriteArrayList<DataModelChangedListener>();
        this.awMapNodesPositions = new HashMap<>();
        this.teamModel = new Team();

        try
        {
            this.championModel = new McocDataManager().getChampionData();
        }
        catch (Exception e)
        {
            LOG.log(Level.SEVERE, "Cannot get champions data", e);
        }

        // Create the AW map nodes
        this.awMapNodes = new HashMap<>();

        // Champion nodes
        for (int i = 1; i <= TOTAL_AW_MAP_CHAMP_NODES; ++i)
        {
            MapNode node = new ChampionNode(new NodeId(NodeType.ChampionNode, i));
            awMapNodes.put(node.getNodeId(), node);
        }

        // Empty nodes
        for (int i = 0; i <= TOTAL_AW_MAP_EMPTY_NODES; ++i)
        {
            MapNode node = new EmptyNode(new NodeId(NodeType.EmptyNode, i));
            awMapNodes.put(node.getNodeId(), node);
        }

        // Portal nodes
        for (int i = 1; i <= TOTAL_AW_MAP_PORTAL_NODES; ++i)
        {
            MapNode node = new PortalNode(new NodeId(NodeType.PortalNode, i));
            awMapNodes.put(node.getNodeId(), node);
        }

        this.awMapLinks = new ArrayList<>();

        setupAwMapNodeLinks();
        // setupAwMapTestNodeLinks();

        setupAwMapNodePositions();

    }

    /**
     * Based on a grid composed by 51 rows and 41 columns
     */
    private void setupAwMapNodePositions()
    {
        // Middle south (beginning)
        addPos(NodeType.EmptyNode, 0, 48, 21);
        addPos(NodeType.PortalNode, 12, 48, 18);
        addPos(NodeType.PortalNode, 15, 48, 24);
        addPos(NodeType.PortalNode, 14, 45, 21);

        // South west (beginning)
        addPos(NodeType.EmptyNode, 1, 44, 1);
        addPos(NodeType.ChampionNode, 1, 40, 1);
        addPos(NodeType.PortalNode, 3, 36, 1);
        addPos(NodeType.ChampionNode, 2, 40, 4);
        addPos(NodeType.PortalNode, 6, 36, 4);
        addPos(NodeType.EmptyNode, 2, 44, 7);
        addPos(NodeType.ChampionNode, 3, 40, 7);
        addPos(NodeType.PortalNode, 7, 36, 7);
        addPos(NodeType.EmptyNode, 4, 32, 1);
        addPos(NodeType.EmptyNode, 5, 32, 7);

        // North middle (bosses)
        addPos(NodeType.ChampionNode, 54, 1, 21);
        addPos(NodeType.ChampionNode, 53, 1, 18);
        addPos(NodeType.ChampionNode, 55, 1, 24);
        addPos(NodeType.EmptyNode, 23, 6, 21);
        addPos(NodeType.ChampionNode, 51, 6, 18);
        addPos(NodeType.ChampionNode, 52, 6, 24);

    }

    private void addPos(NodeType type, int nodeNumber, int row, int column)
    {
        this.awMapNodesPositions.put(new NodeId(type, nodeNumber), new NodeCellPosition(row, column));

    }

    private void setupAwMapTestNodeLinks()
    {
        // Empty node links
        connectNode(new NodeId(NodeType.EmptyNode, 0), new NodeId[] { new NodeId(NodeType.PortalNode, 1) });
        connectNode(new NodeId(NodeType.EmptyNode, 1),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 1), new NodeId(NodeType.ChampionNode, 3) });
        connectNode(new NodeId(NodeType.EmptyNode, 2), new NodeId[] { new NodeId(NodeType.ChampionNode, 2) });

        // Portal node links
        connectNode(new NodeId(NodeType.PortalNode, 1), new NodeId[] { new NodeId(NodeType.EmptyNode, 1) });

        // Champion node links
        connectNode(new NodeId(NodeType.ChampionNode, 1), new NodeId[] { new NodeId(NodeType.EmptyNode, 2) });
        connectNode(new NodeId(NodeType.ChampionNode, 3), new NodeId[] { new NodeId(NodeType.EmptyNode, 2) });
        connectNode(new NodeId(NodeType.ChampionNode, 3), new NodeId[] { new NodeId(NodeType.ChampionNode, 1) });
    }

    private void setupAwMapNodeLinks()
    {
        // Empty node links
        connectNode(new NodeId(NodeType.EmptyNode, 0), new NodeId[] { new NodeId(NodeType.PortalNode, 12),
                new NodeId(NodeType.PortalNode, 14), new NodeId(NodeType.PortalNode, 15) });
        connectNode(new NodeId(NodeType.EmptyNode, 1), new NodeId[] { new NodeId(NodeType.ChampionNode, 1) });
        connectNode(new NodeId(NodeType.EmptyNode, 2), new NodeId[] { new NodeId(NodeType.ChampionNode, 3) });
        connectNode(new NodeId(NodeType.EmptyNode, 3), new NodeId[] { new NodeId(NodeType.ChampionNode, 4),
                new NodeId(NodeType.ChampionNode, 5), new NodeId(NodeType.ChampionNode, 6) });
        connectNode(new NodeId(NodeType.EmptyNode, 4), new NodeId[] { new NodeId(NodeType.ChampionNode, 19) });
        connectNode(new NodeId(NodeType.EmptyNode, 5), new NodeId[] { new NodeId(NodeType.ChampionNode, 21) });
        connectNode(new NodeId(NodeType.EmptyNode, 6), new NodeId[] { new NodeId(NodeType.ChampionNode, 11) });
        connectNode(new NodeId(NodeType.EmptyNode, 7), new NodeId[] { new NodeId(NodeType.ChampionNode, 10) });
        connectNode(new NodeId(NodeType.EmptyNode, 8), new NodeId[] { new NodeId(NodeType.ChampionNode, 12) });
        connectNode(new NodeId(NodeType.EmptyNode, 9), new NodeId[] { new NodeId(NodeType.ChampionNode, 25) });
        connectNode(new NodeId(NodeType.EmptyNode, 10), new NodeId[] { new NodeId(NodeType.ChampionNode, 27) });
        connectNode(new NodeId(NodeType.EmptyNode, 11), new NodeId[] { new NodeId(NodeType.ChampionNode, 28) });
        connectNode(new NodeId(NodeType.EmptyNode, 12), new NodeId[] { new NodeId(NodeType.ChampionNode, 29) });
        connectNode(new NodeId(NodeType.EmptyNode, 13), new NodeId[] { new NodeId(NodeType.ChampionNode, 30) });
        connectNode(new NodeId(NodeType.EmptyNode, 14),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 31), new NodeId(NodeType.ChampionNode, 32) });
        connectNode(new NodeId(NodeType.EmptyNode, 15), new NodeId[] { new NodeId(NodeType.ChampionNode, 33) });
        connectNode(new NodeId(NodeType.EmptyNode, 16),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 41), new NodeId(NodeType.EmptyNode, 17) });
        connectNode(new NodeId(NodeType.EmptyNode, 17), new NodeId[] { new NodeId(NodeType.ChampionNode, 46) });
        connectNode(new NodeId(NodeType.EmptyNode, 18), new NodeId[] { new NodeId(NodeType.ChampionNode, 48) });
        connectNode(new NodeId(NodeType.EmptyNode, 19), new NodeId[] { new NodeId(NodeType.ChampionNode, 51) });
        connectNode(new NodeId(NodeType.EmptyNode, 20), new NodeId[] { new NodeId(NodeType.ChampionNode, 52) });
        connectNode(new NodeId(NodeType.EmptyNode, 21), new NodeId[] { new NodeId(NodeType.ChampionNode, 53) });
        connectNode(new NodeId(NodeType.EmptyNode, 22), new NodeId[] { new NodeId(NodeType.ChampionNode, 55) });
        connectNode(new NodeId(NodeType.EmptyNode, 23), new NodeId[] { new NodeId(NodeType.ChampionNode, 54) });

        // Portal node links
        connectNode(new NodeId(NodeType.PortalNode, 1), new NodeId[] { new NodeId(NodeType.EmptyNode, 9) });
        connectNode(new NodeId(NodeType.PortalNode, 2),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 9), new NodeId(NodeType.EmptyNode, 10) });
        connectNode(new NodeId(NodeType.PortalNode, 3), new NodeId[] { new NodeId(NodeType.EmptyNode, 4) });
        connectNode(new NodeId(NodeType.PortalNode, 4),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 10), new NodeId(NodeType.EmptyNode, 11) });
        connectNode(new NodeId(NodeType.PortalNode, 5), new NodeId[] { new NodeId(NodeType.EmptyNode, 16) });
        connectNode(new NodeId(NodeType.PortalNode, 6),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 4), new NodeId(NodeType.EmptyNode, 5) });
        connectNode(new NodeId(NodeType.PortalNode, 7), new NodeId[] { new NodeId(NodeType.EmptyNode, 5) });
        connectNode(new NodeId(NodeType.PortalNode, 8),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 19), new NodeId(NodeType.EmptyNode, 21) });
        connectNode(new NodeId(NodeType.PortalNode, 9),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 18), new NodeId(NodeType.EmptyNode, 19) });
        connectNode(new NodeId(NodeType.PortalNode, 10),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 18), new NodeId(NodeType.EmptyNode, 19) });
        connectNode(new NodeId(NodeType.PortalNode, 11),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 10), new NodeId(NodeType.EmptyNode, 11) });
        connectNode(new NodeId(NodeType.PortalNode, 12),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 1), new NodeId(NodeType.EmptyNode, 2) });
        connectNode(new NodeId(NodeType.PortalNode, 13), new NodeId[] { new NodeId(NodeType.EmptyNode, 11),
                new NodeId(NodeType.EmptyNode, 12), new NodeId(NodeType.EmptyNode, 13) });
        connectNode(new NodeId(NodeType.PortalNode, 7), new NodeId[] { new NodeId(NodeType.EmptyNode, 6),
                new NodeId(NodeType.EmptyNode, 12), new NodeId(NodeType.EmptyNode, 8) });
        connectNode(new NodeId(NodeType.PortalNode, 15), new NodeId[] { new NodeId(NodeType.EmptyNode, 3) });
        connectNode(new NodeId(NodeType.PortalNode, 16),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 18), new NodeId(NodeType.EmptyNode, 20) });
        connectNode(new NodeId(NodeType.PortalNode, 17),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 13), new NodeId(NodeType.EmptyNode, 14) });
        connectNode(new NodeId(NodeType.PortalNode, 18),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 18), new NodeId(NodeType.EmptyNode, 20) });
        connectNode(new NodeId(NodeType.PortalNode, 19),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 20), new NodeId(NodeType.EmptyNode, 22) });
        connectNode(new NodeId(NodeType.PortalNode, 20), new NodeId[] { new NodeId(NodeType.EmptyNode, 13),
                new NodeId(NodeType.EmptyNode, 14), new NodeId(NodeType.EmptyNode, 15) });
        connectNode(new NodeId(NodeType.PortalNode, 21),
                new NodeId[] { new NodeId(NodeType.EmptyNode, 20), new NodeId(NodeType.EmptyNode, 22) });

        // Champion nodes
        connectNode(new NodeId(NodeType.ChampionNode, 1),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 2), new NodeId(NodeType.PortalNode, 3) });
        connectNode(new NodeId(NodeType.ChampionNode, 2),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 3), new NodeId(NodeType.PortalNode, 6) });
        connectNode(new NodeId(NodeType.ChampionNode, 3), new NodeId[] { new NodeId(NodeType.PortalNode, 7) });
        connectNode(new NodeId(NodeType.ChampionNode, 4), new NodeId[] { new NodeId(NodeType.ChampionNode, 7) });
        connectNode(new NodeId(NodeType.ChampionNode, 5), new NodeId[] { new NodeId(NodeType.ChampionNode, 8) });
        connectNode(new NodeId(NodeType.ChampionNode, 6), new NodeId[] { new NodeId(NodeType.ChampionNode, 9) });
        connectNode(new NodeId(NodeType.ChampionNode, 7), new NodeId[] { new NodeId(NodeType.ChampionNode, 13) });
        connectNode(new NodeId(NodeType.ChampionNode, 8), new NodeId[] { new NodeId(NodeType.ChampionNode, 14) });
        connectNode(new NodeId(NodeType.ChampionNode, 9), new NodeId[] { new NodeId(NodeType.ChampionNode, 15) });
        connectNode(new NodeId(NodeType.ChampionNode, 10), new NodeId[] { new NodeId(NodeType.ChampionNode, 16) });
        connectNode(new NodeId(NodeType.ChampionNode, 11), new NodeId[] { new NodeId(NodeType.ChampionNode, 17) });
        connectNode(new NodeId(NodeType.ChampionNode, 12), new NodeId[] { new NodeId(NodeType.ChampionNode, 18) });
        connectNode(new NodeId(NodeType.ChampionNode, 13), new NodeId[] { new NodeId(NodeType.ChampionNode, 14) });
        connectNode(new NodeId(NodeType.ChampionNode, 14),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 15), new NodeId(NodeType.PortalNode, 20) });
        connectNode(new NodeId(NodeType.ChampionNode, 15), new NodeId[] {});
        connectNode(new NodeId(NodeType.ChampionNode, 16), new NodeId[] { new NodeId(NodeType.ChampionNode, 23) });
        connectNode(new NodeId(NodeType.ChampionNode, 17), new NodeId[] { new NodeId(NodeType.ChampionNode, 22) });
        connectNode(new NodeId(NodeType.ChampionNode, 18), new NodeId[] { new NodeId(NodeType.ChampionNode, 24) });
        connectNode(new NodeId(NodeType.ChampionNode, 19), new NodeId[] { new NodeId(NodeType.PortalNode, 1) });
        connectNode(new NodeId(NodeType.ChampionNode, 20), new NodeId[] { new NodeId(NodeType.PortalNode, 2) });
        connectNode(new NodeId(NodeType.ChampionNode, 21), new NodeId[] { new NodeId(NodeType.PortalNode, 4) });
        connectNode(new NodeId(NodeType.ChampionNode, 22), new NodeId[] { new NodeId(NodeType.PortalNode, 11) });
        connectNode(new NodeId(NodeType.ChampionNode, 23), new NodeId[] { new NodeId(NodeType.PortalNode, 13) });
        connectNode(new NodeId(NodeType.ChampionNode, 24), new NodeId[] { new NodeId(NodeType.PortalNode, 17) });
        connectNode(new NodeId(NodeType.ChampionNode, 25),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 26), new NodeId(NodeType.PortalNode, 5) });
        connectNode(new NodeId(NodeType.ChampionNode, 26),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 27), new NodeId(NodeType.PortalNode, 5) });
        connectNode(new NodeId(NodeType.ChampionNode, 27), new NodeId[] { new NodeId(NodeType.PortalNode, 5) });
        connectNode(new NodeId(NodeType.ChampionNode, 28), new NodeId[] { new NodeId(NodeType.ChampionNode, 34) });
        connectNode(new NodeId(NodeType.ChampionNode, 29),
                new NodeId[] { new NodeId(NodeType.ChampionNode, 34), new NodeId(NodeType.ChampionNode, 35) });
        connectNode(new NodeId(NodeType.ChampionNode, 30), new NodeId[] { new NodeId(NodeType.ChampionNode, 35) });
        connectNode(new NodeId(NodeType.ChampionNode, 31), new NodeId[] { new NodeId(NodeType.ChampionNode, 38) });
        connectNode(new NodeId(NodeType.ChampionNode, 32), new NodeId[] { new NodeId(NodeType.ChampionNode, 39) });
        connectNode(new NodeId(NodeType.ChampionNode, 33), new NodeId[] { new NodeId(NodeType.ChampionNode, 40) });
        connectNode(new NodeId(NodeType.ChampionNode, 34), new NodeId[] { new NodeId(NodeType.ChampionNode, 36) });
        connectNode(new NodeId(NodeType.ChampionNode, 35), new NodeId[] { new NodeId(NodeType.ChampionNode, 37) });
        connectNode(new NodeId(NodeType.ChampionNode, 36), new NodeId[] { new NodeId(NodeType.PortalNode, 10) });
        connectNode(new NodeId(NodeType.ChampionNode, 37), new NodeId[] { new NodeId(NodeType.PortalNode, 16) });
        connectNode(new NodeId(NodeType.ChampionNode, 38), new NodeId[] { new NodeId(NodeType.ChampionNode, 42) });
        connectNode(new NodeId(NodeType.ChampionNode, 39), new NodeId[] { new NodeId(NodeType.ChampionNode, 43) });
        connectNode(new NodeId(NodeType.ChampionNode, 40), new NodeId[] { new NodeId(NodeType.ChampionNode, 44) });
        connectNode(new NodeId(NodeType.ChampionNode, 41), new NodeId[] { new NodeId(NodeType.ChampionNode, 45) });
        connectNode(new NodeId(NodeType.ChampionNode, 42), new NodeId[] { new NodeId(NodeType.PortalNode, 18) });
        connectNode(new NodeId(NodeType.ChampionNode, 43), new NodeId[] { new NodeId(NodeType.PortalNode, 19) });
        connectNode(new NodeId(NodeType.ChampionNode, 44), new NodeId[] { new NodeId(NodeType.PortalNode, 21) });
        connectNode(new NodeId(NodeType.ChampionNode, 45), new NodeId[] { new NodeId(NodeType.ChampionNode, 47) });
        connectNode(new NodeId(NodeType.ChampionNode, 46), new NodeId[] { new NodeId(NodeType.ChampionNode, 49) });
        connectNode(new NodeId(NodeType.ChampionNode, 47), new NodeId[] { new NodeId(NodeType.PortalNode, 9) });
        connectNode(new NodeId(NodeType.ChampionNode, 48), new NodeId[] { new NodeId(NodeType.EmptyNode, 23) });
        connectNode(new NodeId(NodeType.ChampionNode, 49), new NodeId[] { new NodeId(NodeType.ChampionNode, 50) });
        connectNode(new NodeId(NodeType.ChampionNode, 50), new NodeId[] { new NodeId(NodeType.PortalNode, 8) });
        connectNode(new NodeId(NodeType.ChampionNode, 51), new NodeId[] { new NodeId(NodeType.EmptyNode, 23) });
        connectNode(new NodeId(NodeType.ChampionNode, 52), new NodeId[] { new NodeId(NodeType.EmptyNode, 23) });
        connectNode(new NodeId(NodeType.ChampionNode, 53), new NodeId[] { new NodeId(NodeType.ChampionNode, 54) });
        connectNode(new NodeId(NodeType.ChampionNode, 54), new NodeId[] {});
        connectNode(new NodeId(NodeType.ChampionNode, 55), new NodeId[] { new NodeId(NodeType.ChampionNode, 54) });

        // TODO: Boosts nodes
    }

    /**
     * Method creates only the walkable paths (not the boosted ones)
     * 
     * @param nodeId
     * @param connectedNodeIds
     * @return the outcome of the operation
     */
    private boolean connectNode(NodeId nodeId, NodeId[] connectedNodeIds)
    {
        MapNode source = this.awMapNodes.get(nodeId);

        if (source == null)
        {
            LOG.warning("Cannot find source AW node with ID " + nodeId);
            return false;
        }

        for (NodeId nodeDestId : connectedNodeIds)
        {
            MapNode destination = this.awMapNodes.get(nodeDestId);
            if (destination == null)
            {
                LOG.warning("Cannot find destination AW node with ID " + nodeDestId);
                return false;
            }

            source.getConnections().add(destination);

            // Create the links
            // Note, this first IF statement can't deal with boost links. Those
            // must be added separately
            if (source.getNodeId().getType() == NodeType.ChampionNode
                    && destination.getNodeId().getType() == NodeType.ChampionNode)
            {
                this.awMapLinks.add(new MapLink(MapLinkType.PathLinkType, source, destination));
            }
            else if (source.getNodeId().getType() == NodeType.EmptyNode
                    || source.getNodeId().getType() == NodeType.ChampionNode)
            {
                this.awMapLinks.add(new MapLink(MapLinkType.PathLinkType, source, destination));
            }
            else if (source.getNodeId().getType() == NodeType.PortalNode)
            {
                this.awMapLinks.add(new MapLink(MapLinkType.PortalLinkType, source, destination));
            }
            else
            {
                LOG.warning("Cannot create a valid link for source=" + source + " destination=" + destination);
            }
        }

        return true;
    }

    /**
     * Get singleton instance
     * 
     * @return the instance
     */
    public static AWDDataUIModelManager getInstance()
    {
        return INSTANCE;
    }

    /**
     * @return the teamModel
     */
    public Team getTeamModel()
    {
        return this.teamModel;
    }

    /**
     * register listener to get call-backs when the rootAgentModel has changed
     * 
     * @param listener
     */
    public void registerListener(DataModelChangedListener listener)
    {
        this.modelChangeListeners.add(listener);
    }

    /**
     * de-register listener to get call-backs when the rootAgentModel has
     * changed
     * 
     * @param listener
     */
    public void deregisterListener(DataModelChangedListener listener)
    {
        this.modelChangeListeners.remove(listener);
    }

    public void addNewPlayer()
    {
        Player player = new Player("New Player", this.teamModel);
        if (this.teamModel.getPlayers().size() > Team.MAX_TEAM_PLAYERS)
        {
            player.setEnabled(false);
        }

        for (int i = 0; i < 5; ++i)
        {
            PlayerChampion playerChampion = new PlayerChampion(null, "undefined", "1", "0", "1", player);
            player.getChampions().add(playerChampion);
        }

        this.teamModel.getPlayers().add(player);

        for (DataModelChangedListener l : this.modelChangeListeners)
        {
            l.modelChanged();
        }
    }

    /**
     * @return the championModel
     */
    public Map<String, Champion> getChampionModel()
    {
        return championModel;
    }

    public Collection<MapNode> getAwMapNodes()
    {
        return this.awMapNodes.values();
    }

    /**
     * @return the awMapLinks
     */
    public List<MapLink> getAwMapLinks()
    {
        return awMapLinks;
    }

    /**
     * Force the update of the views
     */
    public void forceViewsUpdate()
    {
        for (DataModelChangedListener l : this.modelChangeListeners)
            l.modelChanged();

    }

    /**
     * Get the awMapNodesPositions
     */
    public Map<NodeId, NodeCellPosition> getAwMapNodesPositions()
    {
        return this.awMapNodesPositions;
    }

    /**
     * @param key
     * @return
     * @see java.util.Map#remove(java.lang.Object)
     */
    /*
     * public SubSystemModelItem removeAgentModelItem(String key) {
     * SubSystemModelItem item = rootAgentModel.getSubSystems().remove(key);
     * 
     * for (DataModelChangedListener l : this.modelChangeListeners)
     * l.modelChanged(DataModelType.DataModelTypeAgentNetwork);
     * 
     * return item; }
     */

}
