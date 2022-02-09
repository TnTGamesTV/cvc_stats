package de.throwstnt.developing.labymod.cvc.api.gui.components;

import de.throwstnt.developing.labymod.cvc.api.gui.AbstractCvcInterface;

public class CvcGuiSizedComponent extends CvcGuiComponent {

    public static class Margin {

        int topMargin;
        int leftMargin;
        int bottomMargin;
        int rightMargin;

        public Margin() {
            this(0);
        }

        public Margin(int margin) {
            this(margin, margin);
        }

        public Margin(int topAndBottomMargin, int leftAndRightMargin) {
            this(topAndBottomMargin, leftAndRightMargin, topAndBottomMargin, leftAndRightMargin);
        }

        public Margin(int topMargin, int leftMargin, int bottomMargin, int rightMargin) {
            this.topMargin = topMargin;
            this.leftMargin = leftMargin;
            this.bottomMargin = bottomMargin;
            this.rightMargin = rightMargin;
        }
    }

    private Margin margin;

    private int width;

    private int height;

    private CvcGuiComponent child;

    public CvcGuiSizedComponent(Margin margin, CvcGuiComponent child) {
        this(margin, -1, -1, child);
    }

    public CvcGuiSizedComponent(int width, int height, CvcGuiComponent child) {
        this(new Margin(), width, height, child);
    }

    public CvcGuiSizedComponent(Margin margin, int width, int height, CvcGuiComponent child) {
        this.margin = margin;
        this.width = width;
        this.height = height;
        this.child = child;
    }

    @Override
    public void init(AbstractCvcInterface rootInterface) {
        super.init(rootInterface);

        synchronized (child) {
            this.child.init(rootInterface);
        }
    }

    @Override
    public void draw(int x, int y, int width, int height, int mouseX, int mouseY) {
        super.draw(x, y, width, height, mouseX, mouseY);

        x += this.margin.leftMargin;
        width -= this.margin.leftMargin + this.margin.rightMargin;
        y += this.margin.topMargin;
        height -= this.margin.topMargin + this.margin.bottomMargin;

        if (this.width >= 0) {
            x += width / 2 - this.width / 2;
            width = this.width;
        }

        if (this.height >= 0) {
            y += height / 2 - this.height / 2;
            height = this.height;
        }

        this.child.draw(x, y, width, height, mouseX, mouseY);
    }

    @Override
    public int getWidth() {
        int totalWidth = this.width >= 0 ? this.width : this.child.getWidth();
        return totalWidth + this.margin.leftMargin + this.margin.rightMargin;
    }

    @Override
    public int getHeight() {
        int totalHeight = this.height >= 0 ? this.height : this.child.getHeight();
        return totalHeight + this.margin.topMargin + this.margin.bottomMargin;
    }
}
