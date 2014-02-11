package au.com.mineauz.dynmazes.commands.maze;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import au.com.mineauz.dynmazes.MazeManager;
import au.com.mineauz.dynmazes.Util;
import au.com.mineauz.dynmazes.commands.CommandSenderType;
import au.com.mineauz.dynmazes.commands.ICommand;

public class NewMazeCommand implements ICommand
{
	@Override
	public String getName()
	{
		return "new";
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
		return label + " <name> <type> <options>";
	}

	@Override
	public String getDescription()
	{
		return "Creates a new maze at your location";
	}

	@Override
	public EnumSet<CommandSenderType> getAllowedSenders()
	{
		return EnumSet.of(CommandSenderType.Player);
	}

	@Override
	public boolean onCommand( CommandSender sender, String parent, String label, String[] args )
	{
		if(args.length <= 1)
			return false;
		
		String name = args[0];
		if(!Util.isNameOk(name))
		{
			sender.sendMessage(ChatColor.RED + "Name has invalid characters in it. Only letters, numbers, and _ may be used.");
			return true;
		}
		
		if(MazeManager.getMaze(name) != null)
		{
			sender.sendMessage(ChatColor.RED + "A maze by that name already exists.");
			return true;
		}

		try
		{
			MazeManager.createMaze((Player)sender, name, args[1], Arrays.copyOfRange(args, 2, args.length));
			
			sender.sendMessage(ChatColor.GREEN + "Maze created. Use " + ChatColor.YELLOW + parent + "generate " + name + ChatColor.GREEN + " to build it.");
		}
		catch(IllegalArgumentException e)
		{
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}
		catch(NoSuchFieldException e)
		{
			sender.sendMessage(ChatColor.RED + "Usage: " + parent + label + " <name> " + args[1] + " " + e.getMessage());
		}

		return true;
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, String parent, String label, String[] args )
	{
		return null;
	}

}
