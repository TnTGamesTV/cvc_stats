package de.throwstnt.developing.labymod.cvc.api.gui.components.router;

import static de.throwstnt.developing.labymod.cvc.api.gui.components.builders.CvcGuiStaticBuilders.text;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.gui.AbstractCvcInterface;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;

public class CvcGuiRouterComponent extends CvcGuiComponent {

    private List<CvcGuiRouteComponent> routes;

    private Map<String, CvcGuiRouteComponent> routeComponentMatchCache = new HashMap<>();

    public CvcGuiRouterComponent(CvcGuiRouteComponent... routes) {
        this.routes = Arrays.asList(routes);

        if (CvcAddonManager.getInstance().get().getGuiAdapter().getRoute() == null) {
            CvcAddonManager.getInstance().get().getGuiAdapter().setRoute("/");
        }
    }

    /**
     * Get the current route
     * 
     * @return the current route
     */
    private String _getCurrentRoute() {
        return CvcAddonManager.getInstance().get().getGuiAdapter().getRoute();
    }

    private boolean _matchesCurrentRoute(CvcGuiRouteComponent route) {
        return route.getRoute().equals(this._getCurrentRoute());
    }

    /**
     * Finds the current route component to render
     * 
     * @return the current component to render
     */
    private CvcGuiComponent _findComponent() {
        if (this.routeComponentMatchCache.containsKey(this._getCurrentRoute())) {
            return this.routeComponentMatchCache.get(this._getCurrentRoute());
        }


        Optional<CvcGuiRouteComponent> possibleRouteComponent =
                this.routes.stream().filter(this::_matchesCurrentRoute).findFirst();
        if (possibleRouteComponent.isPresent()) {
            this.routeComponentMatchCache.put(this._getCurrentRoute(),
                    possibleRouteComponent.get());

            return possibleRouteComponent.get();
        } else {
            return text("No route found");
        }
    }

    @Override
    public void init(AbstractCvcInterface rootInterface) {
        super.init(rootInterface);

        // this.routes.forEach(route -> route.init(rootInterface));
        this._findComponent().init(rootInterface);
    }

    @Override
    public int getHeight() {
        return this._findComponent().getHeight();
    }

    @Override
    public int getWidth() {
        return this._findComponent().getWidth();
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        super.draw(x, y, width, height, mouseX, mouseY);

        this._findComponent().draw(x, y, width, height, mouseX, mouseY);
    }
}
