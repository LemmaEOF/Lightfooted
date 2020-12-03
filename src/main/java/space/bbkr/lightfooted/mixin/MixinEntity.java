package space.bbkr.lightfooted.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.bbkr.lightfooted.Lightfooted;

@Mixin(Entity.class)
public class MixinEntity {
	@Inject(method = "bypassesSteppingEffects", at = @At("HEAD"), cancellable = true)
	private void injectLightfooted(CallbackInfoReturnable<Boolean> info) {
		if ((Object)this instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) (Object) this;
			if (EnchantmentHelper.getLevel(Lightfooted.LIGHTFOOTED, living.getEquippedStack(EquipmentSlot.FEET)) > 0) {
				info.setReturnValue(true);
			}
		}
	}
}
