package net.classicAkk.jaw_lab.Screen.KCPNetwork;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classicAkk.jaw_lab.Content.Interactions.NetworkInteractions;
import net.classicAkk.jaw_lab.Content.Network.NetworkRole;
import net.classicAkk.jaw_lab.Lab;
import net.classicAkk.jaw_lab.Screen.Elements.GuiButton;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.OpenCopyMenuPacket;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.OpenMainMenuPacket;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.ProcessingPacket;
import net.classicAkk.jaw_lab.Util.LabPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

public class KeycardProgrammatorNetworkScreen extends AbstractContainerScreen<KeycardProgrammatorNetworkMenu> {
    private EditBox field;
    private Player player;
    private String network = "none";
    private String user = "none";
    private NetworkRole role = null;
    private int cLevel = 0;
    Level level = KeycardProgrammatorNetworkMenu.getServerLevel();
    ServerLevel serverLevel = (ServerLevel) KeycardProgrammatorNetworkMenu.getServerLevel();

    BlockPos pos = menu.blockEntity.getBlockPos();

    private final ResourceLocation TEXTURE =
            new ResourceLocation(Lab.MOD_ID, "textures/gui/keycard_programmator/kcp_network.png");

    public KeycardProgrammatorNetworkScreen(KeycardProgrammatorNetworkMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        player = Minecraft.getInstance().player;

        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 4;
        this.titleLabelX = 65;


        renderElements();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        renderTextElements(guiGraphics);

        if (field.isFocused() && minecraft.player != null) {

            minecraft.player.input.leftImpulse = 0;
            minecraft.player.input.forwardImpulse = 0;
            minecraft.player.input.jumping = false;
            minecraft.player.input.shiftKeyDown = false;
        }
    }

    public int colorPicker(String value) {
        if (value == null || value.equals("none") || value.equals("false") || value.equals("null")) {
            return 0xFF2400;
        }
        return 0xFFA500;
    }
    public int colorPicker(int value) {
        if (value == 0 || value == -1) {
            return 0xFF2400;
        }
        return 0x42AAFF;
    }

    public NetworkRole incrementRole(NetworkRole role) {
        if (role == NetworkRole.FOUNDER) return null;
        if (role == NetworkRole.ADMIN) return NetworkRole.FOUNDER;
        if (role == NetworkRole.MODERATOR) return NetworkRole.ADMIN;
        if (role == NetworkRole.MEMBER) return NetworkRole.MODERATOR;
        return role;
    }
    public NetworkRole decrementRole(NetworkRole role) {
        if (role == NetworkRole.FOUNDER) return NetworkRole.ADMIN;
        if (role == NetworkRole.ADMIN) return NetworkRole.MODERATOR;
        if (role == NetworkRole.MODERATOR) return NetworkRole.MEMBER;
        return role;
    }
    public String stringRole(NetworkRole role) {
        if (role == NetworkRole.FOUNDER) return "Founder";
        if (role == NetworkRole.ADMIN) return "Admin";
        if (role == NetworkRole.MODERATOR) return "Moderator";
        if (role == NetworkRole.MEMBER) return "Member";
        return "none";
    }

    private void renderTextElements(GuiGraphics guiGraphics) {
        guiGraphics.drawString(this.font, network, leftPos+63, topPos+16, colorPicker(network)); //network
        guiGraphics.drawString(this.font, user, leftPos+63, topPos+28, colorPicker(network));
        //guiGraphics.drawString(this.font, "Role:", leftPos+63(88), topPos+40, 0xFFFFFF); //role
        guiGraphics.drawString(this.font, stringRole(role), leftPos+63, topPos+40, colorPicker(stringRole(role)));
        guiGraphics.drawString(this.font, "Lvl:", leftPos+132, topPos+40, 0xFFFFFF); //level
        guiGraphics.drawString(this.font, String.valueOf(cLevel), leftPos+151, topPos+40, colorPicker(cLevel));
    }

    private void renderElements(){
        this.addRenderableWidget( //plus button (network)
                new GuiButton(TEXTURE, leftPos+27, topPos+50, 14, 14, 28, 172, 188, Component.empty(),
                        button -> {
                            if (!field.getValue().isBlank()) {
                                user = player.getName().getString();
                                network = field.getValue();
                                role = NetworkRole.FOUNDER;
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "createNetwork"));
                            }
                        }));
        this.addRenderableWidget( //minus button (network)
                new GuiButton(TEXTURE, leftPos+27, topPos+66, 14, 14, 44, 172, 188, Component.empty(),
                        button -> {
                            if (!network.equals("none")) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "deleteNetwork"));
                            }
                        }));
        this.addRenderableWidget( //lock minus button (network)
                new GuiButton(TEXTURE, leftPos+11, topPos+66, 14, 14, 60, 172, 188, Component.empty(),
                        button -> {}));

        this.addRenderableWidget( //plus button (user)
                new GuiButton(TEXTURE, leftPos+43, topPos+50, 14, 14, 28, 172, 188, Component.empty(),
                        button -> {
                            if (!field.getValue().isBlank() && !network.equals("none")) {
                                user = field.getValue();
                                role = NetworkRole.MEMBER;
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "addUser", user));
                            }
                        }));
        this.addRenderableWidget( //minus button (user)
                new GuiButton(TEXTURE, leftPos+43, topPos+66, 14, 14, 44, 172, 188, Component.empty(),
                        button -> {
                            if (!network.equals("none") && !user.equals("none")) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "removeUser", user));
                            }
                        }));
        this.addRenderableWidget( //find button (user)
                new GuiButton(TEXTURE, leftPos+126, topPos+53, 10, 10, 118, 172, 188, Component.empty(),
                        button -> {
                            if (!field.getValue().isBlank() && !network.equals("none")) {
                                user = field.getValue();
                                cLevel = NetworkInteractions.getUserAccessLevel(serverLevel, network, user);
                                role = NetworkInteractions.getUserRole(serverLevel, network, user);
                            }
                        }));

        this.addRenderableWidget( //increase button (level)
                new GuiButton(TEXTURE, leftPos+147, topPos+53, 10, 10, 76, 172, 188, Component.empty(),
                        button -> {
                            if (!network.equals("none") && !user.equals("none")) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "increaseUserLevel", user));
                                if (NetworkInteractions.canIncreaseAccessLevel(serverLevel, network, user, player)) cLevel++;
                            }
                        }));
        this.addRenderableWidget( //decrease button (level)
                new GuiButton(TEXTURE, leftPos+159, topPos+53, 10, 10, 88, 172, 188, Component.empty(),
                        button -> {
                            if (!network.equals("none") && !user.equals("none")) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, user, "decreaseUserLevel", user));
                                if (NetworkInteractions.canDecreaseAccessLevel(serverLevel, network, user, player)) cLevel--;
                            }
                        }));

        this.addRenderableWidget( //role button (increase)
                new GuiButton(TEXTURE, leftPos+138, topPos+52, 7, 5, 100, 172, 188, Component.empty(),
                        button -> {
                            if (!network.equals("none") && !user.equals("none")) {
                                NetworkRole newRole = incrementRole(role);
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "setRole", user, newRole));
                                if (NetworkInteractions.canSetRole(serverLevel, network, player, user, newRole)); role = newRole;
                            }
                        }));
        this.addRenderableWidget( //role button (decrease)
                new GuiButton(TEXTURE, leftPos+138, topPos+59, 7, 5, 109, 172, 188, Component.empty(),
                        button -> {
                            if (!network.equals("none") && !user.equals("none")) {
                                NetworkRole newRole = decrementRole(role);
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "setRole", user, newRole));
                                if (NetworkInteractions.canSetRole(serverLevel, network, player, user, newRole)); role = newRole;
                            }
                        }));


        this.addRenderableWidget( //next button (mode)
                new GuiButton(TEXTURE, leftPos+159, topPos+66, 10, 14, 4, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new OpenMainMenuPacket(pos));
                        }));
        this.addRenderableWidget( //previous button (mode)
                new GuiButton(TEXTURE, leftPos+147, topPos+66, 10, 14, 16, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new OpenCopyMenuPacket(pos));
                        }));

        field = new EditBox(this.font, leftPos+60, topPos+67, 84, 12, Component.literal("Field"));
        field.setMaxLength(18);field.setBordered(true);field.setVisible(true);field.setTextColor(0xFFFFFF);this.addRenderableWidget(field);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (field.isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }

            if (keyCode == GLFW.GLFW_KEY_E) {
                return true;
            }
            return field.keyPressed(keyCode, scanCode, modifiers) || field.canConsumeInput();
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}