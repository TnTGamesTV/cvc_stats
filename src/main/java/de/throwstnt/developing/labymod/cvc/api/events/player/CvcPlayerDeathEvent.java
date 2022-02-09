package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

public class CvcPlayerDeathEvent extends CvcPlayerEvent {

    public enum DeathReason {
        ENEMY, SELF
    }

    private DeathReason deathReason;

    // TODO: Add reference to killer

    /**
     * The weapon used or null if fall damage
     */
    private WeaponType weaponType;

    /**
     * Is automatically true for items like knifes, grenades or fall damage
     */
    private boolean isHeadshot;

    public CvcPlayerDeathEvent(CvcPlayer player, DeathReason deathReason, WeaponType weaponType,
            boolean isHeadshot) {
        super(player);

        this.deathReason = deathReason;
        this.weaponType = weaponType;
        this.isHeadshot = isHeadshot;
    }

    public DeathReason getDeathReason() {
        return deathReason;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public boolean isHeadshot() {
        return isHeadshot;
    }
}
