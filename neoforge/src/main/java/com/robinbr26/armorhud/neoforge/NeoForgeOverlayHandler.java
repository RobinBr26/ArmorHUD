package com.robinbr26.armorhud.neoforge;

import com.robinbr26.armorhud.ArmorHUDConstants;
import com.robinbr26.armorhud.client.OverlayHandler;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public final class NeoForgeOverlayHandler {
    private NeoForgeOverlayHandler() {
    }

    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ArmorHUDConstants.id("hud"), OverlayHandler::render);
    }
}
