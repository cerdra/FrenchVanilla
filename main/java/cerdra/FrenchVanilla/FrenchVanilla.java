package cerdra.FrenchVanilla;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cerdra.FrenchVanilla.misc.LibReference;
import cerdra.FrenchVanilla.proxy.CommonProxy;


@Mod(modid = LibReference.MOD_ID, name = LibReference.MOD_NAME, version = "0.1")
public class FrenchVanilla
{

    @SidedProxy(serverSide = LibReference.PROXY_COMMON, clientSide = LibReference.PROXY_CLIENT)
    public static CommonProxy proxy;

    @Instance(LibReference.MOD_ID)
    public static FrenchVanilla INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

}
