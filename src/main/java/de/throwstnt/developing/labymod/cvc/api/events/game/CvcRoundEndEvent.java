package de.throwstnt.developing.labymod.cvc.api.events.game;

import de.throwstnt.developing.labymod.cvc.api.game.CvcRound;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcRoundManager;

public class CvcRoundEndEvent extends CvcRoundEvent {

    public CvcRoundEndEvent() {
        super(CvcRoundManager.getInstance().getRound());
    }

    public CvcRoundEndEvent(CvcRound round) {
        super(round);
    }
}
