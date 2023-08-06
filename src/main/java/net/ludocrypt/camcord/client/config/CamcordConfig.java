package net.ludocrypt.camcord.client.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "camcord")
public class CamcordConfig implements ConfigData {
	@ConfigEntry.Gui.Tooltip()
	public boolean enabled = false;
	public float fisheyeZoom = 2.0F;
	public float fisheyeScale = 0.4F;
	public float fisheyePinch = 1.75F;
	public float nearPlane = 1.0F;
	public float farPlane = 100.0F;
	@ConfigEntry.Gui.Tooltip()
	public boolean boarder = true;
	private static ConfigHolder<CamcordConfig> getConfigHolder() { return AutoConfig.getConfigHolder(CamcordConfig.class); }
	public static CamcordConfig getInstance() { return getConfigHolder().getConfig(); }
	public static void saveConfig() { getConfigHolder().save(); }
}
