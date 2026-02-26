package net.classic_akk.ca_lab.Screen.KCPMain;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classic_akk.ca_lab.Lab;
import net.classic_akk.ca_lab.Screen.Elements.GuiButton;
import net.classic_akk.ca_lab.Screen.KCPCopy.KeycardProgrammatorCopyMenu;
import net.classic_akk.ca_lab.Screen.OpenScreen.OpenCopyMenuPacket;
import net.classic_akk.ca_lab.Screen.OpenScreen.OpenMainMenuPacket;
import net.classic_akk.ca_lab.Screen.OpenScreen.OpenNetworkMenuPacket;
import net.classic_akk.ca_lab.Util.LabPackets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class KeycardProgrammatorMainScreen extends AbstractContainerScreen<KeycardProgrammatorMainMenu> {
    BlockPos pos = menu.blockEntity.getBlockPos();

    private final ResourceLocation TEXTURE =
            new ResourceLocation(Lab.MOD_ID, "textures/gui/keycard_programmator/kcp_main.png");

    public KeycardProgrammatorMainScreen(KeycardProgrammatorMainMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
    }

    private void renderElements(){
        this.addRenderableWidget( //reset button (card)
                new GuiButton(TEXTURE, leftPos+11, topPos+66, 14, 14, 28, 172, 188, Component.empty(),
                        button -> {}));

        this.addRenderableWidget( //add_network button (card network)
                new GuiButton(TEXTURE, leftPos+43, topPos+50, 14, 14, 76, 172, 188, Component.empty(),
                        button -> {}));
        this.addRenderableWidget( //delete_network button (card network)
                new GuiButton(TEXTURE, leftPos+43, topPos+66, 14, 14, 92, 172, 188, Component.empty(),
                        button -> {}));

        this.addRenderableWidget( //add_level button (card level)
                new GuiButton(TEXTURE, leftPos+27, topPos+50, 14, 14, 44, 172, 188, Component.empty(),
                        button -> {}));
        this.addRenderableWidget( //decrease_level button (card level)
                new GuiButton(TEXTURE, leftPos+27, topPos+66, 14, 14, 60, 172, 188, Component.empty(),
                        button -> {}));

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
    }
}