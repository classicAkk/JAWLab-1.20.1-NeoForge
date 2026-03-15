package net.classicAkk.jaw_lab.Screen;

import net.classicAkk.jaw_lab.Lab;
import net.classicAkk.jaw_lab.Screen.CodeDoor.CodeDoorMenu;
import net.classicAkk.jaw_lab.Screen.DoorProgrammator.CodeDoor.DoorProgrammatorCodeMenu;
import net.classicAkk.jaw_lab.Screen.DoorProgrammator.KeyDoor.DoorProgrammatorKeyMenu;
import net.classicAkk.jaw_lab.Screen.KCPCopy.KeycardProgrammatorCopyMenu;
import net.classicAkk.jaw_lab.Screen.KCPMain.KeycardProgrammatorMainMenu;
import net.classicAkk.jaw_lab.Screen.KCPNetwork.KeycardProgrammatorNetworkMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LabMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lab.MOD_ID);

    public static final RegistryObject<MenuType<KeycardProgrammatorNetworkMenu>> KCP_NETWORK =
            registerMenuType("kcp_network", KeycardProgrammatorNetworkMenu::new);

    public static final RegistryObject<MenuType<KeycardProgrammatorCopyMenu>> KCP_COPY =
            registerMenuType("kcp_copy", KeycardProgrammatorCopyMenu::new);

    public static final RegistryObject<MenuType<KeycardProgrammatorMainMenu>> KCP_MAIN =
            registerMenuType("kcp_main", KeycardProgrammatorMainMenu::new);

    public static final RegistryObject<MenuType<CodeDoorMenu>> CODE_DOOR =
            registerMenuType("code_door", CodeDoorMenu::new);

    public static final RegistryObject<MenuType<DoorProgrammatorCodeMenu>> DPR_CODE =
            registerMenuType("dpr_code", DoorProgrammatorCodeMenu::new);

    public static final RegistryObject<MenuType<DoorProgrammatorKeyMenu>> DPR_KEY =
            registerMenuType("dpr_key", DoorProgrammatorKeyMenu::new);



    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}