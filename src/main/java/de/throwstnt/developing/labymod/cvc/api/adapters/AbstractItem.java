package de.throwstnt.developing.labymod.cvc.api.adapters;

public abstract class AbstractItem<ItemStack> {

    public enum ItemType {
        WEAPON_AUTO_SHOTGUN, WEAPON_BULLPUP, WEAPON_CARBINE, WEAPON_HANDGUN, WEAPON_KNIFE, WEAPON_MAGNUM, WEAPON_PISTOL, WEAPON_RIFLE, WEAPON_SCOPED_RIFLE, WEAPON_SMG, WEAPON_SNIPER, WEAPON_SHOTGUN, WEAPON_GRENADE, ARMOR_HEAD, ARMOR_CHEST, ARMOR_COPS_HEAD, ARMOR_COPS_CHEST, ARMOR_CRIMS_HEAD, ARMOR_CRIMS_CHEST, TOOL_COP, TOOL_RADIO, TOOL_DEFUSE_KIT, TOOL_BOMB,
    }

    public enum ItemStackType {
        BARRIER, WOODEN_AXE, GOLDEN_SHOVEL, IRON_AXE, STONE_PICKAXE, STONE_SHOVEL, WOODEN_SWORD, GOLDEN_PICKAXE, GOLDEN_AXE, WOODEN_PICKAXE, STONE_HOE, BOW, DIAMOND_SHOVEL, OAK_SAPLING, IRON_HELMET, CHAINMAIL_CHESTPLATE, GOLDEN_HELMET, GOLDEN_CHESTPLATE, DIAMOND_HELMET, DIAMOND_CHESTPLATE, TROPICAL_FISH, PAPER, SHEARS, GOLDEN_APPLE
    }

    public static ItemStackType fromItemType(ItemType type) {
        switch (type) {
            case ARMOR_CHEST:
                return ItemStackType.CHAINMAIL_CHESTPLATE;
            case ARMOR_COPS_CHEST:
                return ItemStackType.GOLDEN_CHESTPLATE;
            case ARMOR_COPS_HEAD:
                return ItemStackType.GOLDEN_HELMET;
            case ARMOR_CRIMS_CHEST:
                return ItemStackType.DIAMOND_CHESTPLATE;
            case ARMOR_CRIMS_HEAD:
                return ItemStackType.DIAMOND_HELMET;
            case ARMOR_HEAD:
                return ItemStackType.IRON_HELMET;
            case TOOL_BOMB:
                return ItemStackType.GOLDEN_APPLE;
            case TOOL_COP:
                return ItemStackType.TROPICAL_FISH;
            case TOOL_DEFUSE_KIT:
                return ItemStackType.SHEARS;
            case TOOL_RADIO:
                return ItemStackType.PAPER;
            case WEAPON_AUTO_SHOTGUN:
                return ItemStackType.WOODEN_AXE;
            case WEAPON_BULLPUP:
                return ItemStackType.GOLDEN_SHOVEL;
            case WEAPON_CARBINE:
                return ItemStackType.IRON_AXE;
            case WEAPON_GRENADE:
                return ItemStackType.OAK_SAPLING;
            case WEAPON_HANDGUN:
                return ItemStackType.STONE_PICKAXE;
            case WEAPON_KNIFE:
                return ItemStackType.WOODEN_SWORD;
            case WEAPON_MAGNUM:
                return ItemStackType.GOLDEN_PICKAXE;
            case WEAPON_PISTOL:
                return ItemStackType.WOODEN_PICKAXE;
            case WEAPON_RIFLE:
                return ItemStackType.STONE_HOE;
            case WEAPON_SCOPED_RIFLE:
                return ItemStackType.GOLDEN_AXE;
            case WEAPON_SHOTGUN:
                return ItemStackType.DIAMOND_SHOVEL;
            case WEAPON_SMG:
                return ItemStackType.STONE_SHOVEL;
            case WEAPON_SNIPER:
                return ItemStackType.BOW;
            default:
                return null;
        }
    }

    public abstract ItemStack getItemStack(ItemStackType type);
}
