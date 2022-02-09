package de.throwstnt.developing.labymod.cvc.api.gui.components.builders;

import static de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiColumnComponent.builder;
import java.util.Arrays;
import java.util.UUID;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiButtonComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiButtonComponent.IHandleClick;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiDynamicTextComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiDynamicTextComponent.ISupplyString;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiItemComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiPlayerHeadComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiSizedComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiSizedComponent.Margin;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiTextComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.container.CvcGuiContainerComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.container.CvcGuiRowComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.container.CvcGuiRowComponent.GrowType;
import de.throwstnt.developing.labymod.cvc.api.gui.components.router.CvcGuiRouteComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.router.CvcGuiRouterComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiColumnComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiTableComponent;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiTableComponent.ISupplyItems;

public class CvcGuiStaticBuilders {

    public static CvcGuiSizedComponent button(Margin margin, String text,
            IHandleClick clickHandler) {
        return sized(margin, new CvcGuiButtonComponent(text, clickHandler));
    }

    public static CvcGuiSizedComponent button(Margin margin, int width, int height, String text,
            IHandleClick clickHandler) {
        return sized(margin, width, height, new CvcGuiButtonComponent(text, clickHandler));
    }

    public static CvcGuiButtonComponent button(String text, IHandleClick clickHandler) {
        return new CvcGuiButtonComponent(text, clickHandler);
    }

    public static CvcGuiContainerComponent container(CvcGuiRowComponent... rows) {
        return new CvcGuiContainerComponent(rows);
    }

    public static CvcGuiRowComponent row(CvcGuiComponent... children) {
        return new CvcGuiRowComponent(children);
    }

    public static CvcGuiRowComponent row(GrowType growType, CvcGuiComponent... children) {
        return new CvcGuiRowComponent(growType, children);
    }

    public static CvcGuiRouteComponent route(String route, CvcGuiComponent component) {
        return new CvcGuiRouteComponent(route, component);
    }

    public static CvcGuiRouterComponent router(CvcGuiRouteComponent... routes) {
        return new CvcGuiRouterComponent(routes);
    }

    @SafeVarargs
    public static <T> CvcGuiTableComponent<T> table(ISupplyItems<T> itemSupplier,
            CvcGuiColumnComponent<T>... columns) {
        return new CvcGuiTableComponent<>(Arrays.asList(columns), itemSupplier);
    }

    @SafeVarargs
    public static <T> CvcGuiTableComponent<T> table(ISupplyItems<T> itemSupplier, GrowType growType,
            CvcGuiColumnComponent<T>... columns) {
        return new CvcGuiTableComponent<>(Arrays.asList(columns), growType, itemSupplier);
    }

    public static CvcGuiTextComponent text(String text, boolean isCentered, double fontSize) {
        return new CvcGuiTextComponent(text, isCentered, fontSize);
    }

    public static CvcGuiTextComponent text(String text, boolean isCentered) {
        return new CvcGuiTextComponent(text, isCentered);
    }

    public static CvcGuiTextComponent text(String text) {
        return new CvcGuiTextComponent(text);
    }

    public static CvcGuiItemComponent item(AbstractItem.ItemType itemType) {
        return new CvcGuiItemComponent(itemType);
    }

    public static CvcGuiItemComponent item(AbstractItem.ItemStackType itemStackType) {
        return new CvcGuiItemComponent(itemStackType);
    }

    public static CvcGuiDynamicTextComponent dynamicText(ISupplyString text, boolean isCentered,
            double fontSize) {
        return new CvcGuiDynamicTextComponent(text, isCentered, fontSize);
    }

    public static CvcGuiDynamicTextComponent dynamicText(ISupplyString text, boolean isCentered) {
        return new CvcGuiDynamicTextComponent(text, isCentered);
    }

    public static CvcGuiDynamicTextComponent dynamicText(ISupplyString text) {
        return new CvcGuiDynamicTextComponent(text);
    }

    public static <T> CvcGuiColumnComponent.Builder<T> column() {
        return builder();
    }

    public static CvcGuiPlayerHeadComponent playerHead(String name, int size) {
        return new CvcGuiPlayerHeadComponent(name, size);
    }

    public static CvcGuiPlayerHeadComponent playerHead(UUID uuid, int size) {
        return new CvcGuiPlayerHeadComponent(uuid, size);
    }

    public static CvcGuiSizedComponent sized(Margin margin, CvcGuiComponent child) {
        return new CvcGuiSizedComponent(margin, child);
    }

    public static CvcGuiSizedComponent sized(int width, int height, CvcGuiComponent child) {
        return new CvcGuiSizedComponent(width, height, child);
    }

    public static CvcGuiSizedComponent sized(Margin margin, int width, int height,
            CvcGuiComponent child) {
        return new CvcGuiSizedComponent(margin, width, height, child);
    }
}
