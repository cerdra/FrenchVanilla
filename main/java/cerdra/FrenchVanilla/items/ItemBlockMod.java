package cerdra.FrenchVanilla.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cerdra.FrenchVanilla.misc.LibReference;

public class ItemBlockMod extends ItemBlock {

    public ItemBlockMod(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack) {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("tile.", "tile." + LibReference.MOD_PREFIX);
    }

}
