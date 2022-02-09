package de.throwstnt.developing.labymod.cvc.api.game.managers;

import java.util.Stack;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcGameEndEvent;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcRoundEndEvent;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcRoundStartEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcRound;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;

public class CvcRoundManager implements CvcEventListener {

    private static CvcRoundManager instance;

    public static CvcRoundManager getInstance() {
        if (instance == null)
            instance = new CvcRoundManager();
        return instance;
    }

    private int roundIndex;

    private Stack<CvcRound> rounds;

    private CvcRound round;

    private CvcRoundManager() {
        this.roundIndex = 0;
        this.rounds = new Stack<>();
    }

    public boolean hasRound() {
        return this.round != null;
    }

    public CvcRound getRound() {
        return this.round;
    }

    public Stack<CvcRound> getRounds() {
        return this.rounds;
    }

    /**
     * Called by sound handler when there is a new round
     */
    public void createRound() {
        CvcRound round = new CvcRound();

        this.rounds.push(round);
        this.round = round;
        this.roundIndex++;

        CvcEventManager.getInstance().fireEvent(new CvcRoundStartEvent(round));
    }

    public void stopRound() {
        CvcEventManager.getInstance().fireEvent(new CvcRoundEndEvent(this.round));

        this.round = null;
    }

    public int getRoundIndex() {
        return this.roundIndex;
    }

    @CvcEventHandler
    public void onRoundStart(CvcRoundStartEvent event) {
        ChatUtil.sendChatMessage("A round has started");

        CvcScoreboardManager.getInstance().detectPlayers();
    }

    @CvcEventHandler
    public void onRoundEnd(CvcRoundEndEvent event) {
        ChatUtil.sendChatMessage("A round has ended");
    }

    @CvcEventHandler
    public void onGameEnd(CvcGameEndEvent event) {
        this.round = null;
        this.roundIndex = 0;
        this.rounds.clear();
    }
}
