package me.venomouspenguin.antcraft.spleef.core;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

	/*
	 * TODO
	 * + Coundown 
	 * + Arena
	 * + Lobby
	 * + Util Classes
	 */
	
	
	
	public static Main instance;
	public String LOGO = ChatColor.DARK_GRAY + "(" + ChatColor.YELLOW + "Antcraft" + ChatColor.DARK_GRAY + ")" + ChatColor.RESET + " ";
	
	public void onEnable()
	{
		if(instance == null)
		{
			instance = this;
		}
		
		saveDefaultConfig();
		ConfigManager.getInstance().setup(this);
	}
	
	public static Main getInstance()
	{
		return instance;
	}
}
