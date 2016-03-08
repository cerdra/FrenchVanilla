// Credit to BeetoGuy for this code
package cerdra.FrenchVanilla.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import cerdra.FrenchVanilla.config.ConfigVars;
import cerdra.FrenchVanilla.misc.LibReference;

public class ItemWateringCan extends ItemMod
{
    public static ArrayList<ItemStack> flowers = null;
    private static Random rand = new Random();
    private IIcon[] icons;

    public ItemWateringCan()
    {
        super();
        this.setUnlocalizedName(LibItemNames.WATERINGCAN);
        this.setHasSubtypes(false);
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister reg)
    {
        icons = new IIcon[2];
        icons[0] = reg.registerIcon(LibReference.MOD_PREFIX + this.getUnlocalizedName().replaceAll("item\\.", ""));
        icons[1] = reg.registerIcon(LibReference.MOD_PREFIX + this.getUnlocalizedName().replaceAll("item\\.", "") + "Empty");
    }


    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (!ConfigVars.allowFakePlayers) {
            if (!(player instanceof FakePlayer || !player.addedToChunk))
                return false;
        }
        waterLocation(world, x + 0.5D, y + 0.5D, z + 0.5D, side, stack, player);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(stack.getItemDamage() != 1)
        {
            if (!ConfigVars.allowFakePlayers) {
                if (player instanceof FakePlayer || !player.addedToChunk)
                    onUsingTick(stack, player, 0);
            }

            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        }
        else
        {
            MovingObjectPosition pos = getMovingObjectPositionFromPlayer(world, player, true);

            if(pos == null)
                return stack;

            if(pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int i = pos.blockX;
                int j = pos.blockY;
                int k = pos.blockZ;

                if(world.getBlock(i, j, k).getMaterial() == Material.water)
                {
                    stack.setItemDamage(0);
                    return stack;
                }
            }
            return stack;
        }

        return stack;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if(stack.getItemDamage() == 1)
            return super.getUnlocalizedName() + ".empty";
        return super.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.none;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        MovingObjectPosition pos = getMovingObjectPositionFromPlayer(player.worldObj, player, true);

        if(pos != null)
            waterLocation(player.worldObj, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, pos.sideHit, stack, player);
    }

    public void initFlowers()
    {
        if(flowers != null)
            return;

        flowers = new ArrayList();

        if(!Loader.isModLoaded("Forestry"))
            return;

        try
        {
            Class flowerMan = Class.forName("forestry.api.apiculture.FlowerManager");
            ArrayList temp = (ArrayList)flowerMan.getField("plainFlowers").get(null);
            flowers.addAll(temp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void waterLocation(World world, double hitX, double hitY, double hitZ, int side, ItemStack stack, EntityPlayer player)
    {
        List ender = world.getEntitiesWithinAABB(EntityEnderman.class, AxisAlignedBB.getBoundingBox(hitX - 1, hitY - 1, hitZ - 1, hitX + 1, hitY + 1, hitZ + 1));

        if(ender != null)
        {
            for(Iterator i = ender.iterator(); i.hasNext();)
            {
                Object aEnder = i.next();
                ((EntityEnderman)aEnder).attackEntityFrom(DamageSource.drown, 1.0F);
            }
        }

        if(world.isRemote)
        {
            double dX = Facing.offsetsXForSide[side]; double dY = Facing.offsetsYForSide[side]; double dZ = Facing.offsetsZForSide[side];
            double x2 = hitX + dX * 0.1D; double y2 = hitY + dY * 0.1D; double z2 = hitZ + dZ * 0.1D;

            for(int i = 0; i < 10; i++)
            {
                world.spawnParticle("splash", x2 + world.rand.nextGaussian() * 0.6D, y2, z2 + world.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D);
            }
        }
        else
        {
            List entities = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(hitX - 1, hitY - 1, hitZ - 1, hitX + 1, hitY + 1, hitZ + 1));

            if(entities != null)
            {
                for(Iterator i = entities.iterator(); i.hasNext();)
                {
                    Object ent = i.next();

                    if(((Entity)ent).isBurning())
                    {
                        float p = 0.01F;

                        if(ent instanceof EntityPlayer)
                            p = 0.333F;

                        ((Entity)ent).extinguish();

                        if(world.rand.nextDouble() < p)
                        {
                            stack.setItemDamage(1);
                            if(player != null)
                                player.stopUsingItem();
                            return;
                        }
                    }
                }
            }

            int blockX = (int)Math.floor(hitX);
            int blockY = (int)Math.floor(hitY);
            int blockZ = (int)Math.floor(hitZ);

            for(int x = blockX - 1; x <= blockX + 1; x++)
            {
                for(int y = blockY - 1; y <= blockY + 1; y++)
                {
                    for(int z = blockZ - 1; z <= blockZ + 1; z++)
                    {
                        Block block = world.getBlock(x, y, z);

                        if(!world.isAirBlock(x, y, z))
                        {
                            if(block == Blocks.fire)
                                world.setBlockToAir(x, y, z);

                            if(block == Blocks.flowing_lava && world.rand.nextInt(2) == 0)
                                Blocks.flowing_lava.updateTick(world, x, y, z, world.rand);

                            if(block == Blocks.farmland && world.getBlockMetadata(x, y, z) < 7)
                                world.setBlockMetadataWithNotify(x, y, z, 7, 2);

                            int timer = -1;

                            if(block == Blocks.grass)
                            {
                                timer = 20;

                                if(world.rand.nextInt(4500) == 0 && world.isAirBlock(x, y + 1, z))
                                {
                                    initFlowers();

                                    if(flowers.size() > 0 && world.rand.nextInt(5) == 0)
                                    {
                                        ItemStack flower = (ItemStack)flowers.get(world.rand.nextInt(flowers.size()));

                                        if(flower.getItem() instanceof ItemBlock && player != null)
                                            ((ItemBlock)flower.getItem()).placeBlockAt(flower, player, world, x, y + 1, z, 1, 0.5F, 1.0F, 0.5F, flower.getItem().getMetadata(flower.getItemDamage()));
                                        else
                                        {
                                            world.getBiomeGenForCoords(x, z).plantFlower(world, rand, x, y + 1, z);
                                        }
                                    }
                                }
                            }
                            else if(block == Blocks.mycelium)
                                timer = 20;
                            else if(block == Blocks.wheat)
                                timer = 40;
                            else if(block instanceof BlockSapling)
                                timer = 50;
                            else if(block instanceof IPlantable || block instanceof IGrowable)
                                timer = 40;
                            else if(block.getMaterial() == Material.grass)
                                timer = 20;

                            if(timer > 0 && block.getTickRandomly())
                                world.scheduleBlockUpdate(x, y, z, block, world.rand.nextInt(timer));
                        }
                    }
                }
            }
        }
    }
}
