package de.throwstnt.developing.labymod.cvc.api.game.managers;

import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcGameEndEvent;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcGameStartEvent;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcRoundEndEvent;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcStateChangeEvent;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcSwitchingSidesEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerTeamChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcGame;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.State;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;

/**
 * Manages the current cvc game and handles creating and deleting it
 */
public class CvcGameManager implements CvcEventListener {

    private static CvcGameManager instance;

    public static CvcGameManager getInstance() {
        if (instance == null)
            instance = new CvcGameManager();
        return instance;
    }

    private CvcGame game;

    /**
     * True if there is a game going on
     * 
     * @return true if there is a game going on
     */
    public boolean hasGame() {
        return this.game != null;
    }

    /**
     * Returns the current game or null
     * 
     * @return the current game or null
     */
    public CvcGame getGame() {
        return this.game;
    }

    /**
     * Start a game if none is running
     */
    public void startGame() {
        if (!this.hasGame()) {
            this.game = new CvcGame();

            CvcEventManager.getInstance().fireEvent(new CvcGameStartEvent(game));
        }
    }

    /**
     * If there is a game running we stop it
     */
    public void stopGame() {
        if (this.hasGame()) {
            // we must cancel and remove the game
            if (CvcRoundManager.getInstance().hasRound()) {
                // if there is a round going on we got to cancel it
                CvcEventManager.getInstance()
                        .fireEvent(new CvcRoundEndEvent(CvcRoundManager.getInstance().getRound()));
            }

            // we fire the game end event at last
            CvcEventManager.getInstance().fireEvent(new CvcGameEndEvent(game));

            this.game = null;
        }
    }

    @CvcEventHandler
    public void onSwitchingSides(CvcSwitchingSidesEvent event) {
        if (this.game != null) {
            this.game.getTeams().forEach(team -> team.onSwitchingSides(event));
        }
    }

    @CvcEventHandler
    public void onPlayerTeamChange(CvcPlayerTeamChangedEvent event) {
        if (this.game != null) {
            this.game.getTeams().stream().filter(team -> team.equals(event.getTeam())).findFirst()
                    .ifPresent(team -> team.onPlayerTeamChange(event));
        }
    }

    @CvcEventHandler
    public void onGameStart(CvcGameStartEvent event) {
        CvcScoreboardManager.getInstance().detectPlayers();

        ChatUtil.sendChatMessage("A game has started");
    }

    @CvcEventHandler
    public void onGameEnd(CvcGameEndEvent event) {
        ChatUtil.sendChatMessage("The game has ended");
    }

    @CvcEventHandler
    public void onStateChange(CvcStateChangeEvent event) {
        if (State.isInGame(event.getNewState())) {
            // new state is in a game so lets create one and fire event
            this.startGame();
        } else if (State.isInGame(event.getOldState())) {
            this.stopGame();
        }
    }
}
