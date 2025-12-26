package com.robinbr26.armorhud.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static final ClientConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public final ModConfigSpec.BooleanValue enableWarning;
    public final ModConfigSpec.IntValue warningThreshold;

    public ClientConfig(ModConfigSpec.Builder builder) {
        builder.push("general");

        enableWarning = builder
                .comment("Enable the low durability warning overlay at the top of the screen.")
                .define("enableWarning", true);

        warningThreshold = builder
                .comment("The percentage threshold (0-100) at which the warning is triggered.")
                .defineInRange("warningThreshold", 10, 0, 100);

        builder.pop();
    }
}
