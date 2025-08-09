package invalid.myask.undertow.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.projectile.EntityArrow;

public class Wrappager {
    public static Wrappager is = new Wrappager();
    public boolean piercing(EntityArrow arrow) {
        if (Loader.isModLoaded("takes_an_illage")) is = new WrappagerLoaded();
        else is = new Unloaded();
        return is.piercing();
    }
    public static class Unloaded extends Wrappager {
        @Override
        public boolean piercing(EntityArrow arrow) {
            return false;
        }
    }
}
