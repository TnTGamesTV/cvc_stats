package de.throwstnt.developing.labymod.cvc.api.events.game;

import de.throwstnt.developing.labymod.cvc.api.events.CvcEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcRound;

public class CvcRoundEvent extends CvcEvent {

    private CvcRound round;

    public CvcRoundEvent(CvcRound round) {
        this.round = round;
    }

    public CvcRound getRound() {
        return round;
    }
}
