package space.bbkr.lightfooted.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.GameEventTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.bbkr.lightfooted.Lightfooted;

@Mixin(Entity.class)
public class MixinEntity {
	@Inject(method = "bypassesSteppingEffects", at = @At("HEAD"), cancellable = true)
	private void injectLightfooted(CallbackInfoReturnable<Boolean> info) {
		if ((Object) this instanceof LivingEntity living) {
			if (EnchantmentHelper.getLevel(Lightfooted.LIGHTFOOTED, living.getEquippedStack(EquipmentSlot.FEET)) > 0) {
				info.setReturnValue(true);
			}
		}
	}

	@Inject(method = "emitGameEvent(Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), cancellable = true)
	private void injectLightfooted(GameEvent event, Entity e, BlockPos pos, CallbackInfo info) {
		if (e instanceof LivingEntity living) {
			if (event.method_40156(GameEventTags.IGNORE_VIBRATIONS_SNEAKING) && EnchantmentHelper.getLevel(Lightfooted.LIGHTFOOTED, living.getEquippedStack(EquipmentSlot.FEET)) > 0) {
				info.cancel();
			}
		}
	}
}
