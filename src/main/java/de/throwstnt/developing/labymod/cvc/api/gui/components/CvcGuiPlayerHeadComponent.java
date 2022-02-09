package de.throwstnt.developing.labymod.cvc.api.gui.components;

import java.util.UUID;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;

public class CvcGuiPlayerHeadComponent extends CvcGuiComponent {

    private int size;

    private String name;

    private UUID uuid;

    public CvcGuiPlayerHeadComponent(String name, int size) {
        super(SizingStrategy.FIXED, PositionStrategy.MIDDLE);

        this.name = name;
        this.size = size;
    }

    public CvcGuiPlayerHeadComponent(UUID uuid, int size) {
        this.uuid = uuid;
        this.size = size;
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

        if (this.name != null) {
            CvcAddonManager.getInstance().get().getGuiAdapter().drawPlayerHead(name, x, y,
                    this.size);
        }

        if (this.uuid != null) {
            CvcAddonManager.getInstance().get().getGuiAdapter().drawPlayerHead(uuid, x, y,
                    this.size);
        }
    }
}
