package invalid.myask.targaseule.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemSword;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import invalid.myask.targaseule.Config;
import invalid.myask.undertow.util.ShieldUtil;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer_dontSwordBlockMove {
    @Definition(id = "block", field = "Lnet/minecraft/item/EnumAction;block:Lnet/minecraft/item/EnumAction;")
    @Expression("? == block")
    @ModifyExpressionValue(method = "renderEquippedItems(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V",
        at = @At("MIXINEXTRAS:EXPRESSION"), require = 1)
    private boolean unBlock(boolean original, AbstractClientPlayer player, float p_77029_2_) {
        if (Config.no_sword_block_tilt_when_shield_block && ShieldUtil.isUsingShield(player)
            && ShieldUtil.nullguardGetItem(player.getCurrentEquippedItem()) instanceof ItemSword)
            return false;
        else return original;
    }
}
