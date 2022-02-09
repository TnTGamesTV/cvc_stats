package de.throwstnt.developing.labymod.cvc.api.stats;

import de.throwstnt.developing.labymod.cvc.api.data.ApiCoordinator;
import de.throwstnt.developing.labymod.cvc.api.data.stats.CvcStats;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerJoinEvent;
import de.throwstnt.developing.labymod.cvc.api.events.stats.CvcStatsChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.util.RankUtil;

public class CvcStatsManager implements CvcEventListener {

    private static CvcStatsManager instance;

    public static CvcStatsManager getInstance() {
        if (instance == null)
            instance = new CvcStatsManager();
        return instance;
    }

    @CvcEventHandler
    public void onPlayerJoin(CvcPlayerJoinEvent event) {
        if (event.getPlayer().getStats() == null) {
            // if the player does not have any stats we will get him some
            ApiCoordinator.getPlayer(event.getPlayer().getUuid(), reply -> {
                if (reply != null) {
                    String rankPrefix = RankUtil.prefixFromPlayerObject(reply.getPlayer());
                    CvcStats stats = CvcStats.fromReply(reply);

                    CvcEventManager.getInstance().fireEvent(
                            new CvcStatsChangedEvent(event.getPlayer(), stats, rankPrefix));
                }
            });
        }
    }
}
