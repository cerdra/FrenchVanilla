package cerdra.FrenchVanilla.misc;

import cerdra.FrenchVanilla.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cerdra.FrenchVanilla.items.ModItems;

import java.util.List;

public final class FrenchVanillaCreativeTab extends CreativeTabs {

    public static FrenchVanillaCreativeTab INSTANCE = new FrenchVanillaCreativeTab();
    List list;

    public FrenchVanillaCreativeTab() {
        super("FrenchVanilla");
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(Blocks.crafting_table);
    }

    @Override
    public Item getTabIconItem() {
        return getIconItemStack().getItem();
    }

    @Override
    public void displayAllReleventItems(List list) {
        this.list = list;

        addBlock(ModBlocks.coarseDirt);
        addBlock(ModBlocks.grassPath);
        addBlock(ModBlocks.oldGravel);

        addItem(ModItems.wateringCan);
    }

    public void addItem(Item item) {
        item.getSubItems(item, this, list);
    }

    public void addBlock (Block block) {
        ItemStack stack = new ItemStack(block);
        block.getSubBlocks(stack.getItem(), this, list);
    }

}
