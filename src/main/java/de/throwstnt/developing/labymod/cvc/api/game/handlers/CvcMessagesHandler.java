package de.throwstnt.developing.labymod.cvc.api.game.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.throwstnt.developing.labymod.cvc.api.data.stats.OtherType;
import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventHandler;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventListener;
import de.throwstnt.developing.labymod.cvc.api.events.CvcEventManager;
import de.throwstnt.developing.labymod.cvc.api.events.minecraft.CvcMinecraftChatMessageEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerDeathEvent;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerDeathEvent.DeathReason;
import de.throwstnt.developing.labymod.cvc.api.events.player.CvcPlayerKillEvent;
import de.throwstnt.developing.labymod.cvc.api.game.CvcPlayer;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcPlayerManager;
import de.throwstnt.developing.labymod.cvc.api.game.managers.CvcRoundManager;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;
import de.throwstnt.developing.labymod.cvc.api.util.SymbolLibrary;

public class CvcMessagesHandler implements CvcEventListener {

    private static CvcMessagesHandler instance;

    public static CvcMessagesHandler getInstance() {
        if (instance == null)
            instance = new CvcMessagesHandler();
        return instance;
    }

    private static Pattern CONST_KILL_PATTERN =
            Pattern.compile("([a-zA-Z0-9_]{1,16})\\s\\W{1,3}\\s([a-zA-Z0-9_]{1,16})");
    private static Pattern CONST_DEATH_PATTERN = Pattern.compile("\\W{1,3}\\s([a-zA-Z0-9_]{1,16})");

    private CvcMessagesHandler() {}

    @CvcEventHandler
    public void onMessage(CvcMinecraftChatMessageEvent event) {
        // check if we even have any symbol in the message
        if (this._hasAnySymbolInMessage(event.getMessage())) {
            String message = ChatUtil.cleanColorCoding(event.getMessage());

            // the weapon used (excluding grenades)
            WeaponType weaponType = SymbolLibrary.weaponFromChatMessage(message);

            // the other death reason (only used when weapon type is null)
            OtherType otherType = SymbolLibrary.otherFromChatMessage(message);

            // if the action contained a headshot symbol (can only be used for weapons that shoot
            // bullets)
            boolean isHeadshot = this._hasHeadshotSymbolInMessage(message);

            Matcher killMatcher = CONST_KILL_PATTERN.matcher(message);
            Matcher deathMatcher = CONST_DEATH_PATTERN.matcher(message);

            if (killMatcher.find()) {
                // we have a kill
                String killerName = killMatcher.group(1);
                String otherName = killMatcher.group(2);

                this._addKill(killerName, otherName, weaponType, otherType, isHeadshot);
            } else if (deathMatcher.find()) {
                // no kill but at least a death
                String otherName = deathMatcher.group(1);

                this._addDeath(otherName, otherType);
            } else {
                ChatUtil.log(
                        "Could not match the following message for weapon " + weaponType.getName());
                ChatUtil.log(message);
            }
        }
    }

    private boolean _hasAnySymbolInMessage(String message) {
        return SymbolLibrary.hasWeaponOrOtherType(message);
    }

    private boolean _hasHeadshotSymbolInMessage(String message) {
        return message.contains(SymbolLibrary.otherToSymbol(OtherType.HEADSHOT));
    }

    private void _addKill(String killerName, String otherName, WeaponType weaponType,
            OtherType otherType, boolean isHeadshot) {
        CvcPlayer killer = CvcPlayerManager.getInstance().getPlayer(killerName);
        CvcPlayer other = CvcPlayerManager.getInstance().getPlayer(otherName);

        if (killer != null && other != null) {
            isHeadshot = isHeadshot || (weaponType != null && weaponType == WeaponType.KNIFE)
                    || OtherType.isGrenade(otherType);

            CvcEventManager.getInstance()
                    .fireEvent(new CvcPlayerKillEvent(killer, other, weaponType, otherType,
                            isHeadshot, System.currentTimeMillis()
                                    - CvcRoundManager.getInstance().getLastRoundStart()));

            CvcEventManager.getInstance()
                    .fireEvent(new CvcPlayerDeathEvent(other, killer, DeathReason.ENEMY, weaponType,
                            otherType, isHeadshot, System.currentTimeMillis()
                                    - CvcRoundManager.getInstance().getLastRoundStart()));
        } else {
            ChatUtil.log("Couldn't find a cvc player for " + killerName + " or " + otherName);
        }
    }

    private void _addDeath(String otherName, OtherType otherType) {
        CvcPlayer other = CvcPlayerManager.getInstance().getPlayer(otherName);

        if (other != null) {
            CvcEventManager.getInstance()
                    .fireEvent(new CvcPlayerDeathEvent(other, null, DeathReason.SELF, null,
                            otherType, true, System.currentTimeMillis()
                                    - CvcRoundManager.getInstance().getLastRoundStart()));
        }
    }

}
