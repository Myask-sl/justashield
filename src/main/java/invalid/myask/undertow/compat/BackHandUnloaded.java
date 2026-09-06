package invalid.myask.undertow.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BackHandUnloaded extends BackhandWraps {
    @Override
    public ItemStack getOffHandItem(EntityPlayer roadhog) {
        return null;
    }
    @Override
    public boolean isOffhandItemInUse(EntityPlayer roadhog) {
        return false;
    }
    @Override
    public boolean isUsingOffhand(EntityPlayer roadhog) {
        return false;
    }
}
