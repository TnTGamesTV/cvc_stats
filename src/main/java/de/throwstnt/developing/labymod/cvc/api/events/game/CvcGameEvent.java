package de.throwstnt.developing.labymod.cvc.api.events.game;

import de.throwstnt.developing.labymod.cvc.api.events.CvcEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcGame;

public class CvcGameEvent extends CvcEvent {

    private CvcGame game;

    public CvcGameEvent(CvcGame game) {
        this.game = game;
    }

    public CvcGame getGame() {
        return game;
    }
}
