package de.throwstnt.developing.labymod.cvc.api.gui.components.router;

import de.throwstnt.developing.labymod.cvc.api.gui.AbstractCvcInterface;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;

public class CvcGuiRouteComponent extends CvcGuiComponent {

    private String route;

    private CvcGuiComponent component;

    public CvcGuiRouteComponent(String route, CvcGuiComponent component) {
        this.route = route;
        this.component = component;
    }

    @Override
    public int getHeight() {
        return this.component.getHeight();
    }

    @Override
    public int getWidth() {
        return this.component.getWidth();
    }

    @Override
    public void init(AbstractCvcInterface rootInterface) {
        super.init(rootInterface);

        this.component.init(rootInterface);
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        super.draw(x, y, width, height, mouseX, mouseY);

        this.component.draw(x, y, width, height, mouseX, mouseY);
    }

    public String getRoute() {
        return route;
    }
}
