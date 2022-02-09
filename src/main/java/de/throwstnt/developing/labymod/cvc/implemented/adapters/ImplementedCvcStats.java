package de.throwstnt.developing.labymod.cvc.implemented.adapters;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.throwstnt.developing.labymod.cvc.api.AbstractCvcStats;
import de.throwstnt.developing.labymod.cvc.api.adapters.ImplementedInboundPacketAdapter;
import de.throwstnt.developing.labymod.cvc.api.adapters.ImplementedServerAdapter;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.implemented.CvcStats;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ImplementedCvcStats
        extends AbstractCvcStats<GameProfile, PacketBuffer, MatrixStack, ItemStack> {

    public ImplementedCvcStats() {
        super(new ImplementedScoreboardAdapter(), new ImplementedPlayerListAdapter(),
                new ImplementedGuiAdapter(), new ImplementedInboundPacketAdapter<>(),
                new ImplementedServerAdapter<>(), new ImplementedItem());

        CvcAddonManager.getInstance().register(this);
    }

    @Override
    public boolean isEnabled() {
        return CvcStats.getInstance().isEnabled();
    }

    @Override
    public boolean areChatMessagesEnabled() {
        return !CvcStats.getInstance().isDisableChatMessage();
    }

    @Override
    public boolean isApiStatusEnabled() {
        return CvcStats.getInstance().isEnableApiStatus();
    }

    @Override
    public String getApiKey() {
        return CvcStats.getInstance().getApiKey();
    }

}
