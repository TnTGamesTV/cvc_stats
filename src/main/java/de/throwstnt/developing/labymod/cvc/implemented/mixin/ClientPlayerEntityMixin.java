package de.throwstnt.developing.labymod.cvc.implemented.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;
import net.minecraft.client.entity.player.ClientPlayerEntity;

@Mixin(value = ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "sendChatMessage", at = @At("HEAD"))
    public void handleSendChatMessage(String message, CallbackInfo info) {
        if (message.startsWith("/cvcstats") || message.startsWith(".cvcstats")) {
            ChatUtil.log("Found cvc stats command");

            info.cancel();
            return;
        }
    }
}
