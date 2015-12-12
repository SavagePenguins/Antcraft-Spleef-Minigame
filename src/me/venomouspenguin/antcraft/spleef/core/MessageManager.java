package me.venomouspenguin.antcraft.spleef.core;

public class MessageManager 
{
	public static MessageManager instance;
	
	public MessageManager()
	{
		instance = this;
	}
	
	public static MessageManager getInstance()
	{
		return instance;
	}
	
	//Messages Sent To Users
	public String ARENA_NOT_FOUND = Main.getInstance().getConfig().getString("antcraft.messages.1").replace("&", "§");
	public String ARENA_FULL = Main.getInstance().getConfig().getString("antcraft.messages.2").replace("&", "§");
	public String ARENA_FORCE_STARTED = Main.getInstance().getConfig().getString("antcraft.messages.3").replace("&", "§");
	public String ARENA_FORCE_STOPPED = Main.getInstance().getConfig().getString("antcraft.messages.4").replace("&", "§");
	public String ARENA_NOT_SETUP = Main.getInstance().getConfig().getString("antcraft.messages.5").replace("&", "§");
	public String ARENA_DISABLED = Main.getInstance().getConfig().getString("antcraft.messages.6").replace("&", "§");
	public String ARENA_IN_GAME = Main.getInstance().getConfig().getString("antcraft.messages.11").replace("&", "§");
	public String JOINED_ARENA = Main.getInstance().getConfig().getString("antcraft.messages.12").replace("&", "§");
	
	//Messages To Creators
	public String ARENA_REMOVED = Main.getInstance().getConfig().getString("antcraft.messages.7").replace("&", "§");
	public String ARENA_CREATED = Main.getInstance().getConfig().getString("antcraft.messages.8").replace("&", "§");
	public String ARENA_DISABLE = Main.getInstance().getConfig().getString("antcraft.messages.9").replace("&", "§");
	public String ARENA_ENABLE = Main.getInstance().getConfig().getString("antcraft.messages.10").replace("&", "§");

}
