package au.com.mineauz.dynmazes.commands;

import au.com.mineauz.dynmazes.commands.design.DesignCommandRoot;
import au.com.mineauz.dynmazes.commands.maze.NewMazeCommand;

public class MazeCommand extends RootCommandDispatcher
{
	public MazeCommand()
	{
		super("dynmaze", "All DynamicMazes commands");
		
		registerCommand(new DesignCommandRoot());
		registerCommand(new NewMazeCommand());
	}
}