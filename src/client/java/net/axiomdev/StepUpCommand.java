package net.axiomdev;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
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
                ctx.getSource().sendFeedback(Text.of("Step height changed to: " + getFloat(ctx, "height")));
                ctx.getSource().getPlayer().setStepHeight(getFloat(ctx, "height"));
                return 0;
            });
    static LiteralArgumentBuilder<FabricClientCommandSource> resetCmd = literal("reset").executes(ctx -> {
        ctx.getSource().sendFeedback(Text.of("Step height reset"));
        ctx.getSource().getPlayer().setStepHeight(0.6f);
        return 0;
    });

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandRegistryAccess registryAccess) {
        dispatcher.register(stepUpCmd
                .then(setCmd.then(heightArg))
                .then(resetCmd));
    }
}