package net.classicAkk.jaw_lab.Content.Items;

import net.classicAkk.jaw_lab.Content.Items.custom.item.items.Crowbar;
import net.classicAkk.jaw_lab.Content.Items.custom.item.items.DuctTape;
import net.classicAkk.jaw_lab.Content.Items.custom.item.items.Fuse;
import net.classicAkk.jaw_lab.Content.Items.custom.item.keycards.*;
import net.classicAkk.jaw_lab.Lab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LabItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lab.MOD_ID);

    //Keycard stuff
    public static final RegistryObject<Item> DOOR_PROGRAMMATOR = ITEMS.register("door_programmator",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KEYCARD1 = ITEMS.register("keycard1",
            () -> new KeyCard1(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KEYCARD2 = ITEMS.register("keycard2",
            () -> new KeyCard2(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KEYCARD3 = ITEMS.register("keycard3",
            () -> new KeyCard3(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KEYCARD4 = ITEMS.register("keycard4",
            () -> new KeyCard4(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KEYCARD5 = ITEMS.register("keycard5",
            () -> new KeyCard5(new Item.Properties().stacksTo(1)));

    //Pickable items
    public static final RegistryObject<Item> CROWBAR = ITEMS.register("crowbar",
            () -> new Crowbar(new Item.Properties()));
    public static final RegistryObject<Item> DUCT_TAPE = ITEMS.register("duct_tape",
            () -> new DuctTape(new Item.Properties()));
    public static final RegistryObject<Item> FUSE = ITEMS.register("fuse",
            () -> new Fuse(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}