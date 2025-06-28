package invalid.myask.targaseule;

import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

import invalid.myask.undertow.recipes.MissingShieldRecipe;

public class TargaRecipes {

    public static void register() {
        GameRegistry.addRecipe(new ShapedOreRecipe(TargaItems.SHIELD,
            "#-#",
            "###",
            " # ",
            '#', "plankWood",
            '-', "ingotIron"));
        if (Config.enable_easter_egg)
            GameRegistry.addRecipe(MissingShieldRecipe.instance);
    }
}
