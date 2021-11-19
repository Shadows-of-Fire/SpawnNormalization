package shadows.spawnnorms;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(SpawnNormalization.MODID)
public class SpawnNormalization {

	public static final String MODID = "spawnnorms";
	public static final Logger LOG = LogManager.getLogger(MODID);

	public SpawnNormalization() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpawnsConfig.SPEC);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
	}

}
