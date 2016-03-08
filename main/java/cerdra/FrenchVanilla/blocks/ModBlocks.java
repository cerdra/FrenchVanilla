package cerdra.FrenchVanilla.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public final class ModBlocks {

    public static Block coarseDirt;
    public static Block grassPath;
    public static Block oldGravel;
    public static Block brownMushroomBlock;
    public static Block redMushroomBlock;

    public static void init() {
        Blocks.command_block.setCreativeTab(CreativeTabs.tabRedstone);
//        brownMushroomBlock = new BlockSilkedMushroom(Blocks.brown_mushroom_block, "brown");
//        redMushroomBlock = new BlockSilkedMushroom(Blocks.red_mushroom_block, "red");
        coarseDirt = new BlockCoarseDirt();
        grassPath = new BlockGrassPath();
        oldGravel = new BlockOldGravel();
    }
}
