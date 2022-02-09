package de.throwstnt.developing.labymod.cvc.implemented.adapters;

import java.util.UUID;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractGuiAdapter;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem;
import de.throwstnt.developing.labymod.cvc.implemented.CvcStats;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ImplementedGuiAdapter extends AbstractGuiAdapter<MatrixStack, ItemStack> {

    @SuppressWarnings("resource")
    @Override
    public int getWidthForString(String text) {
        return Minecraft.getInstance().fontRenderer.getStringWidth(text);
    }

    @SuppressWarnings("resource")
    @Override
    public int getFontHeight() {
        return Minecraft.getInstance().fontRenderer.FONT_HEIGHT;
    }

    @Override
    public int getWidth() {
        return Minecraft.getInstance().getMainWindow().getWidth()
                - Minecraft.getInstance().getMainWindow().getWindowX();
    }

    @Override
    public int getHeight() {
        return Minecraft.getInstance().getMainWindow().getHeight()
                - Minecraft.getInstance().getMainWindow().getWindowY();
    }

    @Override
    public void drawString(String text, int x, int y, double size, boolean isCentered) {
        MatrixStack matrixStack = this.getGlobalRenderContext();

        if (isCentered) {
            LabyMod.getInstance().getDrawUtils().drawCenteredString(matrixStack, text, x, y, size);
        } else {
            LabyMod.getInstance().getDrawUtils().drawString(matrixStack, text, x, y, size);
        }
    }

    @Override
    public void drawBackground() {
        MatrixStack matrixStack = this.getGlobalRenderContext();

        int width = this.getWidth();
        int height = this.getHeight();

        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(matrixStack, height);

        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(matrixStack, 0, 30);
        /*
         * LabyMod.getInstance().getDrawUtils().drawOverlayBackground(matrixStack, height - 32,
         * height);
         */

        // LabyMod.getInstance().getDrawUtils().drawGradientShadowTop(matrixStack, 30, 0.0D, width);
        /*
         * LabyMod.getInstance().getDrawUtils().drawGradientShadowBottom(matrixStack, height -
         * 32.0D, 0.0D, width);
         */
    }

    @Override
    public void drawDebugBackground(String context, int x, int y, int width, int height,
            int color) {
        LabyMod.getInstance().getDrawUtils().drawRectangle(this.getGlobalRenderContext(), x, y,
                x + width, y + height, color);


        LabyMod.getInstance().getDrawUtils().drawString(getGlobalRenderContext(), context, x + 1,
                y + 1);
    }

    @Override
    public void drawRectangle(int x0, int y0, int x1, int y1, int color) {
        LabyMod.getInstance().getDrawUtils().drawRectangle(getGlobalRenderContext(), x0, y0, x1, y1,
                color);
    }

    @Override
    public void drawPlayerHead(String name, int x, int y, int size) {
        LabyMod.getInstance().getDrawUtils().drawPlayerHead(getGlobalRenderContext(), name, x, y,
                size);
    }

    @Override
    public void drawPlayerHead(UUID uuid, int x, int y, int size) {
        LabyMod.getInstance().getDrawUtils().drawPlayerHead(getGlobalRenderContext(), uuid, x, y,
                size);
    }

    @Override
    public void drawItemStack(AbstractItem.ItemStackType itemStackType, int x, int y) {
        ItemStack itemStack = CvcStats.getInstance().getImplementedCvcStats().getItem()
                .getItemStack(itemStackType);

        LabyMod.getInstance().getDrawUtils().drawItem(itemStack, x, y, "");
    }
}
