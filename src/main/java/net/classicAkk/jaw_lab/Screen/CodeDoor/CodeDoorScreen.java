package net.classicAkk.jaw_lab.Screen.CodeDoor;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classicAkk.jaw_lab.Content.Blocks.BlockEntities.Doors.CodeDoorBE;
import net.classicAkk.jaw_lab.Lab;
import net.classicAkk.jaw_lab.Screen.Elements.GuiButton;
import net.classicAkk.jaw_lab.Screen.ProcessingPackets.ProcessingPacket;
import net.classicAkk.jaw_lab.Util.LabPackets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CodeDoorScreen extends AbstractContainerScreen<CodeDoorMenu> {
    public int offsetX = 48;
    public int offsetY = 22;
    private String text = "";
    private boolean hidden = true;
    private final Player player = CodeDoorMenu.getPlayer();
    private final BlockEntity be = CodeDoorMenu.getBE();
    private final Level level = CodeDoorMenu.getLevel();


    private final ResourceLocation TEXTURE =
            new ResourceLocation(Lab.MOD_ID, "textures/gui/code_door.png");

    public CodeDoorScreen(CodeDoorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        this.titleLabelX = 10000;

        renderElements();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 80) / 2;
        int y = (height - 122) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTextElements(guiGraphics);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public static boolean isValidLength(String text, int maxLength) {
        return text != null && text.length() <= maxLength;
    }

    public static String maskString(String input) {
        return input.replaceAll(".", "*");
    }

    private void renderTextElements(GuiGraphics guiGraphics) {
        int textOffsetX = 4;
        int textOffsetY = 3;
        guiGraphics.drawString(this.font, hidden ? maskString(text) : text, leftPos+offsetX + 16, topPos+offsetY + 11, 0xFFA500); //output
        guiGraphics.drawString(this.font, "1", leftPos+offsetX + 14+textOffsetX, topPos+offsetY + 26+textOffsetY, 0xFFFFFF); //1
        guiGraphics.drawString(this.font, "2", leftPos+offsetX + 33+textOffsetX, topPos+offsetY + 26+textOffsetY, 0xFFFFFF); //2
        guiGraphics.drawString(this.font, "3", leftPos+offsetX + 52+textOffsetX, topPos+offsetY + 26+textOffsetY, 0xFFFFFF); //3
        guiGraphics.drawString(this.font, "4", leftPos+offsetX + 14+textOffsetX, topPos+offsetY + 45+textOffsetY, 0xFFFFFF); //4
        guiGraphics.drawString(this.font, "5", leftPos+offsetX + 33+textOffsetX, topPos+offsetY + 45+textOffsetY, 0xFFFFFF); //5
        guiGraphics.drawString(this.font, "6", leftPos+offsetX + 52+textOffsetX, topPos+offsetY + 45+textOffsetY, 0xFFFFFF); //6
        guiGraphics.drawString(this.font, "7", leftPos+offsetX + 14+textOffsetX, topPos+offsetY + 64+textOffsetY, 0xFFFFFF); //7
        guiGraphics.drawString(this.font, "8", leftPos+offsetX + 33+textOffsetX, topPos+offsetY + 64+textOffsetY, 0xFFFFFF); //8
        guiGraphics.drawString(this.font, "9", leftPos+offsetX + 52+textOffsetX, topPos+offsetY + 64+textOffsetY, 0xFFFFFF); //9
        guiGraphics.drawString(this.font, "0", leftPos+offsetX + 33+textOffsetX, topPos+offsetY + 83+textOffsetY, 0xFFFFFF); //0
        guiGraphics.drawString(this.font, "Submit", leftPos+offsetX + 14+textOffsetX, topPos+offsetY + 101+textOffsetY, 0xFFFFFF); //Submit
    }

    private void renderElements() {
        this.addRenderableWidget( //1
                new GuiButton(TEXTURE, leftPos+offsetX + 14, topPos+offsetY + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="1";
                        }));
        this.addRenderableWidget( //2
                new GuiButton(TEXTURE, leftPos+offsetX + 33, topPos+offsetY + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="2";
                        }));
        this.addRenderableWidget( //3
                new GuiButton(TEXTURE, leftPos+offsetX + 52, topPos+offsetY + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="3";
                        }));
        this.addRenderableWidget( //4
                new GuiButton(TEXTURE, leftPos+offsetX + 14, topPos+offsetY + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="4";
                        }));
        this.addRenderableWidget( //5
                new GuiButton(TEXTURE, leftPos+offsetX + 33, topPos+offsetY + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="5";
                        }));
        this.addRenderableWidget( //6
                new GuiButton(TEXTURE, leftPos+offsetX + 52, topPos+offsetY + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="6";
                        }));
        this.addRenderableWidget( //7
                new GuiButton(TEXTURE, leftPos+offsetX + 14, topPos+offsetY + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="7";
                        }));
        this.addRenderableWidget( //8
                new GuiButton(TEXTURE, leftPos+offsetX + 33, topPos+offsetY + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="8";
                        }));
        this.addRenderableWidget( //9
                new GuiButton(TEXTURE, leftPos+offsetX + 52, topPos+offsetY + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="9";
                        }));
        this.addRenderableWidget( //0
                new GuiButton(TEXTURE, leftPos+offsetX + 33, topPos+offsetY + 83, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="0";
                        }));
        this.addRenderableWidget( //backspace
                new GuiButton(TEXTURE, leftPos+offsetX + 52, topPos+offsetY + 83, 14, 14, 34, 208, 224, Component.empty(),
                        button -> {
                    if (!text.isEmpty()) text = text.substring(0, text.length() - 1);
                        }));
        this.addRenderableWidget( //visible
                new GuiButton(TEXTURE, leftPos+offsetX + 14, topPos+offsetY + 83, 14, 14, 2, 208, 224, Component.empty(),
                        button -> {
                            hidden = !hidden;
                        }));

        this.addRenderableWidget( //submit
                new GuiButton(TEXTURE, leftPos+offsetX + 14, topPos+offsetY + 101, 52, 14, 50, 208, 224, Component.empty(),
                        button -> {
                            if (be instanceof CodeDoorBE blockEntity) {
                                if (blockEntity.getPasscode().equals("")) {
                                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "setCode", text, player));
                                    player.closeContainer();
                                }
                                if (blockEntity.getPasscode().equals(text)) {
                                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "openDoor", text, player));
                                    player.closeContainer();
                                }
                                else {
                                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "error", text, player));
                                    player.closeContainer();
                                }
                            }
                        }));
    }
}