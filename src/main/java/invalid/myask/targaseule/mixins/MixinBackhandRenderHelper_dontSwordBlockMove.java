package invalid.myask.targaseule.mixins;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xonin.backhand.client.utils.BackhandRenderHelper;

import invalid.myask.targaseule.Config;
import invalid.myask.undertow.compat.BackhandWraps;
import invalid.myask.undertow.util.ShieldUtil;

@Mixin(value = BackhandRenderHelper.class, remap = false)
public class MixinBackhandRenderHelper_dontSwordBlockMove {
    @Definition(id = "block", field = "Lnet/minecraft/item/EnumAction;block:Lnet/minecraft/item/EnumAction;", remap = false)
    @Expression("? == block")
    @ModifyExpressionValue(method = "renderOffhandItemIn3rdPerson",
        at = @At("MIXINEXTRAS:EXPRESSION"), remap = false, require = 1)
    private static boolean unBlock(boolean original, EntityPlayer player, ModelBiped modelBipedMain, float frame) {
        if (Config.no_sword_block_tilt_when_shield_block && ShieldUtil.isUsingShield(player)
            && ShieldUtil.nullguardGetItem(BackhandWraps.passthrough.getOffHandItem(player)) instanceof ItemSword)
            return false;
        else return original;
    }
}
