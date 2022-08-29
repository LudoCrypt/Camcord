package net.ludocrypt.camcord.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "camcord")
public class CamcordConfig implements ConfigData {

	public float fisheyeZoom = 2.0F;
	public float fisheyeScale = 0.4F;
	public float fisheyePinch = 1.75F;

	public float nearPlane = 1.0F;
	public float farPlane = 100.0F;

	public boolean boarder = true;

	public static CamcordConfig getInstance() {
		return AutoConfig.getConfigHolder(CamcordConfig.class).getConfig();
	}

}
