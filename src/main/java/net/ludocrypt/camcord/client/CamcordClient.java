package net.ludocrypt.camcord.client;

import net.ludocrypt.camcord.client.config.CamcordConfig;
import net.ludocrypt.camcord.client.registry.CamcordKeybindings;
import net.ludocrypt.camcord.client.shaders.CamcordShaders;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class CamcordClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		AutoConfig.register(CamcordConfig.class, GsonConfigSerializer::new);
		CamcordShaders.init();
		CamcordKeybindings.init();
		tick();
	}
	private void tick() {
		ClientTickEvents.END.register(client -> {
			CamcordKeybindings.tick();
		});
	}
}
