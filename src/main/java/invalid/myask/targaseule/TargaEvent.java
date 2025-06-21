package invalid.myask.targaseule;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import invalid.myask.targaseule.item.ItemShield;

public class TargaEvent {
    public static TargaEvent instance = new TargaEvent();
    @SubscribeEvent
    public void handleTransitionsAndBlocks(LivingAttackEvent event) {//TODO: mixin EntityZombie##decreaseAirSupply instead
        if (!event.source.isDamageAbsolute() && !event.source.isUnblockable()
            && event.source instanceof EntityDamageSource eds
            && event.entityLiving instanceof EntityPlayer vic && ItemShield.isUsingShield(vic)) {
            ItemStack stack = vic.getItemInUse(); //oh hey, free Backhand compat...not.

            if (stack.getItem() instanceof ItemShield shield) {
                Entity hitWith = eds.getSourceOfDamage();
                Vec3 lookVec = vic.getLookVec();
                double dX = hitWith.posX - vic.posX, dY = hitWith.posY - vic.posY, dZ = hitWith.posZ - vic.posZ,
                    dotProduct = dX * lookVec.xCoord + dZ * lookVec.zCoord
                        + (Config.shield_pitch_matters ? dY * lookVec.yCoord : 0);
                boolean fromAhead = dotProduct >= 0;
                if (!fromAhead) {
                    if (eds instanceof EntityDamageSourceIndirect indirSource && indirSource.isProjectile() && hitWith.ticksExisted < 5) {
                        //hack for arrows and ghast fireballs, since they add delta-X to init pos in skeleton-used ctor that can make them start "past" you
                        //ctors are not as mixinable as most things
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
                    stack.damageItem(event.ammount >= shield.getMinDamageToDamage() ? MathHelper.ceiling_float_int(event.ammount) : 0, vic);

                    vic.worldObj.playSoundAtEntity(vic, "undertow:shield.block", 1.0F, vic.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                    if (hitWith instanceof EntityArrow ea)
                        ea.shootingEntity = vic;
                    //p.worldObj.playSoundAtEntity(p,  "shield.break", 1.0F, p.worldObj.rand.nextFloat() * 0.2F + 0.9F);
                    if (Config.shield_knockback_enabled && hitWith instanceof EntityLivingBase hitter) {
                        hitter.knockBack(vic, 1.0F, -dX, -dZ);
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
}
