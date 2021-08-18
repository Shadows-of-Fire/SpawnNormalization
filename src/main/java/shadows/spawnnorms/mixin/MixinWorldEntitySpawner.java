package shadows.spawnnorms.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.spawner.WorldEntitySpawner;
import shadows.spawnnorms.SpawnsConfig;

@Mixin(WorldEntitySpawner.class)
public abstract class MixinWorldEntitySpawner {

	/**
	 * 
	 * @Author Shadows_of_Fire
	 * @reason Changes the vanilla position selection algorithm for entity spawns.
	 * The default uses a random selection in the range (0, WORLD_SURFACE], which has a high failure rate unless the world surface is close to zero.
	 */
	@Overwrite
	private static BlockPos getRandomPosWithin(World world, Chunk chunk) {
		ChunkPos chunkpos = chunk.getPos();
		int x = chunkpos.getMinBlockX() + world.random.nextInt(16);
		int z = chunkpos.getMinBlockZ() + world.random.nextInt(16);
		int base = chunk.getHeight(Heightmap.Type.WORLD_SURFACE, x, z) + 1;
		int y = SpawnsConfig.INSTANCE.getAlgo().apply(base, world.random);
		if (SpawnsConfig.INSTANCE.isInverted()) y = base - y;
		return new BlockPos(x, y, z);
	}
}