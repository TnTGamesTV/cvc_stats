package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;
import de.throwstnt.developing.labymod.cvc.api.game.CvcTeam;

/**
 * Gets triggered when the player either gets a new team or data of the his current changes
 */
public class CvcPlayerTeamChangedEvent extends CvcPlayerEvent {

    private CvcTeam team;

    public CvcPlayerTeamChangedEvent(CvcPlayer player, CvcTeam team) {
        super(player);

        this.team = team;
    }

    public CvcTeam getTeam() {
        return team;
    }
}
