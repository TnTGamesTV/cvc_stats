package de.throwstnt.developing.labymod.cvc.api.game;

public class CvcRound {

    private long startedAt;

    public CvcRound() {
        this.startedAt = System.currentTimeMillis();
    }

    public long getStartedAt() {
        return startedAt;
    }
}
