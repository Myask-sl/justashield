package invalid.myask.targaseule.mixins;

import invalid.myask.undertow.compat.BackhandWraps;
import invalid.myask.undertow.ducks.IPlayerItemUser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer_fakeGetItemInUse extends EntityLivingBase implements IPlayerItemUser {

    public MixinEntityPlayer_fakeGetItemInUse(World w) {super(w);}

    @Shadow
    private ItemStack itemInUse;
    @Shadow
    private int itemInUseCount; //countDOWN
    @Shadow
    public boolean isUsingItem() {return false;};

    @Unique
    public ItemStack undertow$getItemInUse() {
        ItemStack possible = BackhandWraps.passthrough.getOffHandItem((EntityPlayer)(Object)this);
        if (possible != null) return possible;
        return itemInUse;
    }
    @Unique
    public int undertow$getItemInUseCount() {
        return itemInUseCount;
    }
    @Unique
    public int undertow$getItemInUseTicks()
    {
        return this.isUsingItem() ? this.undertow$getItemInUse().getMaxItemUseDuration() - this.itemInUseCount : 0;
    }
}
