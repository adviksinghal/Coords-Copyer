package com.cc.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.platform.InputConstants;

public class CoordsCopyerClient implements ClientModInitializer {

    private static KeyMapping copyKey;

    @Override
    public void onInitializeClient() {
        // Updated for Minecraft 26.3 SDL3 Input System
        copyKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "Copy Coords",
                InputConstants.Type.KEYBOARD,  // KEYSYM was removed and replaced by KEYBOARD in 26.3
                InputConstants.KEY_C,          // Replaces the removed GLFW constant
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (copyKey.consumeClick()) {
                if (client.player != null) {
                    double x = client.player.getX();
                    double y = client.player.getY();
                    double z = client.player.getZ();

                    String formattedCoords = String.format("%.2f, %.2f, %.2f", x, y, z);

                    client.keyboardHandler.setClipboard(formattedCoords);
                    client.player.sendSystemMessage(Component.literal("[CoordsCopyer] Coords Copied!"));
                }
            }
        });
    }
}
