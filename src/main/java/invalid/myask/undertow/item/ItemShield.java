package invalid.myask.undertow.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import invalid.myask.targaseule.Config;
import invalid.myask.targaseule.TargaSeule;
import invalid.myask.undertow.client.IItemEntityRendered;

public class ItemShield extends Item implements IItemEntityRendered {

    public static final int VANILLA_SHIELD_DURABILITY = 336;
    protected int damageThreshold = 3;
    protected float meleeBlock = Config.shield_default_melee_block;
    protected float rangedBlock = Config.shield_default_ranged_block;
    ResourceLocation resLoc;

    public ItemShield() {
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
    public boolean setResLoc(String s) {
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

    public void setRangedBlock(float toBe) {
        rangedBlock = toBe;
    }
    public float getRangedBlock() {
        return rangedBlock;
    }

    public void setMeleeBlock(float toBe) {
        meleeBlock = toBe;
    }
    public float getMeleeBlock() {
        return meleeBlock;
    }
}
