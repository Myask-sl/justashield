package invalid.myask.undertow.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import invalid.myask.targaseule.Config;
import invalid.myask.targaseule.TargaItems;

import static invalid.myask.undertow.util.ShieldUtil.hexadecimal_digits;
import static net.minecraft.item.ItemDye.field_150923_a;

public class ItemOldShield extends ItemShield {

    public ItemOldShield() {
        if (Config.old_shield_old_stats) {
            setMeleeBlock(2F / 3F);
            setMaxDamage(181);
            setBaseCooldown(10);
            setBaseWarmup(10);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String resultingName = super.getUnlocalizedName(stack);
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            if (nbt.hasKey("colorIndex")) {
                int colorIndex = nbt.getInteger("colorIndex");
                if (colorIndex >= 0 && colorIndex < field_150923_a.length)
                    resultingName += "." + field_150923_a[15 - colorIndex];
            }
        } else resultingName += ".generic";
        return resultingName;
    }

    @Override
    public List<String> getHeraldry(ItemStack stack) {
        ArrayList<String> heraldry = new ArrayList<>();
        NBTTagCompound nbt = stack.getTagCompound();
        int colorIndex = 0xF; // white, you blankshield.
        if (nbt != null) {
            if (nbt.hasKey("colorIndex")) {
                colorIndex = nbt.getInteger("colorIndex");
            }
        }
        if (colorIndex < 0 || colorIndex >= field_150923_a.length) colorIndex = 0xF;
        heraldry.add(hexadecimal_digits[colorIndex] + ".base");
        return heraldry;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
        if (item.getClass() != this.getClass()) {
            super.getSubItems(item, tab, subItems);
            return;
        }
        ItemStack output = new ItemStack(TargaItems.OLD_SHIELD);
        NBTTagCompound compound = new NBTTagCompound(),
            display = new NBTTagCompound();
        compound.setInteger("colorIndex", 0);
        display.setInteger("color", MapColor.getMapColorForBlockColored(0).colorValue);

        compound.setTag("display", display);
        output.setTagCompound(compound);
        subItems.add(output);
        for (int i = 1; i <= 15; i++) {
            ItemStack woolenShield = output.copy();
            compound = woolenShield.getTagCompound();
            display = (NBTTagCompound) compound.getTag("display");

            compound.setInteger("colorIndex", i);
            display.setInteger("color", MapColor.getMapColorForBlockColored(i).colorValue);

            compound.setTag("display", display);
            woolenShield.setTagCompound(compound);
            subItems.add(woolenShield);
        }
    }
}
