package de.throwstnt.developing.labymod.cvc.api.events.player;

import de.throwstnt.developing.labymod.cvc.api.data.stats.OtherType;
import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;

/**
 * Event gets triggered when a player has achieved a kill
 */
public class CvcPlayerKillEvent extends CvcPlayerEvent {

    private CvcPlayer killed;

    private WeaponType weaponType;
    private OtherType otherType;
    private boolean isHeadshot;
    private long atMillisAfterRoundStart;

    public CvcPlayerKillEvent(CvcPlayer player, CvcPlayer killed, WeaponType weaponType,
            OtherType otherType, boolean isHeadshot, long atMillisAfterRoundStart) {
        super(player);

        this.weaponType = weaponType;
        this.killed = killed;
        this.otherType = otherType;
        this.isHeadshot = isHeadshot;
        this.atMillisAfterRoundStart = atMillisAfterRoundStart;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public boolean isHeadshot() {
        return isHeadshot;
    }

    public long getAtMillisAfterRoundStart() {
        return atMillisAfterRoundStart;
    }

    public OtherType getOtherType() {
        return otherType;
    }

    public CvcPlayer getKilled() {
        return killed;
    }
}
