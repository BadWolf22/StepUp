package net.axiomdev;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;


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

    static LiteralArgumentBuilder<FabricClientCommandSource> helpCmd = literal("help").executes(ctx -> {
        ctx.getSource().sendFeedback(Text.of("For usage examples, execute a command without any parameters."));
        ctx.getSource().sendFeedback(Text.of("§6height§r: Used to adjust the height of your step."));
        ctx.getSource().sendFeedback(Text.of("§6sprintOnly§r: Used to set whether to StepUp only while sprinting or not."));
        ctx.getSource().sendFeedback(Text.of("§6reset§r: Used to get back to normal."));
        return 0;
    });

    static LiteralArgumentBuilder<FabricClientCommandSource> heightCmd = literal("height").executes(ctx -> {
        ctx.getSource().sendFeedback(Text.of("Used to adjust the height of your step."));
        ctx.getSource().sendFeedback(Text.of("  Usage Example: \"/stepup height 1.1\""));
        return 0;
    });
    static RequiredArgumentBuilder<FabricClientCommandSource, Float> heightArg = argument("height", floatArg(0))
            .executes(ctx -> {
                var stepHeight = getFloat(ctx, "height");
                ctx.getSource().sendFeedback(Text.of("Step height changed to: " + stepHeight));
                setStepHeight(stepHeight, ctx.getSource().getPlayer());
                return 0;
            });

    static LiteralArgumentBuilder<FabricClientCommandSource> sprintCmd = literal("sprintOnly").executes(ctx -> {
        ctx.getSource().sendFeedback(Text.of("Used to set whether to StepUp only while sprinting or not."));
        ctx.getSource().sendFeedback(Text.of("  Usage Example: \"/stepup sprintOnly true\""));
        return 0;
    });
    static RequiredArgumentBuilder<FabricClientCommandSource, Boolean> sprintArg = argument("sprintOnly", bool())
            .executes(ctx -> {
                var sprintOnly = getBool(ctx, "sprintOnly");
                ctx.getSource().sendFeedback(Text.of("Activation changed to: " + (sprintOnly ? "Sprinting" : "Always" )));
                setOnlyWhileSprinting(sprintOnly);
                return 0;
            });

    static LiteralArgumentBuilder<FabricClientCommandSource> resetCmd = literal("reset").executes(ctx -> {
        var stepHeight = 0.6f;
        ctx.getSource().sendFeedback(Text.of("Step height reset"));
        setStepHeight(stepHeight, ctx.getSource().getPlayer());
        setOnlyWhileSprinting(true);
        return 0;
    });

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandRegistryAccess registryAccess) {
        dispatcher.register(stepUpCmd
                .then(helpCmd)
                .then(heightCmd.then(heightArg))
                .then(sprintCmd.then(sprintArg))
                .then(resetCmd));
    }

    public static void setStepHeight(float stepHeight, ClientPlayerEntity player) {
        StepUpClient.settingsManager.settings.StepHeight = stepHeight;
        StepUpClient.settingsManager.save();
    }

    public static void setOnlyWhileSprinting(boolean val) {
        StepUpClient.settingsManager.settings.OnlyWhileSprinting = val;
        StepUpClient.settingsManager.save();
    }
}