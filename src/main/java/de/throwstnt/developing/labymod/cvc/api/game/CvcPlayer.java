package de.throwstnt.developing.labymod.cvc.api.game;

import java.util.UUID;
import de.throwstnt.developing.labymod.cvc.api.data.stats.CvcStats;
import de.throwstnt.developing.labymod.cvc.api.data.stats.game.CvcGameStats;
import de.throwstnt.developing.labymod.cvc.api.data.stats.game.CvcWeaponGameStats;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerDeathEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerJoinEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerKillEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerStateChangeEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerTeamChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.events.stats.CvcGameStatsChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.events.stats.CvcStatsChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.util.UUIDFetcher;

public class CvcPlayer implements CvcEventListener {

    public enum State {
        ALIVE, DEAD, LEFT, UNKNOWN;
    }

    /**
     * The uuid of the player
     */
    private UUID uuid;

    /**
     * The cached name of a player
     */
    private String possibleName;

    /**
     * The rank prefix
     */
    private String rankPrefix = "§8";

    /**
     * The global stats of the player
     */
    private CvcStats stats;

    /**
     * The game stats of the player
     */
    private CvcGameStats gameStats;

    /**
     * The team of the player
     */
    private CvcTeam team;

    /**
     * The state of the player
     */
    private CvcPlayer.State state;

    private boolean dirty = false;

    public CvcPlayer(UUID uuid) {
        this.uuid = uuid;

        this.init();

        CvcEventManager.getInstance().fireEvent(new CvcPlayerJoinEvent(this));
    }

    /**
     * Is called on a player if he might be reused (most of the time only used for the player using
     * the addon)
     */
    public void init() {
        if (!this.dirty) {
            this.state = State.UNKNOWN;
            this.gameStats = new CvcGameStats();

            this.dirty = true;
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the players name (either from player list if online or mojang api)
     * 
     * @return the players name
     */
    public String getName() {
        if (this.possibleName == null) {
            if (this.uuid == CvcAddonManager.getInstance().get().getPlayerListAdapter()
                    .getCurrentUUID()) {
                this.possibleName =
                        CvcAddonManager.getInstance().get().getPlayerListAdapter().getCurrentName();
            } else {
                this.possibleName = CvcAddonManager.getInstance().get().getPlayerListAdapter()
                        .getName(this.uuid);

                if (this.possibleName == null) {
                    this.possibleName = UUIDFetcher.getName(this.uuid);
                }
            }
        }

        return this.possibleName;
    }

    public CvcPlayer.State getState() {
        return state;
    }

    public CvcGameStats getGameStats() {
        return gameStats;
    }

    public CvcStats getStats() {
        return stats;
    }

    public CvcTeam getTeam() {
        return team;
    }

    public void onStatsChanged(CvcStatsChangedEvent event) {
        this.stats = event.getStats();

        this.rankPrefix = this.stats.rankPrefix;

        if (this.gameStats != null) {
            // we already have gameStats so we must copy a bit
            CvcGameStats newGameStats = new CvcGameStats();

            newGameStats.setDeaths(this.gameStats.getDeaths());
            newGameStats.setKills(this.gameStats.getKills());
            newGameStats.setHeadshots(this.gameStats.getHeadshots());

            this.gameStats = newGameStats;

            CvcEventManager.getInstance()
                    .fireEvent(new CvcGameStatsChangedEvent(this, this.gameStats));
        } else {
            this.gameStats = new CvcGameStats();

            CvcEventManager.getInstance()
                    .fireEvent(new CvcGameStatsChangedEvent(this, this.gameStats));
        }
    }

    public void onKill(CvcPlayerKillEvent event) {
        // player killed someone
        if (event.getWeaponType() != null) {
            // is actually a gun or knife
            CvcWeaponGameStats weaponStat = this.gameStats.getWeaponStats().stream().filter(
                    filteredWeaponStat -> filteredWeaponStat.type.equals(event.getWeaponType()))
                .findFirst().orElse(null);

            if (weaponStat != null) {
        weaponStat.kills++;

                if (event.isHeadshot()) {
                    weaponStat.headshots++;
                }
            }
        }

        // set the game kills (and maybe headshots)
        this.gameStats.setKills(this.gameStats.getKills() + 1);

        if (event.isHeadshot()) {
            this.gameStats.setHeadshots(this.gameStats.getHeadshots() + 1);
        }

        // update the virtuals
        this.gameStats.updateVirtuals();

        CvcEventManager.getInstance().fireEvent(new CvcGameStatsChangedEvent(this, this.gameStats));
    }

    public void onDeath(CvcPlayerDeathEvent event) {
        this.gameStats.setDeaths(this.gameStats.getDeaths() + 1);
        this.gameStats.updateVirtuals();

        CvcEventManager.getInstance().fireEvent(new CvcGameStatsChangedEvent(this, this.gameStats));

        this.updateState(State.DEAD);
    }

    public void onTeamChanged(CvcPlayerTeamChangedEvent event) {
        this.team = event.getTeam();
    }

    /**
     * Updates the state of the player
     * 
     * @param newState the new state
     */
    public void updateState(CvcPlayer.State newState) {
        CvcPlayer.State oldState = this.state;

        this.state = newState;

        CvcEventManager.getInstance()
                .fireEvent(new CvcPlayerStateChangeEvent(this, oldState, newState));
    }

    public String getRankPrefix() {
        return rankPrefix;
    }

    public void clear() {
        this.gameStats = null;
        this.team = null;
        this.state = null;

        this.dirty = false;
    }
}
