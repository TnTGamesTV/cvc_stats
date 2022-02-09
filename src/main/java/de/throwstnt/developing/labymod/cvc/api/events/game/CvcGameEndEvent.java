package de.throwstnt.developing.labymod.cvc.api.events.game;

import de.throwstnt.developing.labymod.cvc.api.game.CvcGame;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcGameManager;

/**
 * Fired when the game is cancelled or has ended. Calling CvcGameManager#getGame will most likely
 * result in null.
 */
public class CvcGameEndEvent extends CvcGameEvent {

    public CvcGameEndEvent() {
        super(CvcGameManager.getInstance().getGame());
    }

    public CvcGameEndEvent(CvcGame game) {
        super(game);
    }
}
