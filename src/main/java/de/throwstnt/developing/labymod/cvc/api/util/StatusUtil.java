package de.throwstnt.developing.labymod.cvc.api.util;

import net.hypixel.api.data.type.GameType;
import net.hypixel.api.data.type.ServerType;
import net.hypixel.api.reply.StatusReply;
import net.minecraft.util.text.TextFormatting;

public class StatusUtil {

    public static final String DEFUSAL_MODE_KEY = "normal";
    public static final String TDM_MODE_KEY = "deathmatch";

    public static String descriptionFromStatusReply(StatusReply reply) {
        if (reply != null) {
            reply.getSession().getServerType().getName();

            if (reply.getSession().getServerType() == ServerType.valueOf(GameType.MCGO.getName())) {
                if (reply.getSession().getMode().equals("LOBBY")) {
                    return "Is in a " + TextFormatting.GOLD + "Cops and Crimes"
                            + TextFormatting.RESET + " lobby";
                } else {
                    if (reply.getSession().getMode().equals(DEFUSAL_MODE_KEY)) {
                        return "Is playing " + TextFormatting.GOLD + "Cops and Crimes"
                                + TextFormatting.RESET + " defusal on '"
                                + reply.getSession().getMap() + "'";
                    } else if (reply.getSession().getMode().equals(TDM_MODE_KEY)) {
                        return "Is playing " + TextFormatting.GOLD + "Cops and Crimes"
                                + TextFormatting.RESET + " deathmatch on '"
                                + reply.getSession().getMap() + "'";
                    }
                }
            } else if (reply.getSession().getServerType() != null) {
                if (reply.getSession().getMode().equals("LOBBY")) {
                    return "Is in a " + TextFormatting.GRAY
                            + reply.getSession().getServerType().getName() + TextFormatting.RESET
                            + " lobby";
                } else {
                    return "Is playing " + TextFormatting.GRAY
                            + reply.getSession().getServerType().getName() + TextFormatting.RESET;
                }
            } else {
                return TextFormatting.GRAY + "Is not playing" + TextFormatting.RESET;
            }

            return "";
        } else {
            return "Loading ...";
        }
    }
}
