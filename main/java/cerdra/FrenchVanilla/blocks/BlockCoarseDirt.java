package cerdra.FrenchVanilla.blocks;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import cerdra.FrenchVanilla.config.ConfigVars;

public class BlockCoarseDirt extends BlockMod {

    public BlockCoarseDirt() {
        super(Material.ground);
        setHardness(0.5F);
        setResistance(2.5F);
        setStepSound(soundTypeGravel);
        setLightLevel(1F);
        setBlockName(LibBlockNames.COARSEDIRT);
    }

    @Override
    public boolean isEnabled() {
        return ConfigVars.enableCoarseDirt;
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
        return Blocks.dirt.canSustainPlant(world, x, y, z, direction, plant);
    }

    public static void onHoeEvent(UseHoeEvent event) {
        World world = event.world;
        if (world.getBlock(event.x, event.y, event.z) == ModBlocks.coarseDirt) {
            world.setBlock(event.x, event.y, event.z, Blocks.dirt);
            world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
            event.setResult(Event.Result.ALLOW);
        }
    }
}
