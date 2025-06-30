package invalid.myask.undertow.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import invalid.myask.targaseule.Config;

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
            if (nbt.getTag("tag") instanceof NBTTagCompound tag) {
                if (tag.hasKey("colorIndex")) {
                    int colorIndex = tag.getInteger("colorIndex");
                    if (colorIndex >= 0 && colorIndex < field_150923_a.length)
                        resultingName += "." + field_150923_a[15 - colorIndex];
                }
            }
        }
        return resultingName;
    }

    @Override
    public List<String> getHeraldry(ItemStack stack) {
        ArrayList<String> heraldry = new ArrayList<>();
        NBTTagCompound nbt = stack.getTagCompound();
        int colorIndex = 0xF; // white, you blankshield.
        if (nbt != null) {
            if (nbt.getTag("tag") instanceof NBTTagCompound tag) {
                if (tag.hasKey("colorIndex")) {
                    colorIndex = tag.getInteger("colorIndex");
                }
            }
        }
        if (colorIndex < 0 || colorIndex >= field_150923_a.length) colorIndex = 0xF;
        heraldry.add(hexadecimal_digits[colorIndex] + ".base");
        return heraldry;
    }
}
