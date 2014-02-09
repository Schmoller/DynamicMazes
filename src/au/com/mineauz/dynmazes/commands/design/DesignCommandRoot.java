package au.com.mineauz.dynmazes.commands.design;

import java.util.EnumSet;
import java.util.List;

import org.bukkit.command.CommandSender;

import au.com.mineauz.dynmazes.commands.CommandDispatcher;
import au.com.mineauz.dynmazes.commands.CommandSenderType;
import au.com.mineauz.dynmazes.commands.ICommand;

public class DesignCommandRoot extends CommandDispatcher implements ICommand
{
	public DesignCommandRoot()
	{
		super("design", "Allows you to design maze styles");
	
		registerCommand(new NewDesignCommand());
		registerCommand(new EndDesignCommand());
		// maze design edit <name>
		// maze design save
		// maze design end
	}
	@Override
	public String getName()
	{
		return "design";
	}

	@Override
	public String[] getAliases()
	{
		return null;
	}

	@Override
	public String getPermission()
	{
		return null;
	}

	@Override
	public String getUsageString( String label, CommandSender sender )
	{
		return label;
	}

	@Override
	public String getDescription()
	{
		return "Allows you to design maze styles";
	}

	@Override
	public EnumSet<CommandSenderType> getAllowedSenders()
	{
		return EnumSet.of(CommandSenderType.Player);
	}

	@Override
	public boolean onCommand( CommandSender sender, String label, String[] args )
	{
		return dispatchCommand(sender, label, args);
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, String label, String[] args )
	{
		return tabComplete(sender, label, args);
	}

}
