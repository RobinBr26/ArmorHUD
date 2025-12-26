package com.robinbr26.armorhud.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final KeyMapping OPEN_SETTINGS = new KeyMapping(
            "key.armorhud.settings",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            new KeyMapping.Category(ResourceLocation.fromNamespaceAndPath("armorhud", "general")));

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(OPEN_SETTINGS);
    }
}
