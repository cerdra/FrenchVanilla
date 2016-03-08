// Credit to ganymedes01 for some code
package cerdra.FrenchVanilla.misc;

import cerdra.FrenchVanilla.blocks.BlockCoarseDirt;
import cerdra.FrenchVanilla.blocks.BlockGrassPath;
import cerdra.FrenchVanilla.blocks.ModBlocks;
import cerdra.FrenchVanilla.config.ConfigVars;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;

public class ServerEventHandler {

    public static final ServerEventHandler INSTANCE = new ServerEventHandler();

    @SubscribeEvent
    public void harvestEvent(BlockEvent.HarvestDropsEvent event) {
        if (!ConfigVars.isEtFuturumLoaded) {
//            if (ConfigVars.enableSilkTouchingMushrooms && event.isSilkTouching)
//                if (event.block == Blocks.brown_mushroom_block) {
//                    event.drops.clear();
//                    event.drops.add(new ItemStack(ModBlocks.brownMushroomBlock));
//                } else if (event.block == Blocks.red_mushroom_block) {
//                    event.drops.clear();
//                    event.drops.add(new ItemStack(ModBlocks.redMushroomBlock));
//                }

            if (ConfigVars.enableSticksFromDeadBushes)
                if (event.block == Blocks.deadbush) {
                    boolean isShears = event.harvester != null && event.harvester.getCurrentEquippedItem() != null && event.harvester.getCurrentEquippedItem().getItem() instanceof ItemShears;
                    if (event.harvester == null || event.harvester.getCurrentEquippedItem() == null || !isShears)
                        for (int i = 0; i < event.world.rand.nextInt(3); i++)
                            event.drops.add(new ItemStack(Items.stick));
                }

            if (ConfigVars.enableShearableCobwebs)
                if (event.block == Blocks.web && event.harvester != null) {
                    ItemStack stack = event.harvester.getCurrentEquippedItem();
                    if (stack != null && stack.getItem() instanceof ItemShears) {
                        event.drops.clear();
                        event.drops.add(new ItemStack(Blocks.web));
                    }
                }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!ConfigVars.isEtFuturumLoaded)
            BlockGrassPath.onPlayerInteract(event);
    }

    @SubscribeEvent
    public void onHoeUseEvent(UseHoeEvent event) {
        if (!ConfigVars.isEtFuturumLoaded)
            BlockCoarseDirt.onHoeEvent(event);
    }

}
