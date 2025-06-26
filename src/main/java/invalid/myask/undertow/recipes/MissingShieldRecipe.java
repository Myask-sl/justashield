package invalid.myask.undertow.recipes;

import invalid.myask.targaseule.TargaItems;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class MissingShieldRecipe implements IRecipe {
    public static MissingShieldRecipe instance = new MissingShieldRecipe();

    private static final ItemStack output = new ItemStack(TargaItems.SHIELD);
    static {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("easter_egg", true);
        output.setTagCompound(nbt);
    }

    static IRecipe coreRecipe = new ShapedOreRecipe(TargaItems.SHIELD,
        "B P",
        " S ",
        "P B",
        'P', new ItemStack(Blocks.wool, 1, 10),
        'B', new ItemStack(Blocks.wool, 1, 15),
        'S', new ItemStack(TargaItems.SHIELD, 1, OreDictionary.WILDCARD_VALUE));
    @Override
    public boolean matches(InventoryCrafting table, World w) {
        if (coreRecipe.matches(table, w)) {
            NBTTagCompound nbt = table.getStackInRowAndColumn(1,1).getTagCompound();
            return nbt == null || !nbt.getBoolean("easter_egg");
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting table) {
        ItemStack result = table.getStackInRowAndColumn(1,1).copy();
        NBTTagCompound nbt = result.getTagCompound();
        if (nbt == null){
            nbt = new NBTTagCompound();
            result.setTagCompound(nbt);
        }
        nbt.setBoolean("easter_egg", true);
        return result;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }
}
