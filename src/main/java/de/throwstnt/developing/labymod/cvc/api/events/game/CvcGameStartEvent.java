package de.throwstnt.developing.labymod.cvc.api.events.game;

import de.throwstnt.developing.labymod.cvc.api.game.CvcGame;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcGameManager;

public class CvcGameStartEvent extends CvcGameEvent {

    public CvcGameStartEvent() {
        super(CvcGameManager.getInstance().getGame());
    }

    public CvcGameStartEvent(CvcGame game) {
        super(game);
    }
}
