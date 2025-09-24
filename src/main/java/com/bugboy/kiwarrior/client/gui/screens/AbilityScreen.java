package com.bugboy.kiwarrior.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;
import java.util.List;

public class AbilityScreen extends Screen {
    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;
    private static final int ABILITIES_PER_PAGE = 5;


    protected AbilityScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
    }


}
