package invalid.myask.undertow.client;

import java.util.Objects;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

import invalid.myask.targaseule.TargaSeule;
import invalid.myask.undertow.util.ShieldUtil;

public class RenderShield extends Render implements IItemRenderer {

    public static final RenderShield instance = new RenderShield();
    protected static final ResourceLocation PROTEX = new ResourceLocation(TargaSeule.MODID, "textures/items/shield.png");
    protected static final ResourceLocation QLY_MURREY_SABLE = new ResourceLocation(TargaSeule.MODID, "textures/items/shield_quarterly_murrey_and_sable.png");

    private static final Vec3 ITEM_POS = Vec3.createVectorHelper(0.0, 0.3, 0.0);
    private static final float ITEM_SCALE = 1F;

    private static final Vec3 INV_POS = Vec3.createVectorHelper(0, 0, 0);
    private static final float INV_YAW = 210;
    private static final float INV_PITCH = 0;
    private static final float INV_ROLL = -15;
    private static final float INV_SCALE = 1.0F;

    private static final Vec3 EQUIP_3P_POS = Vec3.createVectorHelper(1.25, 0.5, 0.75);
    private static final float EQUIP_3P_YAW = -45;
    private static final float EQUIP_3P_PITCH = 0;
    private static final float EQUIP_3P_ROLL = 75;
    private static final float EQUIP_3P_SCALE = 2.0F;

    private static final Vec3 USING_3P_POS = Vec3.createVectorHelper(0.8, 0.3, 0.9);
    private static final float USING_3P_YAW = 50;
    private static final float USING_3P_PITCH = -40;
    private static final float USING_3P_ROLL = 25;

    private static final Vec3 EQUIP_FP_POS = Vec3.createVectorHelper(0.0, 0, 1.3);
    private static final float EQUIP_FP_YAW = 80;
    private static final float EQUIP_FP_PITCH = 0;
    private static final float EQUIP_FP_ROLL = -10;
    private static final float EQUIP_FP_SCALE = 2F;

    private static final Vec3 USING_FP_POS = Vec3.createVectorHelper(0.75, 0.125, 0.5);
    private static final Vec3 USING_FP_TICK_POS_DELTA = Vec3.createVectorHelper(0, 0.025, 0.0);

    private static final float USING_FP_YAW = 0;
    private static final float USING_FP_PITCH = 200;
    private static final float USING_FP_ROLL = 80;

    private static final float ENTITY_SCALE = 1F;

    public static ShieldModel bonk = new ShieldModel();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float fracTick) {
        ResourceLocation rL = getEntityTexture(entity);
        if (rL == null) rL = QLY_MURREY_SABLE;
        this.bindTexture(rL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(ENTITY_SCALE, ENTITY_SCALE, ENTITY_SCALE);
        bonk.render(entity, 0, 0, 0, 0, fracTick, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity e) {
        return PROTEX;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
        /*
         * return switch (helper) {
         * case BLOCK_3D, ENTITY_ROTATION, ENTITY_BOBBING -> false;
         * default -> true;
         * };
         */
    }

    @Override
    protected void bindTexture(ResourceLocation texRL) {
        if (renderManager == null) setRenderManager(RenderManager.instance);
        if (renderManager == null || renderManager.renderEngine == null) {
            if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft()
                .getTextureManager() != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(texRL);
            }
        } else super.bindTexture(texRL);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type == ItemRenderType.FIRST_PERSON_MAP) return;
        ResourceLocation rL = ((IItemEntityRendered) Objects.requireNonNull(item.getItem())).getResLoc();
        if (rL == null) rL = QLY_MURREY_SABLE;
        GL11.glPushMatrix();
        this.bindTexture(rL);
        switch (type) {
            case INVENTORY -> {
                GL11.glTranslated(INV_POS.xCoord, INV_POS.yCoord, INV_POS.zCoord);

                GL11.glRotatef(INV_ROLL, 0, 0, 1);
                GL11.glRotatef(INV_YAW, 0, 1, 0);
                GL11.glRotatef(INV_PITCH, 1, 0, 0);

                GL11.glScalef(INV_SCALE, INV_SCALE, INV_SCALE);

                bonk.bb_main.render(0.0625F);
            }
            case EQUIPPED_FIRST_PERSON -> {
                if (data.length >= 2 && data[1] instanceof EntityPlayer alex && ShieldUtil.isUsingShield(alex)) {
                    float ticks = alex.getItemInUseDuration() + Minecraft.getMinecraft().timer.renderPartialTicks;
                    if (ticks > 10) ticks = 10;
                    GL11.glTranslated(USING_FP_POS.xCoord + (USING_FP_TICK_POS_DELTA.xCoord * ticks),
                        USING_FP_POS.yCoord + (USING_FP_TICK_POS_DELTA.yCoord * ticks),
                        USING_FP_POS.zCoord + (USING_FP_TICK_POS_DELTA.zCoord * ticks));
                    GL11.glRotatef(USING_FP_ROLL, 0, 0, 1);
                    GL11.glRotatef(USING_FP_YAW, 0, 1, 0);
                    GL11.glRotatef(USING_FP_PITCH, 1, 0, 0);
                } else {
                    GL11.glTranslated(EQUIP_FP_POS.xCoord, EQUIP_FP_POS.yCoord, EQUIP_FP_POS.zCoord);
                    GL11.glRotatef(EQUIP_FP_YAW, 0, 1, 0);
                    GL11.glRotatef(EQUIP_FP_PITCH, 1, 0, 0);
                    GL11.glRotatef(EQUIP_FP_ROLL, 0, 0, 1);
                }

                GL11.glScalef(EQUIP_FP_SCALE, EQUIP_FP_SCALE, EQUIP_FP_SCALE);
                bonk.bb_main.render(0.0625F);
            }
            case EQUIPPED -> {
                if (data.length >= 2 && data[1] instanceof EntityPlayer alex && ShieldUtil.isUsingShield(alex)) {
                    GL11.glTranslated(USING_3P_POS.xCoord, USING_3P_POS.yCoord, USING_3P_POS.zCoord);
                    GL11.glRotatef(USING_3P_YAW, 0, 1, 0);
                    GL11.glRotatef(USING_3P_PITCH, 1, 0, 0);
                    GL11.glRotatef(USING_3P_ROLL, 0, 0, 1);
                } else {
                    GL11.glTranslated(EQUIP_3P_POS.xCoord, EQUIP_3P_POS.yCoord, EQUIP_3P_POS.zCoord);
                    GL11.glRotatef(EQUIP_3P_YAW, 0, 1, 0);
                    GL11.glRotatef(EQUIP_3P_PITCH, 1, 0, 0);
                    GL11.glRotatef(EQUIP_3P_ROLL, 0, 0, 1);
                }
                GL11.glScalef(EQUIP_3P_SCALE, EQUIP_3P_SCALE, EQUIP_3P_SCALE);
                bonk.bb_main.render(0.0625F);
            }
            case ENTITY -> {
                GL11.glTranslated(ITEM_POS.xCoord, ITEM_POS.yCoord, ITEM_POS.zCoord);
                GL11.glScalef(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);

                bonk.bb_main.render(0.0625F);
            }
        }
        GL11.glPopMatrix();
    }
}
