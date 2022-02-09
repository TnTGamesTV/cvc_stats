package de.throwstnt.developing.labymod.cvc.implemented;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import de.throwstnt.developing.labymod.cvc.api.data.ApiCoordinator;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcStateManager;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;
import de.throwstnt.developing.labymod.cvc.implemented.adapters.ImplementedCvcStats;
import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.Tabs;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Material;

public class CvcStats extends LabyModAddon {
    public static final ModuleCategory CONST_HYPIXEL_SERVICES = new ModuleCategory(
            "Hypixel Services", true, new ControlElement.IconData(Material.GOLD_BLOCK));

    private static CvcStats instance;

    public static CvcStats getInstance() {
        return instance;
    }

    private boolean enabled;
    private boolean disableChatMessage;
    private boolean enableApiStatus;
    private String apiKey;

    private ImplementedCvcStats implementedCvcStats;

    @Override
    protected void fillSettings(List<SettingsElement> subSettings) {
        subSettings.add(new BooleanElement("Enabled", this,
                new ControlElement.IconData(Material.LEVER), "enabled", this.enabled));

        subSettings.add(new StringElement("API-Key", this,
                new ControlElement.IconData(Material.PAPER), "apiKey", this.apiKey));
        subSettings.add(new BooleanElement("Disable Chat Messages", this,
                new ControlElement.IconData(Material.DISPENSER), "disableChatMessage",
                this.disableChatMessage));
        subSettings.add(new BooleanElement("Inform when Public API is slow", this,
                new ControlElement.IconData(Material.SOUL_SAND), "enableApiStatus",
                this.enableApiStatus));
    }

    @Override
    public void loadConfig() {
        this.enabled =
                getConfig().has("enabled") ? getConfig().get("enabled").getAsBoolean() : false;

        this.apiKey = getConfig().has("apiKey") ? getConfig().get("apiKey").getAsString() : "";

        this.disableChatMessage = getConfig().has("disableChatMessage")
                ? getConfig().get("disableChatMessage").getAsBoolean()
                : false;

        this.enableApiStatus = getConfig().has("enableApiStatus")
                ? getConfig().get("enableApiStatus").getAsBoolean()
                : false;

        if (this.apiKey.length() > 0) {
            ChatUtil.log("Updated the api key and hypixelApi");
            ApiCoordinator.onApiKey(apiKey);
        }

        // TODO: Add api status checker
        /*
         * if (this.enableApiStatus) { ApiChecker.getInstance().start(); } else {
         * ApiChecker.getInstance().stop(); }
         */
    }

    @Override
    public void onEnable() {
        instance = this;
        this.implementedCvcStats = new ImplementedCvcStats();

        // register the custom category
        ModuleCategoryRegistry.loadCategory(CONST_HYPIXEL_SERVICES);

        // register modules
        // api.registerModule(new InGameKillsModule());

        /*
         * this.tabListOverlay = new GuiPlayerTabListOverlay(Minecraft.getInstance(),
         * Minecraft.getInstance().ingameGUI);
         * 
         * Tabs.registerTab("CvC Stats", new RedirectionGui());
         */

        Tabs.registerTab("Cvc Stats", CvcGui.class);

        TimerTask checkStateTask = new TimerTask() {

            @Override
            public void run() {
                try {
                    CvcStateManager.getInstance().checkState();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };

        Timer timer = new Timer("CvCAddon-Timer");
        timer.schedule(checkStateTask, 500, 500);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDisableChatMessage() {
        return disableChatMessage;
    }

    public boolean isEnableApiStatus() {
        return enableApiStatus;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ImplementedCvcStats getImplementedCvcStats() {
        return implementedCvcStats;
    }
}
