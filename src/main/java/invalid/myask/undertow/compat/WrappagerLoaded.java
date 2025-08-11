package invalid.myask.undertow.compat;

import net.minecraft.entity.projectile.EntityArrow;

import invalid.myask.vindicateandspendicate.api.IPierceArrow;

public class WrappagerLoaded extends Wrappager {
    @Override
    public boolean piercing(EntityArrow arrow) {
        return ((IPierceArrow)arrow).vindicateAndSpendicate$getPierces() > 0;
    }
}
