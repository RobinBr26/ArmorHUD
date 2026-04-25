# Armor HUD

A minimalistic client-side mod for Minecraft 1.21.11 that displays armor durability on the HUD.

## Loaders

- NeoForge 21.11.x
- Fabric Loader 0.19.x with Fabric API 0.141.x
- Fabric config integration is available through Mod Menu 17.x

## Features

### Armor Display

- Shows equipped armor in the bottom-right corner.
- Displays remaining durability as a number.
- Uses white, orange, and red text based on durability.

### Low Durability Warning

- Displays a red warning at the top of the screen when armor durability falls below the configured threshold.
- Multiple low-durability armor pieces are stacked as separate warnings.

### Configuration

- Press `H` by default to open the settings screen.
- NeoForge also exposes the screen through the NeoForge Mods menu.
- Fabric exposes the screen through Mod Menu when Mod Menu is installed.

Settings:

- Enable or disable the low durability warning.
- Adjust the warning threshold from 0 to 100 percent.

## Building

Run:

```bash
./gradlew build
```

The loader-specific jars are written to:

- `neoforge/build/libs/armorhud-neoforge-1.21.11-1.0.0.jar`
- `fabric/build/libs/armorhud-fabric-1.21.11-1.0.0.jar`
