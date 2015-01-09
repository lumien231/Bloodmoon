package lumien.bloodmoon.network;

import lumien.bloodmoon.Bloodmoon;
import lumien.bloodmoon.network.messages.MessageBloodmoonStatus;
import lumien.bloodmoon.network.messages.MessageSpawnParticles;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Bloodmoon.MOD_ID);
	
	public static void init()
	{
		INSTANCE.registerMessage(MessageBloodmoonStatus.class, MessageBloodmoonStatus.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageSpawnParticles.class, MessageSpawnParticles.class, 1, Side.CLIENT);
	}
}
