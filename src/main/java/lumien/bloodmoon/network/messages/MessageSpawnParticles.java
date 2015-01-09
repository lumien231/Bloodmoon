package lumien.bloodmoon.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpawnParticles implements IMessage, IMessageHandler<MessageSpawnParticles, IMessage>
{
	double minX,minY,minZ;
	double maxX,maxY,maxZ;
	
	double posX,posY,posZ;
	
	public MessageSpawnParticles()
	{
		
	}
	
	public MessageSpawnParticles(AxisAlignedBB bb)
	{
		minX = bb.minX;
		minY = bb.minY;
		minZ = bb.minX;
		
		maxX = bb.maxX;
		maxY = bb.maxY;
		maxZ = bb.maxZ;
	}
	
	public MessageSpawnParticles setPosition(double posX,double posY,double posZ)
	{
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		
		return this;
	}
	
	@Override
	public IMessage onMessage(MessageSpawnParticles message, MessageContext ctx)
	{
		for (double x=message.minX;x<message.maxX;x+=(message.maxX-message.minX)/10)
		{
			for (double y=message.minY;y<message.maxY;y+=(message.maxY-message.minY)/10)
			{
				for (double z=message.minZ;z<message.maxZ;z+=(message.maxZ-message.minZ)/10)
				{
					Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.REDSTONE, message.posX + x, message.posY + y, message.posZ + z, 0, 0, 0);
				}
			}
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.minX = buf.readDouble();
		this.minY = buf.readDouble();
		this.minZ = buf.readDouble();
		
		this.maxX = buf.readDouble();
		this.maxY = buf.readDouble();
		this.maxZ = buf.readDouble();
		
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeDouble(minX);
		buf.writeDouble(minY);
		buf.writeDouble(minZ);
		
		buf.writeDouble(maxX);
		buf.writeDouble(maxY);
		buf.writeDouble(maxZ);
		
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
	}

}
