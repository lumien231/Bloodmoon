package lumien.bloodmoon.server;

import lumien.bloodmoon.config.BloodmoonConfig;
import lumien.bloodmoon.network.PacketHandler;
import lumien.bloodmoon.network.messages.MessageBloodmoonStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BloodmoonHandler extends WorldSavedData
{
	public static BloodmoonHandler INSTANCE;

	private BloodmoonSpawner bloodMoonSpawner;

	boolean bloodMoon;
	boolean forceBloodMoon;

	public BloodmoonHandler()
	{
		super("Bloodmoon");
		bloodMoonSpawner = new BloodmoonSpawner();
		bloodMoon = false;
		forceBloodMoon = false;
	}

	public BloodmoonHandler(String name)
	{
		super("Bloodmoon");
		bloodMoonSpawner = new BloodmoonSpawner();
		bloodMoon = false;
		forceBloodMoon = false;
	}

	public void playerJoinedWorld(EntityJoinWorldEvent event)
	{
		if (!event.world.isRemote && event.entity instanceof EntityPlayer)
		{
			if (bloodMoon)
			{
				PacketHandler.INSTANCE.sendTo(new MessageBloodmoonStatus(bloodMoon), (EntityPlayerMP) event.entity);
			}
		}
	}

	public void endWorldTick(TickEvent.WorldTickEvent event)
	{
		if (event.side.isServer() && event.phase == TickEvent.Phase.END)
		{
			World world = event.world;
			if (world.provider.getDimensionId() == 0)
			{
				int time = (int) (world.getWorldTime() % 24000);
				if (isBloodmoonActive())
				{
					if (!BloodmoonConfig.RESPECT_GAMERULE || world.getGameRules().getGameRuleBooleanValue("doMobSpawning"))
					{
						for (int i = 0; i < BloodmoonConfig.SPAWN_SPEED; i++)
						{
							bloodMoonSpawner.findChunksForSpawning((WorldServer) world, true);
						}
					}

					if (time >= 0 && time < 12000)
					{
						setBloodmoon(false);
					}
				}
				else
				{
					if (time == 12000)
					{
						if (forceBloodMoon || Math.random() < BloodmoonConfig.CHANCE)
						{
							forceBloodMoon = false;
							setBloodmoon(true);

							if (BloodmoonConfig.SEND_MESSAGE)
							{
								for (Object object : world.playerEntities)
								{
									EntityPlayer player = (EntityPlayer) object;
									player.addChatMessage(new ChatComponentTranslation("text.bloodmoon.notify", new Object[0]).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
								}
							}
						}
					}
				}
			}
		}
	}

	private void setBloodmoon(boolean bloodMoon)
	{
		if (this.bloodMoon != bloodMoon)
		{
			PacketHandler.INSTANCE.sendToDimension(new MessageBloodmoonStatus(bloodMoon), 0);
			this.markDirty();
		}
		this.bloodMoon = bloodMoon;
	}
	
	public void updateClients()
	{
		PacketHandler.INSTANCE.sendToDimension(new MessageBloodmoonStatus(bloodMoon), 0);
	}

	public void force()
	{
		forceBloodMoon = true;
		this.markDirty();
	}

	public boolean isBloodmoonActive()
	{
		return bloodMoon;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.bloodMoon = nbt.getBoolean("bloodMoon");
		this.forceBloodMoon = nbt.getBoolean("forceBloodMoon");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setBoolean("bloodMoon", bloodMoon);
		nbt.setBoolean("forceBloodMoon", forceBloodMoon);
	}

	public boolean isBloodmoonScheduled()
	{
		return forceBloodMoon;
	}

	public void stop()
	{
		setBloodmoon(false);
	}
}