package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

public class CvcPlayerLeaveEvent extends CvcPlayerEvent {

    public CvcPlayerLeaveEvent(CvcPlayer player) {
        super(player);
    }
}
