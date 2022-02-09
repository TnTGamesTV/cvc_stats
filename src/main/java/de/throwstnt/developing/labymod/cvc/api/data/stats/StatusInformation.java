package de.throwstnt.developing.labymod.cvc.api.data.stats;

import net.hypixel.api.reply.StatusReply;

public class StatusInformation {

    public static final String CVC_DEFUSAL_MODE_KEY = "normal";
    public static final String CVC_TDM_MODE_KEY = "deathmatch";
    public static final String CVC_DEFUSAL_CHALLENGE_MODE_KEY = "normal_party";

    public boolean isOnline;
    public String game;
    public String mode;
    public String map;

    public StatusInformation(StatusReply reply) {
        if (reply.getSession().isOnline()) {
            this.isOnline = true;
            this.game = reply.getSession().getServerType().getName();
            this.mode = reply.getSession().getMode();
            this.map = reply.getSession().getMap();
        } else {
            this.isOnline = false;
            this.game = "";
            this.mode = "";
            this.map = "";
        }
    }
}
