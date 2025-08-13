package invalid.myask.targaseule;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import invalid.myask.undertow.item.ItemShield;
import invalid.myask.undertow.item.ItemOldShield;

public class TargaItems {
    public static final Item SHIELD = new ItemShield().setTextureName("shield").setUnlocalizedName("shield");
    public static final Item OLD_SHIELD = new ItemOldShield().setTextureName("shield").setUnlocalizedName("shield");
    public static final CreativeTabs TAB;
    static {
        TAB = Config.use_vanilla_tabs ? CreativeTabs.tabCombat : new CreativeTabs("justashield") {
            @Override
            public Item getTabIconItem() {
                return SHIELD;
            }
        };
    }
}
