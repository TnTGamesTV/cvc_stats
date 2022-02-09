package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

/**
 * Event gets triggered when a player has achieved a kill
 */
public class CvcPlayerKillEvent extends CvcPlayerEvent {

    private WeaponType weaponType;
    private boolean isHeadshot;
    private long atSecondsAfterRoundStart;

    public CvcPlayerKillEvent(CvcPlayer player, WeaponType weaponType, boolean isHeadshot,
            long atSecondsAfterRoundStart) {
        super(player);

        this.weaponType = weaponType;
        this.isHeadshot = isHeadshot;
        this.atSecondsAfterRoundStart = atSecondsAfterRoundStart;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public boolean isHeadshot() {
        return isHeadshot;
    }

    public long getAtSecondsAfterRoundStart() {
        return atSecondsAfterRoundStart;
    }
}
