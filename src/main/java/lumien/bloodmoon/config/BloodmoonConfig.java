package lumien.bloodmoon.config;

import lumien.bloodmoon.Bloodmoon;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class BloodmoonConfig
{
	public static Configuration cfg;

	Property RED_MOON_PROPERTY;
	public static boolean RED_MOON = true;

	Property RED_SKY_PROPERTY;
	public static boolean RED_SKY = true;

	Property RED_LIGHT_PROPERTY;
	public static boolean RED_LIGHT = true;

	Property BLACK_FOG_PROPERTY;
	public static boolean BLACK_FOG = true;

	Property CHANCE_PROPERTY;
	public static double CHANCE;

	Property SPAWN_SPEED_PROPERTY;
	public static int SPAWN_SPEED;

	Property SPAWN_LIMIT_MULT_PROPERTY;
	public static int SPAWN_LIMIT_MULT;

	Property SPAWN_RANGE_PROPERTY;
	public static int SPAWN_RANGE;
	
	Property SPAWN_DISTANCE_PROPERTY;
	public static int SPAWN_DISTANCE;

	Property NO_SLEEP_PROPERTY;
	public static boolean NO_SLEEP;

	Property VANISH_PROPERTY;
	public static boolean VANISH;

	Property RESPECT_GAMERULE_PROPERTY;
	public static boolean RESPECT_GAMERULE;

	Property SEND_MESSAGE_PROPERTY;
	public static boolean SEND_MESSAGE;

	public void preInit(FMLPreInitializationEvent event)
	{
		cfg = new Configuration(event.getSuggestedConfigurationFile());
		cfg.load();

		CHANCE_PROPERTY = cfg.get("Settings", "Chance", 0.05, "The chance of a bloodmoon occuring (0=Never;1=Every night;0.05=5% of all nights)");
		SPAWN_SPEED_PROPERTY = cfg.get("Settings", "SpawnSpeed", 4, "How much faster enemys spawn on a bloodmoon (0=Vanilla)");
		SPAWN_LIMIT_MULT_PROPERTY = cfg.get("Settings", "SpawnLimitMultiplier", 4, "With which number should the default entity limit be multiplicated on a blood moon");
		SPAWN_RANGE_PROPERTY = cfg.get("Settings", "SpawnRange", 2, "How close can enemys spawn next to the player on a bloodmoon in blocks? (Vanilla=24)");
		SPAWN_DISTANCE_PROPERTY = cfg.get("Settings", "WorldSpawnDistance", 24,"How close can enemys spawn next to the World Spawn (Vanilla=24)");
		NO_SLEEP_PROPERTY = cfg.get("Settings", "NoSleep", true, "Whether players are not able to sleep on a bloodmoon");
		VANISH_PROPERTY = cfg.get("Settings", "Vanish", false, "Whether monsters spawned by a bloodmoon should die at dawn");
		RESPECT_GAMERULE_PROPERTY = cfg.get("Settings", "RespectGamerule", true, "Whether bloodmoons should respect the doMobSpawning gamerule");
		SEND_MESSAGE_PROPERTY = cfg.get("Settings", "SendMessage", true, "Whether all players in the overworld should receive a message when the bloodmoon starts");

		RED_MOON_PROPERTY = cfg.get("Visuals", "RedMoon", true);
		RED_SKY_PROPERTY = cfg.get("Visuals", "RedSky", true);
		RED_LIGHT_PROPERTY = cfg.get("Visuals", "RedLight", true);
		BLACK_FOG_PROPERTY = cfg.get("Visuals", "BlackFog", true);

		syncConfig();
	}

	public void onConfigChange(OnConfigChangedEvent event)
	{
		if (event.modID.equals(Bloodmoon.MOD_ID))
		{
			syncConfig();
		}
	}

	public void syncConfig()
	{
		CHANCE = CHANCE_PROPERTY.getDouble();
		SPAWN_SPEED = SPAWN_SPEED_PROPERTY.getInt();
		SPAWN_LIMIT_MULT = SPAWN_LIMIT_MULT_PROPERTY.getInt();
		SPAWN_RANGE = SPAWN_RANGE_PROPERTY.getInt();
		SPAWN_DISTANCE = SPAWN_DISTANCE_PROPERTY.getInt();
		NO_SLEEP = NO_SLEEP_PROPERTY.getBoolean();
		VANISH = VANISH_PROPERTY.getBoolean();
		RESPECT_GAMERULE = RESPECT_GAMERULE_PROPERTY.getBoolean();
		SEND_MESSAGE = SEND_MESSAGE_PROPERTY.getBoolean();

		RED_MOON = RED_MOON_PROPERTY.getBoolean();
		RED_SKY = RED_SKY_PROPERTY.getBoolean();
		RED_LIGHT = RED_LIGHT_PROPERTY.getBoolean();
		BLACK_FOG = BLACK_FOG_PROPERTY.getBoolean();

		if (cfg.hasChanged())
		{
			cfg.save();
		}
	}
}
