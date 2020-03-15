package lumien.bloodmoon.server;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;

public interface IBloodmoonSpawner {

	final int MOB_COUNT_DIV = (int) Math.pow(17.0D, 2.0D);
	final Set<ChunkPos> eligibleChunksForSpawning = Sets.<ChunkPos> newHashSet();
	
	int findChunksForSpawning(WorldServer worldServerIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs, boolean spawnOnSetTickRate);
	BlockPos getRandomChunkPosition(World worldIn, int x, int z);
	boolean isValidEmptySpawnBlock(IBlockState blockState);
	boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType spawnPlacementTypeIn, World worldIn, BlockPos pos);
	void performWorldGenSpawning(World worldIn, Biome biomeIn, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random randomIn);
}
