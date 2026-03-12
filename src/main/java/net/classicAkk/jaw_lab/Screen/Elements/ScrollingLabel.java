package net.classicAkk.jaw_lab.Screen.Elements;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ScrollingLabel extends AbstractWidget {

    private String text;
    private int textColor;
    private int backgroundColor = 0xFF202020;

    private float scrollOffset = 0;
    private boolean forward = true;
    private int pauseTicks = 0;

    private static final float SPEED = 0.5f;
    private static final int EDGE_PAUSE = 20;

    public ScrollingLabel(String text, int x, int y, int width, int height, int textColor) {
        super(x, y, width, height, Component.empty());
        this.text = text;
        this.textColor = textColor;
    }

    public void setText(String text) {
        this.text = text;
        this.scrollOffset = 0;
        this.forward = true;
        this.pauseTicks = 0;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        Minecraft mc = Minecraft.getInstance();
        int textWidth = mc.font.width(text);

        // Фон
        guiGraphics.fill(getX(), getY(),
                getX() + width, getY() + height,
                backgroundColor);

        // Если текст помещается — центрируем
        if (textWidth <= width - 8) {

            int centerX = getX() + (width - textWidth) / 2;
            int centerY = getY() + (height - 8) / 2;

            guiGraphics.drawString(mc.font, text, centerX, centerY, textColor, false);
            return;
        }

        updateScroll(textWidth);

        enableScissor();

        guiGraphics.drawString(
                mc.font,
                text,
                (int)(getX() + 4 - scrollOffset),
                getY() + (height - 8) / 2,
                textColor,
                false
        );

        disableScissor();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    private void updateScroll(int textWidth) {

        if (pauseTicks > 0) {
            pauseTicks--;
            return;
        }

        float maxOffset = textWidth - width + 8;

        if (forward) {
            scrollOffset += SPEED;
            if (scrollOffset >= maxOffset) {
                scrollOffset = maxOffset;
                forward = false;
                pauseTicks = EDGE_PAUSE;
            }
        } else {
            scrollOffset -= SPEED;
            if (scrollOffset <= 0) {
                scrollOffset = 0;
                forward = true;
                pauseTicks = EDGE_PAUSE;
            }
        }
    }

    private void enableScissor() {

        Minecraft mc = Minecraft.getInstance();
        double scale = mc.getWindow().getGuiScale();
        int windowHeight = mc.getWindow().getHeight();

        RenderSystem.enableScissor(
                (int)(getX() * scale),
                (int)(windowHeight - (getY() + height) * scale),
                (int)(width * scale),
                (int)(height * scale)
        );
    }

    private void disableScissor() {
        RenderSystem.disableScissor();
    }
}