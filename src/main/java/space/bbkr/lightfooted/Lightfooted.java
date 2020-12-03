package space.bbkr.lightfooted;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lightfooted implements ModInitializer {
	public static final String MODID = "lightfooted";

	public static final Enchantment LIGHTFOOTED = Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, MODID),
			new LightfootedEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

	@Override
	public void onInitialize() {

	}
}
