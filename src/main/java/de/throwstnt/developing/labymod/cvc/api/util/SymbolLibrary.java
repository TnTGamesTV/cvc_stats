package de.throwstnt.developing.labymod.cvc.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import de.throwstnt.developing.labymod.cvc.api.data.stats.OtherType;
import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;


public class SymbolLibrary {

    public static final String COP_SYMBOL = "銐";

    public static final String CRIM_SYMBOL = "銑";

    private static Map<String, WeaponType> symbolToWeaponMap;

    private static Map<String, OtherType> symbolToOtherMap;

    static {
        symbolToWeaponMap = new HashMap<>();

        symbolToWeaponMap.put("銜銝", WeaponType.AUTO_SHOTGUN);
        symbolToWeaponMap.put("銔銕", WeaponType.BULLPUP);
        symbolToWeaponMap.put("銈銉", WeaponType.CARBINE); // facing right
        symbolToWeaponMap.put("鉦鉧", WeaponType.CARBINE); // facing left
        symbolToWeaponMap.put("銟", WeaponType.HANDGUN);
        symbolToWeaponMap.put("銌", WeaponType.KNIFE); // facing right
        symbolToWeaponMap.put("鉯", WeaponType.KNIFE); // facing left
        symbolToWeaponMap.put("銎", WeaponType.MAGNUM); // facing right
        symbolToWeaponMap.put("鉡", WeaponType.MAGNUM); // facing left
        symbolToWeaponMap.put("銏", WeaponType.PISTOL); // facing right
        symbolToWeaponMap.put("鉠", WeaponType.PISTOL); // facing left
        symbolToWeaponMap.put("銆銇", WeaponType.RIFLE); // facing right
        symbolToWeaponMap.put("鉨鉩", WeaponType.RIFLE); // facing left
        symbolToWeaponMap.put("銘銙", WeaponType.SCOPED_RIFLE);
        symbolToWeaponMap.put("銊銋", WeaponType.SHOTGUN); // facing right
        symbolToWeaponMap.put("鉤鉥", WeaponType.SHOTGUN); // facing left
        symbolToWeaponMap.put("銍", WeaponType.SMG); // facing right
        symbolToWeaponMap.put("鉢", WeaponType.SMG); // facing left
        symbolToWeaponMap.put("銄銅", WeaponType.SNIPER); // facing right
        symbolToWeaponMap.put("鉪鉫", WeaponType.SNIPER); // facing left

        symbolToOtherMap = new HashMap<>();

        symbolToOtherMap.put("鉼", OtherType.ARMOR_CHESTPLATE); // same as one below
        symbolToOtherMap.put("鉾", OtherType.ARMOR_CHESTPLATE);
        symbolToOtherMap.put("鉽", OtherType.ARMOR_HELMET); // same as one below
        symbolToOtherMap.put("鉿", OtherType.ARMOR_HELMET);
        symbolToOtherMap.put("鉶", OtherType.C4);
        symbolToOtherMap.put("鉺", OtherType.DECOY_GRENADE);
        symbolToOtherMap.put("鉱鉲", OtherType.FALL_DAMAGE);
        symbolToOtherMap.put("鉷", OtherType.FLASH_GRENADE);
        symbolToOtherMap.put("銃", OtherType.FRAG_GRENADE); // facing right
        symbolToOtherMap.put("鉬", OtherType.FRAG_GRENADE); // facing left
        symbolToOtherMap.put("鉰", OtherType.HEADSHOT);
        symbolToOtherMap.put("銀", OtherType.HEALTH);
        symbolToOtherMap.put("鉵", OtherType.OBJECTIVE_COMPLETE);
        symbolToOtherMap.put("鉴", OtherType.OBJECTIVE_INCOMPLETE);
        symbolToOtherMap.put("鉸", OtherType.SMOKE_GRENADE);
        symbolToOtherMap.put("銐", OtherType.TEAM_COPS);
        symbolToOtherMap.put("銑", OtherType.TEAM_CRIMS);
        symbolToOtherMap.put("鉻", OtherType.WIRE_CUTTERS);
    }

    /**
     * Gets the first symbol of a given weapon
     * 
     * @param type the weapon
     * @return the first symbol
     */
    public static String weaponToSymbol(WeaponType type) {
        Optional<String> symbol =
                symbolToWeaponMap.entrySet().stream().filter(entry -> entry.getValue().equals(type))
                        .map(entry -> entry.getKey()).findFirst();

        return symbol.isPresent() ? symbol.get() : null;
    }

    /**
     * Gets the first symbol of a given other object
     * 
     * @param type the other object
     * @return the first symbol
     */
    public static String otherToSymbol(OtherType type) {
        Optional<String> symbol =
                symbolToOtherMap.entrySet().stream().filter(entry -> entry.getValue().equals(type))
                        .map(entry -> entry.getKey()).findFirst();

        return symbol.isPresent() ? symbol.get() : null;
    }

    /**
     * Gets the weapon from a chat message or null if nothing detected
     * 
     * @param message the message
     * @return the weapon or null if nothing detected
     */
    public static WeaponType weaponFromChatMessage(String message) {
        Optional<WeaponType> type = symbolToWeaponMap.entrySet().stream()
                .filter(entry -> message.contains(entry.getKey())).map(entry -> entry.getValue())
                .findFirst();

        return type.isPresent() ? type.get() : null;
    }

    /**
     * Gets another object from a chat message or null if nothing detected
     * 
     * @param message the message
     * @return another object or null if nothing detected
     */
    public static OtherType otherFromChatMessage(String message) {
        Optional<OtherType> type = symbolToOtherMap.entrySet().stream()
                .filter(entry -> message.contains(entry.getKey())).map(entry -> entry.getValue())
                .findFirst();

        return type.isPresent() ? type.get() : null;
    }

    /**
     * Returns true of any symbol is in a chat message
     * 
     * @param message the messagae
     * @return true if any symbol is in a chat message
     */
    public static boolean hasWeaponOrOtherType(String message) {
        return weaponFromChatMessage(message) != null || otherFromChatMessage(message) != null;
    }
}
