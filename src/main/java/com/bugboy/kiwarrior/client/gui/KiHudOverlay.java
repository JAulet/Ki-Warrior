package com.bugboy.kiwarrior.client.gui;

import com.bugboy.kiwarrior.KiWarrior;
import com.bugboy.kiwarrior.client.ClientKiData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;

import static net.minecraft.client.renderer.RenderType.*;

public class KiHudOverlay {

    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(KiWarrior.MODID, "textures/ki/empty_ki.png");
    public static final IGuiOverlay HUD_KI = ((gui, poseStack, partialTick, width, height) -> {
        int kiBarX = 10;
        int kiBarY = 10;
        int kiBarWidth = 150;
        int kiBarHeight = 5;
        Color kiBarEmpty = Color.GRAY;
        Color kiBarFull = Color.BLUE;

        double portion = ((double) ClientKiData.getPlayerKi())/((double) ClientKiData.getPlayerMaxKi());

        String ratio = ClientKiData.getPlayerKi() + " / " + ClientKiData.getPlayerMaxKi();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_THIRST);

        poseStack.fill(kiBarX, kiBarY, kiBarX+kiBarWidth, kiBarY+kiBarHeight, kiBarEmpty.getRGB());

        poseStack.fill(kiBarX,kiBarY, ((int)(kiBarX + (portion * kiBarWidth))), kiBarY+kiBarHeight,kiBarFull.getRGB());

        poseStack.drawString(gui.getFont(), ratio, 20, 20, Color.WHITE.getRGB());
    });

}
