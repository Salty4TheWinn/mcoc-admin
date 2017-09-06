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
    private static final String DEFAULT_RANK = "1";
    private static final String DEFAULT_STAR = "1";

    private Player player;
    private String id;
    private String name;
    private String stars;
    private String sigLevel;
    private String rank;

    /**
     * Constructor
     */
    public PlayerChampion(Player player) {
        this(null, null, "", DEFAULT_STAR, DEFAULT_RANK, player);
    }

    /**
     * @param id
     * @param name
     * @param stars
     * @param sigLevel
     * @param rank
     */
    public PlayerChampion(String id, String name, String stars, String sigLevel, String rank, Player player) {
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
    public String getStars()
    {
        return stars;
    }

    /**
     * @param stars
     *            the stars to set
     */
    public void setStars(String stars)
    {
        this.stars = stars;
    }

    /**
     * @return the sigLevel
     */
    public String getSigLevel()
    {
        return sigLevel;
    }

    /**
     * @param sigLevel
     *            the sigLevel to set
     */
    public void setSigLevel(String sigLevel)
    {
        this.sigLevel = sigLevel;
    }

    /**
     * @return the rank
     */
    public String getRank()
    {
        return rank;
    }

    /**
     * @param rank
     *            the rank to set
     */
    public void setRank(String rank)
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
