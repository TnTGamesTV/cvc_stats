package de.throwstnt.developing.labymod.cvc.api.events.minecraft;

import java.util.List;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractPlayerListAdapter.Profile;

/**
 * Triggered whenever the player list changes aka when a player joins or leaves the server
 */
public class CvcMinecraftPlayerListChangedEvent extends CvcMinecraftEvent {

    private List<Profile> gameProfiles;

    public CvcMinecraftPlayerListChangedEvent(List<Profile> gameProfiles) {
        this.gameProfiles = gameProfiles;
    }

    public List<Profile> getGameProfiles() {
        return gameProfiles;
    }
}
