package de.throwstnt.developing.labymod.cvc.api.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractPlayerListAdapter.Profile;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.minecraft.CvcMinecraftPlayedSoundEvent;
import de.throwstnt.developing.labymod.cvc.api.events.minecraft.CvcMinecraftPlayerListChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;

/**
 * This adapter handles all inbound packets
 */
public class ImplementedInboundPacketAdapter<GameProfileType> {

    /**
     * Handles a sound packet from the server
     * 
     * @param soundName the name
     * @param x the x
     * @param y the y
     * @param z the z
     * @param pitch the pitch
     * @param volume the volume
     */
    public void handleSoundPacket(String soundName, double x, double y, double z, float pitch,
            float volume) {
        CvcEventManager.getInstance().fireEvent(new CvcMinecraftPlayedSoundEvent(soundName, (int) x,
                (int) y, (int) z, volume, pitch));
    }

    private List<Profile> _cachedPlayerList = new ArrayList<>();

    /**
     * Handles a player list change packet
     */
    public void handlePlayerListChanged() {
        List<UUID> possibleNewPlayerList =
                CvcAddonManager.getInstance().get().getPlayerListAdapter().getUuids();

        // find all the players that are already cached
        long overlapSize = this._cachedPlayerList.stream().map(profile -> profile.getUuid())
                .filter(possibleNewPlayerList::contains).count();


        if (overlapSize != possibleNewPlayerList.size()) {
            CvcEventManager.getInstance()
                    .fireEvent(new CvcMinecraftPlayerListChangedEvent(possibleNewPlayerList.stream()
                            .map(uuid -> new Profile(uuid, CvcAddonManager.getInstance().get()
                                    .getPlayerListAdapter().getName(uuid)))
                            .collect(Collectors.toList())));
        }
    }
}
