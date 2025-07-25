package invalid.myask.targaseule;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean shield_pitch_matters = false;
    public static boolean shield_knockback_enabled = true;
    public static boolean block_on_crouch = false;
    public static boolean prevent_block_if_other_hand_using = true;
    public static boolean sword_block_lets_shield_block = true;

    public static float shield_default_melee_block = 1.0F; // 2F / 3F;
    public static float shield_default_ranged_block = 1.0F;
    public static int vanilla_shield_durability = 336;

    public static int default_shield_cooldown = 5;
    public static int default_shield_warmup = 5;
    public static int axe_shield_cooldown = 100;
    public static int axe_cleaving_cooldown = 10;
    public static boolean cleave_bonus_even_failed = false;

    public static float axe_disable_chance_base = 0.25F;
    public static float axe_disable_chance_sprint_bonus = 0.75F; // so sayeth wiki
    public static float axe_disable_chance_efficiency_bonus = 0.05F; // known

    public static boolean fix_gimbal_lock = true;
    public static boolean old_shield_old_stats = true;
    public static boolean old_shield_enable = true;
    public static boolean enable_easter_egg = true;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        shield_pitch_matters = configuration.getBoolean(
            "shield_pitch_matters",
            "shield",
            shield_pitch_matters,
            "Make shield pitch matter for block angle calc (nonvanilla)",
            "config.shield.pitch.matters");
        shield_knockback_enabled = configuration.getBoolean(
            "shield_knockback_enabled",
            "shield",
            shield_knockback_enabled,
            "Make shield knock back melee attackers (like in Bedrock or pre-1.14 bugging it out)",
            "config.shield.knockback");
        block_on_crouch = configuration.getBoolean(
            "block_on_crouch",
            "shield",
            block_on_crouch,
            "Block on crouch like in Bedrock (works, doesn't look good)",
            "config.shield.block.on.crouch");
        prevent_block_if_other_hand_using = configuration.getBoolean(
            "prevent_block_if_other_hand_using",
            "shield",
            prevent_block_if_other_hand_using,
            "Prevent block if other hand is using an item (vanilla)",
            "config.shield.block.if.other.hand.using");
        sword_block_lets_shield_block = configuration.getBoolean(
            "sword_block_lets_shield_block",
            "shield",
            sword_block_lets_shield_block,
            "Let shield block if sword is in mainhand, even if 'prevent block if other hand using' true (sort of vanilla--shields and sword block never co-existed)",
            "config.shield.block.if.sword.blocking");

        shield_default_melee_block = configuration.getFloat(
            "shield_default_melee_block",
            "shield",
            shield_default_melee_block,
            0,
            1.01F,
            "What fraction of mêlée damage to block. (0.66666667 for 1.9-1.10 behavior, else 1.0 vanilla)",
            "config.shield.block.fraction.melee");
        shield_default_ranged_block = configuration.getFloat(
            "shield_default_ranged_block",
            "shield",
            shield_default_ranged_block,
            0,
            1.01F,
            "What fraction of ranged damage to block. (1.0 vanilla)",
            "config.shield.block.fraction.ranged");
        vanilla_shield_durability = configuration.getInt(
            "vanilla_shield_durability",
            "shield",
            vanilla_shield_durability,
            1,
            Integer.MAX_VALUE,
            "Durability of default shield. (181 for 1.9pre value, 336 thereafter)",
            "config.shield.durability.default");

        default_shield_cooldown = configuration.getInt(
            "default_shield_cooldown",
            "shield",
            default_shield_cooldown,
            0,
            Integer.MAX_VALUE,
            "Duration of shield cooldown on hit, in ticks (20 ticks per second. 5 default vanilla, was briefly 10)",
            "config.shield.cooldown.default");
        default_shield_warmup = configuration.getInt(
            "default_shield_warmup",
            "shield",
            default_shield_warmup,
            0,
            Integer.MAX_VALUE,
            "Duration of shield warmup before blocking can happen, in ticks (20 ticks per second)",
            "config.shield.warmup.default");// TODO: implement

        axe_shield_cooldown = configuration.getInt(
            "axe_shield_cooldown",
            "shield.axe",
            axe_shield_cooldown,
            0,
            Integer.MAX_VALUE,
            "Duration of shield cooldown on axe disabling hit, in ticks (20 ticks/s, 100 default vanilla, 32 combat tests)",
            "config.axe.shield.disable.time");
        axe_cleaving_cooldown = configuration.getInt(
            "axe_cleaving_cooldown",
            "shield.axe",
            axe_cleaving_cooldown,
            0,
            Integer.MAX_VALUE,
            "[Note this mod does not add a Cleaving axe enchant.] Duration of shield cooldown added per level of Cleaving on axe, in ticks (20 ticks/s, 10 default combat test)",
            "config.axe.shield.disable.time.cleaving");
        cleave_bonus_even_failed = configuration.getBoolean(
            "cleave_bonus_even_failed",
            "shield.axe",
            cleave_bonus_even_failed,
            "[Note this mod does not add a Cleaving axe enchant.] Apply bonus cooldown from cleaving even if axe fails disable chance roll (nonvanilla)",
            "config.axe.cleave.disable.on.fail");

        axe_disable_chance_base = configuration.getFloat(
            "axe_disable_chance_base",
            "shield.axe",
            axe_disable_chance_base,
            Float.MIN_VALUE,
            Float.MAX_VALUE,
            "Axe disable base chance (0-1 expected. 1 for post-1.11 behavior, 0.25 for 0.9-1.11)",
            "config.axe.shield.disable.chance.base");
        axe_disable_chance_sprint_bonus = configuration.getFloat(
            "axe_disable_chance_sprint_bonus",
            "shield.axe",
            axe_disable_chance_sprint_bonus,
            Float.MIN_VALUE,
            Float.MAX_VALUE,
            "Axe disable chance bonus for sprint-attack (0-1 expected. 0.75 default)",
            "config.axe.shield.disable.chance.sprint"); // dunno
        axe_disable_chance_efficiency_bonus = configuration.getFloat(
            "axe_disable_chance_sprint_bonus",
            "shield.axe",
            axe_disable_chance_efficiency_bonus,
            Float.MIN_VALUE,
            Float.MAX_VALUE,
            "Axe disable chance bonus per Efficiency level (0-1 expected. 0.05 for vanilla)",
            "config.axe.shield.disable.chance.efficiency");

        old_shield_old_stats = configuration.getBoolean(
            "old_shield_old_stats",
            "shield",
            old_shield_old_stats,
            "Whether to make 'old shields' work like very early snapshot.",
            "config.shield.old.stats.snapshot");
        old_shield_enable = configuration.getBoolean(
            "old_shield_enable",
            "shield",
            old_shield_enable,
            "Enable old woollen colored shields",
            "config.shield.old.enable");
        fix_gimbal_lock = configuration.getBoolean(
            "fix_gimbal_lock",
            "shield",
            fix_gimbal_lock,
            "Fix bug where looking straight up or down makes blocking impossible",
            "config.shield.bug.gimbal.lock.fix");
        enable_easter_egg = configuration.getBoolean(
            "enable_easter_egg",
            "shield",
            enable_easter_egg,
            "Enable a joke.",
            "config.shield.easter_egg");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
