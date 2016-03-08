// Credit to ganymedes01 for parts of this code
package cerdra.FrenchVanilla.config;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import java.io.File;

import cerdra.FrenchVanilla.misc.LibReference;

public class ConfigHandler {

    public static ConfigHandler INSTANCE = new ConfigHandler();
    public Configuration configFile;
    public String[] usedCategories = { Configuration.CATEGORY_GENERAL };

    private int configInteger(String name, boolean requireRestart, int def) {
        return configInteger(name, null, requireRestart, def);
    }

    private int configInteger(String name, String tooltip, boolean requireRestart, int def) {
        int config = configFile.get(Configuration.CATEGORY_GENERAL, name, def, tooltip).getInt(def);
        return config >= 0 ? config : def;
    }

    private boolean configBoolean(String name, String tooltip, boolean requireRestart, boolean def) {
        return configFile.get(Configuration.CATEGORY_GENERAL, name, def, tooltip).getBoolean(def);
    }

    private boolean configBoolean(String name, boolean requireRestart, boolean def) {
        return configBoolean(name, null, requireRestart, def);
    }

    public void init(File file) {
        configFile = new Configuration(file);
        syncConfigs();
    }

    private void syncConfigs() {
        ConfigVars.allowFakePlayers = configBoolean("Allow fake players to use the Watering Can", false, ConfigVars.allowFakePlayers);
        ConfigVars.enableHardenedClaySlabs = configBoolean("Hardened Clay Slabs enabled", true, ConfigVars.enableHardenedClaySlabs);
        ConfigVars.enableHardenedClayStairs = configBoolean("Hardened Clay Stairs enabled", true, ConfigVars.enableHardenedClayStairs);
        ConfigVars.enableGrassPath = configBoolean("Grass Paths enabled", true, ConfigVars.enableGrassPath);
        ConfigVars.enableOldGravel = configBoolean("Old Gravel enabled", true, ConfigVars.enableOldGravel);
        ConfigVars.enableSatchel = configBoolean("Satchel enabled", true, ConfigVars.enableSatchel);
        ConfigVars.enableShearableCobwebs = configBoolean("Shearing Cobwebs give you Cobwebs", true, ConfigVars.enableShearableCobwebs);
        ConfigVars.enableSilkTouchingMushrooms = configBoolean("Mushrooms can be harvested with Silk Touch", true, ConfigVars.enableSilkTouchingMushrooms);
        ConfigVars.enableSticksFromDeadBushes = configBoolean("Dead Bushes give you Sticks when destroyed", true, ConfigVars.enableSticksFromDeadBushes);
        ConfigVars.enableWateringCan = configBoolean("Watering Can enabled", true, ConfigVars.enableWateringCan);
        ConfigVars.enableWoodenBucket = configBoolean("Wooden Bucket enabled", true, ConfigVars.enableWoodenBucket);
        ConfigVars.moveCommandBlockToRedstoneTab = configBoolean("Command Block is in the Redstone Creative Tab", true, ConfigVars.moveCommandBlockToRedstoneTab);
        ConfigVars.fastGrassPaths = configBoolean("Grass Paths provide a speed boost", true, ConfigVars.fastGrassPaths);

        if (configFile.hasChanged())
            configFile.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (LibReference.MOD_ID.equals(eventArgs.modID))
            syncConfigs();
    }
}
