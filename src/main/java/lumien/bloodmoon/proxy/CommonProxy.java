package lumien.bloodmoon.proxy;

import lumien.bloodmoon.client.ClientBloodmoonHandler;
import lumien.bloodmoon.handler.BloodmoonEventHandler;
import lumien.bloodmoon.network.PacketHandler;
import lumien.bloodmoon.server.BloodmoonHandler;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		BloodmoonEventHandler handler = new BloodmoonEventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);

		PacketHandler.init();
	}

	public void init(FMLInitializationEvent event)
	{

	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public boolean isBloodmoon()
	{
		if (BloodmoonHandler.INSTANCE == null)
		{
			return false;
		}
		else
		{
			return BloodmoonHandler.INSTANCE.isBloodmoonActive();
		}
	}
}
