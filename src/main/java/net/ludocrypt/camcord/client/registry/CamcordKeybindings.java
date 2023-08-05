package net.ludocrypt.camcord.client.registry;

import com.mojang.blaze3d.platform.InputUtil;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.ludocrypt.camcord.client.config.CamcordConfig;
import net.ludocrypt.camcord.common.data.CamcordData;
import net.ludocrypt.camcord.common.util.CamcordLogType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBind;
import org.lwjgl.glfw.GLFW;

public class CamcordKeybindings {
	public static final KeyBind TOGGLE_SHADER = KeyBindingHelper.registerKeyBinding(new KeyBind("camcord.keybinding.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "camcord.keybinding.category"));
	public static final KeyBind OPEN_CONFIG = KeyBindingHelper.registerKeyBinding(new KeyBind("camcord.keybinding.config", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "camcord.keybinding.category"));
	public static void init() {
		CamcordData.sendToLog(CamcordLogType.INFO, "Registering Keybindings.");
	}
	public static void tick() {
		if (TOGGLE_SHADER.wasPressed()) {
			CamcordConfig.getInstance().enabled = !CamcordConfig.getInstance().enabled;
			CamcordConfig.saveConfig();
		}
		if (OPEN_CONFIG.wasPressed()) MinecraftClient.getInstance().setScreen(AutoConfig.getConfigScreen(CamcordConfig.class, MinecraftClient.getInstance().currentScreen).get());
	}
}
