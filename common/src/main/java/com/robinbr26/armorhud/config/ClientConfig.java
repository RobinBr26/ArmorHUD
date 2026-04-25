package com.robinbr26.armorhud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ClientConfig {
    public static final ClientConfig INSTANCE = new ClientConfig();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("config", "armorhud-client.json");

    public final BooleanValue enableWarning = new BooleanValue(true);
    public final IntValue warningThreshold = new IntValue(10, 0, 100);

    private ClientConfig() {
    }

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            Data data = GSON.fromJson(reader, Data.class);
            if (data != null) {
                if (data.enableWarning != null) {
                    INSTANCE.enableWarning.set(data.enableWarning);
                }
                if (data.warningThreshold != null) {
                    INSTANCE.warningThreshold.set(data.warningThreshold);
                }
            }
        } catch (IOException | RuntimeException ignored) {
            save();
        }
    }

    public static void save() {
        try {
            Path parent = CONFIG_PATH.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(new Data(
                        INSTANCE.enableWarning.get(),
                        INSTANCE.warningThreshold.get()), writer);
            }
        } catch (IOException ignored) {
        }
    }

    private record Data(Boolean enableWarning, Integer warningThreshold) {
    }

    public static final class BooleanValue {
        private boolean value;

        private BooleanValue(boolean defaultValue) {
            this.value = defaultValue;
        }

        public boolean get() {
            return value;
        }

        public void set(boolean value) {
            this.value = value;
        }
    }

    public static final class IntValue {
        private final int min;
        private final int max;
        private int value;

        private IntValue(int defaultValue, int min, int max) {
            this.min = min;
            this.max = max;
            this.value = clamp(defaultValue);
        }

        public int get() {
            return value;
        }

        public void set(int value) {
            this.value = clamp(value);
        }

        private int clamp(int value) {
            return Math.max(min, Math.min(max, value));
        }
    }
}
