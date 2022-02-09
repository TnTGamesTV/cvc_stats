package de.throwstnt.developing.labymod.cvc.api.adapters;

import java.util.Timer;
import java.util.TimerTask;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.minecraft.CvcMinecraftChatMessageEvent;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager.State;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;

/**
 * An adapter receiving for server events
 */
public class ImplementedServerAdapter<PacketBufferType> {

    public static final String CONST_HYPIXEL_IP = "hypixel.net";

    /**
     * Handles a message in chat
     * 
     * @param message the message
     */
    public void handleChatMessage(String message) {
        // TODO: If we are connected to hypixel redirect this message to our message analyzer
        CvcEventManager.getInstance().fireEvent(new CvcMinecraftChatMessageEvent(message));
    }

    /**
     * Handles a connect to the given server
     * 
     * @param ip the ip of the server
     * @param port the port of the server
     */
    public void handleConnectServer(String ip, int port) {
        if (ip.contains(CONST_HYPIXEL_IP)) {
            if (CvcAddonManager.getInstance().get().getApiKey().equals("")) {
                ChatUtil.sendChatMessage(
                        "Please set the api key in the addon configuration. Optain an api key by typing /api");
            }

            CvcAddonManager.getInstance().get().setConnectedToHypixel(true);
        }
    }

    /**
     * Handles a disconnect from the given server
     * 
     * @param ip the ip of the server
     * @param port the port of the server
     */
    public void handleDisconnectServer(String ip, int port) {
        CvcAddonManager.getInstance().get().setConnectedToHypixel(false);
    }

    /**
     * Handles a server message send to the client
     * 
     * @param messageChannel the channel
     * @param packetBuffer the message
     */
    public void handleServerMessage(String messageChannel, PacketBufferType packetBuffer) {
        if (messageChannel.contains("minecraft:brand") || messageChannel.contains("MC|brand")) {
            if (State.isInGame(CvcStateManager.getInstance().getState())) {
                // if we are in game but this message comes we should reset state
                CvcStateManager.getInstance().updateState(State.UNKNOWN);
            }

            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    CvcStateManager.getInstance().checkState();
                }
            }, 500);
        }
    }
}
