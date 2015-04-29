package lumien.bloodmoon;

import lumien.bloodmoon.client.ClientBloodmoonHandler;
import lumien.bloodmoon.config.BloodmoonConfig;
import lumien.bloodmoon.server.BloodmoonHandler;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BloodmoonEventHandler
{
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load event)
	{
		if (!event.world.isRemote && event.world.provider.getDimensionId() == 0)
		{
			BloodmoonHandler.INSTANCE = (BloodmoonHandler) event.world.getMapStorage().loadData(BloodmoonHandler.class, "Bloodmoon");

			if (BloodmoonHandler.INSTANCE == null)
			{
				BloodmoonHandler.INSTANCE = new BloodmoonHandler();
				BloodmoonHandler.INSTANCE.markDirty();
			}

			event.world.getMapStorage().setData("Bloodmoon", BloodmoonHandler.INSTANCE);
			
			BloodmoonHandler.INSTANCE.updateClients();
		}
	}

	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent event)
	{
		if (BloodmoonConfig.VANISH && BloodmoonHandler.INSTANCE != null && event.entityLiving.dimension == 0 && !event.entityLiving.worldObj.isRemote && !BloodmoonHandler.INSTANCE.isBloodmoonActive() && event.entityLiving.worldObj.getTotalWorldTime() % 20 == 0 && Math.random() <= 0.2f)
		{
			if (event.entityLiving.getEntityData().getBoolean("bloodmoonSpawned"))
			{
				event.entityLiving.setDead();
			}
		}
	}

	@SubscribeEvent
	public void sleepInBed(PlayerSleepInBedEvent event)
	{
		if (BloodmoonHandler.INSTANCE != null && BloodmoonConfig.NO_SLEEP)
		{
			if (Bloodmoon.proxy.isBloodmoon())
			{
				event.result = EnumStatus.OTHER_PROBLEM;
				event.entityPlayer.addChatMessage(new ChatComponentTranslation("text.bloodmoon.nosleep").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		}
	}

	@SubscribeEvent
	public void onConfigChange(OnConfigChangedEvent event)
	{
		Bloodmoon.config.onConfigChange(event);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void fogColor(FogColors event)
	{
		if (BloodmoonConfig.BLACK_FOG && ClientBloodmoonHandler.INSTANCE.isBloodmoonActive())
		{
			event.red = Math.max(event.red - ClientBloodmoonHandler.INSTANCE.fogRemove, 0);
			event.green = Math.max(event.green - ClientBloodmoonHandler.INSTANCE.fogRemove, 0);
			event.blue = Math.max(event.blue - ClientBloodmoonHandler.INSTANCE.fogRemove, 0);
		}
	}

	@SubscribeEvent
	public void playerJoinedWorld(EntityJoinWorldEvent event)
	{
		if (BloodmoonHandler.INSTANCE != null && !event.world.isRemote)
		{
			BloodmoonHandler.INSTANCE.playerJoinedWorld(event);
		}
	}

	@SubscribeEvent
	public void endWorldTick(TickEvent.WorldTickEvent event)
	{
		if (BloodmoonHandler.INSTANCE != null)
		{
			BloodmoonHandler.INSTANCE.endWorldTick(event);
		}
	}
}
