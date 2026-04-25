package com.robinbr26.armorhud.client;

import com.robinbr26.armorhud.config.ClientConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class OverlayHandler {
    private OverlayHandler() {
    }

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
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

        int itemSpacing = 20;

        for (int i = 3; i >= 0; i--) {
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

        renderWarning(guiGraphics, font, screenWidth, player);
    }

    private static void renderWarning(GuiGraphics guiGraphics, Font font, int screenWidth, Player player) {
        if (!ClientConfig.INSTANCE.enableWarning.get()) {
            return;
        }

        int threshold = ClientConfig.INSTANCE.warningThreshold.get();
        int warningY = 15;

        for (int i = 0; i < 4; i++) {
            ItemStack stack = player.getInventory().getItem(36 + i);

            if (!stack.isEmpty() && stack.isDamageableItem()) {
                int maxDamage = stack.getMaxDamage();
                int currentDamage = stack.getDamageValue();
                int remaining = maxDamage - currentDamage;

                if (maxDamage == 0) {
                    continue;
                }

                float percent = ((float) remaining / maxDamage) * 100f;

                if (percent < threshold) {
                    String rawName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
                    String message = String.format("WARNING: %s has %.1f %% durability left!", rawName, percent);
                    int textWidth = font.width(message);
                    int textX = (screenWidth - textWidth) / 2;

                    guiGraphics.drawString(font, message, textX, warningY, 0xFFFF5555, true);
                    warningY += 10;
                }
            }
        }
    }
}
