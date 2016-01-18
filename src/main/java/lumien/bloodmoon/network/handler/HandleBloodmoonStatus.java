package lumien.bloodmoon.network.handler;

import lumien.bloodmoon.client.ClientBloodmoonHandler;
import lumien.bloodmoon.network.messages.MessageBloodmoonStatus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandleBloodmoonStatus implements IMessageHandler<MessageBloodmoonStatus, IMessage>
{

	@Override
	public IMessage onMessage(final MessageBloodmoonStatus message, MessageContext ctx)
	{
		Minecraft.getMinecraft().addScheduledTask(new Runnable()
		{
			@Override
			public void run()
			{
				ClientBloodmoonHandler.INSTANCE.setBloodmoon(message.bloodmoonActive);
			}
		});

		return null;
	}

}
