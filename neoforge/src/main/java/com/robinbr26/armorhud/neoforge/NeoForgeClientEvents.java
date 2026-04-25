package com.robinbr26.armorhud.neoforge;

import com.robinbr26.armorhud.ArmorHUDConstants;
import com.robinbr26.armorhud.client.KeyBindings;
import com.robinbr26.armorhud.client.SettingsScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = ArmorHUDConstants.MOD_ID, value = Dist.CLIENT)
public final class NeoForgeClientEvents {
    private NeoForgeClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        while (KeyBindings.OPEN_SETTINGS.consumeClick()) {
            minecraft.setScreen(new SettingsScreen(minecraft.screen));
        }
    }
}
