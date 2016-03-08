package cerdra.FrenchVanilla.proxy;

import cerdra.FrenchVanilla.blocks.ModBlocks;
import cerdra.FrenchVanilla.config.ConfigHandler;
import cerdra.FrenchVanilla.config.ConfigVars;
import cerdra.FrenchVanilla.items.ModItems;
import cerdra.FrenchVanilla.misc.LibReference;
import cerdra.FrenchVanilla.misc.ServerEventHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

public class CommonProxy {

    public void registerEvents() {
        FMLCommonHandler.instance().bus().register(ConfigHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(ServerEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ServerEventHandler.INSTANCE);
    }

    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.INSTANCE.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + LibReference.MOD_ID + ".cfg"));
        System.out.println("Why do you need to know the unlocalized name of dirt?");
        ModBlocks.init();
        ModItems.init();
    }

    public void init(FMLInitializationEvent event) {
        ConfigVars.isEtFuturumLoaded = Loader.isModLoaded("etfuturum");
        ConfigVars.isTinkersConstructLoaded = Loader.isModLoaded("TConstruct");
        registerEvents();
//        if (ConfigVars.doChickensDropFeathers)
//        if (ConfigVars.doEndermenDropBlocks)
//        if (ConfigVars.doPlantablesAutoPlant)
    }
}
