package invalid.myask.undertow.recipes;

import net.minecraft.block.material.MapColor;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

import invalid.myask.targaseule.TargaItems;

public class OldShieldRecipe implements IRecipe {

    protected final int colorIndex;
    protected final ItemStack output;
    protected final IRecipe baseRecipe;

    public OldShieldRecipe() { this(0); }
    public OldShieldRecipe(int colorIndex) {
        this.colorIndex = colorIndex;
        this.output = new ItemStack(TargaItems.OLD_SHIELD);
        NBTTagCompound compound = new NBTTagCompound(),
            display = new NBTTagCompound();
        compound.setInteger("colorIndex", colorIndex);
        display.setInteger("color", MapColor.getMapColorForBlockColored(colorIndex).colorValue);

        compound.setTag("display", display);
        output.setTagCompound(compound);

        // int stupidMetaInversion = 15 - colorIndex;
        baseRecipe = new ShapedOreRecipe(
            TargaItems.OLD_SHIELD,
            "CW ",
            "CWI",
            "CW ",
            'C', new ItemStack(Blocks.wool, 1, colorIndex),//stupidMetaInversion),
            'W', "plankWood",
            'I', "ingotIron");
    }

    @Override
    public boolean matches(InventoryCrafting table, World world) {
        return baseRecipe.matches(table, world);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting table) {
        return output.copy();
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
