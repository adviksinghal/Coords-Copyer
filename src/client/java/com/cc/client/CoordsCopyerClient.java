package com.cc.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
// Updated package: 'keymapping' instead of 'keybinding'
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.platform.InputConstants;

import org.lwjgl.glfw.GLFW;

public class CoordsCopyerClient implements ClientModInitializer {

	private static KeyMapping copyKey;

	@Override
	public void onInitializeClient() {
		// Updated Method: registerKeyMapping
		// Updated Category: KeyMapping.Category.MISC instead of a String
		copyKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.coords-copyer.copy",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_C,
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