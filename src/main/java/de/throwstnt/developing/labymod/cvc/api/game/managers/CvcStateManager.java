package de.throwstnt.developing.labymod.cvc.api.game.managers;

import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcStateChangeEvent;
import de.throwstnt.developing.labymod.cvc.api.util.SymbolLibrary;

/**
 * Manages the state of the addon
 */
public class CvcStateManager {

    public static enum State {
        UNKNOWN, CVC_LOBBY, CVC_WAITING, CVC_GAME_DEFUSAL, CVC_GAME_TDM;

        public static boolean isInGame(State state) {
            return state == CVC_GAME_DEFUSAL || state == CVC_GAME_TDM;
        }
    }

    public static enum GuiState {
        MAIN_STATS, GAME_STATS,
    }

    private static CvcStateManager instance;

    public static CvcStateManager getInstance() {
        if (instance == null)
            instance = new CvcStateManager();
        return instance;
    }

    private GuiState guiState = GuiState.MAIN_STATS;

    private State currentState = State.UNKNOWN;

    private CvcStateManager() {}

    /**
     * Checks the state and fires an event if it has changed
     */
    public void checkState() {
        if (CvcAddonManager.getInstance().get().isConnectedToHypixel()) {
            State possibleNewState = this._getState();

            this.updateState(possibleNewState);
        }
    }

    /**
     * Get the current state
     * 
     * @return the current state
     */
    public State getState() {
        return this.currentState;
    }

    /**
     * Updates the state if it has changed
     * 
     * @param possibleNewState the possible new state
     */
    public void updateState(State possibleNewState) {
        if (this.currentState != possibleNewState) {
            CvcEventManager.getInstance()
                    .fireEvent(new CvcStateChangeEvent(currentState, possibleNewState));

            this.currentState = possibleNewState;
        }
    }

    private State _getState() {
        String title = CvcAddonManager.getInstance().get().getScoreboardAdapter().getTitle();
        String content = CvcAddonManager.getInstance().get().getScoreboardAdapter().getContent();

        boolean isInCvc = title.contains("COPS AND CRIMS") || title.contains("TEAM DEATHMATCH")
                || title.contains(SymbolLibrary.COP_SYMBOL);

        if (isInCvc) {
            if (content != null) {
                // is in cvc lobby
                if (content.contains("Coins:"))
                    return State.CVC_LOBBY;

                // is waiting for game to start
                if (content.contains("Mode:"))
                    return State.CVC_WAITING;

                // is in game (defusal)
                if (content.contains("Armor:"))
                    return State.CVC_GAME_DEFUSAL;

                // is in game (tdm)
                if (content.contains("Points:"))
                    return State.CVC_GAME_TDM;
            }
        }

        return State.UNKNOWN;
    }

    public GuiState getGuiState() {
        return guiState;
    }

    public void setGuiState(GuiState guiState) {
        if (State.isInGame(currentState)) {
            this.guiState = guiState;

            if (this.guiState == GuiState.MAIN_STATS) {
                CvcAddonManager.getInstance().get().getGuiAdapter().setRoute("/main");
            } else {
                if (this.currentState == State.CVC_GAME_DEFUSAL) {
                    CvcAddonManager.getInstance().get().getGuiAdapter().setRoute("/defusal");
                } else {
                    CvcAddonManager.getInstance().get().getGuiAdapter().setRoute("/deathmatch");
                }
            }
        }
    }
}
