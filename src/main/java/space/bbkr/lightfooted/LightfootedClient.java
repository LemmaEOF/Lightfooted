package space.bbkr.lightfooted;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public class LightfootedClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.put(RenderLayer.getTranslucent(), Lightfooted.FROSTED_ICE_SHEET);
	}
}
