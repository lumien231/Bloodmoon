package lumien.bloodmoon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lumien.bloodmoon.config.BloodmoonConfig;
import lumien.bloodmoon.lib.Reference;
import lumien.bloodmoon.proxy.CommonProxy;
import lumien.bloodmoon.server.CommandBloodmoon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, certificateFingerprint = Reference.MOD_FINGERPRINT, acceptedMinecraftVersions = "[1.12,1.13)")
public class Bloodmoon
{
	@Instance(value = "Bloodmoon")
	public static Bloodmoon instance;

	@SidedProxy(clientSide = "lumien.bloodmoon.proxy.ClientProxy", serverSide = "lumien.bloodmoon.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static BloodmoonConfig config;
	
	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandBloodmoon());
	}
}
