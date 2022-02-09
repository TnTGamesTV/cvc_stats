package de.throwstnt.developing.labymod.cvc.api.gui.components;

import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;

public class CvcGuiItemComponent extends CvcGuiComponent {

    private AbstractItem.ItemStackType itemStackType;

    private int size = 16;

    public CvcGuiItemComponent(AbstractItem.ItemType itemType) {
        this(AbstractItem.fromItemType(itemType));
    }

    public CvcGuiItemComponent(AbstractItem.ItemStackType itemStackType) {
        super(SizingStrategy.FIXED, PositionStrategy.MIDDLE);

        this.itemStackType = itemStackType;
    }


    @Override
    public int getWidth() {
        return this.size + this.getMargin() * 2;
    }

    @Override
    public int getHeight() {
        return this.size + this.getMargin() * 2;
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        super.draw(x, y, width, height, mouseX, mouseY);

        x = x + width / 2 - size / 2;
        y = y + height / 2 - size / 2;

        CvcAddonManager.getInstance().get().getGuiAdapter().drawItemStack(itemStackType, x, y);
    }
}
