package de.throwstnt.developing.labymod.cvc.api.gui;

import java.util.ArrayList;
import java.util.List;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.gui.CvcGuiRouteChangedEvent;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;

/**
 * The root interface of the cvc stats addon
 */
public class AbstractCvcInterface implements CvcEventListener {

    public interface IHandleClick {

        void handleClick(int mouseX, int mouseY, int mouseButton);
    }

    private CvcGuiComponent rootComponent;

    private List<IHandleClick> clickListeners;

    public AbstractCvcInterface(CvcGuiComponent rootComponent) {
        this.rootComponent = rootComponent;
    }

    /**
     * Returns the height of the interface
     * 
     * @return the height of the interface
     */
    public int getHeight() {
        return this.rootComponent.getHeight();
    }

    public void addClickListener(IHandleClick listener) {
        this.clickListeners.add(listener);
    }

    public void init() {
        this.clickListeners = new ArrayList<>();

        this.rootComponent.init(this);
        CvcEventManager.getInstance().registerListeners(this);
    }

    @CvcEventHandler
    public void onRouteChange(CvcGuiRouteChangedEvent event) {
        this.clickListeners = new ArrayList<>();

        this.rootComponent.init(this);
    }

    public void render(int x, int y, int width, int height, int mouseX, int mouseY,
            float partialTicks, double scrollAmount) {
        CvcAddonManager.getInstance().get().getGuiAdapter().drawBackground();

        int totalHeight = this.getHeight();
        int correctX = (int) (x * scrollAmount * -1);

        this.rootComponent.draw(correctX, y, width, height, mouseX, mouseY);
    }

    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        synchronized (clickListeners) {
            this.clickListeners
                    .forEach(listener -> listener.handleClick(mouseX, mouseY, mouseButton));
        }
    }
}
