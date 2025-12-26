package com.robinbr26.armorhud.client;

import com.robinbr26.armorhud.config.ClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SettingsScreen extends Screen {
    private final Screen lastScreen;

    public SettingsScreen(Screen lastScreen) {
        super(Component.translatable("menu.armorhud.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Checkbox: Enable Warning
        boolean currentEnabled = ClientConfig.INSTANCE.enableWarning.get();
        Checkbox enableWarningBox = Checkbox
                .builder(Component.translatable("config.armorhud.enable_warning"), this.font)
                .pos(centerX - 100, centerY - 40)
                .selected(currentEnabled)
                .onValueChange((checkbox, msg) -> ClientConfig.INSTANCE.enableWarning.set(checkbox.selected()))
                .build();
        this.addRenderableWidget(enableWarningBox);

        // Slider: Warning Threshold
        // Slider: Warning Threshold

        int currentThreshold = ClientConfig.INSTANCE.warningThreshold.get();

        this.addRenderableWidget(
                new net.minecraft.client.gui.components.AbstractSliderButton(centerX - 100, centerY - 10, 200, 20,
                        Component.literal("Threshold: " + currentThreshold + "%"), (double) currentThreshold / 100.0) {
                    @Override
                    protected void updateMessage() {
                        this.setMessage(Component.literal("Threshold: " + (int) (this.value * 100) + "%"));
                    }

                    @Override
                    protected void applyValue() {
                        ClientConfig.INSTANCE.warningThreshold.set((int) (this.value * 100));
                    }
                });

        // Done Button
        this.addRenderableWidget(Button.builder(Component.literal("Done"), (btn) -> {
            ClientConfig.SPEC.save();
            this.minecraft.setScreen(this.lastScreen);
        }).bounds(centerX - 100, centerY + 50, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Semi-transparent black background
        guiGraphics.fill(0, 0, this.width, this.height, 0x90000000);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }
}
