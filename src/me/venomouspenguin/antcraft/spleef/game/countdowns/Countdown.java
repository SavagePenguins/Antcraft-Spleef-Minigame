package me.venomouspenguin.antcraft.spleef.game.countdowns;

import me.venomouspenguin.antcraft.spleef.game.Arena;
import me.venomouspenguin.antcraft.spleef.game.GameManager;
import me.venomouspenguin.antcraft.spleef.game.GameStates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable
{
	
	private int i;
	private Arena a;
	
	public Countdown(int countdownTicks, Arena a)
	{
		this.i =  countdownTicks;
		this.a = a;
	}

	public void run()
	{
		
		if(a.getState() == GameStates.COUNTDOWN && a.getPlayers().size() >= a.getMinPlayers())
		{
			if(a.getPlayers().size() == a.getMaxPlayers())
			{
				GameManager.getInstance().broadcastMessage(a, "The game is closed for more players " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + a.getPlayers().size() + 
						ChatColor.DARK_GRAY + "/" + ChatColor.GREEN + a.getMaxPlayers() + ChatColor.DARK_GRAY + "]");
				a.setAbleToJoin(false);
			}
			else
			{
				GameManager.getInstance().broadcastMessage(a, "The game is open for more players " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + a.getPlayers().size() + 
						ChatColor.DARK_GRAY + "/" + ChatColor.GREEN + a.getMaxPlayers() + ChatColor.DARK_GRAY + "]");
				a.setAbleToJoin(true);
			}
			
			if(i ==	 0)
			{
				Bukkit.getServer().getScheduler().cancelTask(a.getTask());
				GameManager.getInstance().start(a);	
			}
			
			if(i == 30 || i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2)
			{
				GameManager.getInstance().broadcastMessage(a, "Game starting in: " + ChatColor.GREEN + i + " Seconds");
				for(Player p : a.getPlayers())
				{
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10, 1);
				}
			}
			else if(i == 1)
			{
				GameManager.getInstance().broadcastMessage(a, "Game starting in: " + ChatColor.GREEN + i + " Second");
				for(Player p : a.getPlayers())
				{
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10, 1);
				}
			}
			
			i--;
		}
		else
		{
			Bukkit.getServer().getScheduler().cancelTask(a.getTask());
			GameManager.getInstance().broadcastMessage(a, "Not enough players " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + a.getPlayers().size() + 
					ChatColor.DARK_GRAY + "/" + ChatColor.GREEN + a.getMaxPlayers() + ChatColor.DARK_GRAY + "]");
			GameManager.getInstance().broadcastMessage(a, "Countdown aborted");
		}
		
		if(a.getState() == GameStates.INGAME)
		{
			if(i == 0)
			{
				Bukkit.getServer().getScheduler().cancelTask(a.getTask());
				//end game
			}
			
			if(i == 600 || i == 300 || i == 120 || i == 60)
			{
				int realTime = i / 60;
				GameManager.getInstance().broadcastMessage(a, "Game ending in: " + ChatColor.GREEN + realTime + " Minutes");
				for(Player p : a.getPlayers())
				{
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10, 1);
				}
			}
			else if(i == 45 || i == 30 || i == 15 || i == 10 || i == 5 || i == 4 || i == 3 || i == 2)
			{
				GameManager.getInstance().broadcastMessage(a, "Game ending in: " + ChatColor.GREEN + i + " Seconds");
				for(Player p : a.getPlayers())
				{
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10, 1);
				}
			}
			else if(i == 1)
			{
				GameManager.getInstance().broadcastMessage(a, "Game ending in: " + ChatColor.GREEN + i + " Second");
				for(Player p : a.getPlayers())
				{
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 10, 1);
				}
			}
			
			i--;
		}
	
	}
	
	
}
