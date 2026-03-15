package net.classicAkk.jaw_lab.Screen.DoorProgrammator.CodeDoor;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Doors.CodeDoorBE;
import net.classicAkk.jaw_lab.Content.Interactions.DoorInteractions;
import net.classicAkk.jaw_lab.Content.Network.Network;
import net.classicAkk.jaw_lab.Lab;
import net.classicAkk.jaw_lab.Screen.CodeDoor.CodeDoorMenu;
import net.classicAkk.jaw_lab.Screen.Elements.GuiButton;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.OpenMainMenuPacket;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.OpenNetworkMenuPacket;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.ProcessingPacket;
import net.classicAkk.jaw_lab.Util.LabPackets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lwjgl.glfw.GLFW;

public class DoorProgrammatorCodeScreen extends AbstractContainerScreen<DoorProgrammatorCodeMenu> {
    public int offsetX = 55;
    public int offsetY = 55;
    private String net;
    private EditBox field;
    private final Player player = DoorProgrammatorCodeMenu.getPlayer();
    private final BlockEntity blockEntity = DoorProgrammatorCodeMenu.getBE();
    private final Level level = DoorProgrammatorCodeMenu.getLevel();

    private final ResourceLocation TEXTURE =
            new ResourceLocation(Lab.MOD_ID, "textures/gui/door_programmator.png");

    public DoorProgrammatorCodeScreen(DoorProgrammatorCodeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 4;
        this.titleLabelX = 6500;
        if (blockEntity instanceof CodeDoorBE codeDoorBE) net = codeDoorBE.getNetwork();

        renderElements();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width  - 66) / 2;
        int y = (height - 56) / 2;

        String network = DoorInteractions.getNetwork(blockEntity);
        if (network != null) guiGraphics.drawString(this.font, network, leftPos+offsetX+8, topPos+offsetY+29, 0xFFA500);
        guiGraphics.blit(TEXTURE, x, y, 148, 0, imageWidth-84, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderElements(){
        this.addRenderableWidget( //Reset button (network)
                new GuiButton(TEXTURE, leftPos+offsetX+18, topPos+offsetY+6, 14, 14, 2, 208, 224, Component.empty(),
                        button -> {
                    if (net != null) LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "switchAutoClose", net, player));
                        }));

        this.addRenderableWidget( //Auto-close button (mode)
                new GuiButton(TEXTURE, leftPos+offsetX+34, topPos+offsetY+6, 14, 14, 50, 208, 224, Component.empty(),
                        button -> {
                    if (net != null) LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "resetDoor", net, player));
                        }));
        this.addRenderableWidget( //Set Network (mode)
                new GuiButton(TEXTURE, leftPos+offsetX+28, topPos+offsetY+56, 11, 11, 66, 208, 224, Component.empty(),
                        button -> {
                            if ((net != null || !net.isEmpty()) && !field.getValue().isEmpty() ) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "setNetwork", field.getValue(), net, player));
                                if (DoorInteractions.canSetNetwork(blockEntity, level, field.getValue(), net, player)) {
                                    net = field.getValue();
                                }
                            }
                            if ((net == null || net.isEmpty()) && !field.getValue().isEmpty()) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "setNetwork", field.getValue(), "", player));
                                if (DoorInteractions.canSetNetwork(blockEntity, level, field.getValue(), "", player)) {
                                    net = field.getValue();
                                }
                            }
                        }));

        field = new EditBox(this.font, leftPos+offsetX+6, topPos+offsetY+42, 54, 11, Component.literal("Field"));
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