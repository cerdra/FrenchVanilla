package cerdra.FrenchVanilla.blocks;

import cerdra.FrenchVanilla.items.ItemBlockMod;
import cerdra.FrenchVanilla.misc.FrenchVanillaCreativeTab;
import cerdra.FrenchVanilla.misc.LibReference;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockMod extends Block {

    public BlockMod(Material par2Material) {
        super(par2Material);
        if (isEnabled()) setCreativeTab(FrenchVanillaCreativeTab.INSTANCE);
    }

    public BlockMod(Material par2Material, boolean noCreativeTab) {
        super(par2Material);
    }

    public boolean isEnabled() {
        return true;
    }

    @Override
    public Block setBlockName(String par1Str) {
        if(isEnabled()) GameRegistry.registerBlock(this, ItemBlockMod.class, par1Str);
        return super.setBlockName(par1Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(LibReference.MOD_PREFIX + this.getUnlocalizedName().replaceAll("tile\\.", ""));
    }

}