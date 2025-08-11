package invalid.myask.undertow.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BackhandWraps {
    public static BackhandWraps passthrough = new BackhandWraps();
    private void loadMod() {
        if (Loader.isModLoaded("backhand")) passthrough = new BackhandLoaded();
        else passthrough = new Unloaded();
    }

    public ItemStack getOffHandItem(EntityPlayer herobrine) {
        loadMod();
        return passthrough.getOffHandItem(herobrine);
    }

    public boolean isOffhandItemInUse(EntityPlayer herobrine) {
        loadMod();
        return passthrough.isOffhandItemInUse(herobrine);
    }

    public boolean isUsingOffhand(EntityPlayer herobrine) {
        loadMod();
        return passthrough.isUsingOffhand(herobrine);
    }

    public static class Unloaded extends BackhandWraps {
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
}
