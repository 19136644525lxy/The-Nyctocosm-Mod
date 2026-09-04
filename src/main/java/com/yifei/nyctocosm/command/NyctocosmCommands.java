package com.yifei.nyctocosm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.yifei.nyctocosm.NyctocosmMod;
import com.yifei.nyctocosm.spell.*;
import com.yifei.nyctocosm.spell.data.ISpellPlayerData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

/**
 * Nyctocosm 调试与管理命令注册。
 * <p>
 * 用法：
 * <pre>
 * /nyctocosm list                列出所有已注册法术及元信息
 * /nyctocosm cast &lt;spell_id&gt;     对命令执行者直接施放指定法术（跳过蜡烛阵，便于测试）
 * /nyctocosm cancel              取消执行者当前吟唱
 * /nyctocosm status [player]     查看玩家吟唱/持续法术/属性统计
 * </pre>
 * 命令权限：list/status 为 0（任何人可用）；cast/cancel 为 2（仅作弊/OPS）。
 *
 * 通过 {@link RegisterCommandsEvent} 自动注册到 Forge 命令分发器。
 */
@Mod.EventBusSubscriber(modid = NyctocosmMod.MOD_ID)
public final class NyctocosmCommands {

    private NyctocosmCommands() {}

    /** 法术 ID 建议补全（列出 SpellRegistry 全部 ID） */
    private static final SuggestionProvider<CommandSourceStack> SPELL_SUGGESTIONS =
            (ctx, builder) -> SharedSuggestionProvider.suggest(
                    SpellRegistry.all().stream().map(Spell::getId), builder);

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // 根节点：/nyctocosm
        var root = Commands.literal("nyctocosm")
                .requires(src -> true); // 所有人都能看到子命令；子命令自行控制权限

        // —— list 子命令（权限 0）——
        root.then(Commands.literal("list")
                .executes(NyctocosmCommands::listSpells));

        // —— cast <spell_id> 子命令（权限 2）——
        root.then(Commands.literal("cast")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("spell_id", StringArgumentType.word())
                        .suggests(SPELL_SUGGESTIONS)
                        .executes(NyctocosmCommands::castSpell)));

        // —— cancel 子命令（权限 2）——
        root.then(Commands.literal("cancel")
                .requires(src -> src.hasPermission(2))
                .executes(NyctocosmCommands::cancelCasting));

        // —— status [player] 子命令（权限 0，其他玩家需权限 2）——
        root.then(Commands.literal("status")
                .executes(ctx -> showStatus(ctx, null))
                .then(Commands.argument("player", EntityArgument.player())
                        .requires(src -> src.hasPermission(2))
                        .executes(ctx -> showStatus(ctx, EntityArgument.getPlayer(ctx, "player")))));

        // —— matrix：列出 16 蜡烛精确偏移 + 验证当前玩家脚下矩阵（P3.5 调试）——
        root.then(Commands.literal("matrix")
                .requires(src -> src.hasPermission(2))
                .executes(NyctocosmCommands::matrixInfo));

        // —— catalyst：列出 17 种掉落物 → 法术映射（P3.5 调试）——
        root.then(Commands.literal("catalyst")
                .executes(NyctocosmCommands::catalystList));

        dispatcher.register(root);
        NyctocosmMod.LOGGER.debug("[NyctocosmCommands] 注册 /nyctocosm 命令（list/cast/cancel/status/matrix/catalyst）");
    }

    // ============================================================
    // list: 列出所有已注册法术
    // ============================================================
    private static int listSpells(CommandContext<CommandSourceStack> ctx) {
        Collection<Spell> all = SpellRegistry.all();
        MutableComponent header = Component.literal("===== Nyctocosm 法术列表（共 "
                + all.size() + " 个）=====").withStyle(ChatFormatting.GOLD);
        ctx.getSource().sendSuccess(() -> header, false);

        for (Spell s : all) {
            MutableComponent line = Component.literal("  ")
                    .append(Component.literal(s.getId()).withStyle(ChatFormatting.YELLOW))
                    .append(Component.literal("  [" + s.getCategory().name() + "] ").withStyle(ChatFormatting.GRAY))
                    .append(formatElements(s))
                    .append(Component.literal("  dmg=" + s.getBaseDamage()
                            + "  cast=" + s.getCastTime() + "t  dur=" + s.getDuration() + "t  pwr=" + s.getPower())
                            .withStyle(ChatFormatting.DARK_GRAY));
            ctx.getSource().sendSuccess(() -> line, false);
        }
        return all.size();
    }

    private static MutableComponent formatElements(Spell s) {
        MutableComponent c = Component.literal("{");
        boolean first = true;
        for (ElementType t : ElementType.values()) {
            if (s.hasElement(t)) {
                if (!first) c.append(Component.literal(","));
                c.append(Component.literal(t.name()).withStyle(t.getChatFormat()));
                first = false;
            }
        }
        c.append(Component.literal("}"));
        return c;
    }

    // ============================================================
    // cast <spell_id>: 直接施法（绕过蜡烛阵）
    // ============================================================
    private static int castSpell(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer executor;
        try {
            executor = ctx.getSource().getPlayerOrException();
        } catch (Exception ex) {
            ctx.getSource().sendFailure(Component.literal("该子命令必须由玩家执行").withStyle(ChatFormatting.RED));
            return 0;
        }
        String id = StringArgumentType.getString(ctx, "spell_id");
        int rc = SpellCastingManager.castDirect(executor, id);
        return switch (rc) {
            case 1 -> {
                Spell s = SpellRegistry.byId(id);
                String info = (s == null) ? id : s.getId()
                        + "（吟唱 " + s.getCastTime() + " tick，元素" + describeElements(s) + "）";
                ctx.getSource().sendSuccess(() -> Component.literal("[Nyctocosm] 开始吟唱：" + info)
                        .withStyle(ChatFormatting.GREEN), true);
                yield 1;
            }
            case -1 -> {
                ctx.getSource().sendFailure(Component.literal("[Nyctocosm] 玩家已在吟唱中，请先等待或 /nyctocosm cancel")
                        .withStyle(ChatFormatting.RED));
                yield -1;
            }
            case -2 -> {
                ctx.getSource().sendFailure(Component.literal("[Nyctocosm] 法术不存在：" + id
                        + "（/nyctocosm list 查看全部）").withStyle(ChatFormatting.RED));
                yield -2;
            }
            case -3 -> {
                ctx.getSource().sendFailure(Component.literal("[Nyctocosm] 施法条件不满足（法术 canCast 返回 false）")
                        .withStyle(ChatFormatting.RED));
                yield -3;
            }
            default -> {
                ctx.getSource().sendFailure(Component.literal("[Nyctocosm] 施法失败（玩家无效/旁观或 Capability 缺失）")
                        .withStyle(ChatFormatting.RED));
                yield 0;
            }
        };
    }

    private static String describeElements(Spell s) {
        StringBuilder sb = new StringBuilder();
        for (ElementType t : ElementType.values()) if (s.hasElement(t)) {
            if (!sb.isEmpty()) sb.append('/');
            sb.append(t.name());
        }
        return sb.toString();
    }

    // ============================================================
    // cancel: 取消执行者的吟唱
    // ============================================================
    private static int cancelCasting(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer executor;
        try {
            executor = ctx.getSource().getPlayerOrException();
        } catch (Exception ex) {
            ctx.getSource().sendFailure(Component.literal("该子命令必须由玩家执行").withStyle(ChatFormatting.RED));
            return 0;
        }
        final boolean[] ok = {false};
        executor.getCapability(SpellCapabilities.SPELL_PLAYER).ifPresent(data -> {
            if (data.isCasting()) {
                Spell cur = data.getCurrentSpell();
                if (cur != null) cur.onCancelled(executor, executor.level());
                data.cancelCasting();
                ok[0] = true;
            }
        });
        if (ok[0]) {
            ctx.getSource().sendSuccess(() -> Component.literal("[Nyctocosm] 已取消吟唱").withStyle(ChatFormatting.GOLD), true);
            return 1;
        } else {
            ctx.getSource().sendFailure(Component.literal("[Nyctocosm] 玩家当前未在吟唱").withStyle(ChatFormatting.GRAY));
            return 0;
        }
    }

    // ============================================================
    // status [player]: 显示玩家法术状态
    // ============================================================
    private static int showStatus(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
        ServerPlayer who;
        if (target != null) {
            who = target;
        } else {
            try {
                who = ctx.getSource().getPlayerOrException();
            } catch (Exception ex) {
                ctx.getSource().sendFailure(Component.literal("控制台执行 status 必须指定目标玩家：/nyctocosm status <player>")
                        .withStyle(ChatFormatting.RED));
                return 0;
            }
        }
        final ISpellPlayerData[] holder = {null};
        who.getCapability(SpellCapabilities.SPELL_PLAYER).ifPresent(d -> holder[0] = d);
        ISpellPlayerData data = holder[0];
        if (data == null) {
            ctx.getSource().sendFailure(Component.literal("[Nyctocosm] 无法读取玩家法术 Capability").withStyle(ChatFormatting.RED));
            return 0;
        }

        MutableComponent head = Component.literal("===== " + who.getName().getString() + " 法术状态 =====")
                .withStyle(ChatFormatting.GOLD);
        ctx.getSource().sendSuccess(() -> head, false);

        if (data.isCasting()) {
            Spell cur = data.getCurrentSpell();
            String info = (cur == null) ? "（数据异常：无 spell 引用）" :
                    cur.getId() + "（剩余 " + data.getCastTicksLeft() + "/" + cur.getCastTime() + " tick）";
            ctx.getSource().sendSuccess(() -> Component.literal(" 吟唱中：").withStyle(ChatFormatting.RED)
                    .append(info), false);
        } else {
            ctx.getSource().sendSuccess(() -> Component.literal(" 吟唱中：无").withStyle(ChatFormatting.GRAY), false);
        }

        if (data.getActiveSpells().isEmpty()) {
            ctx.getSource().sendSuccess(() -> Component.literal(" 持续法术：无").withStyle(ChatFormatting.GRAY), false);
        } else {
            MutableComponent line = Component.literal(" 持续法术：").withStyle(ChatFormatting.YELLOW);
            boolean first = true;
            for (var entry : data.getActiveSpells().entrySet()) {
                if (!first) line.append(Component.literal(", "));
                line.append(Component.literal(entry.getKey() + "(" + entry.getValue() + "t)"));
                first = false;
            }
            final MutableComponent fline = line;
            ctx.getSource().sendSuccess(() -> fline, false);
        }

        // 属性：traceChanceBase + spellPower
        ctx.getSource().sendSuccess(() -> Component.literal(" 基础掉落概率 traceChanceBase=" + data.getTraceChanceBase()
                + " / 法术强度 spellPower=" + data.getSpellPower()).withStyle(ChatFormatting.DARK_GRAY), false);

        // 统计：按元素显示伤害/击杀/施法次数
        int totalDmg = 0, totalKills = 0, totalCasts = 0;
        for (int i = 0; i < 6; i++) {
            totalDmg += data.getDamageDealt()[i];
            totalKills += data.getKills()[i];
            totalCasts += data.getSpellsCast()[i];
        }
        int finalTotalDmg = totalDmg;
        int finalTotalKills = totalKills;
        int finalTotalCasts = totalCasts;
        ctx.getSource().sendSuccess(() -> Component.literal(" 累计：伤害=" + finalTotalDmg + " 击杀="
                + finalTotalKills + " 施法=" + finalTotalCasts).withStyle(ChatFormatting.DARK_GRAY), false);

        return 1;
    }

    // ============================================================
    // matrix: 显示 16 蜡烛精确偏移 + 当前玩家脚下校验（P3.5 调试）
    // ============================================================
    private static int matrixInfo(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer executor;
        try {
            executor = ctx.getSource().getPlayerOrException();
        } catch (Exception ex) {
            ctx.getSource().sendFailure(Component.literal("matrix 必须由玩家执行").withStyle(ChatFormatting.RED));
            return 0;
        }
        java.util.List<BlockPos> offs = CandleMatrixMatcher.candleOffsets();
        BlockPos center = executor.blockPosition();

        MutableComponent head = Component.literal("===== 蜡烛阵 16 坐标（相对玩家 (dx,dz)）=====")
                .withStyle(ChatFormatting.GOLD);
        ctx.getSource().sendSuccess(() -> head, false);

        // 按格式化为 5 行：±3 列（6 个） / ±3 行（6 个） / 对角（4 个）
        String groupLabels = "±3 列 (X=±3,Z∈{0,±1})  /  ±3 行 (X∈{0,±1},Z=±3)  /  对角 (±2,±2)";
        ctx.getSource().sendSuccess(() -> Component.literal(" 分组：" + groupLabels)
                .withStyle(ChatFormatting.DARK_GRAY), false);

        int pass = 0;
        for (BlockPos off : offs) {
            BlockPos worldPos = center.offset(off);
            boolean ok = CandleMatrixMatcher.verifyMatrixPosition(executor.level(), center, off);
            if (ok) pass++;
            String txt = String.format("  (%+d, %+d)  -> world %s  %s",
                    off.getX(), off.getZ(),
                    "[" + worldPos.getX() + " " + worldPos.getY() + " " + worldPos.getZ() + "]",
                    ok ? "✅" : "❌");
            MutableComponent line = Component.literal(txt).withStyle(ok ? ChatFormatting.GREEN : ChatFormatting.RED);
            ctx.getSource().sendSuccess(() -> line, false);
        }
        int finalPass = pass;
        MutableComponent sum = Component.literal("汇总：通过 " + finalPass + "/" + offs.size())
                .withStyle(finalPass == offs.size() ? ChatFormatting.GREEN : ChatFormatting.RED);
        ctx.getSource().sendSuccess(() -> sum, false);
        return finalPass;
    }

    // ============================================================
    // catalyst: 列出 17 种掉落物 → 法术映射（P3.5 调试）
    // ============================================================
    private static int catalystList(CommandContext<CommandSourceStack> ctx) {
        var map = CandleMatrixMatcher.catalystMap();
        MutableComponent header = Component.literal("===== Nyctocosm 催化剂（17 种互斥掉落物）=====")
                .withStyle(ChatFormatting.GOLD);
        ctx.getSource().sendSuccess(() -> header, false);

        for (var entry : map.entrySet()) {
            Spell spell = SpellRegistry.byId(entry.getValue());
            String itemName = entry.getKey().getDescription().getString(); // 翻译后的显示名
            String itemId = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(entry.getKey()).toString();
            MutableComponent line = Component.literal("  ")
                    .append(Component.literal(itemId).withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("（").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(itemName).withStyle(ChatFormatting.WHITE))
                    .append(Component.literal("） → ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(entry.getValue()).withStyle(ChatFormatting.YELLOW));
            if (spell != null) {
                line.append(Component.literal("  [" + spell.getCategory().name() + "] ").withStyle(ChatFormatting.DARK_GRAY));
                // 元素组合
                boolean first = true;
                StringBuilder sb = new StringBuilder("{");
                for (ElementType t : ElementType.values()) if (spell.hasElement(t)) {
                    if (!first) sb.append(",");
                    sb.append(t.name());
                    first = false;
                }
                sb.append("}");
                line.append(Component.literal(sb.toString()).withStyle(ChatFormatting.DARK_GRAY));
            }
            ctx.getSource().sendSuccess(() -> line, false);
        }
        return map.size();
    }
}
