package com.bugboy.kiwarrior.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_WARRIOR = "key.category.kiwarrior.warrior";
    public static final String KEY_SHOOT_BLAST = "key.kiwarrior.shoot_blast";

    public static final KeyBinding INSTANCE = new KeyBinding();

    private KeyBinding() {}

    public static final KeyMapping SHOOTING_KEY = new KeyMapping(
            KEY_SHOOT_BLAST,
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_B, -1),
            KEY_CATEGORY_WARRIOR);


}
