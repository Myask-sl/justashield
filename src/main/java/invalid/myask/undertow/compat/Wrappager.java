package invalid.myask.undertow.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.projectile.EntityArrow;

public abstract class Wrappager {
    public static Wrappager is;
    static {
        if (Loader.isModLoaded("vindicateandspendicate")) is = new WrappagerLoaded();
        else is = new WrappagerUnloaded();
    }

    public abstract boolean piercing(EntityArrow arrow);
}
