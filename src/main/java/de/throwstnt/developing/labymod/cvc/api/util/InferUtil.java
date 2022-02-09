package de.throwstnt.developing.labymod.cvc.api.util;

import static de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiColumnComponent.builder;
import de.throwstnt.developing.labymod.cvc.api.gui.components.table.CvcGuiColumnComponent;

public class InferUtil {

    public static <T> CvcGuiColumnComponent.Builder<T> column() {
        return builder();
    }
}
