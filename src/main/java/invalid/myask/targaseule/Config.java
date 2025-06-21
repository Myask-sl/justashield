package invalid.myask.targaseule;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean shield_pitch_matters = false;
    public static boolean shield_knockback_enabled = true;
    public static boolean block_on_crouch = false;

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
            "Block on crouch like in Bedrock",
            "config.shield.block.on.crouch");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
