package com.robinbr26.armorhud.neoforge;

import com.robinbr26.armorhud.ArmorHUDConstants;
import com.robinbr26.armorhud.client.SettingsScreen;
import com.robinbr26.armorhud.config.ClientConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(ArmorHUDConstants.MOD_ID)
public class ArmorHUDNeoForge {
    public ArmorHUDNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        ClientConfig.load();

        modEventBus.addListener(NeoForgeOverlayHandler::registerOverlays);
        modEventBus.addListener(NeoForgeKeyBindings::register);

        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (minecraft, parent) -> new SettingsScreen(parent));
    }
}
