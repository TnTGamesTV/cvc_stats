package de.throwstnt.developing.labymod.cvc.api.events.minecraft;

public class CvcMinecraftChatMessageEvent extends CvcMinecraftEvent {

    private String message;
    private long receivedAt;

    public CvcMinecraftChatMessageEvent(String message) {
        this.message = message;
        this.receivedAt = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public long getReceivedAt() {
        return receivedAt;
    }
}
