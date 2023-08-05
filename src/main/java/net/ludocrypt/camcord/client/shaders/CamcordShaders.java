package net.ludocrypt.camcord.client.shaders;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.ludocrypt.camcord.client.config.CamcordConfig;
import net.ludocrypt.camcord.common.data.CamcordData;
import net.ludocrypt.camcord.common.util.CamcordLogType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class CamcordShaders {
	private static final ManagedShaderEffect SHADER_BOKEH;
	private static final ManagedShaderEffect SHADER_FISH_EYE;
	private static final ManagedShaderEffect SHADER_VHS;
	private static ManagedShaderEffect getShaderData(String shader) {
		return ShaderEffectManager.getInstance().manage(new Identifier(CamcordData.ID, "shaders/post/" + shader + ".json"));
	}
	private static ManagedShaderEffect getShaderData(String shader, Consumer<ManagedShaderEffect> initCallback) {
		return ShaderEffectManager.getInstance().manage(new Identifier(CamcordData.ID, "shaders/post/" + shader + ".json"), initCallback);
	}
	public static void init() {
		CamcordData.sendToLog(CamcordLogType.INFO, "Registering Shaders.");
		ShaderEffectRenderCallback.EVENT.register((tickDelta) -> {
			if (CamcordConfig.getInstance().enabled) {
				SHADER_BOKEH.setUniformValue("nearPlane", CamcordConfig.getInstance().nearPlane);
				SHADER_BOKEH.setUniformValue("farPlane", CamcordConfig.getInstance().farPlane);

				SHADER_FISH_EYE.setUniformValue("C1", CamcordConfig.getInstance().fisheyeZoom);
				SHADER_FISH_EYE.setUniformValue("C2", CamcordConfig.getInstance().fisheyeScale);
				SHADER_FISH_EYE.setUniformValue("zoomFactor", CamcordConfig.getInstance().fisheyePinch);

				SHADER_BOKEH.render(tickDelta);
				SHADER_FISH_EYE.render(tickDelta);

				if (CamcordConfig.getInstance().boarder) {
					SHADER_VHS.render(tickDelta);
				}
			}
		});
	}
	static {
		SHADER_BOKEH = getShaderData("bokeh", (shader) -> {
			shader.setSamplerUniform("DepthSampler", ((ReadableDepthFramebuffer) MinecraftClient.getInstance().getFramebuffer()).getStillDepthMap());
		});
		SHADER_FISH_EYE = getShaderData("fisheye");
		SHADER_VHS = getShaderData("vhs");
	}
}
