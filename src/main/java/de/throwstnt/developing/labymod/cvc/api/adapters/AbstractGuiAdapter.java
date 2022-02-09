package de.throwstnt.developing.labymod.cvc.api.adapters;

import java.util.Random;
import java.util.Stack;
import java.util.UUID;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.gui.CvcGuiRouteChangedEvent;

/**
 * The adapter that handels contact with the interface
 */
public abstract class AbstractGuiAdapter<RenderContext, ItemStackType> {

    private String currentRoute;

    private Stack<String> history = new Stack<>();

    /**
     * Set a new route
     * 
     * @param route
     */
    public void setRoute(String route) {
        if (this.currentRoute == null)
            this.currentRoute = route;

        if (this.currentRoute != null && !this.currentRoute.equals(route)) {
            history.add(currentRoute);
            this.currentRoute = route;

            CvcEventManager.getInstance().fireEvent(new CvcGuiRouteChangedEvent(route));
        }
    }

    /**
     * Get the current route
     * 
     * @return the route
     */
    public String getRoute() {
        return this.currentRoute;
    }

    /**
     * Go back in the route history by one
     * 
     * @return the new (old) route
     */
    public String back() {
        if (this.history.size() >= 1) {
            String backRoute = this.history.pop();

            this.currentRoute = backRoute;
            return backRoute;
        } else {
            return this.currentRoute;
        }
    }

    /**
     * Calculates the width of a string
     * 
     * @param text the string
     * @return the width
     */
    public abstract int getWidthForString(String text);

    /**
     * Return the font height
     * 
     * @return the font height
     */
    public abstract int getFontHeight();

    /**
     * Get the width of the screen
     * 
     * @return the width
     */
    public abstract int getWidth();

    /**
     * Get the height of the screen
     * 
     * @return the height
     */
    public abstract int getHeight();

    private RenderContext globalRenderContext;

    /**
     * Get the global context
     * 
     * @return the global context
     */
    public RenderContext getGlobalRenderContext() {
        return this.globalRenderContext;
    }

    private Random random = new Random();

    public int generateRandomColor() {
        int[] rgb = new int[4];
        rgb[0] = 255;
        for (int i = 1; i < 4; i++) {
            rgb[i] = random.nextInt(255);
        }

        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private boolean isDebugEnabled;

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public void setDebugEnabled(boolean isDebugEnabled) {
        this.isDebugEnabled = isDebugEnabled;
    }

    /**
     * Sets the global render context before each render
     * 
     * @param globalRenderContext
     */
    public void setGlobalRenderContext(RenderContext globalRenderContext) {
        this.globalRenderContext = globalRenderContext;
    }

    public abstract void drawBackground();

    /**
     * Draws a colored rectangle for the component
     * 
     * @param context the title of the component
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param color the color
     */
    public abstract void drawDebugBackground(String context, int x, int y, int width, int height,
            int color);

    /**
     * Draw a string
     * 
     * @param renderContext the context
     * @param text the text
     * @param x the x
     * @param y the y
     * @param size the font size
     * @param isCentered if the string is centered
     */
    public abstract void drawString(String text, int x, int y, double size, boolean isCentered);

    /**
     * Draws a rectangle
     * 
     * @param x0 the left
     * @param y0 the top
     * @param x1 the right
     * @param y1 the bottom
     * @param color the color
     */
    public abstract void drawRectangle(int x0, int y0, int x1, int y1, int color);

    /**
     * Draws a players head
     * 
     * @param name the name of the player
     * @param x the x
     * @param y the y
     * @param size the size
     */
    public abstract void drawPlayerHead(String name, int x, int y, int size);

    /**
     * Draws a players head
     * 
     * @param uuid the uuid of the player
     * @param x the x
     * @param y the y
     * @param size the size
     */
    public abstract void drawPlayerHead(UUID uuid, int x, int y, int size);

    /**
     * Draws an item
     * 
     * @param itemStack the item
     * @param x the x
     * @param y the y
     */
    public abstract void drawItemStack(AbstractItem.ItemStackType itemStack, int x, int y);
}
