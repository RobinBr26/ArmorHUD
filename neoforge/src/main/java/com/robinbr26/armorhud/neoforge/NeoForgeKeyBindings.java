package com.robinbr26.armorhud.neoforge;

import com.robinbr26.armorhud.client.KeyBindings;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public final class NeoForgeKeyBindings {
    private NeoForgeKeyBindings() {
    }

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.OPEN_SETTINGS);
    }
}
