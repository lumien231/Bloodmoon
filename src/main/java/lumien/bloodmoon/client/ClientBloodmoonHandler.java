package lumien.bloodmoon.client;

import lumien.bloodmoon.Bloodmoon;
import org.lwjgl.opengl.GL11;

import lumien.bloodmoon.config.BloodmoonConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientBloodmoonHandler
{
	public static ClientBloodmoonHandler INSTANCE = new ClientBloodmoonHandler();

	boolean bloodmoonActive;

	final float sinMax = (float) (Math.PI / 12000d);

	float lightSub;
	public float fogRemove;
	float skyColorAdd;
	float moonColorRed;

	float d = 1f / 15000f;
	int difTime = 0;

	double sin;

	public ClientBloodmoonHandler()
	{
		bloodmoonActive = false;
	}

	public boolean isBloodmoonActive()
	{
		return bloodmoonActive;
	}

	public void setBloodmoon(boolean active)
	{
		this.bloodmoonActive = active;
	}

	public void moonColorHook()
	{
		if (isBloodmoonActive() && BloodmoonConfig.APPEARANCE.RED_MOON && Bloodmoon.proxy.canColorMoon() )
		{
			GL11.glColor3f(0.8f, 0, 0);
		}
	}

	public Vec3d skyColorHook(Vec3d color)
	{
		if (isBloodmoonActive() && BloodmoonConfig.APPEARANCE.RED_SKY)
		{
			color.x += INSTANCE.skyColorAdd;
		}
		
		return color;
	}

	public int manipulateRed(int position, int originalValue)
	{
		return originalValue;
	}

	public int manipulateGreen(int position, int originalValue)
	{
		if (isBloodmoonActive() && BloodmoonConfig.APPEARANCE.RED_LIGHT)
		{
			int height = position / 16;

			if (height < 16)
			{
				float mod = 1F / 16F * height;
				originalValue -= mod * lightSub * (sin / 2f + 1);
				return Math.max(originalValue, 0);
			}
		}
		return originalValue;
	}

	public int manipulateBlue(int position, int originalValue)
	{
		if (isBloodmoonActive() && BloodmoonConfig.APPEARANCE.RED_LIGHT)
		{
			int height = position / 16;

			if (height < 16)
			{
				float mod = 1F / 16F * height;
				originalValue -= mod * lightSub * 2.3f;
				return Math.max(originalValue, 0);
			}
		}
		return originalValue;
	}

	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent event)
	{
		if (isBloodmoonActive())
		{
			WorldClient world = Minecraft.getMinecraft().world;
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			if (world != null && player != null)
			{
				float difTime = (int) (world.getWorldTime() % 24000) - 12000;
				sin = Math.sin(difTime * sinMax);
				lightSub = (float) (sin * 150f);
				skyColorAdd = (float) (sin * 0.1f);
				moonColorRed = (float) (sin * 0.7f);

				fogRemove = (float) (sin * d * 6000f);

				if (world.provider.getDimension() != 0)
				{
					bloodmoonActive = false;
				}
			}
			else if (bloodmoonActive)
			{
				bloodmoonActive = false;
			}
		}
	}
}
