package invalid.myask.undertow.client;
// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.7 - 1.12 //needed correction
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;

public class ShieldModel extends ModelBase {

    final ModelRenderer bb_main;

    public ShieldModel() {
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addBox(-6.0F, -11.0F, -1.0F, 12, 22, 1, 0.0F);
        bb_main.setTextureOffset(26, 0).addBox(-1.0F, -3.0F, 0.0F, 2, 6, 6, 0.0F);
    }

    @Override
    public void render(Entity entity, float x, float y, float z, float partialTickTime, float yaw, float scale) {
        bb_main.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    final static double LEFT = 2D / 64,
        TOP = LEFT,
        RIGHT = 12D / 64,
        BOTTOM = 22D / 64;

    public void paintShield(float scale) {
        double x = 5 * scale,
            y = 10 * scale;
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.addVertexWithUV(-x, -y, -1.0001,    TOP,  LEFT);
        Tessellator.instance.addVertexWithUV( x, -y, -1.0001,    TOP, RIGHT);
        Tessellator.instance.addVertexWithUV( x,  y, -1.0001, BOTTOM, RIGHT);
        Tessellator.instance.addVertexWithUV(-x,  y, -1.0001, BOTTOM,  LEFT);
        Tessellator.instance.draw();
    }
}
