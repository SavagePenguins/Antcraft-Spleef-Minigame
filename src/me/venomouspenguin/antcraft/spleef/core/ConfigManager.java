package me.venomouspenguin.antcraft.spleef.core;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

	public ConfigManager() { }
	
	static ConfigManager instance = new ConfigManager();
	
    public static ConfigManager getInstance() 
    {
        return instance;
    }
	
	Plugin p;
	FileConfiguration arenaConfig;
	File arenaFile;
	
	public void setup(Plugin p)
	{
		arenaFile = new File(p.getDataFolder(), "arenas.yml");
		
		if(!arenaFile.exists())
		{
			try
			{
				arenaFile.createNewFile();	
			} catch(IOException e)
			{
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not generate a arenas.yml");
			}
		}
		
		arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
	}
	
	public FileConfiguration getArenas()
	{
		return arenaConfig;
	}
	public void saveArenas()
	{
		try
		{
			arenaConfig.save(arenaFile);
		} catch(IOException e)
		{
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save arenas.yml");
		}
	}
	public void reloadArenas()
	{
		arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
	}
}
