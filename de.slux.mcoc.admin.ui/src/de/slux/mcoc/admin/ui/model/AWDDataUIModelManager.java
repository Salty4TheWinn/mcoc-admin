package de.slux.mcoc.admin.ui.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import de.slux.mcoc.admin.data.model.Champion;
import de.slux.mcoc.admin.data.model.ChampionManager;
import de.slux.mcoc.admin.ui.model.PlayerChampion.Rank;
import de.slux.mcoc.admin.ui.model.listener.DataModelChangedListener;

/**
 * Data UI Model Manager
 * 
 * @author slux
 */
public class AWDDataUIModelManager
{
    private static final Logger LOG = Logger.getLogger(AWDDataUIModelManager.class.getName());
    private static AWDDataUIModelManager INSTANCE = new AWDDataUIModelManager();

    private List<DataModelChangedListener> modelChangeListeners;
    private Team teamModel;
    private Map<String, Champion> championModel;

    /**
     * Private singleton constructor
     */
    private AWDDataUIModelManager() {
        this.modelChangeListeners = new CopyOnWriteArrayList<DataModelChangedListener>();
        this.teamModel = new Team();

        try
        {
            this.championModel = new ChampionManager().getChampionData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Create a testing model here
        /*
         * Random r = new Random(); int low = 0; int high =
         * this.championModel.size() - 1; for (int i = 0; i < 10; i++) { Player
         * p = new Player("Player" + Integer.toString(i), this.teamModel);
         * this.teamModel.getPlayers().add(p); for (int j = 0; j < 5; j++) {
         * Champion c = this.championModel.values().toArray(new
         * Champion[0])[r.nextInt(high - low)]; p.getChampions().add(new
         * PlayerChampion(c.getId(), c.getName(), r.nextInt(5) + 1,
         * r.nextInt(high - low), Rank.Rank5, p)); } }
         */
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
            PlayerChampion playerChampion = new PlayerChampion(player);
            player.getChampions().add(playerChampion);
        }

        this.teamModel.getPlayers().add(player);

        for (DataModelChangedListener l : this.modelChangeListeners)
        {
            l.modelChanged();
        }
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
