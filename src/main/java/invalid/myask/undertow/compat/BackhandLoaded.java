package invalid.myask.undertow.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xonin.backhand.api.core.IBackhandPlayer;
import xonin.backhand.api.core.IOffhandInventory;

public class BackhandLoaded extends BackhandWraps {
    @Override
    public ItemStack getOffHandItem(EntityPlayer alex) {
        return ((IOffhandInventory)alex.inventory).backhand$getOffhandItem();
    }

    @Override
    public boolean isOffhandItemInUse(EntityPlayer alex) {
        return ((IBackhandPlayer)alex).isOffhandItemInUse();
    }

    @Override
    public boolean isUsingOffhand(EntityPlayer alex) {
        return ((IBackhandPlayer)alex).isUsingOffhand();
    }
}
