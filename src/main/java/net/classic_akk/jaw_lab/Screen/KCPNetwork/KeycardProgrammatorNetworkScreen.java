package net.classic_akk.jaw_lab.Screen.KCPNetwork;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classic_akk.jaw_lab.Content.Network.NetworkInteractions;
import net.classic_akk.jaw_lab.Content.Network.NetworkRole;
import net.classic_akk.jaw_lab.Lab;
import net.classic_akk.jaw_lab.Screen.Elements.GuiButton;
import net.classic_akk.jaw_lab.Screen.Elements.ScrollingLabel;
import net.classic_akk.jaw_lab.Screen.ProcessingPackets.OpenCopyMenuPacket;
import net.classic_akk.jaw_lab.Screen.ProcessingPackets.OpenMainMenuPacket;
import net.classic_akk.jaw_lab.Screen.ProcessingPackets.ProcessingPacket;
import net.classic_akk.jaw_lab.Util.LabPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

public class KeycardProgrammatorNetworkScreen extends AbstractContainerScreen<KeycardProgrammatorNetworkMenu> {
    private EditBox nicknameField;
    private EditBox networkField;
    private Player player;
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

        if (nicknameField.isFocused() && minecraft.player != null) {

            minecraft.player.input.leftImpulse = 0;
            minecraft.player.input.forwardImpulse = 0;
            minecraft.player.input.jumping = false;
            minecraft.player.input.shiftKeyDown = false;
        }
    }

    private void renderTextElements(GuiGraphics guiGraphics) {
        NetworkRole status = NetworkInteractions.getUserStatus(serverLevel, networkField.getValue(), nicknameField.getValue());
        int accessLevel = NetworkInteractions.getUserAccessLevel(serverLevel, networkField.getValue(), nicknameField.getValue());

        if (status == null) {
            new ScrollingLabel("nonedgjkhergkhruighiuhuhiushfuisfhseifisefhsuighuighuighushfsuifs", leftPos+62, topPos+8, 105, 40, 0xFF2400);
            guiGraphics.drawString(this.font, "none", leftPos+62, topPos+16, 0xFF2400);
        } else {
            guiGraphics.drawString(this.font, status.toString(), leftPos+62, topPos+16, 0xFFA500);
        }
        if (accessLevel == -1) {
            guiGraphics.drawString(this.font, "none", leftPos+62, topPos+28, 0xFF2400);
        } else {
            guiGraphics.drawString(this.font, String.valueOf(accessLevel), leftPos+63, topPos+28, 0x42AAFF);
        }
    }

    private void renderElements(){
        this.addRenderableWidget( //plus button (network)
                new GuiButton(TEXTURE, leftPos+27, topPos+50, 14, 14, 76, 172, 188, Component.empty(),
                        button -> {
                    if (!networkField.getValue().isBlank()) {
                        LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "createNetwork"));
                    }
                }));
        this.addRenderableWidget( //minus button (network)
                new GuiButton(TEXTURE, leftPos+27, topPos+66, 14, 14, 92, 172, 188, Component.empty(),
                        button -> {
                    if (!networkField.getValue().isBlank()) {
                        LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "deleteNetwork"));
                    }
                }));
        this.addRenderableWidget( //lock minus button (network)
                new GuiButton(TEXTURE, leftPos+11, topPos+66, 14, 14, 108, 172, 188, Component.empty(),
                        button -> {}));

        this.addRenderableWidget( //plus button (user)
                new GuiButton(TEXTURE, leftPos+128, topPos+50, 14, 14, 76, 172, 188, Component.empty(),
                        button -> {
                    if (!nicknameField.getValue().isBlank() && !networkField.getValue().isBlank()) {
                        LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "addUser", nicknameField.getValue()));
                    }
                }));
        this.addRenderableWidget( //minus button (user)
                new GuiButton(TEXTURE, leftPos+128, topPos+66, 14, 14, 92, 172, 188, Component.empty(),
                        button -> {
                    if (!nicknameField.getValue().isBlank() && !networkField.getValue().isBlank()) {
                        LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "removeUser", nicknameField.getValue()));
                    }
                }));

        this.addRenderableWidget( //add button (level)
                new GuiButton(TEXTURE, leftPos+147, topPos+51, 11, 11, 124, 172, 188, Component.empty(),
                        button -> {
                    if (!nicknameField.getValue().isBlank() && !networkField.getValue().isBlank()) {
                        LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "increaseUserLevel", nicknameField.getValue()));
                    }
                }));
        this.addRenderableWidget( //subtract button (level)
                new GuiButton(TEXTURE, leftPos+160, topPos+51, 11, 11, 137, 172, 188, Component.empty(),
                        button -> {
                    if (!nicknameField.getValue().isBlank() && !networkField.getValue().isBlank()) {
                        LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "decreaseUserLevel", nicknameField.getValue()));
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

        this.addRenderableWidget( //role button (founder)
                new GuiButton(TEXTURE, leftPos+48, topPos+41, 18, 7, 150, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "setRole", nicknameField.getValue(), NetworkRole.FOUNDER));
                        }));
        this.addRenderableWidget( //role button (admin)
                new GuiButton(TEXTURE, leftPos+68, topPos+41, 18, 7, 170, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "setRole", nicknameField.getValue(), NetworkRole.ADMIN));
                        }));
        this.addRenderableWidget( //role button (moderator)
                new GuiButton(TEXTURE, leftPos+88, topPos+41, 18, 7, 190, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "setRole", nicknameField.getValue(), NetworkRole.MODERATOR));
                        }));
        this.addRenderableWidget( //role button (member)
                new GuiButton(TEXTURE, leftPos+108, topPos+41, 18, 7, 210, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, networkField.getValue(), "setRole", nicknameField.getValue(), NetworkRole.MEMBER));
                        }));

        nicknameField = new EditBox(this.font, leftPos+47, topPos+51, 78, 12, Component.literal("User"));
        nicknameField.setMaxLength(50);nicknameField.setBordered(true);nicknameField.setVisible(true);nicknameField.setTextColor(0xFFFFFF);this.addRenderableWidget(nicknameField);

        networkField = new EditBox(this.font, leftPos+47, topPos+67, 78, 12, Component.literal("Network"));
        networkField.setMaxLength(50);networkField.setBordered(true);networkField.setVisible(true);networkField.setTextColor(0xFFFFFF);this.addRenderableWidget(networkField);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (nicknameField.isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }

            if (keyCode == GLFW.GLFW_KEY_E) {
                return true;
            }
            return nicknameField.keyPressed(keyCode, scanCode, modifiers) || nicknameField.canConsumeInput();
        }
        if (networkField.isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }

            if (keyCode == GLFW.GLFW_KEY_E) {
                return true;
            }
            return networkField.keyPressed(keyCode, scanCode, modifiers) || networkField.canConsumeInput();
        }


        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}