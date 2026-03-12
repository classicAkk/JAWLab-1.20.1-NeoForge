package net.classicAkk.jaw_lab.Content.Sound;

import net.classicAkk.jaw_lab.Lab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LabSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Lab.MOD_ID);

    public static final RegistryObject<SoundEvent> KEYDOOR_TICK = registerSoundEvents("door_tick");
    public static final RegistryObject<SoundEvent> KEYDOOR_OPEN = registerSoundEvents("door_open");
    public static final RegistryObject<SoundEvent> KEYDOOR_CLOSE = registerSoundEvents("door_close");
    public static final RegistryObject<SoundEvent> KEYDOOR_ERROR = registerSoundEvents("door_error");

    public static final RegistryObject<SoundEvent> ELECTRIC_OPEN = registerSoundEvents("electric_open");
    public static final RegistryObject<SoundEvent> ELECTRIC_DUCT_TAPE = registerSoundEvents("electric_duct_tape");
    public static final RegistryObject<SoundEvent> ELECTRIC_FUSE = registerSoundEvents("electric_fuse");
    public static final RegistryObject<SoundEvent> ELECTRIC_FUSE1 = registerSoundEvents("electric_fuse1");

    public static final RegistryObject<SoundEvent> STAMP = registerSoundEvents("stamp");
    public static final RegistryObject<SoundEvent> ITEM_PICKUP = registerSoundEvents("item_pickup");
    public static final RegistryObject<SoundEvent> ITEM_CROWBAR_PICKUP = registerSoundEvents("item_crowbar_pickup");





    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Lab.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
