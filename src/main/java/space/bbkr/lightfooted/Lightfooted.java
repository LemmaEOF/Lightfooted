package space.bbkr.lightfooted;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Lightfooted implements ModInitializer {
	public static final String MODID = "lightfooted";

	public static final Enchantment LIGHTFOOTED = Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, MODID),
			new LightfootedEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

	public static final Block FROSTED_ICE_SHEET = Registry.register(Registry.BLOCK, new Identifier(MODID, "frosted_ice_sheet"),
			new FrostedIceSheetBlock(
					AbstractBlock.Settings.of(Material.ICE)
							.slipperiness(0.98F)
							.ticksRandomly()
							.strength(0.5F)
							.sounds(BlockSoundGroup.GLASS)
							.nonOpaque()
							.allowsSpawning((state, world, pos, entityType) -> entityType == EntityType.POLAR_BEAR)
			)
	);

	@Override
	public void onInitialize() {

	}
}
