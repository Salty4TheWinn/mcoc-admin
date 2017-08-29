/**
 * 
 */
package de.slux.mcoc.admin.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Root node
 */
public class Team
{
    public static final int MAX_TEAM_PLAYERS = 10;
    private List<Player> players;

    /**
     * Constructor
     */
    public Team() {
        this.players = new ArrayList<>();
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers()
    {
        return players;
    }

}
