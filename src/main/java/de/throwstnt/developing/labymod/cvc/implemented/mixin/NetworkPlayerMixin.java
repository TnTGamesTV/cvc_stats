package de.throwstnt.developing.labymod.cvc.implemented.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import de.throwstnt.developing.labymod.cvc.implemented.CvcStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.network.play.server.SCustomPayloadPlayPacket;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.util.text.ChatType;

@Mixin(value = ClientPlayNetHandler.class)
public class NetworkPlayerMixin {

    @Inject(method = "handlePlayerListItem", at = @At("TAIL"))
    public void handlePlayerListItem(SPlayerListItemPacket packetIn, CallbackInfo info) {
        if (packetIn.getAction() == SPlayerListItemPacket.Action.ADD_PLAYER
                || packetIn.getAction() == SPlayerListItemPacket.Action.REMOVE_PLAYER) {
            CvcStats.getInstance().getImplementedCvcStats().getInboundPacketAdapter()
                    .handlePlayerListChanged();
        }
    }

    @Inject(method = "handleCustomSound", at = @At("TAIL"))
    public void handleCustomSound(SPlaySoundPacket packetIn, CallbackInfo info) {
        CvcStats.getInstance().getImplementedCvcStats().getInboundPacketAdapter().handleSoundPacket(
                packetIn.getSoundName().getPath(), packetIn.getX(), packetIn.getY(),
                packetIn.getZ(), packetIn.getPitch(), packetIn.getVolume());
    }

    @Inject(method = "handleJoinGame", at = @At("TAIL"))
    public void handleJoinGame(SJoinGamePacket packetIn, CallbackInfo info) {
        if (Minecraft.getInstance().getCurrentServerData() != null) {
            ServerData serverData = Minecraft.getInstance().getCurrentServerData();

            CvcStats.getInstance().getImplementedCvcStats().getServerAdapter()
                    .handleConnectServer(serverData.serverIP, -1);
        }
    }

    @Inject(method = "handleDisconnect", at = @At("HEAD"))
    public void handleDisconnect(SDisconnectPacket packetIn, CallbackInfo info) {
        if (Minecraft.getInstance().getCurrentServerData() != null) {
            ServerData serverData = Minecraft.getInstance().getCurrentServerData();

            CvcStats.getInstance().getImplementedCvcStats().getServerAdapter()
                    .handleDisconnectServer(serverData.serverIP, -1);
        }
    }

    @Inject(method = "handleChat", at = @At("TAIL"))
    public void handleChat(SChatPacket packetIn, CallbackInfo info) {
        if (packetIn.getType() == ChatType.CHAT) {
            CvcStats.getInstance().getImplementedCvcStats().getServerAdapter()
                    .handleChatMessage(packetIn.getChatComponent().getString());
        }
    }

    @Inject(method = "handleCustomPayload", at = @At("TAIL"))
    public void handleCustomPayload(SCustomPayloadPlayPacket packetIn, CallbackInfo info) {
        String channelName = packetIn.getChannelName().toString();

        CvcStats.getInstance().getImplementedCvcStats().getServerAdapter()
                .handleServerMessage(channelName, packetIn.getBufferData());
    }
}
