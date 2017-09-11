/**
 * 
 */
package de.slux.mcoc.admin.ui.model;

/**
 * @author Slux
 *
 */
public class ChampionNode extends MapNode
{

    private PlayerChampion playerChampion;

    /**
     * @param nodeId
     */
    public ChampionNode(NodeId nodeId) {
        super(nodeId);
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the playerChampion
     */
    public PlayerChampion getPlayerChampion()
    {
        return playerChampion;
    }

    /**
     * @param playerChampion
     *            the playerChampion to set
     */
    public void setPlayerChampion(PlayerChampion playerChampion)
    {
        this.playerChampion = playerChampion;
    }

}
