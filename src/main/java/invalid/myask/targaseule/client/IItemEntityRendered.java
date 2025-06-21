package invalid.myask.targaseule.client;

import net.minecraft.util.ResourceLocation;

public interface IItemEntityRendered {

    ResourceLocation getResLoc();
    boolean setResLoc(String s);
    boolean setResLoc(String modID, String loc);
    void setResLoc(ResourceLocation rl);

}
