package de.throwstnt.developing.labymod.cvc.api.game;

import java.util.ArrayList;
import java.util.List;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcSwitchingSidesEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerTeamChangedEvent;

public class CvcTeam {

    public enum Type {
        COPS, CRIMS;
    }

    private CvcTeam.Type type;

    private List<CvcPlayer> players;

    public CvcTeam(CvcTeam.Type type) {
        this.type = type;
        this.players = new ArrayList<>();
    }

    public CvcTeam.Type getType() {
        return type;
    }

    public List<CvcPlayer> getPlayers() {
        return players;
    }

    public void onSwitchingSides(CvcSwitchingSidesEvent event) {
        if (this.type == Type.COPS) {
            this.type = Type.CRIMS;
        } else {
            this.type = Type.COPS;
        }
    }

    public void onPlayerTeamChange(CvcPlayerTeamChangedEvent event) {
        if (event.getTeam() == this) {
            if (!this.players.contains(event.getPlayer())) {
                this.players.add(event.getPlayer());
            }
        } else if (event.getTeam() != this) {
            if (this.players.contains(event.getPlayer())) {
                this.players.remove(event.getPlayer());
            }
        }
    }
}
