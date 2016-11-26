package lumien.bloodmoon.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lumien.bloodmoon.Bloodmoon;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandBloodmoon extends CommandBase
{
	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		List<String> suggestions = new ArrayList<String>();

		if (args.length == 1)
		{
			suggestions.add("force");
			suggestions.add("stop");
			suggestions.add("entitynames");
		}

		return getListOfStringsMatchingLastWord(args, suggestions);
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public String getCommandName()
	{
		return "bloodmoon";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/bloodmoon <force|stop|entitynames>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length == 0)
		{
			throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
		}
		else
		{
			String subCommand = args[0];

			if (BloodmoonHandler.INSTANCE == null)
			{
				throw new CommandException(I18n.format("text.bloodmoon.commandError"));
			}

			if (subCommand.equals("force"))
			{
				BloodmoonHandler.INSTANCE.force();
				sender.addChatMessage(new TextComponentTranslation("text.bloodmoon.force"));
			}
			else if (subCommand.equals("stop"))
			{
				BloodmoonHandler.INSTANCE.stop();
				sender.addChatMessage(new TextComponentTranslation("text.bloodmoon.stop"));
			}
			else if (subCommand.equals("entitynames"))
			{
				Entity senderEntity = sender.getCommandSenderEntity();

				Set<String> names = new HashSet<String>();

				List<Entity> monsterNearby = senderEntity.worldObj.getEntitiesInAABBexcluding(senderEntity, senderEntity.getEntityBoundingBox().expand(10, 10, 10), EntitySelectors.NOT_SPECTATING);

				for (Entity e : monsterNearby)
				{
					if (e instanceof IMob)
					{
						names.add(Bloodmoon.config.getEntityName(e.getClass()));
					}
				}

				sender.addChatMessage(new TextComponentTranslation("text.bloodmoon.entity"));

				for (String s : names)
				{
					sender.addChatMessage(new TextComponentString(" - " + s));
				}
			}
			else
			{
				throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
			}
		}
	}

}
