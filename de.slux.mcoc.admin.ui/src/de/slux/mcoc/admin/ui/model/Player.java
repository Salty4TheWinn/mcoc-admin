/**
 * 
 */
package de.slux.mcoc.admin.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slux
 *
 */
public class Player
{
    private Team team;
    private String name;
    private List<PlayerChampion> champions;
    private Boolean enabled;

    /**
     * Constructor
     * 
     * @param name
     */
    public Player(String name, Team team) {
        super();
        this.name = name;
        this.team = team;
        this.champions = new ArrayList<>();
        this.enabled = true;
    }

    /**
     * Constructor
     */
    public Player() {
        this(null, null);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Player [name=" + name + "]";
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
     * @return the champions
     */
    public List<PlayerChampion> getChampions()
    {
        return champions;
    }

    /**
     * @return the team
     */
    public Team getTeam()
    {
        return team;
    }

    /**
     * @param team
     *            the team to set
     */
    public void setTeam(Team team)
    {
        this.team = team;
    }

    /**
     * @return the enabled
     */
    public Boolean isEnabled()
    {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

}
