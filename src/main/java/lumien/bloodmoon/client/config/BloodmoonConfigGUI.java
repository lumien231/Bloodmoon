package lumien.bloodmoon.client.config;

import java.util.ArrayList;
import java.util.List;

import lumien.bloodmoon.config.BloodmoonConfig;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;;

public class BloodmoonConfigGUI extends GuiConfig
{
	public BloodmoonConfigGUI(GuiScreen parent)
	{
		super(parent, getConfigElements(), "Bloodmoon", false, false, GuiConfig.getAbridgedConfigPath(BloodmoonConfig.cfg.toString()));
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("Settings", "Settings", new ConfigElement(BloodmoonConfig.cfg.getCategory("settings")).getChildElements()));
		list.add(new DummyCategoryElement("Visuals", "Visuals", new ConfigElement(BloodmoonConfig.cfg.getCategory("visuals")).getChildElements()));
		return list;
	}
}
