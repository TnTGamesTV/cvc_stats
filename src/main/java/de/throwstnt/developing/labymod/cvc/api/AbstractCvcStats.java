package de.throwstnt.developing.labymod.cvc.api;

import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractGuiAdapter;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractPlayerListAdapter;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractScoreboardAdapter;
import de.throwstnt.developing.labymod.cvc.api.adapters.ImplementedInboundPacketAdapter;
import de.throwstnt.developing.labymod.cvc.api.adapters.ImplementedServerAdapter;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.game.CvcStateChangeEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;
import de.throwstnt.developing.labymod.cvc.api.game.handlers.CvcMessagesHandler;
import de.throwstnt.developing.labymod.cvc.api.game.handlers.CvcSoundHandler;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcGameManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcPlayerManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcRoundManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcScoreboardManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.GuiState;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.State;
import de.throwstnt.developing.labymod.cvc.api.stats.CvcStatsManager;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;

/**
 * An abstract cvc stats
 */
public abstract class AbstractCvcStats<GameProfileType, PacketBufferType, RenderContextType, ItemStackType>
        implements CvcEventListener {

    /**
     * All of our adapters that we use to be version independent
     */
    private AbstractPlayerListAdapter<GameProfileType> playerListAdapter;
    private AbstractGuiAdapter<RenderContextType, ItemStackType> guiAdapter;
    private AbstractScoreboardAdapter scoreboardAdapter;
    private ImplementedInboundPacketAdapter<GameProfileType> inboundPacketAdapter;
    private ImplementedServerAdapter<PacketBufferType> serverAdapter;

    private AbstractItem<ItemStackType> item;

    /**
     * If we are connected to hypixel
     */
    private boolean connectedToHypixel;

    /**
     * Creates a new cvc stats
     * 
     * @param scoreboardAdapter the scoreboard adapter
     * @param playerListAdapter the player list adapter
     * @param inboundPacketAdapter the inbound packet adapter
     * @param serverAdapter the server adapter
     */
    public AbstractCvcStats(AbstractScoreboardAdapter scoreboardAdapter,
            AbstractPlayerListAdapter<GameProfileType> playerListAdapter,
            AbstractGuiAdapter<RenderContextType, ItemStackType> guiAdapter,
            ImplementedInboundPacketAdapter<GameProfileType> inboundPacketAdapter,
            ImplementedServerAdapter<PacketBufferType> serverAdapter,
            AbstractItem<ItemStackType> item) {
        this.scoreboardAdapter = scoreboardAdapter;
        this.playerListAdapter = playerListAdapter;
        this.guiAdapter = guiAdapter;
        this.inboundPacketAdapter = inboundPacketAdapter;
        this.serverAdapter = serverAdapter;
        this.item = item;

        this.connectedToHypixel = false;

        // register event listeners
        CvcEventManager.getInstance().registerListeners(new CvcSoundHandler());
        CvcEventManager.getInstance().registerListeners(CvcGameManager.getInstance());
        CvcEventManager.getInstance().registerListeners(CvcRoundManager.getInstance());
        CvcEventManager.getInstance().registerListeners(CvcPlayerManager.getInstance());
        CvcEventManager.getInstance().registerListeners(CvcScoreboardManager.getInstance());
        CvcEventManager.getInstance().registerListeners(CvcStatsManager.getInstance());
        CvcEventManager.getInstance().registerListeners(CvcMessagesHandler.getInstance());
        CvcEventManager.getInstance().registerListeners(this);
    }

    @CvcEventHandler
    public void onStateChange(CvcStateChangeEvent event) {
        ChatUtil.log("Changed state from " + event.getOldState() + " to " + event.getNewState());

        if (State.isInGame(event.getNewState())) {
            CvcStateManager.getInstance().setGuiState(GuiState.MAIN_STATS);

            CvcPlayer currentPlayer = CvcPlayerManager.getInstance().getPlayer(
                    CvcAddonManager.getInstance().get().getPlayerListAdapter().getCurrentUUID());

            CvcScoreboardManager.getInstance().detectPlayer(currentPlayer);
        } else if (State.isInGame(event.getOldState())) {
            CvcAddonManager.getInstance().get().getGuiAdapter().setRoute("/overview");
        }
    }

    /**
     * Get the scoreboard adapter
     * 
     * @return the scoreboard adapter
     */
    public AbstractScoreboardAdapter getScoreboardAdapter() {
        return scoreboardAdapter;
    }

    /**
     * Get the player list adapter
     * 
     * @return the player list adapter
     */
    public AbstractPlayerListAdapter<GameProfileType> getPlayerListAdapter() {
        return playerListAdapter;
    }

    /**
     * Get the gui adapter
     * 
     * @return the gui adapter
     */
    public AbstractGuiAdapter<RenderContextType, ItemStackType> getGuiAdapter() {
        return guiAdapter;
    }

    /**
     * Get the inbound packet adapter
     * 
     * @return the inbound packet adapter
     */
    public ImplementedInboundPacketAdapter<GameProfileType> getInboundPacketAdapter() {
        return inboundPacketAdapter;
    }

    /**
     * Get the server adapter
     * 
     * @return the server adapter
     */
    public ImplementedServerAdapter<PacketBufferType> getServerAdapter() {
        return serverAdapter;
    }

    public AbstractItem<ItemStackType> getItem() {
        return item;
    }

    /**
     * Gets if we are connected to hypixel
     * 
     * @return true if we are connected to hypixel
     */
    public boolean isConnectedToHypixel() {
        return this.connectedToHypixel;
    }

    /**
     * Sets if we are connected to hypixel
     * 
     * @param connectedToHypixel true if we are connected to hypixel
     */
    public void setConnectedToHypixel(boolean connectedToHypixel) {
        this.connectedToHypixel = connectedToHypixel;
    }

    /**
     * Gets if addon is enabled by the user
     * 
     * @return true if addon is enabled
     */
    public abstract boolean isEnabled();

    /**
     * Gets if addon should output any chat messages. Config value
     * 
     * @return true if addon should output any chat messages
     */
    public abstract boolean areChatMessagesEnabled();

    /**
     * Gets if addon should check api status. Config value
     * 
     * @return true if addon should check api status
     */
    public abstract boolean isApiStatusEnabled();

    /**
     * Gets the api key. Config value
     * 
     * @return the api key
     */
    public abstract String getApiKey();
}
