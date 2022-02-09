package de.throwstnt.developing.labymod.cvc.api.events.gui;

public class CvcGuiRouteChangedEvent extends CvcGuiEvent {

    private String newRoute;

    public CvcGuiRouteChangedEvent(String newRoute) {
        this.newRoute = newRoute;
    }

    public String getNewRoute() {
        return newRoute;
    }
}
