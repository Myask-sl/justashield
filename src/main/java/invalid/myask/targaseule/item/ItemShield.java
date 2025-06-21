package invalid.myask.targaseule.item;

import invalid.myask.targaseule.Config;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import invalid.myask.targaseule.TargaSeule;
import invalid.myask.targaseule.client.IItemEntityRendered;

public class ItemShield extends Item implements IItemEntityRendered {
    public static final int VANILLA_SHIELD_DURABILITY = 336;
    protected int damageThreshold = 3;
    ResourceLocation resLoc;

    public ItemShield(){
        setMaxStackSize(1);
        setMaxDamage(VANILLA_SHIELD_DURABILITY);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World w, EntityPlayer player, int itemInUseCount) {
        player.clearItemInUse();
        NBTTagCompound nBT1 = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        NBTTagCompound nbt2 = nBT1.getCompoundTag("tag");
        nbt2.setInteger("cooldown", 0);
        stack.setTagCompound(nBT1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return super.onItemRightClick(stack, worldIn, player);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 0xFFFF;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(TargaSeule.MODID + ":" + iconString);
        setResLoc(TargaSeule.MODID, "textures/items/" + iconString + ".png");
    }

    @Override
    public ResourceLocation getResLoc() {
        return resLoc;
    }

    @Override
    public boolean setResLoc(String s){
        resLoc = new ResourceLocation(s);
        return (resLoc != null);
    }

    @Override
    public boolean setResLoc(String modID, String loc) {
        resLoc = new ResourceLocation(modID, loc);
        return (resLoc != null);
    }

    @Override
    public void setResLoc(ResourceLocation rl) {
        resLoc = rl;
    }

    public int getMinDamageToDamage() {
        return damageThreshold;
    }

    public Item setMinDamageToDamage(int threshold) {
        damageThreshold = threshold;
        return this;
    }

    public static boolean isUsingShield(EntityPlayer alex) {
        if (alex.getItemInUse() != null && alex.getItemInUse().getItem() instanceof ItemShield) {
            if (alex.isUsingItem()) return true;
        }
        if ((alex.getHeldItem() != null && alex.getHeldItem().getItem() instanceof ItemShield)
            ) {//|| (alex.getOffHandItem != null && alex.getOffHandItem().getItem() instanceof ItemShield)) {

            if (alex.isSneaking() && Config.block_on_crouch) return true;
        }
        return false;
    }
}
