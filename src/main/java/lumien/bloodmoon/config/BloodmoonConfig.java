package lumien.bloodmoon.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lumien.bloodmoon.Bloodmoon;
import lumien.bloodmoon.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;

@Config(modid = Reference.MOD_ID, category = "bloodmoon")
public class BloodmoonConfig
{
	public static General GENERAL = new General();
	
	public static class General
	{
		@Name(value = "NoSleep")
		@Comment(value = { "Whether players are not able to sleep on a bloodmoon" })
		public boolean NO_SLEEP = true;

		@Name(value = "Vanish")
		@Comment(value = { "Whether monsters spawned by a bloodmoon should die at dawn" })
		public boolean VANISH = false;

		@Name(value = "RespectGamerule")
		@Comment(value = { "Whether bloodmoons should respect the doMobSpawning gamerule" })
		public boolean RESPECT_GAMERULE = true;

		@Name(value = "SendMessage")
		@Comment(value = { "Whether all players in the overworld should receive a message when the bloodmoon starts" })
		public boolean SEND_MESSAGE = true;
	}
	
	@Name(value = "appearance")
	public static Appearance APPEARANCE = new Appearance();

	public static class Appearance
	{
		@Name(value = "RedMoon")
		public boolean RED_MOON = true;

		@Name(value = "RedSky")
		public boolean RED_SKY = true;

		@Name(value = "RedLight")
		public boolean RED_LIGHT = true;

		@Name(value = "BlackFog")
		public boolean BLACK_FOG = true;
	}

	@Name(value = "schedule")
	public static Schedule SCHEDULE = new Schedule();

	public static class Schedule
	{
		@Name(value = "Chance")
		@Comment(value = { "The chance of a bloodmoon occuring at the beginning of a night (0=Never;1=Every night;0.05=5% of all nights)" })
		public double CHANCE = 0.05;

		@Name(value = "Fullmoon")
		@Comment(value = { "Whether there should be a bloodmoon whenever there is a full moon" })
		public boolean FULLMOON = false;

		@Name(value = "NthNight")
		@Comment(value = { "Every nth night there will be a bloodmoon (0 disables this, 1 would be every night, 2 every second night)" })
		public int NTH_NIGHT = 0;
	}

	@Name(value = "spawning")
	public static Spawning SPAWNING = new Spawning();

	public static class Spawning
	{
		@Name(value = "SpawnSpeed")
		@Comment(value = { "How much faster enemys spawn on a bloodmoon (0=Vanilla)" })
		public int SPAWN_SPEED = 4;

		@Name(value = "SpawnLimitMultiplier")
		@Comment(value = { "With which number should the default entity limit be multiplicated on a blood moon" })
		public int SPAWN_LIMIT_MULT = 4;

		@Name(value = "SpawnRange")
		@Comment(value = { "How close can enemys spawn next to the player on a bloodmoon in blocks? (Vanilla=24)" })
		public int SPAWN_RANGE = 2;

		@Name(value = "WorldSpawnDistance")
		@Comment(value = { "How close can enemys spawn next to the World Spawn (Vanilla=24)" })
		public int SPAWN_DISTANCE = 24;

		@Name(value = "SpawnWhitelist")
		@Comment(value = { "If this isn't empty only monsters which names are in this list will get spawned by the bloodmoon. (Example: \"Skeleton,Spider\")" })
		public String[] SPAWN_WHITELIST = new String[0];
		
		@Name(value = "SpawnBlacklist")
		@Comment(value = { "Monsters which names are on this list won't get spawned by the bloodmoon (Has no effect when a whitelist is active). (Example: \"Skeleton,Spider\")" })
		public String[] SPAWN_BLACKLIST = new String[0];
	}

	// Cache
	static HashMap<String, String> classToEntityNameMap = new HashMap<String, String>();
	
	public static boolean canSpawn(Class<? extends Entity> entityClass)
	{
		if (SPAWNING.SPAWN_WHITELIST.length == 0)
		{
			if (SPAWNING.SPAWN_BLACKLIST.length == 0)
			{
				return true;
			}
			else
			{
				String className = entityClass.getName();
				String entityName;
				
				if (classToEntityNameMap.containsKey(className))
				{
					entityName = classToEntityNameMap.get(className);
				}
				else
				{
					entityName = getEntityName(entityClass);
					classToEntityNameMap.put(className, entityName);
				}

				for (int i = 0; i < SPAWNING.SPAWN_BLACKLIST.length; i++)
				{
					if (SPAWNING.SPAWN_BLACKLIST[i].equals(entityName))
					{
						return false;
					}
				}

				return true;
			}
		}
		else
		{
			String className = entityClass.getName();
			String entityName;
			
			if (classToEntityNameMap.containsKey(className))
			{
				entityName = classToEntityNameMap.get(className);
			}
			else
			{
				entityName = getEntityName(entityClass);
				classToEntityNameMap.put(className, entityName);
			}

			for (int i = 0; i < SPAWNING.SPAWN_WHITELIST.length; i++)
			{
				if (SPAWNING.SPAWN_WHITELIST[i].equals(entityName))
				{
					return true;
				}
			}

			return false;
		}
	}

	public static String getEntityName(Class<? extends Entity> entityClass)
	{
		String entityName = null;
		entityName = EntityList.getTranslationName(EntityList.getKey(entityClass));

		if (entityName == null)
		{
			EntityRegistration registration = EntityRegistry.instance().lookupModSpawn(entityClass, false);

			if (registration != null)
			{
				entityName = registration.getEntityName();
			}
		}

		return entityName;
	}
}
