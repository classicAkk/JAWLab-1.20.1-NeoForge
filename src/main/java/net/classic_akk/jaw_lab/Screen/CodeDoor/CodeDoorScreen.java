package net.classic_akk.jaw_lab.Screen.CodeDoor;

import com.mojang.blaze3d.systems.RenderSystem;
import net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.CodeDoors.CodeDoorBE;
import net.classic_akk.jaw_lab.Lab;
import net.classic_akk.jaw_lab.Screen.Elements.GuiButton;
import net.classic_akk.jaw_lab.Screen.KCPCopy.KeycardProgrammatorCopyMenu;
import net.classic_akk.jaw_lab.Screen.ProcessingPackets.OpenMainMenuPacket;
import net.classic_akk.jaw_lab.Screen.ProcessingPackets.OpenNetworkMenuPacket;
import net.classic_akk.jaw_lab.Screen.ProcessingPackets.ProcessingPacket;
import net.classic_akk.jaw_lab.Util.LabPackets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.lwjgl.glfw.GLFW;

public class CodeDoorScreen extends AbstractContainerScreen<CodeDoorMenu> {
    private String text;
    private boolean hidden = true;
    private int lPos = (width / 2)+173;
    private int tPos = (height / 2)+59;
    private BlockEntity be;
    private Level level = CodeDoorMenu.getLevel();


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
        int x = (width / 2)-40;
        int y = (height / 2)-61;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderElements() {
        this.addRenderableWidget( //1
                new GuiButton(TEXTURE, lPos + 14, tPos + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="1";
                        }));
        this.addRenderableWidget( //2
                new GuiButton(TEXTURE, lPos + 33, tPos + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="2";
                        }));
        this.addRenderableWidget( //3
                new GuiButton(TEXTURE, lPos + 52, tPos + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="3";
                        }));
        this.addRenderableWidget( //4
                new GuiButton(TEXTURE, lPos + 14, tPos + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="4";
                        }));
        this.addRenderableWidget( //5
                new GuiButton(TEXTURE, lPos + 33, tPos + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="5";
                        }));
        this.addRenderableWidget( //6
                new GuiButton(TEXTURE, lPos + 52, tPos + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="6";
                        }));
        this.addRenderableWidget( //7
                new GuiButton(TEXTURE, lPos + 14, tPos + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="7";
                        }));
        this.addRenderableWidget( //8
                new GuiButton(TEXTURE, lPos + 33, tPos + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="8";
                        }));
        this.addRenderableWidget( //9
                new GuiButton(TEXTURE, lPos + 52, tPos + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="9";
                        }));
        this.addRenderableWidget( //0
                new GuiButton(TEXTURE, lPos + 33, tPos + 83, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                            text+="0";
                        }));
        this.addRenderableWidget( //backspace
                new GuiButton(TEXTURE, lPos + 52, tPos + 83, 14, 14, 34, 208, 224, Component.empty(),
                        button -> {
                            text = text.substring(0, text.length() - 1);
                        }));
        this.addRenderableWidget( //visible
                new GuiButton(TEXTURE, lPos + 14, tPos + 83, 14, 14, 2, 208, 224, Component.empty(),
                        button -> {
                            hidden = !hidden;
                        }));

        this.addRenderableWidget( //submit
                new GuiButton(TEXTURE, lPos + 14, tPos + 101, 52, 14, 50, 208, 224, Component.empty(),
                        button -> {
                            if (be instanceof CodeDoorBE blockEntity) {
                                if (blockEntity.getPasscode().equals(text)) {
                                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "openDoor"));
                                }
                                if (blockEntity.getPasscode() == null) {
                                    blockEntity.setData(blockEntity, blockEntity.getPasscode());
                                }
                            }
                        }));
    }
}
