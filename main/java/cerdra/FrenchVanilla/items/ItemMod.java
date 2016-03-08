package cerdra.FrenchVanilla.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cerdra.FrenchVanilla.misc.FrenchVanillaCreativeTab;
import cerdra.FrenchVanilla.misc.LibReference;

public class ItemMod extends Item {

    public ItemMod () {
        super();
        if (isEnabled()) setCreativeTab(FrenchVanillaCreativeTab.INSTANCE);
    }

    public ItemMod(boolean NoCreativeTab) {
        super();
    }

    public boolean isEnabled() {
        return true;
    }

    @Override
    public Item setUnlocalizedName(String name) {
        if (isEnabled()) GameRegistry.registerItem(this, name);
        return super.setUnlocalizedName(name);
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack istack) {
        return super.getUnlocalizedNameInefficiently(istack).replaceAll("item\\.", ("item." + LibReference.MOD_PREFIX));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        itemIcon = reg.registerIcon(LibReference.MOD_PREFIX + this.getUnlocalizedName().replaceAll("item\\.", ""));
    }
}
