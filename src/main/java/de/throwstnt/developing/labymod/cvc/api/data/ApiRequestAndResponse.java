package de.throwstnt.developing.labymod.cvc.api.data;

import java.util.UUID;
import net.hypixel.api.reply.AbstractReply;

public class ApiRequestAndResponse {

    public String request;
    public UUID uuid;
    public AbstractReply reply;

    public ApiRequestAndResponse(String request, UUID uuid, AbstractReply reply) {
        this.request = request;
        this.uuid = uuid;
        this.reply = reply;
    }
}
