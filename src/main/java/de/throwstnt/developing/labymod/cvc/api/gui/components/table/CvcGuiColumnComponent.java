package de.throwstnt.developing.labymod.cvc.api.gui.components.table;

import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiBuilder;

/**
 * A single column inside of a table
 *
 * @param <ItemType> the type of the items displayed
 */
public class CvcGuiColumnComponent<ItemType> {

    public static class Builder<Type> extends CvcGuiBuilder<CvcGuiColumnComponent<Type>> {

        private int minWidth = 0;

        private CvcGuiComponent title = null;

        private IGetComponent<Type> componentGetter;

        public Builder<Type> minWidth(int minWidth) {
            this.minWidth = minWidth;
            return this;
        }

        public Builder<Type> title(CvcGuiComponent title) {
            this.title = title;
            return this;
        }

        public Builder<Type> components(IGetComponent<Type> componentGetter) {
            this.componentGetter = componentGetter;
            return this;
        }

        @Override
        public CvcGuiColumnComponent<Type> build() {
            return new CvcGuiColumnComponent<Type>(title, minWidth, componentGetter);
        }
    }

    public static <T> CvcGuiColumnComponent.Builder<T> builder() {
        return new CvcGuiColumnComponent.Builder<T>();
    }

    /**
     * Returns the component for the given item
     * 
     * @param <ItemType> the type of the item
     */
    public interface IGetComponent<ItemType> {

        /**
         * Get the component from the given item
         * 
         * @param item the item
         * @return the component
         */
        CvcGuiComponent getComponent(ItemType item);
    }

    /**
     * The minimum width of a column
     */
    private int minWidth;

    /**
     * The title component or null for none
     */
    private CvcGuiComponent title;

    /**
     * The component getter
     */
    private IGetComponent<ItemType> componentGetter;

    private ItemType lastItem;
    private CvcGuiComponent lastComponent;

    public CvcGuiColumnComponent(IGetComponent<ItemType> componentGetter) {
        this(null, componentGetter);
    }

    public CvcGuiColumnComponent(CvcGuiComponent title,
            IGetComponent<ItemType> componentGetter) {
        this(title, 0, componentGetter);
    }

    public CvcGuiColumnComponent(CvcGuiComponent title, int minWidth,
            IGetComponent<ItemType> componentGetter) {
        this.title = title;
        this.minWidth = minWidth;
        this.componentGetter = componentGetter;
    }

    public void draw(ItemType item, int x, int y, int width, int height, int mouseX, int mouseY) {
        CvcGuiComponent component = this.getComponent(item);

        component.draw(x, y, width, height, mouseX, mouseY);
    }

    public int getHeight(ItemType item) {
        return this.getComponent(item).getHeight();
    }

    public int getWidth(ItemType item) {
        int width = this.getComponent(item).getWidth();

        return Math.max(width, minWidth);
    }

    public int getMinWidth() {
        return minWidth;
    }

    public CvcGuiComponent getTitle() {
        return title;
    }

    public CvcGuiComponent getComponent(ItemType item) {
        if (this.lastItem != null && this.lastComponent != null) {
            if (this.lastItem.equals(item)) {
                return this.lastComponent;
            }
        }

        this.lastItem = item;
        this.lastComponent = this.componentGetter.getComponent(item);

        return this.lastComponent;
    }
}
