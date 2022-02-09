package de.throwstnt.developing.labymod.cvc.implemented.adapters;

import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ImplementedItem extends AbstractItem<ItemStack> {

    @Override
    public ItemStack getItemStack(ItemStackType type) {
        switch (type) {
            case BARRIER:
                return Items.BARRIER.getDefaultInstance().copy();
            case BOW:
                return Items.BOW.getDefaultInstance().copy();
            case CHAINMAIL_CHESTPLATE:
                return Items.CHAINMAIL_CHESTPLATE.getDefaultInstance().copy();
            case DIAMOND_CHESTPLATE:
                return Items.DIAMOND_CHESTPLATE.getDefaultInstance().copy();
            case DIAMOND_HELMET:
                return Items.DIAMOND_HELMET.getDefaultInstance().copy();
            case DIAMOND_SHOVEL:
                return Items.DIAMOND_SHOVEL.getDefaultInstance().copy();
            case GOLDEN_APPLE:
                return Items.GOLDEN_APPLE.getDefaultInstance().copy();
            case GOLDEN_AXE:
                return Items.GOLDEN_AXE.getDefaultInstance().copy();
            case GOLDEN_CHESTPLATE:
                return Items.GOLDEN_CHESTPLATE.getDefaultInstance().copy();
            case GOLDEN_HELMET:
                return Items.GOLDEN_HELMET.getDefaultInstance().copy();
            case GOLDEN_PICKAXE:
                return Items.GOLDEN_PICKAXE.getDefaultInstance().copy();
            case GOLDEN_SHOVEL:
                return Items.GOLDEN_SHOVEL.getDefaultInstance().copy();
            case IRON_AXE:
                return Items.IRON_AXE.getDefaultInstance().copy();
            case IRON_HELMET:
                return Items.IRON_HELMET.getDefaultInstance().copy();
            case OAK_SAPLING:
                return Items.OAK_SAPLING.getDefaultInstance().copy();
            case PAPER:
                return Items.PAPER.getDefaultInstance().copy();
            case SHEARS:
                return Items.SHEARS.getDefaultInstance().copy();
            case STONE_HOE:
                return Items.STONE_HOE.getDefaultInstance().copy();
            case STONE_PICKAXE:
                return Items.STONE_PICKAXE.getDefaultInstance().copy();
            case STONE_SHOVEL:
                return Items.STONE_SHOVEL.getDefaultInstance().copy();
            case TROPICAL_FISH:
                return Items.TROPICAL_FISH.getDefaultInstance().copy();
            case WOODEN_AXE:
                return Items.WOODEN_AXE.getDefaultInstance().copy();
            case WOODEN_PICKAXE:
                return Items.WOODEN_PICKAXE.getDefaultInstance().copy();
            case WOODEN_SWORD:
                return Items.WOODEN_SWORD.getDefaultInstance().copy();
            default:
                return null;
        }
    }
}
