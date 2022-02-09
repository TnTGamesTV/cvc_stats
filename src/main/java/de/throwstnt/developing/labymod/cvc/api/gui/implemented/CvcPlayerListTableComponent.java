package de.throwstnt.developing.labymod.cvc.api.gui.implemented;

import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.item;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.playerHead;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.sized;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.text;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem;
import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;
import de.throwstnt.developing.labymod.cvc.api.game.CvcTeam;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcPlayerManager;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiSizedComponent.Margin;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiColumnComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiColumnComponent.IGetComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiTableComponent;
import de.throwstnt.developing.labymod.cvc.api.util.InferUtil;
import de.throwstnt.developing.labymod.cvc.api.util.SymbolLibrary;

public class CvcPlayerListTableComponent extends CvcGuiTableComponent<CvcPlayer> {

    public enum PlayerListTableMode {
        ALL, COPS, CRIMS;

        public ISupplyItems<CvcPlayer> getSupplier() {
            if (this == COPS)
                return CvcPlayerManager.getInstance().copsSupplier;
            if (this == CRIMS)
                return CvcPlayerManager.getInstance().crimsSupplier;
            return CvcPlayerManager.getInstance().allSupplier;
        }
    }

    public enum PlayerListTableRoute {
        OTHER, DEFUSAL_GAME_STATS, DEATHMATCH_GAME_STATS, MAIN_STATS,
    }

    private PlayerListTableMode mode;

    private PlayerListTableRoute route;

    public CvcPlayerListTableComponent() {
        this(PlayerListTableMode.ALL, PlayerListTableRoute.OTHER);
    }

    public CvcPlayerListTableComponent(PlayerListTableMode mode) {
        this(mode, PlayerListTableRoute.OTHER);
    }

    public CvcPlayerListTableComponent(PlayerListTableRoute route) {
        this(PlayerListTableMode.ALL, route);
    }

    public CvcPlayerListTableComponent(PlayerListTableMode mode, PlayerListTableRoute route) {
        super(_make(route), mode.getSupplier());

        this.mode = mode;
        this.route = route;
    }

    @Override
    public Comparator<CvcPlayer> sort() {
        return (a, b) -> {
            if (a.getStats() != null && b.getStats() != null) {
                return Integer.compare(b.getStats().getScore(), a.getStats().getScore());
            }
            return 0;
        };
    }

    private static List<CvcGuiColumnComponent<CvcPlayer>> _make(PlayerListTableRoute route) {
        switch (route) {
            case DEATHMATCH_GAME_STATS:
                return _makeColumnsDeathmatchGameStats();
            case DEFUSAL_GAME_STATS:
                return _makeColumnsDefusalGameStats();
            case MAIN_STATS:
                return _makeColumnsGameMainStats();
            case OTHER:
                return _makeColumnsForLobbyAndWaitingAndUnknown();
            default:
                return _makeColumnsForLobbyAndWaitingAndUnknown();
        }
    }

    private static List<CvcGuiColumnComponent<CvcPlayer>> _makeColumnsForLobbyAndWaitingAndUnknown() {
        return Arrays.asList(_makeHeadColumn(), _makeNameColumn(), _makeScoreColumn(),
                _makeKillsColumn(), _makeHSColumn(), _makeAssistsColumn(), _makeDeathsColumn(),
                _makeKDColumn(), _makeHSKColumn(), _makePrimaryWeaponColumn(),
                _makeSecondaryWeaponColumn());
    }

    private static List<CvcGuiColumnComponent<CvcPlayer>> _makeColumnsDefusalGameStats() {
        return Arrays.asList(_makeHeadColumn(), _makeNameColumn(), _makeTeamColumn(),
                _makeStateColumn(), _makeScoreColumn(), _makeGameKillsColumn(),
                _makeGameHeadshotsColumn(), _makeGameDeathsColumn(), _makeGameKDColumn(),
                _makeGameHSKColumn(), _makePrimaryWeaponColumn(), _makeSecondaryWeaponColumn());
    }

    private static List<CvcGuiColumnComponent<CvcPlayer>> _makeColumnsGameMainStats() {
        return Arrays.asList(_makeHeadColumn(), _makeNameColumn(), _makeTeamColumn(),
                _makeStateColumn(), _makeScoreColumn(), _makeKillsColumn(), _makeHSColumn(),
                _makeAssistsColumn(), _makeDeathsColumn(), _makeKDColumn(), _makeHSKColumn(),
                _makePrimaryWeaponColumn(), _makeSecondaryWeaponColumn());
    }

    private static List<CvcGuiColumnComponent<CvcPlayer>> _makeColumnsDeathmatchGameStats() {
        return Arrays.asList(_makeHeadColumn(), _makeNameColumn(), _makeTeamColumn(),
                _makeStateColumn(), _makeScoreColumn(), _makeGamePointsColumn(),
                _makeGameKillsColumn(), _makeGameHeadshotsColumn(), _makeGameDeathsColumn(),
                _makeGameKDColumn(), _makeGameHSKColumn(), _makePrimaryWeaponColumn(),
                _makeSecondaryWeaponColumn());
    }

    private static CvcGuiColumnComponent.Builder<CvcPlayer> column() {
        return InferUtil.<CvcPlayer>column();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeHeadColumn() {
        return column().title(text(""))
                .components(player -> sized(new Margin(4), playerHead(player.getUuid(), 20)))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeNameColumn() {
        return column().title(text("Name"))
                .components(player -> text(player.getRankPrefix() + " " + player.getName()))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeStateColumn() {
        String aliveState = "§aAlive";
        String deadState = "§cDead";
        String leftState = "§7Left";
        String nothing = "§8-";

        return column().title(text("State"))
                .components(player -> text(player.getState() == CvcPlayer.State.ALIVE ? aliveState
                        : player.getState() == CvcPlayer.State.DEAD ? deadState
                                : player.getState() == CvcPlayer.State.LEFT ? leftState : nothing))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeTeamColumn() {
        String cops = "§3" + SymbolLibrary.COP_SYMBOL;
        String crims = "§4" + SymbolLibrary.CRIM_SYMBOL;
        String nothing = "§8-";

        return column().title(text("Team")).components(player -> {
            if (player.getTeam() != null) {
                if (player.getTeam().getType() == CvcTeam.Type.COPS) {
                    return text(cops);
                }

                if (player.getTeam().getType() == CvcTeam.Type.CRIMS) {
                    return text(crims);
                }
            }
            return text(nothing);
        }).build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeGamePointsColumn() {
        return column().title(text("Points")).components(player -> text(
                player.getGameStats() == null ? "§8-" : "" + player.getGameStats().getPoints()))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeGameKillsColumn() {
        return column().title(text("Kills")).components(player -> text(
                player.getGameStats() == null ? "§8-" : "" + player.getGameStats().getKills()))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeGameHeadshotsColumn() {
        return column().title(text("HS")).components(player -> text(
                player.getGameStats() == null ? "§8-" : "" + player.getGameStats().getHeadshots()))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeGameDeathsColumn() {
        return column().title(text("Deaths")).components(player -> text(
                player.getGameStats() == null ? "§8-" : "" + player.getGameStats().getDeaths()))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeGameKDColumn() {
        return column().title(text("K/D")).components(player -> text(player.getGameStats() == null
                ? "§8-"
                : "" + String.format("%.2f", player.getGameStats().getVirtualKillDeathRatio())))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeGameHSKColumn() {
        return column().title(text("HS/K"))
                .components(player -> text(player.getGameStats() == null ? "§8-"
                        : "" + NumberFormat.getPercentInstance()
                                .format(player.getGameStats().getVirtualHeadshotAccuracy())))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeScoreColumn() {
        return column().title(text("Score")).components(player -> text(player.getStats() == null
                ? "§8-"
                : "" + player.getStats().getColorCodeFromScore() + player.getStats().getScore()))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeKillsColumn() {
        return column().title(text("Kills")).components(
                player -> text(player.getStats() == null ? "§8-" : "" + player.getStats().kills))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeHSColumn() {
        return column().title(text("HS"))
                .components(player -> text(
                        player.getStats() == null ? "§8-" : "" + player.getStats().headshotKills))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeAssistsColumn() {
        return column().title(text("Assists")).components(
                player -> text(player.getStats() == null ? "§8-" : "" + player.getStats().assists))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeDeathsColumn() {
        return column().title(text("Deaths")).components(
                player -> text(player.getStats() == null ? "§8-" : "" + player.getStats().deaths))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeKDColumn() {
        return column().title(text("K/D"))
                .components(player -> text(player.getStats() == null ? "§8-"
                        : "" + String.format("%.2f", player.getStats().virtualKillDeathRatio)))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeHSKColumn() {
        return column().title(text("HS/K"))
                .components(
                        player -> text(
                                player.getStats() == null ? "§8-"
                                        : "" + NumberFormat.getPercentInstance()
                                                .format(player.getStats().virtualHeadshotRatio)))
                .build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makePrimaryWeaponColumn() {
        return column().title(text("#1")).components(_makeWeaponGetter(true)).build();
    }

    private static CvcGuiColumnComponent<CvcPlayer> _makeSecondaryWeaponColumn() {
        return column().title(text("#2")).components(_makeWeaponGetter(false)).build();
    }

    private static IGetComponent<CvcPlayer> _makeWeaponGetter(boolean isPrimary) {
        return player -> {
            if (player.getStats() != null) {
                WeaponType weaponType = (isPrimary ? player.getStats().possiblePrimaryWeapon
                        : player.getStats().possibleSecondaryWeapon).type;

                return item(weaponType.getItemType());
            }

            return item(AbstractItem.ItemStackType.BARRIER);
        };
    }

    public PlayerListTableMode getMode() {
        return mode;
    }

    public PlayerListTableRoute getRoute() {
        return route;
    }
}
