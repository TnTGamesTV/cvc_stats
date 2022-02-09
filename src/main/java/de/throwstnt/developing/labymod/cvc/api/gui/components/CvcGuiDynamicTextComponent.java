package de.throwstnt.developing.labymod.cvc.api.gui.components;

import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;

/**
 * A simple text and nothing else
 */
public class CvcGuiDynamicTextComponent extends CvcGuiComponent {

    public interface ISupplyString {

        String supply();
    }

    private ISupplyString textSupplier;

    private double fontSize;

    private boolean isCentered;

    public CvcGuiDynamicTextComponent(ISupplyString textSupplier) {
        this(textSupplier, true, 1);
    }

    public CvcGuiDynamicTextComponent(ISupplyString textSupplier, boolean isCentered) {
        this(textSupplier, isCentered, 1);
    }

    public CvcGuiDynamicTextComponent(ISupplyString textSupplier, boolean isCentered,
            double fontSize) {
        this.textSupplier = textSupplier;
        this.isCentered = isCentered;
        this.fontSize = fontSize;

        this.setMargin(4);
    }

    @Override
    public String getName() {
        return "text";
    }

    @Override
    public int getWidth() {
        return CvcAddonManager.getInstance().get().getGuiAdapter()
                .getWidthForString(textSupplier.supply()) + this.getMargin() * 2;
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        super.draw(x, y, width, height, mouseX, mouseY);

        x += width / 2;
        y += height / 2 - CvcAddonManager.getInstance().get().getGuiAdapter().getFontHeight() / 2;

        CvcAddonManager.getInstance().get().getGuiAdapter().drawString(textSupplier.supply(), x, y,
                this.fontSize, this.isCentered);
    }

    @Override
    public int getHeight() {
        return CvcAddonManager.getInstance().get().getGuiAdapter().getFontHeight()
                + this.getMargin() * 2;
    }
}
