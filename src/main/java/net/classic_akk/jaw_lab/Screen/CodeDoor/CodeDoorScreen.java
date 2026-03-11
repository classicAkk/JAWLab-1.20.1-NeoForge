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
import net.minecraft.ChatFormatting;
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

public class CodeDoorScreen extends AbstractContainerScreen<CodeDoorMenu> {
    private String text = "";
    private boolean hidden = true;
    private final Player player = CodeDoorMenu.getPlayer();
    private int lPos;
    private int tPos;
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

        int x = (this.width - 80) / 2;
        int y = (this.height - 122) / 2;
        lPos = leftPos;
        tPos = topPos;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderTextElements(guiGraphics);
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public static boolean isValidLength(String text, int maxLength) {
        return text != null && text.length() <= maxLength;
    }

    public static String maskString(String input) {
        return input.replaceAll(".", "*");
    }

    private void renderTextElements(GuiGraphics guiGraphics) {
        guiGraphics.drawString(this.font, hidden ? maskString(text) : text, lPos+13, tPos+11, 0xFFA500);//output
    }

    private void renderElements() {
        this.addRenderableWidget( //1
                new GuiButton(TEXTURE, lPos + 14, tPos + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="1";
                        }));
        this.addRenderableWidget( //2
                new GuiButton(TEXTURE, lPos + 33, tPos + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="2";
                        }));
        this.addRenderableWidget( //3
                new GuiButton(TEXTURE, lPos + 52, tPos + 26, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="3";
                        }));
        this.addRenderableWidget( //4
                new GuiButton(TEXTURE, lPos + 14, tPos + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="4";
                        }));
        this.addRenderableWidget( //5
                new GuiButton(TEXTURE, lPos + 33, tPos + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="5";
                        }));
        this.addRenderableWidget( //6
                new GuiButton(TEXTURE, lPos + 52, tPos + 45, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="6";
                        }));
        this.addRenderableWidget( //7
                new GuiButton(TEXTURE, lPos + 14, tPos + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="7";
                        }));
        this.addRenderableWidget( //8
                new GuiButton(TEXTURE, lPos + 33, tPos + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="8";
                        }));
        this.addRenderableWidget( //9
                new GuiButton(TEXTURE, lPos + 52, tPos + 64, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="9";
                        }));
        this.addRenderableWidget( //0
                new GuiButton(TEXTURE, lPos + 33, tPos + 83, 14, 14, 18, 208, 224, Component.empty(),
                        button -> {
                    if (isValidLength(text, 9)) text+="0";
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
                                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "openDoor", text, player));
                                    player.closeContainer();
                                }
                                else {
                                    player.displayClientMessage(Component.literal("Access Denied").withStyle(ChatFormatting.RED), true);
                                    player.closeContainer();
                                }
                                if (blockEntity.getPasscode().isEmpty()) {
                                    LabPackets.INSTANCE.sendToServer(new ProcessingPacket(level, blockEntity, "setCode", text, player));
                                    player.closeContainer();
                                }
                            }
                        }));
    }
}
