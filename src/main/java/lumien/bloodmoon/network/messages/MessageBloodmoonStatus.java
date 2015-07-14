package lumien.bloodmoon.network.messages;

import lumien.bloodmoon.client.ClientBloodmoonHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBloodmoonStatus implements IMessage, IMessageHandler<MessageBloodmoonStatus, IMessage>
{
	boolean bloodmoonActive;

	public MessageBloodmoonStatus(boolean bloodMoon)
	{
		this.bloodmoonActive = bloodMoon;
	}

	public MessageBloodmoonStatus()
	{

	}

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

	@Override
	public void fromBytes(ByteBuf buf)
	{
		bloodmoonActive = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(bloodmoonActive);
	}

	public MessageBloodmoonStatus setStatus(boolean active)
	{
		this.bloodmoonActive = active;
		return this;
	}
}
