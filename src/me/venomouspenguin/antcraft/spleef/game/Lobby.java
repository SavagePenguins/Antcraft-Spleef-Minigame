package me.venomouspenguin.antcraft.spleef.game;

import me.venomouspenguin.antcraft.spleef.core.ConfigManager;
import me.venomouspenguin.antcraft.spleef.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Lobby 
{
	
	private World w;
	private double x,y,z;
	private float pitch, yaw;
	private Arena a;
	
	public Lobby(Arena a)
	{
		this.w = Bukkit.getServer().getWorld(ConfigManager.getInstance().getArenas().getString(a.getPathName() + ".lobby.world"));
		this.x = ConfigManager.getInstance().getArenas().getDouble(a.getPathName() + ".lobby.x");
		this.y = ConfigManager.getInstance().getArenas().getDouble(a.getPathName() + ".lobby.y");
		this.z = ConfigManager.getInstance().getArenas().getDouble(a.getPathName() + ".lobby.z");
		this.pitch = (float) ConfigManager.getInstance().getArenas().getDouble(a.getPathName() + ".lobby.pitch");
		this.yaw = (float) ConfigManager.getInstance().getArenas().getDouble(a.getPathName() + ".lobby.yaw");
		this.a = a;
	}
	
	//Getters
	public double getX()
	{
		return this.x;
	}
	public double getY()
	{
		return this.y;
	}
	public double getZ()
	{
		return this.z;
	}
	public float getPitch()
	{
		return this.pitch;
	}

	public float getYaw()
	{
		return this.yaw;
	}
	public World getWorld()
	{
		return this.w;
	}
	
	//Setters
	public void setX(double x, Player p)
	{
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.x", x);
		ConfigManager.getInstance().reloadArenas();
		ConfigManager.getInstance().saveArenas();
		p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have set the x postion of the lobby for arena: " + ChatColor.GREEN + a.getArenaID());
	}
	public void setY(double y, Player p)
	{
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.y", y);
		ConfigManager.getInstance().reloadArenas();
		ConfigManager.getInstance().saveArenas();
		p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have set the y postion of the lobby for arena: " + ChatColor.GREEN + a.getArenaID());
	}
	public void setZ(double z, Player p)
	{
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.z", z);
		ConfigManager.getInstance().reloadArenas();
		ConfigManager.getInstance().saveArenas();
		p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have set the z postion of the lobby for arena: " + ChatColor.GREEN + a.getArenaID());
	}
	public void setYaw(float yaw, Player p)
	{
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.yaw", yaw);
		ConfigManager.getInstance().reloadArenas();
		ConfigManager.getInstance().saveArenas();
		p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have set the yaw postion of the lobby for arena: " + ChatColor.GREEN + a.getArenaID());

	}
	public void setPitch(float pitch, Player p)
	{
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.pitch", pitch);
		ConfigManager.getInstance().reloadArenas();
		ConfigManager.getInstance().saveArenas();
		p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have set the pitch postion of the lobby for arena: " + ChatColor.GREEN + a.getArenaID());
	}
}
