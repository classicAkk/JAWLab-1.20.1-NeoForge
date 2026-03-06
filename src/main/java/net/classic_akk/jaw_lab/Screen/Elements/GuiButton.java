package net.classic_akk.jaw_lab.Screen.Elements;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiButton extends Button {
    private final ResourceLocation TEXTURE;
    private final int u;
    private final int v;
    private final int hoverV;

    public GuiButton(ResourceLocation TEXTURE,int x, int y, int width, int height, int u, int v, int hoverV, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.TEXTURE = TEXTURE;
        this.u = u;
        this.v = v;
        this.hoverV = hoverV;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int textureV = this.isHovered ? hoverV : v;

        guiGraphics.blit(TEXTURE, this.getX(), this.getY(), u, textureV, this.width, this.height);
    }
}