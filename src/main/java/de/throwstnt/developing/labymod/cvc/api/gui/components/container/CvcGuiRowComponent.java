package de.throwstnt.developing.labymod.cvc.api.gui.components.container;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.google.common.collect.Lists;
import de.throwstnt.developing.labymod.cvc.api.gui.AbstractCvcInterface;
import de.throwstnt.developing.labymod.cvc.api.gui.components.CvcGuiComponent;

public class CvcGuiRowComponent extends CvcGuiComponent {

    public static enum GrowType {
        FIRST, CENTERED;
    }

    private GrowType growType;

    private List<CvcGuiComponent> children;

    public CvcGuiRowComponent(CvcGuiComponent... children) {
        this(GrowType.CENTERED, children);
    }

    public CvcGuiRowComponent(GrowType growType, CvcGuiComponent... children) {
        this.growType = growType;
        this.children = Lists.newArrayList(children);
    }

    @Override
    public void init(AbstractCvcInterface rootInterface) {
        super.init(rootInterface);

        synchronized (children) {
            this.children.forEach(child -> child.init(rootInterface));
        }
    }

    @Override
    public int getWidth() {
        return this.getTotalWidth();
    }

    @Override
    public int getHeight() {
        return this.getMaximumHeight();
    }

    /**
     * Get the total height of the row Which is the maximum height of all containers
     * 
     * @return the maxium height
     */
    public int getMaximumHeight() {
        synchronized (children) {
            return this.getMaximumHeight(this.children);
        }
    }

    public int getMaximumHeight(List<CvcGuiComponent> children) {
        synchronized (children) {
            return children.stream().mapToInt((child) -> child.getHeight()).max().orElse(0);
        }
    }

    /**
     * Returns the sum of widths of all containers
     * 
     * @return
     */
    public int getTotalWidth() {
        synchronized (children) {
            return this.getTotalWidth(this.children);
        }
    }

    private int getTotalWidth(List<CvcGuiComponent> children) {
        synchronized (children) {
            return children.stream().mapToInt((child) -> child.getWidth()).sum();
        }
    }

    public List<CvcGuiComponent> getOverflowingChildren(int allowedWidth) {
        synchronized (children) {
            return this.getOverflowingChildren(allowedWidth, this.children);
        }
    }

    public List<CvcGuiComponent> getOverflowingChildren(int allowedWidth,
            List<CvcGuiComponent> children) {
        synchronized (children) {
            List<CvcGuiComponent> overflowingChildren = new ArrayList<>();

            int currentSubListEnd = children.size();
            boolean isOverflowingAndMoreThanOne = true;
            while (isOverflowingAndMoreThanOne) {
                List<CvcGuiComponent> subContainers = children.subList(0, currentSubListEnd);

                isOverflowingAndMoreThanOne = (this.getTotalWidth(subContainers) > allowedWidth)
                        && subContainers.size() > 1;

                if (isOverflowingAndMoreThanOne) {
                    currentSubListEnd -= 1;
                    overflowingChildren.add(children.get(currentSubListEnd));
                }
            }

            return Lists.reverse(overflowingChildren);
        }
    }

    public List<CvcGuiComponent> getNotOverflowingChildren(int allowedWidth) {
        List<CvcGuiComponent> overflowingChildren = this.getOverflowingChildren(allowedWidth);

        synchronized (children) {
            return this.children.stream().filter((child) -> !overflowingChildren.contains(child))
                    .collect(Collectors.toList());
        }
    }

    public List<CvcGuiComponent> getNotOverflowingChildren(int allowedWidth,
            List<CvcGuiComponent> containers) {
        List<CvcGuiComponent> overflowingChildren =
                this.getOverflowingChildren(allowedWidth, containers);

        synchronized (containers) {
            return containers.stream().filter((child) -> !overflowingChildren.contains(child))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Returns true if the total width of the row extends past the given allowed width
     * 
     * @param allowedWidth the allowed width
     * @return true if the row extends past the given allowed width
     */
    public boolean doesExtendPastAllowedWidth(int allowedWidth) {
        return this.getTotalWidth() > allowedWidth;
    }

    public GrowType getGrowType() {
        return growType;
    }
}
