package com.robinbr26.armorhud;

import net.minecraft.resources.Identifier;

public final class ArmorHUDConstants {
    public static final String MOD_ID = "armorhud";

    private ArmorHUDConstants() {
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
