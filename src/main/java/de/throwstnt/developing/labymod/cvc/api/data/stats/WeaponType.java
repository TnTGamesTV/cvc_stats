package de.throwstnt.developing.labymod.cvc.api.data.stats;

import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem.ItemType;

public enum WeaponType {
    CARBINE(ItemType.WEAPON_CARBINE, "M4"), PISTOL(ItemType.WEAPON_PISTOL, "USP"), SCOPED_RIFLE(
            ItemType.WEAPON_SCOPED_RIFLE,
            "Steyr AUG"), HANDGUN(ItemType.WEAPON_HANDGUN, "HK45"), MAGNUM(ItemType.WEAPON_MAGNUM,
                    "Deagle"), SNIPER(ItemType.WEAPON_SNIPER, "50 Cal"), RIFLE(
                            ItemType.WEAPON_RIFLE,
                            "AK-47"), AUTO_SHOTGUN(ItemType.WEAPON_AUTO_SHOTGUN,
                                    "SPAS-12"), SHOTGUN(ItemType.WEAPON_SHOTGUN,
                                            "Pump Action"), BULLPUP(ItemType.WEAPON_BULLPUP,
                                                    "P90"), SMG(ItemType.WEAPON_SMG, "MP5"), KNIFE(
                                                            ItemType.WEAPON_KNIFE, "Knife");

    private AbstractItem.ItemType itemType;
    private String name;

    WeaponType(AbstractItem.ItemType itemType, String name) {
        this.itemType = itemType;
        this.name = name;
    }

    public AbstractItem.ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }
}
