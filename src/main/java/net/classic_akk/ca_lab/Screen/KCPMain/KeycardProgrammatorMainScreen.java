package net.classic_akk.ca_lab.Screen.KCPMain;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classic_akk.ca_lab.Content.Blocks.BlockEntities.Util.KeycardInteractions;
import net.classic_akk.ca_lab.Lab;
import net.classic_akk.ca_lab.Screen.Elements.GuiButton;
import net.classic_akk.ca_lab.Screen.ProcessingPackets.OpenCopyMenuPacket;
import net.classic_akk.ca_lab.Screen.ProcessingPackets.OpenNetworkMenuPacket;
import net.classic_akk.ca_lab.Screen.ProcessingPackets.ProcessingPacket;
import net.classic_akk.ca_lab.Util.LabPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class KeycardProgrammatorMainScreen extends AbstractContainerScreen<KeycardProgrammatorMainMenu> {
    LocalPlayer player;
    private EditBox nicknameField;
    BlockPos pos = menu.blockEntity.getBlockPos();
    ItemStack stack = menu.getSlot(36).getItem();

    private final ResourceLocation TEXTURE =
            new ResourceLocation(Lab.MOD_ID, "textures/gui/keycard_programmator/kcp_main.png");

    public KeycardProgrammatorMainScreen(KeycardProgrammatorMainMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();

        player = Minecraft.getInstance().player;
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
        this.addRenderableWidget( //reset button (card)
                new GuiButton(TEXTURE, leftPos+11, topPos+66, 14, 14, 28, 172, 188, Component.empty(),
                        button -> {
                            KeycardInteractions.resetKeycard(stack);
                        }));

        this.addRenderableWidget( //add_network button (card network)
                new GuiButton(TEXTURE, leftPos+43, topPos+50, 14, 14, 76, 172, 188, Component.empty(),
                        button -> {}));
        this.addRenderableWidget( //delete_network button (card network)
                new GuiButton(TEXTURE, leftPos+43, topPos+66, 14, 14, 92, 172, 188, Component.empty(),
                        button -> {}));

        this.addRenderableWidget( //bind button (owner UUID)
                new GuiButton(TEXTURE, leftPos+59, topPos+66, 10, 14, 108, 172, 188, Component.empty(),
                        button -> {
                            System.out.print("Process\n");
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(36));
                        }));
        this.addRenderableWidget( //add button (card owner)
                new GuiButton(TEXTURE, leftPos+71, topPos+66, 10, 14, 120, 172, 188, Component.empty(),
                        button -> {
                            KeycardInteractions.setUsername(stack, player);
                        }));

        this.addRenderableWidget( //add_level button (card level)
                new GuiButton(TEXTURE, leftPos+27, topPos+50, 14, 14, 44, 172, 188, Component.empty(),
                        button -> {
                            KeycardInteractions.increaseLevel(stack);
                        }));
        this.addRenderableWidget( //decrease_level button (card level)
                new GuiButton(TEXTURE, leftPos+27, topPos+66, 14, 14, 60, 172, 188, Component.empty(),
                        button -> {
                            KeycardInteractions.decreaseLevel(stack);
                        }));

        this.addRenderableWidget( //next button (mode)
                new GuiButton(TEXTURE, leftPos+159, topPos+66, 10, 14, 4, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new OpenCopyMenuPacket(pos));
                        }));
        this.addRenderableWidget( //previous button (mode)
                new GuiButton(TEXTURE, leftPos+147, topPos+66, 10, 14, 16, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new OpenNetworkMenuPacket(pos));
                        }));

        nicknameField = new EditBox(this.font, leftPos+84, topPos+66, 60, 14, Component.literal("User"));
        nicknameField.setMaxLength(50);nicknameField.setBordered(true);nicknameField.setVisible(true);nicknameField.setTextColor(0xFFFFFF);this.addRenderableWidget(nicknameField);
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

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}