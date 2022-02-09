package de.throwstnt.developing.labymod.cvc.api.game.managers;

import de.throwstnt.developing.labymod.cvc.api.AbstractCvcStats;

public class CvcAddonManager {

    private static CvcAddonManager instance;

    public static CvcAddonManager getInstance() {
        if (instance == null)
            instance = new CvcAddonManager();
        return instance;
    }

    private AbstractCvcStats<?, ?, ?, ?> cvcStats;

    /**
     * Get the addon
     * 
     * @return
     */
    public AbstractCvcStats<?, ?, ?, ?> get() {
        return this.cvcStats;
    }

    /**
     * Register the implemented cvc stats
     * 
     * @param cvcStats the cvc stats
     */
    public void register(AbstractCvcStats<?, ?, ?, ?> cvcStats) {
        this.cvcStats = cvcStats;
    }
}
