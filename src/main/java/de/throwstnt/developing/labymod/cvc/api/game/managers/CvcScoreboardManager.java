package de.throwstnt.developing.labymod.cvc.api.game.managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractScoreboardAdapter.ScoreboardTeam;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerJoinEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerTeamChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.events.stats.CvcPlayerStatsEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;
import de.throwstnt.developing.labymod.cvc.api.game.CvcTeam;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;
import de.throwstnt.developing.labymod.cvc.api.util.SymbolLibrary;

/**
 * This manager handles assigning teams and existing ingame stats
 */
public class CvcScoreboardManager implements CvcEventListener {

    private static final Pattern CONST_DEFUSAL_PATTERN =
            Pattern.compile("\\[(\\d{1,})\\-(\\d{1,})\\]");

    private static final Pattern CONST_TDM_PATTERN = Pattern.compile("\\[(\\d{1,})\\]");

    private static CvcScoreboardManager instance;

    public static CvcScoreboardManager getInstance() {
        if (instance == null)
            instance = new CvcScoreboardManager();
        return instance;
    }

    @CvcEventHandler
    public void onPlayerJoin(CvcPlayerJoinEvent event) {
        this.detectPlayer(event.getPlayer());
    }

    /**
     * Detect for all players
     */
    public void detectPlayers() {
        if (CvcGameManager.getInstance().hasGame()) {
            CvcGameManager.getInstance().getGame().getPlayers()
                    .forEach(player -> this.detectPlayer(player));
        }
    }

    /**
     * Detect for the given player
     * 
     * @param player the player
     */
    public void detectPlayer(CvcPlayer player) {
        // try init the player if he is not dirty (aka has not been init)
        player.init();

        if (!CvcGameManager.getInstance().hasGame())
            return;

        ScoreboardTeam possibleTeam = CvcAddonManager.getInstance().get().getScoreboardAdapter()
                .getTeams().stream().filter(filterTeam -> filterTeam.name.equals(player.getName()))
                .findFirst().orElse(null);

        if (possibleTeam != null) {
            CvcTeam.Type possibleTeamType = this._typeFromPrefix(possibleTeam.prefix);

            if (CvcGameManager.getInstance().hasGame() && possibleTeamType != null) {
                CvcTeam team = CvcGameManager.getInstance().getGame().getTeams().stream()
                        .filter(filterTeam -> filterTeam.getType().equals(possibleTeamType))
                        .findFirst().orElse(null);

                if (team != null) {
                    Matcher defusalMatcher = CONST_DEFUSAL_PATTERN
                            .matcher(ChatUtil.cleanColorCoding(possibleTeam.suffix));

                    if (defusalMatcher.find()) {
                        int kills = Integer.parseInt(defusalMatcher.group(1));
                        int deaths = Integer.parseInt(defusalMatcher.group(2));

                        player.getGameStats().setKills(kills);
                        player.getGameStats().setDeaths(deaths);

                        CvcEventManager.getInstance().fireEvent(new CvcPlayerStatsEvent(player));
                    } else {
                        Matcher tdmMatcher = CONST_TDM_PATTERN
                                .matcher(ChatUtil.cleanColorCoding(possibleTeam.suffix));

                        if (tdmMatcher.find()) {
                            int points = Integer.parseInt(tdmMatcher.group(1));

                            player.getGameStats().setPoints(points);

                            CvcEventManager.getInstance()
                                    .fireEvent(new CvcPlayerStatsEvent(player));
                        }
                    }

                    CvcEventManager.getInstance()
                            .fireEvent(new CvcPlayerTeamChangedEvent(player, team));
                }
            }
        }
    }

    /**
     * Gets the team type based on a given prefix
     * 
     * @param prefix the prefix
     * @return the team type or null
     */
    private CvcTeam.Type _typeFromPrefix(String prefix) {
        if (prefix.contains(SymbolLibrary.COP_SYMBOL))
            return CvcTeam.Type.COPS;
        if (prefix.contains(SymbolLibrary.CRIM_SYMBOL))
            return CvcTeam.Type.CRIMS;
        return null;
    }
}
