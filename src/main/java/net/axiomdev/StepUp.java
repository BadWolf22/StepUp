package net.axiomdev;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepUp implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("stepup");

	@Override
	public void onInitialize() {
		LOGGER.warn("This is a client-only mod.");
	}
}