package invalid.myask.vindicateandspendicate.api;

import java.util.UUID;

public interface IPierceArrow {
    void vindicateAndSpendicate$setPierces(int number);
    int vindicateAndSpendicate$getPierces();

    int vindicateAndSpendicate$getInitialPierces();
    void vindicateAndSpendicate$setShotGroupUUID(UUID uuid);
    UUID vindicateAndSpendicate$getShotGroupUUID();
}
