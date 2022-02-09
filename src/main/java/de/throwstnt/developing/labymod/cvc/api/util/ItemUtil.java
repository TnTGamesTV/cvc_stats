package de.throwstnt.developing.labymod.cvc.api.util;

import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemUtil {

    public static ItemStack weaponTypeToItemStack(WeaponType type) {
        switch (type) {
            default:
                return null;
            case AUTO_SHOTGUN:
                return Items.WOODEN_AXE.getDefaultInstance().copy();
            case BULLPUP:
                return Items.GOLDEN_SHOVEL.getDefaultInstance().copy();
            case CARBINE:
                return Items.IRON_AXE.getDefaultInstance().copy();
            case HANDGUN:
                return Items.STONE_PICKAXE.getDefaultInstance().copy();
            case KNIFE:
                return Items.WOODEN_SWORD.getDefaultInstance().copy();
            case MAGNUM:
                return Items.GOLDEN_PICKAXE.getDefaultInstance().copy();
            case PISTOL:
                return Items.WOODEN_PICKAXE.getDefaultInstance().copy();
            case RIFLE:
                return Items.STONE_HOE.getDefaultInstance().copy();
            case SCOPED_RIFLE:
                return Items.GOLDEN_AXE.getDefaultInstance().copy();
            case SMG:
                return Items.STONE_SHOVEL.getDefaultInstance().copy();
            case SNIPER:
                return Items.BOW.getDefaultInstance().copy();
            case SHOTGUN:
                return Items.DIAMOND_SHOVEL.getDefaultInstance().copy();
        }
    }

    public static ItemStack stringToItemStack(String type) {
        switch (type) {
            default:
                return null;
            case "grenade":
                return Items.OAK_SAPLING.getDefaultInstance().copy();
            case "armor_head":
                return Items.IRON_HELMET.getDefaultInstance().copy();
            case "armor_chest":
                return Items.CHAINMAIL_CHESTPLATE.getDefaultInstance().copy();
            case "armor_cops_head":
                return Items.GOLDEN_HELMET.getDefaultInstance().copy();
            case "armor_cops_chest":
                return Items.GOLDEN_CHESTPLATE.getDefaultInstance().copy();
            case "armor_crims_head":
                return Items.DIAMOND_HELMET.getDefaultInstance().copy();
            case "armor_crims_chest":
                return Items.DIAMOND_CHESTPLATE.getDefaultInstance().copy();
            case "cop":
                return Items.TROPICAL_FISH.getDefaultInstance().copy();
            case "radio":
                return Items.PAPER.getDefaultInstance().copy();
            case "defuse_kit":
                return Items.SHEARS.getDefaultInstance().copy();
            case "bomb":
                return Items.GOLDEN_APPLE.getDefaultInstance().copy();
        }
    }
}
