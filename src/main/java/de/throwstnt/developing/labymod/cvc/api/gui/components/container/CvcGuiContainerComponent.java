package de.throwstnt.developing.labymod.cvc.api.gui.components.container;

import java.util.List;
import com.google.common.collect.Lists;
import de.throwstnt.developing.labymod.cvc.api.gui.AbstractCvcInterface;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.container.CvcGuiRowComponent.GrowType;

/**
 * A component wrapping other components
 */
public class CvcGuiContainerComponent extends CvcGuiComponent {

    private int maxWidth;

    private List<CvcGuiRowComponent> rows;

    public CvcGuiContainerComponent(CvcGuiRowComponent... rows) {
        this.maxWidth = 0;
        this.rows = Lists.newArrayList(rows);
    }

    @Override
    public void init(AbstractCvcInterface rootInterface) {
        this.rows.forEach(row -> row.init(rootInterface));
    }

    @Override
    public int getHeight() {
        return this._getHeight();
    }

    @Override
    public int getWidth() {
        return this.rows.stream().mapToInt(row -> row.getWidth()).sum();
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        this.maxWidth = width;

        super.draw(x, y, width, height, mouseX, mouseY);

        int currentX = x;
        int currentY = y;

        int allowedWidth = width;

        for (CvcGuiRowComponent row : this.rows) {
            // ChatUtil.log("drawing row");

            // find all overflowing containers from the entire row
            List<CvcGuiComponent> overflowingContainers = row.getOverflowingChildren(allowedWidth);

            List<CvcGuiComponent> notOverflowingContainersFirstPass =
                    row.getNotOverflowingChildren(allowedWidth);

            // ChatUtil.log("firstPassOverflowing=" + overflowingContainers.size() + ",
            // firstPassNotOverflowing=" + notOverflowingContainersFirstPass.size());

            // get the maximum height of them
            int rowHeightFirstPass = row.getMaximumHeight(notOverflowingContainersFirstPass);

            // draw them
            this._drawRow(currentX, currentY, allowedWidth, rowHeightFirstPass, mouseX, mouseY, row,
                    notOverflowingContainersFirstPass);

            // after drawing go one row lower
            currentY += rowHeightFirstPass;

            // pass 1: if there are overflowing containers we draw them here
            // pass >= 2: we check if there are still containers overflowing and then draw them
            while (overflowingContainers.size() > 0) {
                // get all containers that we are actually allowed to draw
                List<CvcGuiComponent> notOverflowingContainers =
                        row.getNotOverflowingChildren(allowedWidth, overflowingContainers);

                // ChatUtil.log("morePassOverflowing=" + overflowingContainers.size() + ",
                // morePassNotOverflowing=" + notOverflowingContainers.size());

                // get the maximum height of them
                int rowHeight = row.getMaximumHeight(notOverflowingContainers);

                // draw them
                this._drawRow(currentX, currentY, allowedWidth, rowHeight, mouseX, mouseY, row,
                        notOverflowingContainers);

                // search for all overflowing containers that not even going to fit in the
                // "overflowing"
                // row
                overflowingContainers =
                        row.getOverflowingChildren(allowedWidth, overflowingContainers);

                // add y
                currentY += rowHeight;
            }
        }
    }

    /**
     * Draws a single row
     * 
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param mouseX the mouse x
     * @param mouseY the mouse y
     * @param row the row
     * @param notOverflowingChildren all children that are not overflowing
     */
    private void _drawRow(int x, int y, int width, int height, int mouseX, int mouseY,
            CvcGuiRowComponent row, List<CvcGuiComponent> notOverflowingChildren) {
        int currentX = x;
        int allowedWidth = width;
        int rowHeight = height;

        int currentContainer = 0;
        for (CvcGuiComponent child : notOverflowingChildren) {
            int containerWidth = child.getWidth();

            if (row.getGrowType() == GrowType.FIRST
                    && currentContainer == (notOverflowingChildren.size() - 1)) {
                // calculate the available space
                containerWidth = allowedWidth - currentX + x;
            } else if (row.getGrowType() == GrowType.CENTERED) {
                containerWidth = Math.floorDiv(allowedWidth, notOverflowingChildren.size());
            }

            // ChatUtil.log("Drawing container " + currentContainer + " with: width=" +
            // containerWidth +
            // ", x=" + currentX);

            child.draw(currentX, y, containerWidth, rowHeight, mouseX, mouseY);

            currentX += containerWidth;
            currentContainer++;
        }
    }

    /**
     * Returns the actual height after checking for overflow
     * 
     * @return the actual height
     */
    private int _getHeight() {
        int currentY = 0;

        int allowedWidth = this.maxWidth;

        for (CvcGuiRowComponent row : this.rows) {
            // find all overflowing containers from the entire row
            List<CvcGuiComponent> overflowingChildren = row.getOverflowingChildren(allowedWidth);

            List<CvcGuiComponent> notOverflowingChildrenFirstPass =
                    row.getNotOverflowingChildren(allowedWidth);

            // get the maximum height of them
            int rowHeightFirstPass = row.getMaximumHeight(notOverflowingChildrenFirstPass);

            // after drawing go one row lower
            currentY += rowHeightFirstPass;

            // pass 1: if there are overflowing containers we draw them here
            // pass >= 2: we check if there are still containers overflowing and then draw them
            while (overflowingChildren.size() > 0) {
                // get all containers that we are actually allowed to draw
                List<CvcGuiComponent> notOverflowingChildren =
                        row.getNotOverflowingChildren(allowedWidth, overflowingChildren);

                // get the maximum height of them
                int rowHeight = row.getMaximumHeight(notOverflowingChildren);

                // search for all overflowing containers that not even going to fit in the
                // "overflowing" row
                overflowingChildren = row.getOverflowingChildren(allowedWidth, overflowingChildren);

                // add y
                currentY += rowHeight;
            }
        }

        return currentY;
    }
}
