package cerdra.FrenchVanilla.items;

import net.minecraft.item.Item;

public final class ModItems {

    public static Item wateringCan;
    public static Item woodenBucket;

    public static void init() {
        wateringCan = new ItemWateringCan();
    }
}
