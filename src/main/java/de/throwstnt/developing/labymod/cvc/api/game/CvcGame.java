package de.throwstnt.developing.labymod.cvc.api.game;

import java.util.ArrayList;
import java.util.List;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcPlayerManager;

public class CvcGame {

    private List<CvcRound> rounds;

    private List<CvcPlayer> players;

    private List<CvcTeam> teams;

    private CvcRound currentRound;

    /**
     * When the cvc game was started in milliseconds
     */
    private long startedAt;

    public CvcGame() {
        this.rounds = new ArrayList<>();
        this.teams = new ArrayList<>();

        this.players = CvcPlayerManager.getInstance().getPlayers();

        this._createTeams();
    }

    /**
     * Creates two teams
     */
    private void _createTeams() {
        CvcTeam crimTeam = new CvcTeam(CvcTeam.Type.CRIMS);
        CvcTeam copTeam = new CvcTeam(CvcTeam.Type.COPS);

        this.teams.add(crimTeam);
        this.teams.add(copTeam);
    }

    public CvcRound getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(CvcRound currentRound) {
        this.currentRound = currentRound;
    }

    public List<CvcRound> getRounds() {
        return rounds;
    }

    public List<CvcPlayer> getPlayers() {
        return players;
    }

    public List<CvcTeam> getTeams() {
        return teams;
    }

    public long getStartedAt() {
        return startedAt;
    }
}
