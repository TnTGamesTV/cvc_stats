package de.throwstnt.developing.labymod.cvc.api.gui.components;

import java.util.Timer;
import java.util.TimerTask;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.gui.AbstractCvcInterface;

public class CvcGuiButtonComponent extends CvcGuiComponent {

    /**
     * An interface handling clicks
     */
    public interface IHandleClick {
        void onClick(CvcGuiButtonComponent reference);
    }

    private String text;
    private IHandleClick clickHandler;

    private int drawX;
    private int drawY;
    private int drawWidth;
    private int drawHeight;

    private boolean wasClicked = false;

    public CvcGuiButtonComponent(String text, IHandleClick clickHandler) {
        this.text = text;
        this.clickHandler = clickHandler;
    }

    @Override
    public void init(AbstractCvcInterface rootInterface) {
        super.init(rootInterface);

        rootInterface.addClickListener(
                (mouseX, mouseY, mouseButton) -> this._onClick(mouseX, mouseY, mouseButton));
    }

    private void _onClick(int mouseX, int mouseY, int mouseButton) {
        if (this._isHovered(drawX, drawY, drawWidth, drawHeight, mouseX, mouseY)) {
            this.wasClicked = true;

            this.clickHandler.onClick(this);

            int wasClickedDelay = 500;

            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    CvcGuiButtonComponent.this.wasClicked = false;
                }
            }, wasClickedDelay);
        }
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        super.draw(x, y, width, height, mouseX, mouseY);

        int correctX = x;
        int correctY = y;

        this.drawX = x;
        this.drawY = y;
        this.drawWidth = width;
        this.drawHeight = height;

        if (this._isHovered(correctX, correctY, width, height, mouseX, mouseY)) {
            // top line
            CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(correctX, correctY,
                    correctX + width, correctY + 1, 0xffffffff);

            // bottom line
            CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(correctX,
                    correctY + height - 1, correctX + width, correctY + height, 0xffffffff);

            // left line
            CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(correctX, correctY,
                    correctX + 1, correctY + height, 0xffffffff);

            // right line
            CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(correctX + width - 1,
                    correctY, correctX + width, correctY + height, 0xffffffff);
        }

        int backgroundColor = this.wasClicked ? 0xddffffff : 0x50ffffff;

        CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(correctX, correctY,
                correctX + width, correctY + height, backgroundColor);
        CvcAddonManager.getInstance().get().getGuiAdapter().drawString(text, correctX + width / 2
                - CvcAddonManager.getInstance().get().getGuiAdapter().getWidthForString(this.text)
                        / 2,
                correctY + height / 2
                        - CvcAddonManager.getInstance().get().getGuiAdapter().getFontHeight() / 2,
                1, true);
    }

    private boolean _isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        boolean isBelowTopBorder = x < mouseX;
        boolean isRightOfLeftBorder = y < mouseY;
        boolean isLeftOfRightBorder = (y + height) > mouseY;
        boolean isAboveBottomBorder = (x + width) > mouseX;

        return isBelowTopBorder && isRightOfLeftBorder && isLeftOfRightBorder
                && isAboveBottomBorder;
    }

    @Override
    public int getWidth() {
        return CvcAddonManager.getInstance().get().getGuiAdapter().getWidthForString(this.text);
    }

    @Override
    public int getHeight() {
        return CvcAddonManager.getInstance().get().getGuiAdapter().getFontHeight();
    }
}
