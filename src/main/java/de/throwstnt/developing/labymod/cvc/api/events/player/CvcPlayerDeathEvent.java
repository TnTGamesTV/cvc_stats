package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.data.stats.OtherType;
import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

public class CvcPlayerDeathEvent extends CvcPlayerEvent {

    public enum DeathReason {
        ENEMY, SELF
    }

    private DeathReason deathReason;

    private CvcPlayer killer;

    /**
     * The weapon used or null if fall damage
     */
    private WeaponType weaponType;
    private OtherType otherType;

    /**
     * Is automatically true for items like knifes, grenades or fall damage
     */
    private boolean isHeadshot;

    private long atMillisAfterRoundStart;

    public CvcPlayerDeathEvent(CvcPlayer player, CvcPlayer killer, DeathReason deathReason,
            WeaponType weaponType, OtherType otherType, boolean isHeadshot,
            long atMillisAfterRoundStart) {
        super(player);

        this.deathReason = deathReason;
        this.weaponType = weaponType;
        this.killer = killer;
        this.otherType = otherType;
        this.isHeadshot = isHeadshot;
        this.atMillisAfterRoundStart = atMillisAfterRoundStart;
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

    public CvcPlayer getKiller() {
        return killer;
    }

    public OtherType getOtherType() {
        return otherType;
    }

    public long getAtMillisAfterRoundStart() {
        return atMillisAfterRoundStart;
    }
}
