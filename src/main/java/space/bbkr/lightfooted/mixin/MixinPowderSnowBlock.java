package space.bbkr.lightfooted.mixin;

import net.minecraft.block.PowderSnowBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.bbkr.lightfooted.Lightfooted;

@Mixin(PowderSnowBlock.class)
public class MixinPowderSnowBlock {
	@Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
	private static void injectLightfooted(Entity entity, CallbackInfoReturnable<Boolean> info) {
		if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) entity;
			if (EnchantmentHelper.getLevel(Lightfooted.LIGHTFOOTED, living.getEquippedStack(EquipmentSlot.FEET)) > 0) {
				info.setReturnValue(true);
			}
		}
	}
}
