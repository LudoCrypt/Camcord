package net.ludocrypt.camcord;

import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.blaze3d.platform.InputUtil;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.ludocrypt.camcord.config.CamcordConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBind;
import net.minecraft.util.Identifier;

public class Camcord implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Camcord");

	public static boolean shouldRender = false;
	public static final KeyBind VHS = KeyBindingHelper.registerKeyBinding(new KeyBind("camcord.key.vhs", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "key.categories.misc"));

	public static final ManagedShaderEffect BOKEH = ShaderEffectManager.getInstance().manage(new Identifier("camcord", "shaders/post/bokeh.json"), (shader) -> {
		shader.setSamplerUniform("DepthSampler", ((ReadableDepthFramebuffer) MinecraftClient.getInstance().getFramebuffer()).getStillDepthMap());
	});

	public static final ManagedShaderEffect FISH_EYE = ShaderEffectManager.getInstance().manage(new Identifier("camcord", "shaders/post/fisheye.json"));
	public static final ManagedShaderEffect VHS_OVERLAY = ShaderEffectManager.getInstance().manage(new Identifier("camcord", "shaders/post/vhs.json"));

	@Override
	public void onInitializeClient(ModContainer mod) {
		AutoConfig.register(CamcordConfig.class, GsonConfigSerializer::new);
		ClientTickEvents.END.register(client -> {
			while (VHS.wasPressed()) {
				shouldRender = !shouldRender;
			}
		});

		ShaderEffectRenderCallback.EVENT.register((tickDelta) -> {
			if (shouldRender) {
				BOKEH.setUniformValue("nearPlane", CamcordConfig.getInstance().nearPlane);
				BOKEH.setUniformValue("farPlane", CamcordConfig.getInstance().farPlane);

				FISH_EYE.setUniformValue("C1", CamcordConfig.getInstance().fisheyeZoom);
				FISH_EYE.setUniformValue("C2", CamcordConfig.getInstance().fisheyeScale);
				FISH_EYE.setUniformValue("zoomFactor", CamcordConfig.getInstance().fisheyePinch);

				BOKEH.render(tickDelta);
				FISH_EYE.render(tickDelta);

				if (CamcordConfig.getInstance().boarder) {
					VHS_OVERLAY.render(tickDelta);
				}
			}
		});
	}

}
