package net.ludocrypt.camcord.common.data;

import net.ludocrypt.camcord.common.util.CamcordLogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamcordData {
	public static final String NAME = "Camcord";
	public static final String ID = "camcord";
	public static final Integer MAJOR_VERSION = 1;
	public static final Integer MINOR_VERSION = 2;
	public static final Integer PATCH_VERSION = 0;
	public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION;
	public static final String PREFIX = "[" + NAME + " " + VERSION + "] ";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static void sendToLog(CamcordLogType logType, String log) {
		if (logType.equals(CamcordLogType.WARN)) LOGGER.warn(PREFIX + log);
		if (logType.equals(CamcordLogType.ERROR)) LOGGER.error(PREFIX + log);
		if (logType.equals(CamcordLogType.DEBUG)) LOGGER.debug(PREFIX + log);
		if (logType.equals(CamcordLogType.INFO)) LOGGER.info(PREFIX + log);
	}
}
