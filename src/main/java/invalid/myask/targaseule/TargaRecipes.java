package invalid.myask.targaseule;

import net.minecraft.item.ItemDye;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

import invalid.myask.undertow.recipes.MissingShieldRecipe;
import invalid.myask.undertow.recipes.OldShieldRecipe;

public class TargaRecipes {

    public static void register() {
        GameRegistry.addRecipe(new ShapedOreRecipe(TargaItems.SHIELD,
            "#-#",
            "###",
            " # ",
            '#', "plankWood",
            '-', "ingotIron"));
        if (Config.old_shield_enable) {
            for (int i = 0; i < ItemDye.field_150923_a.length; i++) {
                GameRegistry.addRecipe(new OldShieldRecipe(i));
            }
        }
        if (Config.enable_easter_egg)
            GameRegistry.addRecipe(MissingShieldRecipe.instance);
    }
}
