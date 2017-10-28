package lumien.bloodmoon.handler;

import lumien.bloodmoon.Bloodmoon;
import lumien.bloodmoon.client.ClientBloodmoonHandler;
import lumien.bloodmoon.config.BloodmoonConfig;
import lumien.bloodmoon.server.BloodmoonHandler;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
		if (!event.getWorld().isRemote && event.getWorld().provider.getDimension() == 0)
		{
			BloodmoonHandler.INSTANCE = (BloodmoonHandler) event.getWorld().getMapStorage().getOrLoadData(BloodmoonHandler.class, "Bloodmoon");

			if (BloodmoonHandler.INSTANCE == null)
			{
				BloodmoonHandler.INSTANCE = new BloodmoonHandler();
				BloodmoonHandler.INSTANCE.markDirty();
			}

			event.getWorld().getMapStorage().setData("Bloodmoon", BloodmoonHandler.INSTANCE);

			BloodmoonHandler.INSTANCE.updateClients();
		}
	}

	@SubscribeEvent
	public void livingDrops(LivingDropsEvent event)
	{
		if (!event.getEntityLiving().world.isRemote)
		{
			if (event.getSource() == DamageSource.OUT_OF_WORLD && event.getEntityLiving().getEntityData().getBoolean("bloodmoonSpawned"))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent event)
	{
		if (BloodmoonConfig.VANISH && BloodmoonHandler.INSTANCE != null && event.getEntityLiving().dimension == 0 && !event.getEntityLiving().world.isRemote && !BloodmoonHandler.INSTANCE.isBloodmoonActive() && event.getEntityLiving().world.getTotalWorldTime() % 20 == 0 && Math.random() <= 0.2f)
		{
			if (event.getEntityLiving().getEntityData().getBoolean("bloodmoonSpawned"))
			{
				event.getEntityLiving().onKillCommand();
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
				event.setResult(SleepResult.OTHER_PROBLEM);
				event.getEntityPlayer().sendMessage(new TextComponentTranslation("text.bloodmoon.nosleep").setStyle(new Style().setColor(TextFormatting.RED)));
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
			event.setRed(Math.max(event.getRed() - ClientBloodmoonHandler.INSTANCE.fogRemove, 0));
			event.setGreen(Math.max(event.getGreen() - ClientBloodmoonHandler.INSTANCE.fogRemove, 0));
			event.setBlue(Math.max(event.getBlue() - ClientBloodmoonHandler.INSTANCE.fogRemove, 0));
		}
	}

	@SubscribeEvent
	public void playerJoinedWorld(EntityJoinWorldEvent event)
	{
		if (BloodmoonHandler.INSTANCE != null && !event.getWorld().isRemote)
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
