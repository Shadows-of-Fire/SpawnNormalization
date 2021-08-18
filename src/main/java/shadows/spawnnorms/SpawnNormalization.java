package shadows.spawnnorms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(SpawnNormalization.MODID)
public class SpawnNormalization {

	public static final String MODID = "spawnnorms";
	public static final Logger LOG = LogManager.getLogger(MODID);

	public SpawnNormalization() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpawnsConfig.SPEC);
	}

}
