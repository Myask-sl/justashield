package invalid.myask.undertow.compat;

import net.minecraft.entity.projectile.EntityArrow;

public class WrappagerUnloaded extends Wrappager {
    @Override
    public boolean piercing(EntityArrow arrow) {
        return false;
    }
}
