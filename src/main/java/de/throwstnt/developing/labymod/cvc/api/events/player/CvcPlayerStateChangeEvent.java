package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

public class CvcPlayerStateChangeEvent extends CvcPlayerEvent {

    private CvcPlayer.State oldState;
    private CvcPlayer.State newState;

    public CvcPlayerStateChangeEvent(CvcPlayer player, CvcPlayer.State oldState,
            CvcPlayer.State newState) {
        super(player);

        this.oldState = oldState;
        this.newState = newState;
    }

    public CvcPlayer.State getOldState() {
        return oldState;
    }

    public CvcPlayer.State getNewState() {
        return newState;
    }
}
