package shadows.spawnnorms;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * Spawn Algorithms used by SpawnNormalization.
 * Graphical View: https://i.imgur.com/v7BEnmm.png
 */
public enum SpawnAlgorithms {
	VANILLA(SpawnAlgorithms::vanillaY),
	SURFACE_PREF(SpawnAlgorithms::surfacePref),
	SKYBLOCK(SpawnAlgorithms::skyblock),
	TWO_LAYER(SpawnAlgorithms::twoLayer),
	SURFACE_ONLY(SpawnAlgorithms::surfaceOnly);

	private final BiFunction<Integer, Random, Integer> func;

	private SpawnAlgorithms(BiFunction<Integer, Random, Integer> func) {
		this.func = func;
	}

	public int apply(int base, Random rand) {
		return func.apply(base, rand);
	}

	/**
	 * Vanilla - just a uniform random distribution.
	 */
	private static int vanillaY(int base, Random rand) {
		return rand.nextInt(base + 1);
	}

	/**
	 * Weird-pseudo modified normal distribution to allow for more selections towards the surface level.
	 * This causes less selections near bedrock (less than 1% at y = 1/3 base value), but more near the surface (6%).
	 */
	private static int surfacePref(int base, Random rand) {
		double y = normalDist(rand, base / 3D, base);
		if (y >= base && y <= base + 3) return base;
		else if (y > base || y < 0) return vanillaY(base, rand);
		return (int) y;
	}

	/**
	 * Normal distribution with a reflection over the point at (y = base).
	 * This causes much higher selections at the surface and areas near the surface, and almost no selections at low levels.
	 */
	private static int skyblock(int base, Random rand) {
		int y = (int) normalDist(rand, base / 4D, base);
		if (y > base) {
			y--;
			if (y > base) y = -y + 2 * base;
		}
		if (y < 0) y = 0;
		return y;
	}

	/**
	 * Modified normal distribution that causes mobs to be most common near the surface and near y = base/3.
	 * Useful if you want the surface and deep caves to be dangerous, but mid level caves to be safe.
	 */
	private static int twoLayer(int base, Random rand) {
		int y = (int) normalDist(rand, base / 5D, base);
		if (y > base) {
			y = (int) (-y + 1.33D * base);
		}
		if (y < 0) y = vanillaY(base, rand);
		return y;
	}

	private static int surfaceOnly(int base, Random rand) {
		int y = base - 4 + rand.nextInt(5);
		return y < 0 ? 0 : y;
	}

	private static double normalDist(Random rand, double stDev, double mean) {
		return rand.nextGaussian() * stDev + mean;
	}
}
