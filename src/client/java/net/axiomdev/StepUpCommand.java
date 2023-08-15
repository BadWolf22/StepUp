package net.axiomdev;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

public class StepUpCommand {
    static LiteralArgumentBuilder<FabricClientCommandSource> stepUpCmd = literal("stepup").executes(ctx -> {
        ctx.getSource().sendFeedback(Text.of("Developed by BadWolf22"));
        return 0;
    });
    static LiteralArgumentBuilder<FabricClientCommandSource> setCmd = literal("set");
    static RequiredArgumentBuilder<FabricClientCommandSource, Float> heightArg = argument("height", floatArg(0))
            .executes(ctx -> {
                var stepHeight = getFloat(ctx, "height");
                ctx.getSource().sendFeedback(Text.of("Step height changed to: " + stepHeight));
                setStepHeight(stepHeight, ctx.getSource().getPlayer());
                return 0;
            });
    static LiteralArgumentBuilder<FabricClientCommandSource> resetCmd = literal("reset").executes(ctx -> {
        var stepHeight = 0.6f;
        ctx.getSource().sendFeedback(Text.of("Step height reset"));
        setStepHeight(stepHeight, ctx.getSource().getPlayer());
        return 0;
    });

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandRegistryAccess registryAccess) {
        dispatcher.register(stepUpCmd
                .then(setCmd.then(heightArg))
                .then(resetCmd));
    }

    public static void setStepHeight(float stepHeight, ClientPlayerEntity player) {
        StepUpClient.settingsManager.settings.StepHeight = stepHeight;
        StepUpClient.settingsManager.save();
    }
}