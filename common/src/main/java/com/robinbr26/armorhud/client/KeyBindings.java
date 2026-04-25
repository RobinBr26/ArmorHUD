package com.robinbr26.armorhud.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.robinbr26.armorhud.ArmorHUDConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class KeyBindings {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            ArmorHUDConstants.id("general"));

    public static final KeyMapping OPEN_SETTINGS = new KeyMapping(
            "key.armorhud.settings",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            CATEGORY);

    private KeyBindings() {
    }
}
