package de.throwstnt.developing.labymod.cvc.implemented.adapters;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractPlayerListAdapter;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcGameManager;
import net.minecraft.client.Minecraft;

public class ImplementedPlayerListAdapter extends AbstractPlayerListAdapter<GameProfile> {

    @Override
    public String getName(UUID uuid) {
        if (Minecraft.getInstance() != null) {
            if (Minecraft.getInstance().getConnection() != null) {
                if (Minecraft.getInstance().getConnection().getPlayerInfoMap() != null) {
                    List<GameProfile> gameProfiles =
                            Minecraft.getInstance().getConnection().getPlayerInfoMap().stream()
                                    .map(playerInfo -> playerInfo.getGameProfile())
                                    .collect(Collectors.toList());



                    synchronized (gameProfiles) {

                        return gameProfiles.stream()
                                .filter(gameProfile -> gameProfile.getId().equals(uuid))
                                .map(GameProfile::getName).findFirst().orElse(null);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<UUID> getUuids() {
        if (Minecraft.getInstance() != null) {
            if (Minecraft.getInstance().getConnection() != null) {
                if (Minecraft.getInstance().getConnection().getPlayerInfoMap() != null) {
                    List<GameProfile> gameProfiles =
                            Minecraft.getInstance().getConnection().getPlayerInfoMap().stream()
                                    .map(playerInfo -> playerInfo.getGameProfile())
                                    .collect(Collectors.toList());

                    synchronized (gameProfiles) {
                        List<UUID> uuids =
                                gameProfiles.stream().map(gameProfile -> gameProfile.getId())
                                        .collect(Collectors.toList());

                        if (CvcGameManager.getInstance().hasGame()) {
                            CvcGameManager.getInstance().getGame().getPlayers()
                                    .forEach(playingPlayer -> {
                                        if (!uuids.contains(playingPlayer.getUuid())) {
                                            uuids.add(playingPlayer.getUuid());
                                        }
                                    });
                        }


                        return uuids;
                    }
                }
            }
        }
        return Lists.newArrayList();
    }

    @SuppressWarnings("resource")
    @Override
    public UUID getCurrentUUID() {
        if (Minecraft.getInstance() != null) {
            if (Minecraft.getInstance().getConnection() != null) {
                return Minecraft.getInstance().player.getGameProfile().getId();
            }
        }
        return null;
    }

    @SuppressWarnings("resource")
    @Override
    public String getCurrentName() {
        if (Minecraft.getInstance() != null) {
            if (Minecraft.getInstance().getConnection() != null) {
                return Minecraft.getInstance().player.getGameProfile().getName();
            }
        }
        return null;
    }

    @Override
    public UUID getUUID(String name) {
        if (Minecraft.getInstance() != null) {
            if (Minecraft.getInstance().getConnection() != null) {
                if (Minecraft.getInstance().getConnection().getPlayerInfoMap() != null) {
                    List<GameProfile> gameProfiles =
                            Minecraft.getInstance().getConnection().getPlayerInfoMap().stream()
                                    .map(playerInfo -> playerInfo.getGameProfile())
                                    .collect(Collectors.toList());



                    synchronized (gameProfiles) {

                        return gameProfiles.stream()
                                .filter(profile -> profile.getName().equals(name))
                                .map(profile -> profile.getId()).findFirst().orElse(null);
                    }
                }
            }
        }
        return null;
    }
}
