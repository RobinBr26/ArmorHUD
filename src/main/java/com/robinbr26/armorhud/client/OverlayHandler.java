package com.robinbr26.armorhud.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class OverlayHandler {

    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
                ResourceLocation.fromNamespaceAndPath("armorhud", "hud"),
                OverlayHandler::render);
    }

    private static void render(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker) {
        renderHud(guiGraphics);
    }

    private static void renderHud(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || mc.options.hideGui) {
            return;
        }

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        Font font = mc.font;

        int itemSpacing = 20; // Space between items

        for (int i = 3; i >= 0; i--) {
            // Access armor slots: 36=boots, 39=helmet
            ItemStack stack = player.getInventory().getItem(36 + i);

            int yPos = screenHeight - 20 - (i * itemSpacing);
            int xPos = screenWidth - 20;

            if (!stack.isEmpty()) {
                guiGraphics.renderItem(stack, xPos, yPos);
                guiGraphics.renderItemDecorations(font, stack, xPos, yPos);

                if (stack.isDamageableItem()) {
                    int maxDamage = stack.getMaxDamage();
                    int currentDamage = stack.getDamageValue();
                    int remaining = maxDamage - currentDamage;

                    String text = String.valueOf(remaining);
                    int textWidth = font.width(text);

                    int textX = xPos - textWidth - 4;
                    int textY = yPos + 4;

                    // Color logic
                    int color = 0xFFFFFFFF;
                    if (maxDamage > 0) {
                        float percentage = (float) remaining / maxDamage;
                        if (percentage < 0.10f) {
                            color = 0xFFFF5555;
                        } else if (percentage < 0.25f) {
                            color = 0xFFFFAA00;
                        }
                    }

                    guiGraphics.drawString(font, text, textX, textY, color, true);
                }
            }
        }

        // Render Warning Overlay
        renderWarning(guiGraphics, font, screenWidth, player);
    }

    private static void renderWarning(GuiGraphics guiGraphics, Font font, int screenWidth, Player player) {
        if (!com.robinbr26.armorhud.config.ClientConfig.INSTANCE.enableWarning.get()) {
            return;
        }

        int threshold = com.robinbr26.armorhud.config.ClientConfig.INSTANCE.warningThreshold.get();
        int warningY = 15; // Top, slightly below where bossbar might handle itself or below top edge

        for (int i = 0; i < 4; i++) {
            // Iterate all armor slots
            ItemStack stack = player.getInventory().getItem(36 + i);

            if (!stack.isEmpty() && stack.isDamageableItem()) {
                int maxDamage = stack.getMaxDamage();
                int currentDamage = stack.getDamageValue();
                int remaining = maxDamage - currentDamage;

                // Avoid division by zero
                if (maxDamage == 0)
                    continue;

                float percent = ((float) remaining / maxDamage) * 100f;

                if (percent < threshold) {
                    // "WARNING: netherite_helmet has x % durabillity left!"
                    String rawName = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem())
                            .getPath();

                    String message = String.format("WARNING: %s has %.1f %% durability left!", rawName, percent);

                    int textWidth = font.width(message);
                    int textX = (screenWidth - textWidth) / 2;

                    guiGraphics.drawString(font, message, textX, warningY, 0xFFFF5555, true); // Red

                    warningY += 10; // Stack messages if multiple items are low
                }
            }
        }
    }
}
