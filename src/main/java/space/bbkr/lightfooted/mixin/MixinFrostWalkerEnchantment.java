package space.bbkr.lightfooted.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import space.bbkr.lightfooted.Lightfooted;

@Mixin(FrostWalkerEnchantment.class)
public class MixinFrostWalkerEnchantment {
	@Redirect(method = "freezeWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"))
	private static BlockState injectFrostedIceSheet(Block instance) {
		return Lightfooted.FROSTED_ICE_SHEET.getDefaultState();
	}

	@ModifyArg(method = "freezeWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;scheduleBlockTick(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;I)V"), index = 1)
	private static Block injectFrostedIceSheetTick(Block prior) {
		return Lightfooted.FROSTED_ICE_SHEET;
	}
}
