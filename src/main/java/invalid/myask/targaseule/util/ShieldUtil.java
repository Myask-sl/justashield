package invalid.myask.targaseule.util;

import invalid.myask.targaseule.Config;
import invalid.myask.targaseule.item.ItemShield;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ShieldUtil {
    public static boolean isUsingShield(EntityPlayer alex) {
        if (alex.getItemInUse() != null && alex.getItemInUse().getItem() instanceof ItemShield) {
            if (alex.isUsingItem()) return true;
        }
        if ((alex.getHeldItem() != null && alex.getHeldItem().getItem() instanceof ItemShield)
        ) {//|| (alex.getOffHandItem != null && alex.getOffHandItem().getItem() instanceof ItemShield)) {

            if (alex.isSneaking() && Config.block_on_crouch) return true;
        }
        return false;
    }

    public static ItemStack getShieldInUse(EntityPlayer alex) {
        if (alex.getItemInUse() != null && alex.getItemInUse().getItem() instanceof ItemShield) {
            if (alex.isUsingItem()) return alex.getItemInUse();
        }
        if ((alex.getHeldItem() != null && alex.getHeldItem().getItem() instanceof ItemShield)
        ) {//|| (alex.getOffHandItem != null && alex.getOffHandItem().getItem() instanceof ItemShield)) {

            if (alex.isSneaking() && Config.block_on_crouch) return alex.getHeldItem();
        }
        return null;
    }

    public static long disabledUntil(ItemStack shield, EntityLivingBase vic) {
        NBTTagCompound compound = shield.getTagCompound();
        if (compound != null && compound.hasKey("cooldown_end")) {
            return compound.getLong("cooldown_end");
        }
        return Long.MIN_VALUE;
    }

    /**
     * Sets "cooldown_end" to vic's worldtime + time, if greater than shield's current "coolown_end"
     * @param time to disable for, from present. Negative values permitted but won't work.
     * @param shield Eponymous.
     * @param vic got hit
     * @return Whether it set a new time.
     */
    public static boolean disableFor(long time, ItemStack shield, EntityLivingBase vic) {
        NBTTagCompound compound = shield.getTagCompound();
        long newEnd = vic.worldObj.getTotalWorldTime() + time;
        if (compound == null) {
            compound = new NBTTagCompound();
            shield.setTagCompound(compound);
        } else if (compound.hasKey("cooldown_end")) {
            if (compound.getLong("cooldown_end") > newEnd) return false;
        }
        compound.setLong("cooldown_end", newEnd);
        return true;
    }

    public static int getDisableTime(Entity damager) {
        if (damager instanceof EntityLivingBase attacker) {
            if (attacker.getHeldItem() != null && attacker.getHeldItem().getItem() instanceof ItemAxe) {
                if (axeDisableRoll(attacker))
                    return Config.axe_shield_cooldown;
            }
        }
        return Config.default_shield_cooldown;
    }

    public static boolean axeDisableRoll(EntityLivingBase attacker) {
        if (Config.axe_disable_chance_base >= 1.0) return true;
        ItemStack axe = attacker.getHeldItem();
        if (axe.getItem() instanceof ItemAxe) {
            float chance = Config.axe_disable_chance_base;
            if (attacker.isSprinting()) chance += Config.axe_disable_chance_sprint_bonus;
            int eff_level = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, axe);
            chance += Config.axe_disable_chance_efficiency_bonus * eff_level;
            return attacker.getRNG().nextFloat() <= chance;
        }
        return false;
    }
}
