package de.throwstnt.developing.labymod.cvc.api.events.game;

import de.throwstnt.developing.labymod.cvc.api.game.CvcRound;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcRoundManager;

public class CvcRoundStartEvent extends CvcRoundEvent {

    public CvcRoundStartEvent() {
        super(CvcRoundManager.getInstance().getRound());
    }

    public CvcRoundStartEvent(CvcRound round) {
        super(round);
    }
}
