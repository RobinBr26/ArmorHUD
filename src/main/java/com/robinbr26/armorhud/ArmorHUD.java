package com.robinbr26.armorhud;

import com.robinbr26.armorhud.client.OverlayHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("armorhud")
public class ArmorHUD {
    public ArmorHUD(IEventBus modEventBus, net.neoforged.fml.ModContainer modContainer) {
        // Register the OverlayHandler to the mod event bus for client setup
        modEventBus.addListener(OverlayHandler::registerOverlays);

        // Register Config
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.CLIENT,
                com.robinbr26.armorhud.config.ClientConfig.SPEC);

        // Register KeyBindings
        modEventBus.addListener(com.robinbr26.armorhud.client.KeyBindings::register);

        // Register Config Screen functionality for Mods menu
        modContainer.registerExtensionPoint(net.neoforged.neoforge.client.gui.IConfigScreenFactory.class,
                (mc, parent) -> new com.robinbr26.armorhud.client.SettingsScreen(parent));
    }
}
