/**
 * 
 */
package de.slux.mcoc.admin.ui.model;

/**
 * @author slux
 *
 */
public class PlayerChampion
{
    /**
     * Chanpion ranks
     */
    public enum Rank
    {
        RankUnknown, Rank1, Rank2, Rank3, Rank4, Rank5
    };

    private Player player;
    private String id;
    private String name;
    private int stars;
    private int sigLevel;
    private Rank rank;

    /**
     * Constructor
     */
    public PlayerChampion(Player player) {
        this(null, null, -1, -1, Rank.RankUnknown, player);
    }

    /**
     * @param id
     * @param name
     * @param stars
     * @param sigLevel
     * @param rank
     */
    public PlayerChampion(String id, String name, int stars, int sigLevel, Rank rank, Player player) {
        super();
        this.player = player;
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.sigLevel = sigLevel;
        this.rank = rank;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the stars
     */
    public int getStars()
    {
        return stars;
    }

    /**
     * @param stars
     *            the stars to set
     */
    public void setStars(int stars)
    {
        this.stars = stars;
    }

    /**
     * @return the sigLevel
     */
    public int getSigLevel()
    {
        return sigLevel;
    }

    /**
     * @param sigLevel
     *            the sigLevel to set
     */
    public void setSigLevel(int sigLevel)
    {
        this.sigLevel = sigLevel;
    }

    /**
     * @return the rank
     */
    public Rank getRank()
    {
        return rank;
    }

    /**
     * @param rank
     *            the rank to set
     */
    public void setRank(Rank rank)
    {
        this.rank = rank;
    }

    /**
     * @return the player
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * @param player
     *            the player to set
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "PlayerChampion [id=" + id + ", name=" + name + ", stars=" + stars + ", sigLevel=" + sigLevel + ", rank="
                + rank + "]";
    }

}
