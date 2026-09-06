package invalid.myask.undertow.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class BackhandWraps {
    public static BackhandWraps passthrough;
    static {
        if (Loader.isModLoaded("backhand")) passthrough = new BackhandLoaded();
        else passthrough = new BackHandUnloaded();
    }

    public abstract ItemStack getOffHandItem(EntityPlayer herobrine);

    public abstract boolean isOffhandItemInUse(EntityPlayer herobrine);

    public abstract boolean isUsingOffhand(EntityPlayer herobrine);
}
