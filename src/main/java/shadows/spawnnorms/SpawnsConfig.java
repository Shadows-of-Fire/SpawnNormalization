package shadows.spawnnorms;

import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class SpawnsConfig {

	public static final Predicate<Object> ALGO_VALIDATOR = s -> {
		try {
			SpawnAlgorithms.valueOf((String) s);
			return true;
		} catch (Exception e) {
			return false;
		}
	};

	public static final ForgeConfigSpec SPEC;
	public static final SpawnsConfig INSTANCE;
	static {
		Pair<SpawnsConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SpawnsConfig::new);
		SPEC = specPair.getRight();
		INSTANCE = specPair.getLeft();
	}

	public final ConfigValue<String> algorithm;
	public final BooleanValue inverse;

	public SpawnsConfig(ForgeConfigSpec.Builder build) {
		build.comment("Server configuration");
		build.push("server");
		algorithm = build.comment("The currently active spawn algorithm.", "Valid types are VANILLA, SURFACE_PREF, SKYBLOCK, TWO_LAYER, SURFACE_ONLY.", "See curseforge for implementation details.").define("spawn algorithm", "VANILLA", ALGO_VALIDATOR);
		inverse = build.comment("If the spawn algorithm is inverted (y = max height - y)").define("inverted", false);
		build.pop();
	}

	private SpawnAlgorithms algo;

	public SpawnAlgorithms getAlgo() {
		if (algo == null) algo = SpawnAlgorithms.valueOf(algorithm.get());
		return algo;
	}

	public boolean isInverted() {
		return inverse.get();
	}

}
