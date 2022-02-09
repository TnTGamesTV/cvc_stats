package de.throwstnt.developing.labymod.cvc.api.gui;

import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.button;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.container;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.dynamicText;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.route;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.router;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.row;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.sized;
import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.text;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcPlayerManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcRoundManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.GuiState;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.State;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiDynamicTextComponent.ISupplyString;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiSizedComponent.Margin;
import de.throwstnt.developing.labymod.cvc.api.gui.components.container.CvcGuiRowComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.implemented.CvcPlayerListTableComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.implemented.CvcPlayerListTableComponent.PlayerListTableMode;
import de.throwstnt.developing.labymod.cvc.api.gui.implemented.CvcPlayerListTableComponent.PlayerListTableRoute;

public class ImplementedCvcInterface extends AbstractCvcInterface {

    public ImplementedCvcInterface() {
        super(router(route("/", _makeIndex()), route("/overview", _makeOverview()),
                route("/main", _makeMain()), route("/defusal", _makeDefusal()),
                route("/deathmatch", _makeDeathmatch()), route("/debug", _makeDebug())));
    }

    /**
     * -> /: menu -> /main: main stats -> /defusal: defusal game stats -> /deathmatch: deathmatch
     * game stats -> /overview: other stats
     */

    private static CvcGuiRowComponent _makeMenuRow() {
        return row(button(new Margin(5), 200, 20, "Change view", (button) -> {
            GuiState guiState = CvcStateManager.getInstance().getGuiState();

            if (guiState == GuiState.MAIN_STATS) {
                CvcStateManager.getInstance().setGuiState(GuiState.GAME_STATS);
            } else {
                CvcStateManager.getInstance().setGuiState(GuiState.MAIN_STATS);
            }
        }));
    }

    private static CvcGuiComponent _makeMain() {
        return sized(new Margin(15, 100),
                container(_makeMenuRow(), row(sized(new Margin(4),
                        dynamicText(
                                () -> "§7Round §9"
                                        + (CvcRoundManager.getInstance().getRoundIndex() + 1)
                                        + "§7/§99"))),
                        row(new CvcPlayerListTableComponent(PlayerListTableMode.COPS,
                                PlayerListTableRoute.MAIN_STATS)),
                        row(sized(new Margin(4), dynamicText(() -> "§7Total §3cops§7: §f"
                                + CvcPlayerManager.getInstance().copsSupplier.supply().size())),
                                sized(new Margin(4), dynamicText(() -> "§7Score: §f" + 10000))),
                        row(new CvcPlayerListTableComponent(PlayerListTableMode.CRIMS,
                                PlayerListTableRoute.MAIN_STATS)),
                        row(sized(new Margin(4), dynamicText(() -> "§7Total §4crims§7: §f"
                                + CvcPlayerManager.getInstance().crimsSupplier.supply().size())),
                                sized(new Margin(4), dynamicText(() -> "§7Score: §f" + 10000)))));
    }

    private static CvcGuiComponent _makeDefusal() {
        return sized(new Margin(15, 100),
                container(_makeMenuRow(), row(sized(new Margin(4),
                        dynamicText(
                                () -> "§7Round §9"
                                        + (CvcRoundManager.getInstance().getRoundIndex() + 1)
                                        + "§7/§99"))),
                        row(new CvcPlayerListTableComponent(PlayerListTableMode.COPS,
                                PlayerListTableRoute.DEFUSAL_GAME_STATS)),
                        row(sized(new Margin(4), dynamicText(() -> "§7Total §3cops§7: §f"
                                + CvcPlayerManager.getInstance().copsSupplier.supply().size())),
                                sized(new Margin(4), dynamicText(() -> "§7Score: §f" + 10000))),
                        row(new CvcPlayerListTableComponent(PlayerListTableMode.CRIMS,
                                PlayerListTableRoute.DEFUSAL_GAME_STATS)),
                        row(sized(new Margin(4), dynamicText(() -> "§7Total §4crims§7: §f"
                                + CvcPlayerManager.getInstance().crimsSupplier.supply().size())),
                                sized(new Margin(4), dynamicText(() -> "§7Score: §f" + 10000)))));
    }

    private static CvcGuiComponent _makeDeathmatch() {
        return sized(new Margin(15, 100),
                container(_makeMenuRow(),
                        row(new CvcPlayerListTableComponent(PlayerListTableMode.COPS,
                                PlayerListTableRoute.DEATHMATCH_GAME_STATS)),
                        row(sized(new Margin(4),
                                dynamicText(() -> "§7Total §3cops§7: §f"
                                        + CvcPlayerManager.getInstance().getPlayers().size())),
                                sized(new Margin(4), dynamicText(() -> "§7Score: §f" + 10000))),
                        row(new CvcPlayerListTableComponent(PlayerListTableMode.CRIMS,
                                PlayerListTableRoute.DEATHMATCH_GAME_STATS)),
                        row(sized(new Margin(4),
                                dynamicText(() -> "§7Total §4crims§7: §f"
                                        + CvcPlayerManager.getInstance().getPlayers().size())),
                                sized(new Margin(4), dynamicText(() -> "§7Score: §f" + 10000)))));
    }

    private static CvcGuiComponent _makeOverview() {
        return sized(new Margin(15, 100),
                container(row(new CvcPlayerListTableComponent()),
                        row(sized(new Margin(4), dynamicText(() -> "§7Total players: §f"
                                + CvcPlayerManager.getInstance().getPlayers().size())))));
    }

    private static CvcGuiComponent _makeIndex() {
        return container(row(button(new Margin(5), 200, 20, "Change route again", (button) -> {
            if (State.isInGame(CvcStateManager.getInstance().getState())) {
                CvcStateManager.getInstance().setGuiState(GuiState.MAIN_STATS);
            } else {
                CvcAddonManager.getInstance().get().getGuiAdapter().setRoute("/overview");
            }
        })), row(button(new Margin(5), 200, 20, "Debug", (button) -> {
            CvcAddonManager.getInstance().get().getGuiAdapter().setRoute("/debug");
        })));
    }

    private static CvcGuiComponent _makeDebug() {
        Map<String, ISupplyString> debugComponentMap = new HashMap<>();

        debugComponentMap.put("playerList.count",
                () -> "" + CvcPlayerManager.getInstance().getPlayers().size());

        debugComponentMap.put("state", () -> "" + CvcStateManager.getInstance().getState());

        return container(debugComponentMap.entrySet().stream()
                .map(entry -> row(text(entry.getKey()), dynamicText(entry.getValue())))
                .collect(Collectors.toList()).toArray(new CvcGuiRowComponent[0]));
    }
}
