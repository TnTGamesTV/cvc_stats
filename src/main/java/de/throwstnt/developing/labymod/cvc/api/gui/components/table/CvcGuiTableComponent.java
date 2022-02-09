package de.throwstnt.developing.labymod.cvc.api.gui.components.table;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.container.CvcGuiRowComponent.GrowType;

public class CvcGuiTableComponent<ItemType> extends CvcGuiComponent {

    public interface ISupplyItems<ItemType> {

        List<ItemType> supply();
    }

    private class CachedWidthAndHeight {

        public int width;
        public int height;

        public CachedWidthAndHeight(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    private List<CvcGuiColumnComponent<ItemType>> columns;

    private GrowType growType;

    private ISupplyItems<ItemType> itemSupplier;

    public CvcGuiTableComponent(List<CvcGuiColumnComponent<ItemType>> columns,
            ISupplyItems<ItemType> itemSupplier) {
        this.columns = columns;
        this.itemSupplier = itemSupplier;
        this.growType = GrowType.CENTERED;
    }

    public CvcGuiTableComponent(List<CvcGuiColumnComponent<ItemType>> columns, GrowType growType,
            ISupplyItems<ItemType> itemSupplier) {
        this.columns = columns;
        this.itemSupplier = itemSupplier;
        this.growType = growType;
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        super.draw(x, y, width, height, mouseX, mouseY);

        Map<CvcGuiColumnComponent<ItemType>, CachedWidthAndHeight> cachedWidthAndHeightMap =
                new HashMap<>();

        List<ItemType> items = this._supply();

        synchronized (items) {
            Map<ItemType, CachedWidthAndHeight> cachedMaxHeightAndWidthForRowMap = new HashMap<>();

            items.forEach(item -> {
                int maxHeight = this.columns.stream()
                        .mapToInt(column -> column.getComponent(item).getHeight()).max().orElse(0);

                /*
                 * int maxWidth = this.columns.stream() .mapToInt(column ->
                 * column.getComponent(item).getWidth()).sum();
                 */

                cachedMaxHeightAndWidthForRowMap.put(item, new CachedWidthAndHeight(-1, maxHeight));
            });

            // precalculate all width and height of each column for the following render
            columns.forEach(column -> {
                int maxWidth = column.getTitle() != null ? 0 : column.getTitle().getWidth();

                for (ItemType item : items) {
                    int itemWidth = column.getComponent(item).getWidth();

                    if (maxWidth < itemWidth) {
                        maxWidth = itemWidth;
                    }
                }

                int columnHeight = column.getTitle() == null ? 0 : column.getTitle().getHeight();

                for (ItemType item : items) {
                    int cachedMaxHeight = cachedMaxHeightAndWidthForRowMap.get(item).height;

                    columnHeight += cachedMaxHeight;

                }

                cachedWidthAndHeightMap.put(column,
                        new CachedWidthAndHeight(maxWidth, columnHeight));
            });

            // render each column before going to the next
            int currentX = x;
            int currentY = y;

            int totalUsedWidth = 0;
            int columnIndex = 0;
            for (CvcGuiColumnComponent<ItemType> column : this.columns) {
                int totalWidth = cachedMaxHeightAndWidthForRowMap.values().stream()
                        .mapToInt(widthAndHeight -> widthAndHeight.width).max().orElse(0);

                int columnWidth = cachedWidthAndHeightMap.get(column).width;
                int columnHeight = cachedWidthAndHeightMap.get(column).height;

                // calculate the correct columnWidth
                if (this.growType == GrowType.FIRST && columnIndex == (this.columns.size() - 1)) {
                    // calculate the available space
                    int growFirstWidth = width - currentX + x;

                    if (columnWidth < growFirstWidth) {
                        columnWidth = growFirstWidth;
                    }
                } else if (this.growType == GrowType.CENTERED) {
                    int growCenteredWidth = Math.floorDiv(width - totalUsedWidth,
                            this.columns.size() - columnIndex);

                    if (columnWidth < growCenteredWidth) {
                        columnWidth = growCenteredWidth;
                    }
                }

                totalUsedWidth += columnWidth;

                // render title if exists
                if (column.getTitle() != null) {
                    CvcGuiComponent titleComponent = column.getTitle();

                    int titleComponentHeight = titleComponent.getHeight();

                    titleComponent.draw(currentX, currentY, columnWidth, titleComponentHeight,
                            mouseX, mouseY);

                    currentY += titleComponentHeight;
                }

                // for each item we render the columns component
                for (ItemType item : items) {
                    // draw a line above the item (horizontal)
                    CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(currentX,
                            currentY, currentX + columnWidth, currentY + 1, 0xffffffff);
                    currentY += 1;

                    CvcGuiComponent itemComponent = column.getComponent(item);

                    int itemHeight = cachedMaxHeightAndWidthForRowMap.get(item).height;

                    itemComponent.draw(currentX, currentY, columnWidth, itemHeight, mouseX, mouseY);

                    currentY += itemHeight;
                }

                currentY = y;

                // check if not last column
                if (columnIndex < (columns.size() - 1)) {
                    // draw line right of column
                    CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(
                            currentX + columnWidth, currentY, currentX + columnWidth + 1,
                            currentY + columnHeight + items.size(), 0xffffffff);
                    currentX += 1;
                }

                currentX += columnWidth;
                columnIndex++;
            }
        }
    }

    @Override
    public int getHeight() {
        int totalHeight = 0;

        // calculate the height of the titles
        totalHeight += this.columns.stream()
                .mapToInt(column -> column.getTitle() == null ? 0 : column.getTitle().getHeight())
                .max().orElse(0);

        List<ItemType> items = this._supply();

        synchronized (items) {
            totalHeight += items.stream()
                    .mapToInt(item -> this.columns.stream()
                            .mapToInt(column -> column.getComponent(item).getHeight()).max()
                            .orElse(0))
                    .sum();

            // do not forget to add the lines between cells
            totalHeight += this._supply().size();
        }

        return totalHeight;
    }

    @Override
    public int getWidth() {
        int totalWidth = this.columns.stream()
                .mapToInt(column -> column.getTitle() == null ? 0 : column.getTitle().getWidth())
                .sum();

        List<ItemType> items = this._supply();

        synchronized (items) {
            for (ItemType item : items) {
                int width = this.columns.stream().mapToInt(column -> column.getWidth(item)).sum();

                if (totalWidth < width) {
                    totalWidth = width;
                }
            }
        }

        return totalWidth + (this.columns.size() - 1);
    }

    public Comparator<ItemType> sort() {
        return null;
    }

    private List<ItemType> _supply() {
        Comparator<ItemType> possibleComparator = this.sort();

        if (possibleComparator != null) {
            List<ItemType> items = this.itemSupplier.supply();

            synchronized (items) {
                items.sort(possibleComparator);

                return items;
            }
        }

        return this.itemSupplier.supply();
    }
}
