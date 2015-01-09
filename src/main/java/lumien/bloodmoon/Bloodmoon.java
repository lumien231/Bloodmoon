package lumien.bloodmoon;

import lumien.bloodmoon.config.BloodmoonConfig;
import lumien.bloodmoon.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Bloodmoon.MOD_ID,name = Bloodmoon.MOD_NAME,version = Bloodmoon.MOD_VERSION,guiFactory = "lumien.bloodmoon.client.config.BloodmoonGuiFactory")
public class Bloodmoon
{
	public static final String MOD_ID = "Bloodmoon";
	public static final String MOD_NAME = "Bloodmoon";
	public static final String MOD_VERSION = "@VERSION@";

	@Instance(value = "Bloodmoon")
	public static Bloodmoon instance;

	@SidedProxy(clientSide = "lumien.bloodmoon.proxy.ClientProxy", serverSide = "lumien.bloodmoon.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static BloodmoonConfig config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
		
		config = new BloodmoonConfig();
		config.preInit(event);
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
}
