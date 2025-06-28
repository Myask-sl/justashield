package invalid.myask.undertow.util;

import cpw.mods.fml.common.Loader;

public class ModLoaded {
    static Boolean backhandLoaded = null;
    public static boolean isBackHand() {
        if (backhandLoaded == null) {
            backhandLoaded = Loader.isModLoaded("backhand");
        }
        return backhandLoaded;
    }
}
