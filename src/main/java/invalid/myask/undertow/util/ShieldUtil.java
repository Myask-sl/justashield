package invalid.myask.undertow.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;

import invalid.myask.targaseule.Config;
import invalid.myask.undertow.compat.BackhandWraps;
import invalid.myask.undertow.ducks.IPlayerItemUser;
import invalid.myask.undertow.item.ItemShield;

public class ShieldUtil {

    public static final char[] hexadecimal_digits = { '0', '1', '2', '3',
                                                      '4', '5', '6', '7',
                                                      '8', '9', 'A', 'B',
                                                      'C', 'D', 'E', 'F' };
    public static Enchantment cleaving = null;
    public static Enchantment piercing = null;

    public static void checkForCleavingAndPiercing() {
        for (Enchantment e : Enchantment.enchantmentsList) {
            if (e == null) continue;
            if (e.getName() != null) {
                if (e.getName().contains("cleaving")) {
                    cleaving = e;
                } else if (e.getName().contains("piercing")) {
                    piercing = e;
                }
                if (cleaving != null && piercing != null) break;
            }
        }
    }

    public static int cleavingLevel(ItemStack axe) {
        if (cleaving == null) return 0;
        return EnchantmentHelper.getEnchantmentLevel(cleaving.effectId, axe);
    }

    public static Item nullguardGetItem(ItemStack in) {
        return (in != null) ? in.getItem() : null;
    }

    public static boolean isUsingShield(EntityPlayer alex) {
        return getShieldInUse(alex) != null;
    }

    public static ItemStack getShieldInUse(EntityPlayer alex) {
        if (alex.isUsingItem()) {
            if (nullguardGetItem(((IPlayerItemUser)alex).undertow$getItemInUse()) instanceof ItemShield)
                return ((IPlayerItemUser)alex).undertow$getItemInUse();
            if (ModLoaded.isBackHand() && (nullguardGetItem(BackhandWraps.passthrough.getOffHandItem(alex)) instanceof ItemShield)
                && (!Config.prevent_block_if_other_hand_using ||
                (Config.sword_block_lets_shield_block &&
                    ((IPlayerItemUser)alex).undertow$getItemInUse().getItemUseAction() == EnumAction.block
                        && nullguardGetItem(((IPlayerItemUser)alex).undertow$getItemInUse()) instanceof ItemSword)))
                return BackhandWraps.passthrough.getOffHandItem(alex);
        }
        if (Config.block_on_crouch && alex.isSneaking()) {
            if (nullguardGetItem(alex.getHeldItem()) instanceof ItemShield)
                return alex.getHeldItem();
            if (ModLoaded.isBackHand() && nullguardGetItem(BackhandWraps.passthrough.getOffHandItem(alex)) instanceof ItemShield)
                return BackhandWraps.passthrough.getOffHandItem(alex);
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
     * Sets "cooldown_end" to vic's worldtime + time, if greater than shield's current "cooldown_end"
     *
     * @param time   to disable for, from present. Negative values permitted but won't work.
     * @param shield Eponymous.
     * @param vic    got hit
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

    public static int getDisableTime(Entity damager, ItemStack stack) {
        int cooldown = Config.default_shield_cooldown;
        if (stack != null && stack.getItem() instanceof ItemShield shield) {
            cooldown = shield.getBaseCooldown();
            if (damager instanceof EntityLivingBase attacker) {
                if (attacker.getHeldItem() != null && attacker.getHeldItem().getItem() instanceof ItemAxe) {
                    boolean doCleave = Config.cleave_bonus_even_failed;
                    if (axeDisableRoll(attacker)) {
                        cooldown = Config.axe_shield_cooldown;
                        doCleave = true;
                    }
                    if (doCleave) {
                        cooldown += cleavingLevel(attacker.getHeldItem()) * Config.axe_cleaving_cooldown;
                    }
                }
            }
        }
        return cooldown;
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
