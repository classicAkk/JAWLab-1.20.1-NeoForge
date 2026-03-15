package net.classicAkk.jaw_lab.Screen.KCPMain;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classicAkk.jaw_lab.Content.Interactions.KeycardInteractions;
import net.classicAkk.jaw_lab.Lab;
import net.classicAkk.jaw_lab.Screen.Elements.GuiButton;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.OpenCopyMenuPacket;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.OpenNetworkMenuPacket;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

public class KeycardProgrammatorMainScreen extends AbstractContainerScreen<KeycardProgrammatorMainMenu> {
    Player player;
    Level level = KeycardProgrammatorMainMenu.getServerLevel();
    BlockPos pos = menu.blockEntity.getBlockPos();
    String network;
    private EditBox field;

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
        this.titleLabelX = 25;

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
        network = KeycardInteractions.getCardNetwork(menu, 36);


        if (field.isFocused() && minecraft.player != null) {
            minecraft.player.input.leftImpulse = 0;
            minecraft.player.input.forwardImpulse = 0;
            minecraft.player.input.jumping = false;
            minecraft.player.input.shiftKeyDown = false;
        }
    }

    private void renderTextElements(GuiGraphics guiGraphics){
        String owner = KeycardInteractions.getCardOwner(menu, 36);
        String uuid = KeycardInteractions.getCardUUID(menu, 36);
        //String network = KeycardInteractions.getCardNetwork(menu, 36);
        int cLevel = KeycardInteractions.getCardLevel(menu, 36);
        guiGraphics.drawString(this.font, "Owner:", leftPos+62, topPos+16, 0xFFFFFF);
        guiGraphics.drawString(this.font, owner, leftPos+96, topPos+16, KeycardInteractions.getColor(owner));
        guiGraphics.drawString(this.font, "UUID:", leftPos+62, topPos+24, 0xFFFFFF);
        guiGraphics.drawString(this.font, uuid, leftPos+88, topPos+24, KeycardInteractions.getColor(uuid));
        guiGraphics.drawString(this.font, "Network:", leftPos+62, topPos+32, 0xFFFFFF);
        guiGraphics.drawString(this.font, network, leftPos+105, topPos+32, KeycardInteractions.getColor(network));
        guiGraphics.drawString(this.font, "Level:", leftPos+62, topPos+40, 0xFFFFFF);
        guiGraphics.drawString(this.font, String.valueOf(cLevel), leftPos+93, topPos+40, KeycardInteractions.getColorNumbers(cLevel));
    }

    private void renderElements(){
        this.addRenderableWidget( //reset button (card)
                new GuiButton(TEXTURE, leftPos+11, topPos+66, 14, 14, 28, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(36, "resetKeycard"));
                        }));

        this.addRenderableWidget( //add_network button (card network)
                new GuiButton(TEXTURE, leftPos+43, topPos+50, 14, 14, 44, 172, 188, Component.empty(),
                        button -> {
                            if (!field.getValue().isEmpty()) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(36, "addNetwork", player, field.getValue()));
                            }
                        }));
        this.addRenderableWidget( //delete_network button (card network)
                new GuiButton(TEXTURE, leftPos+43, topPos+66, 14, 14, 60, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(36, "removeNetwork"));
                        }));

        this.addRenderableWidget( //bind button (owner UUID)
                new GuiButton(TEXTURE, leftPos+27, topPos+66, 14, 14, 76, 172, 188, Component.empty(),
                        button -> {
                            LabPackets.INSTANCE.sendToServer(new ProcessingPacket(36, "uuid"));
                        }));
        this.addRenderableWidget( //add button (card owner)
                new GuiButton(TEXTURE, leftPos+27, topPos+50, 14, 14, 44, 172, 188, Component.empty(),
                        button -> {
                            if (!field.getValue().isEmpty()) {
                                LabPackets.INSTANCE.sendToServer(new ProcessingPacket(36, "owner", field.getValue()));
                            }
                        }));

        this.addRenderableWidget( //add_level button (card level)
                new GuiButton(TEXTURE, leftPos+147, topPos+53, 10, 10, 92, 172, 188, Component.empty(),
                        button -> {
                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "increaseLevel"));
                        }));
        this.addRenderableWidget( //decrease_level button (card level)
                new GuiButton(TEXTURE, leftPos+159, topPos+53, 10, 10, 104, 172, 188, Component.empty(),
                        button -> {
                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, player, network, "decreaseLevel"));
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