package net.axiomdev;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class StepUpClient implements ClientModInitializer {
	static SettingsManager settingsManager;

	@Override
	public void onInitializeClient() {
		settingsManager = new SettingsManager("settings.json", "./config/stepup/");
		ClientCommandRegistrationCallback.EVENT.register(StepUpCommand::register);
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.player == null) return;
			if (settingsManager.settings.OnlyWhileSprinting && !client.player.isSprinting()) {
				client.player.setStepHeight(0.6f);
			}
			else {
				client.player.setStepHeight(settingsManager.settings.StepHeight);
			}
		});
	}
}