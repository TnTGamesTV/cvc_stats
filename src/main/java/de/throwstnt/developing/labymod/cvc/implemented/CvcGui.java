package de.throwstnt.developing.labymod.cvc.implemented;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcAddonManager;
import de.throwstnt.developing.labymod.cvc.api.gui.ImplementedCvcInterface;
import de.throwstnt.developing.labymod.cvc.api.util.MathUtil;
import net.labymod.gui.elements.Tabs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class CvcGui extends Screen {

    private ImplementedCvcInterface cvcInterface;

    private int x;
    private int y;
    private int correctWidth;
    private int correctHeight;

    /**
     * The height of the content. Gets calculate on each render
     */
    private int contentHeight;

    private double scrollPercentage;
    private double scrollDelta;

    public CvcGui() {
        super(new StringTextComponent("Cvc Stats Gui"));

        this.cvcInterface = new ImplementedCvcInterface();
    }

    @Override
    protected void init() {
        this.cvcInterface.init();
        CvcAddonManager.getInstance().get().getGuiAdapter().setDebugEnabled(false);

        this.x = 0;
        this.y = 0 + 30;
        this.correctWidth = this.width;
        this.correctHeight = this.height - 30;

        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        CvcStats.getInstance().getImplementedCvcStats().getGuiAdapter()
                .setGlobalRenderContext(matrixStack);

        this.contentHeight = this.cvcInterface.getHeight();

        double maxScrollY = this.contentHeight - this.correctHeight;

        this.cvcInterface.render(x, 30 + (int) (maxScrollY * this.scrollPercentage * -1),
                correctWidth, correctHeight, mouseX, mouseY, partialTicks, 0);

        // scrollbar
        double scrollAmount = (double) this.correctHeight / (double) this.contentHeight;
        int scrollWidth = 6;
        int scrollHeight = (int) (scrollAmount * this.correctHeight);
        int scrollX = this.correctWidth - 6;
        int scrollY = (int) (30 + this.scrollPercentage * (this.correctHeight - scrollHeight));

        CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(scrollX, scrollY,
                scrollX + scrollWidth, scrollY + scrollHeight, 0x80000000);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        // draw background for the tabs
        CvcAddonManager.getInstance().get().getGuiAdapter().drawRectangle(0, 0, this.correctWidth,
                30, 0xff000000);

        Tabs.drawScreen(this, matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        double deltaBoost = 10.0;

        this.scrollDelta = MathUtil.clampDouble(0, this.contentHeight,
                scrollDelta + (delta * deltaBoost) * -1);
        this.scrollPercentage = this.scrollDelta / (double) this.contentHeight;

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        Tabs.mouseClicked(this);

        this.cvcInterface.handleClick((int) mouseX, (int) mouseY, mouseButton);

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX,
            double dragY) {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
        return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
    }
}
