package net.classic_akk.ca_lab.Screen.KCPNetwork;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classic_akk.ca_lab.Lab;
import net.classic_akk.ca_lab.Screen.Elements.GuiButton;
import net.classic_akk.ca_lab.Screen.ProcessingPackets.OpenCopyMenuPacket;
import net.classic_akk.ca_lab.Screen.ProcessingPackets.OpenMainMenuPacket;
import net.classic_akk.ca_lab.Util.LabPackets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

public class KeycardProgrammatorNetworkScreen extends AbstractContainerScreen<KeycardProgrammatorNetworkMenu> {
    private EditBox nicknameField;
    private EditBox networkField;
    BlockPos pos = menu.blockEntity.getBlockPos();

    private final ResourceLocation TEXTURE =
            new ResourceLocation(Lab.MOD_ID, "textures/gui/keycard_programmator/kcp_network.png");

    public KeycardProgrammatorNetworkScreen(KeycardProgrammatorNetworkMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
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

        if (nicknameField.isFocused() && minecraft.player != null) {

            minecraft.player.input.leftImpulse = 0;
            minecraft.player.input.forwardImpulse = 0;
            minecraft.player.input.jumping = false;
            minecraft.player.input.shiftKeyDown = false;
        }
    }

    private void renderElements(){
        this.addRenderableWidget( //plus button (network)
                new GuiButton(TEXTURE, leftPos+27, topPos+50, 14, 14, 76, 172, 188, Component.empty(),
                        button -> {}));
        this.addRenderableWidget( //minus button (network)
                new GuiButton(TEXTURE, leftPos+27, topPos+66, 14, 14, 92, 172, 188, Component.empty(),
                        button -> {}));
        this.addRenderableWidget( //lock minus button (network)
                new GuiButton(TEXTURE, leftPos+11, topPos+66, 14, 14, 108, 172, 188, Component.empty(),
                        button -> {}));

        this.addRenderableWidget( //plus button (user)
                new GuiButton(TEXTURE, leftPos+128, topPos+50, 14, 14, 76, 172, 188, Component.empty(),
                        button -> {}));
        this.addRenderableWidget( //minus button (user)
                new GuiButton(TEXTURE, leftPos+128, topPos+66, 14, 14, 92, 172, 188, Component.empty(),
                        button -> {}));

        this.addRenderableWidget( //add button (level)
                new GuiButton(TEXTURE, leftPos+147, topPos+51, 11, 11, 124, 172, 188, Component.empty(),
                        button -> {}));
        this.addRenderableWidget( //subtract button (level)
                new GuiButton(TEXTURE, leftPos+160, topPos+51, 11, 11, 137, 172, 188, Component.empty(),
                        button -> {}));

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

        nicknameField = new EditBox(this.font, leftPos+47, topPos+50, 78, 14, Component.literal("User"));
        nicknameField.setMaxLength(50);nicknameField.setBordered(true);nicknameField.setVisible(true);nicknameField.setTextColor(0xFFFFFF);this.addRenderableWidget(nicknameField);

        networkField = new EditBox(this.font, leftPos+47, topPos+66, 78, 14, Component.literal("Network"));
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