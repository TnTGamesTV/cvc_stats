package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.events.CvcEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

/**
 * An event that contains a player
 *
 */
public class CvcPlayerEvent extends CvcEvent {

    private CvcPlayer player;

    public CvcPlayerEvent(CvcPlayer player) {
        this.player = player;
    }

    public CvcPlayer getPlayer() {
        return player;
    }
}
