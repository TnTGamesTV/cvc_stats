package de.throwstnt.developing.labymod.cvc.api.events.game;

import de.throwstnt.developing.labymod.cvc.api.events.CvcEvent;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.State;

public class CvcStateChangeEvent extends CvcEvent {

    private State oldState;
    private State newState;

    public CvcStateChangeEvent(State oldState, State newState) {
        this.oldState = oldState;
        this.newState = newState;
    }

    public State getOldState() {
        return oldState;
    }

    public State getNewState() {
        return newState;
    }
}
