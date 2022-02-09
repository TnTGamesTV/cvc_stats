package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

public class CvcPlayerJoinEvent extends CvcPlayerEvent {

    public CvcPlayerJoinEvent(CvcPlayer player) {
        super(player);
    }
}
