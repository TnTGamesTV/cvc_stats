package de.throwstnt.developing.labymod.cvc.api.data.stats.game;

import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;

public class CvcWeaponGameStats {

    /**
     * The weapon
     */
    public WeaponType type;

    /**
     * The total kills with this weapon
     */
    public int kills;

    /**
     * The total headshots with this weapon
     */
    public int headshots;

    /**
     * The accuracy with the weapon (0-1)
     */
    public double virtualHeadshotAccuracy;

    public CvcWeaponGameStats(WeaponType type) {
        this.type = type;

        this.kills = 0;
        this.headshots = 0;

        this.updateVirtuals();
    }

    /**
     * Updates all virtual scores
     */
    public void updateVirtuals() {
        this.virtualHeadshotAccuracy = (double) this.headshots / Math.max((double) this.kills, 1.0);
    }
}
