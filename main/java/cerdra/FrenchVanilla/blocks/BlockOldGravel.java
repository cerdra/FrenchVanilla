package cerdra.FrenchVanilla.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.item.Item;
import cerdra.FrenchVanilla.config.ConfigVars;
import cerdra.FrenchVanilla.items.ItemBlockMod;
import cerdra.FrenchVanilla.misc.FrenchVanillaCreativeTab;

import java.util.Random;

public class BlockOldGravel extends BlockGravel{

    public BlockOldGravel() {
        setHardness(0.6F);
        setStepSound(soundTypeGravel);
        setBlockTextureName("FrenchVanilla:oldGravel");
        setBlockName(LibBlockNames.OLDGRAVEL);
        setCreativeTab(isEnabled() ? FrenchVanillaCreativeTab.INSTANCE : null);
    }

    @Override
    public Block setBlockName(String par1Str) {
        if(isEnabled()) GameRegistry.registerBlock(this, ItemBlockMod.class, par1Str);
        return super.setBlockName(par1Str);
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public boolean isEnabled() {
        return ConfigVars.enableOldGravel;
    }
}