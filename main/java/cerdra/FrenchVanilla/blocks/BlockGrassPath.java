package cerdra.FrenchVanilla.blocks;

import cerdra.FrenchVanilla.config.ConfigVars;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.lang.reflect.Field;

public class BlockGrassPath extends BlockMod {

        public BlockGrassPath() {
            super(Material.ground);
            setBlockBounds(0F, 0F, 0F, 1F, 15F / 16F, 1F);
            setLightOpacity(255);
            setHardness(0.6F);
            setStepSound(soundTypeGravel);
            setBlockName(LibBlockNames.GRASSPATH);
            useNeighborBrightness = true;
        }

        @Override
        public boolean isEnabled() {
            return ConfigVars.enableGrassPath;
        }

        @Override
        public boolean isToolEffective(String type, int metadata) {
            return type.equals("shovel");
        }

        @Override
        public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
            if (ConfigVars.fastGrassPaths) {
                float speed = 2F;
                float max = 0.4F;

                double motionX = Math.abs(entity.motionX);
                double motionZ = Math.abs(entity.motionZ);
                if (motionX < max)
                    entity.motionX *= speed;
                if (motionZ < max)
                    entity.motionZ *= speed;
            }
        }

        @Override
        public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
            Block blockAbove = world.getBlock(x, y + 1, z);
            if(!blockAbove.isAir(world, x, y + 1, z))
                setBlockBounds(0F, 0F, 0F, 1F, 1, 1F);
            else setBlockBounds(0F, 0F, 0F, 1F, 15F / 16F, 1F);
        }

        @Override
        public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
            return side == ForgeDirection.DOWN;
        }

        @Override
        public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
            setBlockBoundsBasedOnState(world, x, y, z);
        }

        @Override
        public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
            return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
        }

        @Override
        public boolean isOpaqueCube() {
            return false;
        }

        @Override
        public boolean renderAsNormalBlock() {
            return false;
        }

        @Override
        public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
            return plantable.getPlantType(world, x, y - 1, z) == EnumPlantType.Plains;
        }

        public static void onPlayerInteract(PlayerInteractEvent event) {
            if (ConfigVars.enableGrassPath)
                if (event.entityPlayer != null) {
                    World world = event.entityPlayer.worldObj;
                    if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
                        if (world.getBlock(event.x, event.y, event.z) == Blocks.grass) {
                            ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
                            if (stack != null && (stack.getItem() instanceof ItemSpade || isTinkersShovel(stack))) {
                                world.setBlock(event.x, event.y, event.z, ModBlocks.grassPath);
                                event.entityPlayer.swingItem();
                                stack.damageItem(1, event.entityPlayer);
                                world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
                            }
                        }
                }
        }

        private static Item tinkersShovel;

        private static boolean isTinkersShovel(ItemStack stack) {
            if (ConfigVars.isTinkersConstructLoaded) {
                if (tinkersShovel == null)
                    try {
                        Class<?> TinkerTools = Class.forName("tconstruct.tools.TinkerTools");
                        Field field = TinkerTools.getDeclaredField("shovel");
                        field.setAccessible(true);
                        tinkersShovel = (Item) field.get(null);
                    } catch (Exception e) {
                    }
                return tinkersShovel == stack.getItem();
            }

            return false;
        }
    }
