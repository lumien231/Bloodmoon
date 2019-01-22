package lumien.bloodmoon.proxy;

import lumien.bloodmoon.client.ClientBloodmoonHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		FMLCommonHandler.instance().bus().register(ClientBloodmoonHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ClientBloodmoonHandler.INSTANCE);
	}

	@Override
	public boolean isBloodmoon()
	{
		return ClientBloodmoonHandler.INSTANCE.isBloodmoonActive();
	}

	@Override
	public boolean canColorMoon() {
		try
		{
			return Display.isCurrent();
		}
		catch (LWJGLException e)
		{
			return false;
		}
	}
}
