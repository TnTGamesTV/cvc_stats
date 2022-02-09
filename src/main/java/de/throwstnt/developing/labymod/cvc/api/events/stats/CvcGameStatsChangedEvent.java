package de.throwstnt.developing.labymod.cvc.api.events.stats;

import de.throwstnt.developing.labymod.cvc.api.data.stats.game.CvcGameStats;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

public class CvcGameStatsChangedEvent extends CvcPlayerStatsEvent {

    private CvcGameStats gameStats;

    public CvcGameStatsChangedEvent(CvcPlayer player, CvcGameStats gameStats) {
        super(player);

        this.gameStats = gameStats;
    }

    public CvcGameStats getGameStats() {
        return gameStats;
    }
}
