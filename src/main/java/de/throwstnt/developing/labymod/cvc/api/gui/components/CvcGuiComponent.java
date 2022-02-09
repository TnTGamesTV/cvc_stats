package de.throwstnt.developing.labymod.cvc.api.gui.components;

import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.gui.AbstractCvcInterface;

/**
 * The most basic component
 */
public abstract class CvcGuiComponent {

    /**
     * Different types of sizing strategies to determine the final size of the component
     */
    public enum SizingStrategy {
        FILL, FIXED
    }

    /**
     * Different types of position strategies to determine the final position of the component
     */
    public enum PositionStrategy {
        TOP_LEFT, TOP, TOP_RIGHT, MIDDLE_LEFT, MIDDLE, MIDDLE_RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT
    }

    /**
     * The debug color of the component
     */
    private int debugColor =
            CvcAddonManager.getInstance().get().getGuiAdapter().generateRandomColor();

    /**
     * The margin of the component
     */
    private int margin = 0;

    /**
     * If the component is hidden or not
     */
    private boolean isHidden;

    private PositionStrategy positionStrategy = PositionStrategy.TOP_LEFT;

    private SizingStrategy sizingStrategy = SizingStrategy.FILL;

    /**
     * Create a new component
     */
    public CvcGuiComponent() {
        this(0);
    }

    /**
     * Create a new component with the given margin
     * 
     * @param margin
     */
    public CvcGuiComponent(int margin) {
        this.margin = margin;

        this.setHidden(false);
    }

    public CvcGuiComponent(SizingStrategy sizingStrategy, PositionStrategy positionStrategy) {
        this(0);

        this.sizingStrategy = sizingStrategy;
        this.positionStrategy = positionStrategy;
    }


    /**
     * Called when the interface is created
     */
    public void init(AbstractCvcInterface rootInterface) {}

    /**
     * Executed before the draw to adjust parameters
     * 
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     */
    public void preDraw(int x, int y, int width, int height, int mouseX, int mouseY) {
        x += margin;
        y += margin;
        width -= margin;
        height -= margin;

        int contentHeight = this.getHeight();
        int contentWidth = this.getWidth();

        if (!this.isHidden) {
            if (this.sizingStrategy == SizingStrategy.FILL) {
                this.draw(x, y, width, height, mouseX, mouseY);
            } else if (this.sizingStrategy == SizingStrategy.FIXED) {
                if (this.positionStrategy == PositionStrategy.TOP_LEFT) {
                    this.draw(x, y, contentWidth, contentHeight, mouseX, mouseY);
                } else if (this.positionStrategy == PositionStrategy.TOP) {
                    this.draw(x + width / 2 - contentWidth / 2, y, contentWidth, contentHeight,
                            mouseX, mouseY);
                } else if (this.positionStrategy == PositionStrategy.TOP_RIGHT) {
                    this.draw(x + width - contentWidth, y, contentWidth, contentHeight, mouseX,
                            mouseY);
                } else if (this.positionStrategy == PositionStrategy.MIDDLE_LEFT) {
                    this.draw(x, y + height / 2 - contentHeight / 2, contentWidth, contentHeight,
                            mouseX, mouseY);
                } else if (this.positionStrategy == PositionStrategy.MIDDLE) {
                    this.draw(x + width / 2 - contentWidth / 2, y + height / 2 - contentHeight / 2,
                            contentWidth, contentHeight, mouseX, mouseY);
                } else if (this.positionStrategy == PositionStrategy.MIDDLE_RIGHT) {
                    this.draw(x + width - contentWidth, y + height / 2 - contentHeight / 2,
                            contentWidth, contentHeight, mouseX, mouseY);
                } else if (this.positionStrategy == PositionStrategy.BOTTOM_LEFT) {
                    this.draw(x, y + height - contentHeight, contentWidth, contentHeight, mouseX,
                            mouseY);
                } else if (this.positionStrategy == PositionStrategy.BOTTOM) {
                    this.draw(x + width / 2 - contentWidth / 2, y + height - contentHeight,
                            contentWidth, contentHeight, mouseX, mouseY);
                } else if (this.positionStrategy == PositionStrategy.BOTTOM_RIGHT) {
                    this.draw(x + width - contentWidth, y + height - contentHeight, contentWidth,
                            contentHeight, mouseX, mouseY);
                }
            }
        }
    }

    /**
     * Draw the component
     * 
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     */
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        if (CvcAddonManager.getInstance().get().getGuiAdapter().isDebugEnabled()) {
            CvcAddonManager.getInstance().get().getGuiAdapter().drawDebugBackground(this.getName(),
                    x, y, width, height, this.getDebugColor());
        }
    }

    /**
     * Get the width of this component
     * 
     * @return
     */
    public abstract int getWidth();

    /**
     * Get the height of the component
     * 
     * @return the height
     */
    public abstract int getHeight();

    /**
     * Returns the name of the component type
     * 
     * @return the name of the component type
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get the debug color of the component
     * 
     * @return the debug color
     */
    public int getDebugColor() {
        return this.debugColor;
    }

    /**
     * Returns if the component is hidden
     * 
     * @return
     */
    public boolean isHidden() {
        return isHidden;
    }

    /**
     * Set the component to hidden or not
     * 
     * @param isHidden if the component is hidden
     */
    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    /**
     * Get the margin of this component
     * 
     * @return the margin
     */
    public int getMargin() {
        return this.margin;
    }

    /**
     * Set the components margin
     * 
     * @param margin the margin
     */
    public void setMargin(int margin) {
        this.margin = margin;
    }

    public PositionStrategy getPositionStrategy() {
        return positionStrategy;
    }

    public SizingStrategy getSizingStrategy() {
        return sizingStrategy;
    }
}
