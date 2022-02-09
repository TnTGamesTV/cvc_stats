package de.throwstnt.developing.labymod.cvc.api.util;

import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import net.labymod.main.LabyMod;
import net.labymod.support.util.Debug;
import net.labymod.support.util.Debug.EnumDebugMode;
import net.minecraft.util.text.TextFormatting;

public class ChatUtil {

    public static void sendChatMessage(String message) {
        if (CvcAddonManager.getInstance().get().isEnabled()
                && CvcAddonManager.getInstance().get().areChatMessagesEnabled()) {
            LabyMod.getInstance()
                    .displayMessageInChat(TextFormatting.DARK_BLUE + "[" + TextFormatting.BLUE
                            + "CvCStats" + TextFormatting.DARK_BLUE + "] " + TextFormatting.GRAY
                            + message);
        }
    }

    public static void log(String message) {
        Debug.log(EnumDebugMode.ADDON, "[CVCADDON] " + message);
    }

    /**
     * Removes all color codes from a message
     * 
     * @param message the message
     * @return the cleaned message
     */
    public static String cleanColorCoding(String message) {
        return message.replaceAll("§[0-9a-z]{1}", "");
    }
}
