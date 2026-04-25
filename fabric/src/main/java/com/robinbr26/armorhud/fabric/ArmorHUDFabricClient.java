package com.robinbr26.armorhud.fabric;

import com.robinbr26.armorhud.ArmorHUDConstants;
import com.robinbr26.armorhud.client.KeyBindings;
import com.robinbr26.armorhud.client.OverlayHandler;
import com.robinbr26.armorhud.client.SettingsScreen;
import com.robinbr26.armorhud.config.ClientConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;

public final class ArmorHUDFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientConfig.load();

        KeyBindingHelper.registerKeyBinding(KeyBindings.OPEN_SETTINGS);
        HudElementRegistry.addLast(ArmorHUDConstants.id("hud"), OverlayHandler::render);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyBindings.OPEN_SETTINGS.consumeClick()) {
                client.setScreen(new SettingsScreen(client.screen));
            }
        });
    }
}
