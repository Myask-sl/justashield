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

    public void paintShield(float scale, int tincture, boolean mirror) {
        double x = 5 * scale * (mirror ? -1 : 1),
            y = 10 * scale,
            z = -1 * scale - 0.0001;
        Tessellator.instance.startDrawingQuads();
        if (tincture != -1)
            Tessellator.instance.setColorOpaque((tincture >> 16) & 0xFF, (tincture >> 8) & 0xFF, tincture & 0xFF);
        Tessellator.instance.addVertexWithUV(-x,  y, z, RIGHT,    TOP);
        Tessellator.instance.addVertexWithUV( x,  y, z,  LEFT,    TOP);
        Tessellator.instance.addVertexWithUV( x, -y, z,  LEFT, BOTTOM);
        Tessellator.instance.addVertexWithUV(-x, -y, z, RIGHT, BOTTOM);
        Tessellator.instance.draw();
    }
}
