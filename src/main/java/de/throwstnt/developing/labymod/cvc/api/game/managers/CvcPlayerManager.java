package de.throwstnt.developing.labymod.cvc.api.game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractPlayerListAdapter.Profile;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcGameEndEvent;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcRoundStartEvent;
import de.throwstnt.developing.labymod.cvc.api.events.minecraft.CvcMinecraftPlayerListChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerDeathEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerJoinEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerKillEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerLeaveEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerTeamChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.events.stats.CvcStatsChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;
import de.throwstnt.developing.labymod.cvc.api.game.CvcTeam.Type;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.State;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiTableComponent.ISupplyItems;
import de.throwstnt.developing.labymod.cvc.api.util.UUIDFetcher;

public class CvcPlayerManager implements CvcEventListener {

    public static class PlayerSupplier implements ISupplyItems<CvcPlayer> {

        private Predicate<CvcPlayer> filter;

        public PlayerSupplier() {
            this((player) -> true);
        }

        public PlayerSupplier(Predicate<CvcPlayer> filter) {
            this.filter = filter;
        }

        @Override
        public List<CvcPlayer> supply() {
            List<CvcPlayer> players = CvcPlayerManager.getInstance().getPlayers();

            synchronized (players) {
                return players.stream().filter(filter).collect(Collectors.toList());
            }
        }
    }

    private static CvcPlayerManager instance;

    public static CvcPlayerManager getInstance() {
        if (instance == null)
            instance = new CvcPlayerManager();
        return instance;
    }

    public PlayerSupplier allSupplier = new PlayerSupplier();
    public PlayerSupplier copsSupplier = new PlayerSupplier(
            (player) -> player.getTeam() != null && player.getTeam().getType() == Type.COPS);
    public PlayerSupplier crimsSupplier = new PlayerSupplier(
            (player) -> player.getTeam() != null && player.getTeam().getType() == Type.CRIMS);

    private List<CvcPlayer> players;

    public CvcPlayerManager() {
        this.players = new ArrayList<>();
    }

    @CvcEventHandler
    public void onPlayerListChange(CvcMinecraftPlayerListChangedEvent event) {
        List<String> teamNames =
                CvcAddonManager.getInstance().get().getScoreboardAdapter().getTeamNames();

        // If state == game => check for team names and everyone that has a team name is a player
        Predicate<Profile> gamePredicate = (profile) -> {
            return teamNames.contains(profile.getName());
        };

        // If state == waiting => everyone is fine (no npcs)
        Predicate<Profile> waitingPredicate = (profile) -> {
            return true;
        };

        // If state == lobby => everyone with a valid uuid is good
        Predicate<Profile> lobbyPredicate = (profile) -> {
            return UUIDFetcher.isValidPlayer(profile.getUuid());
        };

        // If state == unknown => everyone with a valid uuid
        Predicate<Profile> unknownPredicate = lobbyPredicate;

        State state = CvcStateManager.getInstance().getState();
        Predicate<Profile> currentPredicate = State.isInGame(state) ? gamePredicate
                : state == State.CVC_WAITING ? waitingPredicate
                        : state == State.CVC_LOBBY ? lobbyPredicate : unknownPredicate;

        List<UUID> newUuids = event.getGameProfiles().stream()
                .map(gameProfile -> new Profile(gameProfile.getUuid(), gameProfile.getName()))
                .filter(currentPredicate).map(profile -> profile.getUuid())
                .collect(Collectors.toList());

        // gets or creates the player
        newUuids.forEach(this::getPlayer);

        synchronized (this.players) {
            if (State.isInGame(CvcStateManager.getInstance().getState())) {
                if (CvcRoundManager.getInstance().hasRound()) {
                    // players are most likely not leaving but just dead
                    this.players.stream()
                            .filter(player -> !newUuids.contains(player.getUuid())
                                    && player.getState() == CvcPlayer.State.ALIVE)
                            .forEach(player -> player.updateState(CvcPlayer.State.DEAD));
                }
            } else {
                this.players.stream().filter(player -> !newUuids.contains(player.getUuid()))
                        .forEach(player -> CvcEventManager.getInstance()
                                .fireEvent(new CvcPlayerLeaveEvent(player)));
            }
        }
    }

    @CvcEventHandler
    public void onStatsChanged(CvcStatsChangedEvent event) {
        synchronized (players) {
            players.forEach(player -> {
                if (player.getUuid() == event.getPlayer().getUuid()) {
                    player.onStatsChanged(event);
                }
            });
        }
    }

    @CvcEventHandler
    public void onKill(CvcPlayerKillEvent event) {
        synchronized (players) {
            players.forEach(player -> {
                if (player.getUuid() == event.getPlayer().getUuid()) {
                    player.onKill(event);
                }
            });
        }
    }

    @CvcEventHandler
    public void onRoundStart(CvcRoundStartEvent event) {
        synchronized (players) {
            players.forEach(player -> {
                if (CvcAddonManager.getInstance().get().getPlayerListAdapter().getUuids()
                        .contains(player.getUuid())) {
                    player.updateState(CvcPlayer.State.ALIVE);
                } else {
                    player.updateState(CvcPlayer.State.LEFT);
                }
            });
        }
    }

    @CvcEventHandler
    public void onDeath(CvcPlayerDeathEvent event) {
        synchronized (players) {
            players.forEach(player -> {
                if (player.getUuid() == event.getPlayer().getUuid()) {
                    player.onDeath(event);
                }
            });
        }
    }

    @CvcEventHandler
    public void onTeamChanged(CvcPlayerTeamChangedEvent event) {
        synchronized (players) {
            players.forEach(player -> {
                if (player.getUuid() == event.getPlayer().getUuid()) {
                    player.onTeamChanged(event);
                }
            });
        }
    }

    /**
     * Returns the player object for the given UUID
     * 
     * @param uuid the uuid
     * @return the player
     */
    public CvcPlayer getPlayer(UUID uuid) {
        synchronized (players) {
            Optional<CvcPlayer> optionalPlayer = this.players.stream()
                    .filter(player -> player != null && player.getUuid() != null
                            && player.getUuid().equals(uuid))
                    .findFirst();

            if (!optionalPlayer.isPresent()) {
                CvcPlayer player = new CvcPlayer(uuid);

                this.players.add(player);

                return player;
            } else {
                return optionalPlayer.get();
            }
        }
    }

    /**
     * Try to get a player by his name (will first try local and later external uuid)
     * 
     * @param name the name
     * @return the player or null if the name doesn't exist
     */
    public CvcPlayer getPlayer(String name) {
        UUID possibleUUID =
                CvcAddonManager.getInstance().get().getPlayerListAdapter().getUUID(name);

        // the player doesn't exist in the local tab list
        if (possibleUUID == null) {
            possibleUUID = UUIDFetcher.getUUID(name);

            // the player doesn't exist externally
            if (possibleUUID == null)
                return null;
        }

        return this.getPlayer(possibleUUID);
    }


    /**
     * Returns all the players currently stored
     * 
     * @return the players
     */
    public List<CvcPlayer> getPlayers() {
        synchronized (players) {
            return this.players;
        }
    }

    /**
     * Returns all the players from the given uuids
     * 
     * @param uuids the uuids
     * @return the players
     */
    public List<CvcPlayer> getPlayers(List<UUID> uuids) {
        return uuids.stream().map(CvcPlayerManager.getInstance()::getPlayer)
                .collect(Collectors.toList());
    }

    @CvcEventHandler
    public void onJoin(CvcPlayerJoinEvent event) {}

    @CvcEventHandler
    public void onLeave(CvcPlayerLeaveEvent event) {
        synchronized (players) {
            this.players.removeIf(player -> event.getPlayer().equals(player));
        }
    }

    @CvcEventHandler
    public void onGameEnd(CvcGameEndEvent event) {
        synchronized (players) {
            this.players.forEach(CvcPlayer::clear);
        }
    }
}
