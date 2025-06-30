package invalid.myask.undertow.client;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

import invalid.myask.targaseule.Config;
import invalid.myask.targaseule.TargaSeule;
import invalid.myask.undertow.util.ShieldUtil;
import invalid.myask.undertow.item.ItemShield;

public class RenderShield extends Render implements IItemRenderer {

    public static final RenderShield instance = new RenderShield();
    protected static final ResourceLocation PROTEX = new ResourceLocation(
        TargaSeule.MODID,
        "textures/items/shield.png");
    protected static final ResourceLocation QLY_MURREY_SABLE = new ResourceLocation(
        TargaSeule.MODID,
        "textures/items/shield_quarterly_murrey_and_sable.png");
    public static HashMap<String, ResourceLocation> heraldryTextures = new HashMap<>();
    public static final String[] heraldryKeys = {"base"}; //,
    //"stripe_bottom", "stripe_top", "stripe_left", "stripe_right", "stripe_center", "stripe_middle",
    //"stripe_downright", "stripe_downleft", "small_stripes", "cross", "straight_cross", "diagonal_left",
    //"diagonal_right", "diagonal_up_left", "diagonal_up_right", "half_vertical", "half_vertical_right",
    //"half_horizontal", "half_horizontal_bottom", "square_bottom_left", "square_bottom_right", "square_top_left",
    //"square_top_right", "triangle_bottom", "triangle_top", "triangles_bottom", "triangles_top", "circle", "rhombus",
    //"border", "curly_border", "bricks", "gradient", "gradient_up",
    //"creeper", "skull", "flower", "mojang", "globe", "piglin", "flow", "guster"};


    static { //initHeraldry()
        for (String s: heraldryKeys) {
            heraldryTextures.put(s, new ResourceLocation(TargaSeule.MODID,
                "textures/items/heraldry/" + s + ".png"));
        }
    }

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
    private static final float EQUIP_FP_YAW = -100;
    private static final float EQUIP_FP_PITCH = 0;
    private static final float EQUIP_FP_ROLL = -10;
    private static final float EQUIP_FP_SCALE = 2F;

    private static final Vec3 USING_FP_POS = Vec3.createVectorHelper(0.75, 0.125, 0.5);
    private static final Vec3 USING_FP_TICK_POS_DELTA = Vec3.createVectorHelper(0, 0.025, 0.0);

    private static final float USING_FP_YAW = 0;
    private static final float USING_FP_PITCH = 200;
    private static final float USING_FP_ROLL = 80;

    private static final Vec3 CROUCHBLOCK_FP_POS = Vec3.createVectorHelper(0, 0.25, 0.75);

    private static final float CROUCHBLOCK_FP_YAW = -55;
    private static final float CROUCHBLOCK_FP_PITCH = 0;
    private static final float CROUCHBLOCK_FP_ROLL = 0;

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
        if (rL == null || (Config.enable_easter_egg && (item.getTagCompound() != null
            && item.getTagCompound().getBoolean("easter_egg")))) rL = QLY_MURREY_SABLE;
        GL11.glPushMatrix();
        this.bindTexture(rL);
        switch (type) {
            case INVENTORY -> {
                GL11.glTranslated(INV_POS.xCoord, INV_POS.yCoord, INV_POS.zCoord);

                GL11.glRotatef(INV_ROLL, 0, 0, 1);
                GL11.glRotatef(INV_YAW, 0, 1, 0);
                GL11.glRotatef(INV_PITCH, 1, 0, 0);

                GL11.glScalef(INV_SCALE, INV_SCALE, INV_SCALE);
            }
            case EQUIPPED_FIRST_PERSON -> {
                if (data.length >= 2 && data[1] instanceof EntityPlayer alex && ShieldUtil.isUsingShield(alex)) {
                    float ticks = alex.getItemInUseDuration() + Minecraft.getMinecraft().timer.renderPartialTicks;
                    if (ticks > 10) ticks = 10;
                    if (alex.getItemInUse() != item) {
                        GL11.glTranslated(
                            CROUCHBLOCK_FP_POS.xCoord,
                            CROUCHBLOCK_FP_POS.yCoord,
                            CROUCHBLOCK_FP_POS.zCoord);

                        GL11.glRotatef(CROUCHBLOCK_FP_ROLL, 0, 0, 1);
                        GL11.glRotatef(CROUCHBLOCK_FP_YAW, 0, 1, 0);
                        GL11.glRotatef(CROUCHBLOCK_FP_PITCH, 1, 0, 0);
                    } else {
                        GL11.glTranslated(
                            USING_FP_POS.xCoord + (USING_FP_TICK_POS_DELTA.xCoord * ticks),
                            USING_FP_POS.yCoord + (USING_FP_TICK_POS_DELTA.yCoord * ticks),
                            USING_FP_POS.zCoord + (USING_FP_TICK_POS_DELTA.zCoord * ticks));
                        GL11.glRotatef(USING_FP_ROLL, 0, 0, 1);
                        GL11.glRotatef(USING_FP_YAW, 0, 1, 0);
                        GL11.glRotatef(USING_FP_PITCH, 1, 0, 0);
                    }
                } else {
                    GL11.glTranslated(EQUIP_FP_POS.xCoord, EQUIP_FP_POS.yCoord, EQUIP_FP_POS.zCoord);
                    GL11.glRotatef(EQUIP_FP_YAW, 0, 1, 0);
                    GL11.glRotatef(EQUIP_FP_PITCH, 1, 0, 0);
                    GL11.glRotatef(EQUIP_FP_ROLL, 0, 0, 1);
                }

                GL11.glScalef(EQUIP_FP_SCALE, EQUIP_FP_SCALE, EQUIP_FP_SCALE);
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
            }
            case ENTITY -> {
                GL11.glTranslated(ITEM_POS.xCoord, ITEM_POS.yCoord, ITEM_POS.zCoord);
                GL11.glScalef(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            }
        }
        if (type != ItemRenderType.FIRST_PERSON_MAP) {
            bonk.bb_main.render(0.0625F);
            if (item.getItem() instanceof ItemShield shield) {
                List<String> heraldry = shield.getHeraldry(item);
                if (heraldry != null && !heraldry.isEmpty()) {
                    String entry = heraldry.get(0),
                        pattern;
                    char c = entry.charAt(0);
                    int colorIndex = "0123456789ABCDEF?$".indexOf(c),
                        tincture;
                    if (colorIndex == -1) colorIndex = "0123456789abcdef?".indexOf(c);
                    if (colorIndex >= 0 && colorIndex <= 17) {
                        if (colorIndex == 17) {
                            if (entry.length() >= 6) tincture = Integer.parseInt(heraldry.get(0).substring(1, 6), 16);
                            else tincture = 0;
                        }
                        else tincture = MapColor.getMapColorForBlockColored(colorIndex).colorValue;
                        GL11.glColor3ub((byte)((tincture >> 16) & 0xFF), (byte)((tincture >> 8) & 0xFF), (byte)(tincture & 0xFF));
                        int i = entry.indexOf('.');
                        if (entry.length() == i + 1) i = -1;
                        pattern = i == -1 ? "base" : entry.substring(i + 1);
                        renderManager.renderEngine.bindTexture(heraldryTextures.get(pattern));
                        bonk.paintShield(0.0625F);
                    }
                }
            }
        }
        GL11.glPopMatrix();
    }
}
