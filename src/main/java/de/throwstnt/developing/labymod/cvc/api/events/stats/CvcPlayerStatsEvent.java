package de.throwstnt.developing.labymod.cvc.api.events.stats;

import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

public class CvcPlayerStatsEvent extends CvcPlayerEvent {

    public CvcPlayerStatsEvent(CvcPlayer player) {
        super(player);
    }
}
