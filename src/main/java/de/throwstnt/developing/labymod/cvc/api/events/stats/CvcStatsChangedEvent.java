package de.throwstnt.developing.labymod.cvc.api.events.stats;

import de.throwstnt.developing.labymod.cvc.api.data.stats.CvcStats;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

/**
 * Gets triggered when the api has returned values for a player. Despite it's name it is mostly used
 * when the data is available rather then when it is changed
 */
public class CvcStatsChangedEvent extends CvcPlayerStatsEvent {

    private CvcStats stats;
    private String rankPrefix;

    public CvcStatsChangedEvent(CvcPlayer player, CvcStats stats, String rankPrefix) {
        super(player);

        this.stats = stats;
        this.rankPrefix = rankPrefix;
    }

    public CvcStats getStats() {
        return stats;
    }

    public String getRankPrefix() {
        return rankPrefix;
    }
}
