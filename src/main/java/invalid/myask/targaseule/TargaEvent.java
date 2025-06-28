package invalid.myask.targaseule;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import invalid.myask.undertow.item.ItemShield;
import invalid.myask.undertow.util.ShieldUtil;

public class TargaEvent {

    public static TargaEvent instance = new TargaEvent();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void handleBlocking(LivingAttackEvent event) {
        if (!event.source.isDamageAbsolute() && !event.source.isUnblockable()
            && event.source instanceof EntityDamageSource eds
            && event.entityLiving instanceof EntityPlayer vic
            && ShieldUtil.isUsingShield(vic)) {
            ItemStack stack = ShieldUtil.getShieldInUse(vic); // oh hey, free Backhand compat...not.
            if (stack != null) {
                if (vic.worldObj.getTotalWorldTime() >= ShieldUtil.disabledUntil(stack, vic)) {
                    if (stack.getItem() instanceof ItemShield shield) {
                        Entity hitWith = eds.getSourceOfDamage();
                        Vec3 lookVec = vic.getLookVec();
                        double dX = hitWith.posX - vic.posX, dY = hitWith.posY - vic.posY, dZ = hitWith.posZ - vic.posZ,
                            dotProduct = dX * lookVec.xCoord + dZ * lookVec.zCoord
                                + (Config.shield_pitch_matters ? dY * lookVec.yCoord : 0);
                        boolean fromAhead = dotProduct >= 0;
                        if (!fromAhead) {
                            if (eds instanceof EntityDamageSourceIndirect indirSource && indirSource.isProjectile()
                                && hitWith.ticksExisted < 5) {
                                // hack for arrows and ghast fireballs, since they add delta-X to init pos in
                                // skeleton-used ctor that can make them start "past" you
                                // ctors are not as mixinable as most things
                                dotProduct = hitWith.motionX * lookVec.xCoord + hitWith.motionZ * lookVec.zCoord
                                    + (Config.shield_pitch_matters ? hitWith.motionY * lookVec.yCoord : 0);
                                fromAhead = dotProduct >= 0;
                                if (!fromAhead) {
                                    dX = indirSource.getEntity().posX - vic.posX;
                                    dY = indirSource.getEntity().posY - vic.posY;
                                    dZ = indirSource.getEntity().posZ - vic.posZ;
                                    dotProduct = dX * lookVec.xCoord + dZ * lookVec.zCoord
                                        + (Config.shield_pitch_matters ? dY * lookVec.yCoord : 0);
                                    fromAhead = dotProduct >= 0;
                                }
                            }
                            if (!fromAhead) {
                                dX = hitWith.prevPosX - vic.posX;
                                dY = hitWith.prevPosY - vic.posY;
                                dZ = hitWith.prevPosZ - vic.posZ;
                                dotProduct = dX * lookVec.xCoord + dZ * lookVec.zCoord
                                    + (Config.shield_pitch_matters ? dY * lookVec.yCoord : 0);
                                fromAhead = dotProduct >= 0;
                            }
                        }
                        if (fromAhead) { // within 90 degrees of facing, block
                            stack.damageItem(
                                event.ammount >= shield.getMinDamageToDamage()
                                    ? MathHelper.ceiling_float_int(event.ammount)
                                    : 0,
                                vic);
                            ShieldUtil.disableFor(ShieldUtil.getDisableTime(eds.getSourceOfDamage()), stack, vic);

                            if (stack.getTagCompound() != null && stack.getTagCompound().getBoolean("easter_egg"))
                                vic.worldObj.playSoundAtEntity(vic, "targaseule:shield.whiff", 1.3F, vic.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                            else
                                vic.worldObj.playSoundAtEntity(vic, "targaseule:shield.block", 1.0F, vic.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                            if (hitWith instanceof EntityArrow ea)
                                ea.shootingEntity = vic;
                            //p.worldObj.playSoundAtEntity(p,  "shield.break", 1.0F, p.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                            if (Config.shield_knockback_enabled && hitWith instanceof EntityLivingBase hitter) {
                                hitter.knockBack(vic, 1.0F, -dX, -dZ);
                            }
                            float newAmount = event.ammount
                                * ((event.source instanceof EntityDamageSourceIndirect || event.source.isProjectile())
                                    ? 1 - shield.getRangedBlock()
                                    : 1 - shield.getMeleeBlock());
                            event.setCanceled(true);
                            if (newAmount > 0.1) {
                                vic.attackEntityFrom(event.source, newAmount);
                                // cooldown should prevent further recursion
                                // can't set unblockable as that also bypasses armor
                            }
                        }
                    }
                }
            }
        }
    }
}
