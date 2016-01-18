package lumien.bloodmoon.server;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;

public class CommandBloodmoon extends CommandBase
{

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
		return "/bloodmoon <force|stop>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
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
				sender.addChatMessage(new ChatComponentTranslation("text.bloodmoon.force"));
			}
			else if (subCommand.equals("stop"))
			{
				BloodmoonHandler.INSTANCE.stop();
				sender.addChatMessage(new ChatComponentTranslation("text.bloodmoon.stop"));
			}
			else
			{
				throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
			}
		}
	}

}
