package me.venomouspenguin.antcraft.spleef.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.venomouspenguin.antcraft.spleef.core.ConfigManager;
import me.venomouspenguin.antcraft.spleef.core.Main;
import me.venomouspenguin.antcraft.spleef.core.MessageManager;
import me.venomouspenguin.antcraft.spleef.game.countdowns.Countdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GameManager 
{
	
	private static GameManager gm;
	
	public List<Arena> arenas = new ArrayList<Arena>();
	public HashMap<Player, Arena> playerArena = new HashMap<Player, Arena>();
	
	public GameManager()
	{
		if(gm == null)
		{
			gm = this;
		}
	}
	
	public static GameManager getInstance()
	{
		return gm;
	}
	
	public Arena getArena(String s){
        for(Arena a : arenas){
            if(a.getArenaID() == s){
                return a;
            }
        }
        return null;
    }

	public void createArena(String name, int min, int max, Player p)
	{
		if(getArena(name) != null)
		{
			p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Arena already created with name of: " + ChatColor.GREEN + name);
			return;
		}

		Arena a = new Arena(name, min, max, null);
		Lobby l = new Lobby(a);
		
		ConfigManager.getInstance().getArenas().set("antcraft.arenas." + name + ".info.world", "world");
		ConfigManager.getInstance().getArenas().set("antcraft.arenas." + name + ".lobby.world", "world");
		ConfigManager.getInstance().getArenas().set("antcraft.arenas." + name + ".lobby.x", "0.0");
		ConfigManager.getInstance().getArenas().set("antcraft.arenas." + name + ".lobby.y", "0.0");
		ConfigManager.getInstance().getArenas().set("antcraft.arenas." + name + ".lobby.z", "0.0");
		ConfigManager.getInstance().getArenas().set("antcraft.arenas." + name + ".lobby.pitch", "0.0");
		ConfigManager.getInstance().getArenas().set("antcraft.arenas." + name + ".lobby.yaw", "0.0");
		ConfigManager.getInstance().reloadArenas();
		ConfigManager.getInstance().saveArenas();
		a = new Arena(name, min, max, l);
		
		arenas.add(a);
		p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + MessageManager.getInstance().ARENA_CREATED);
		return;
	}
	
	public void removeArena(String name, Player p)
	{
		Arena a = getArena(name);
		if(a == null)
		{
			p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "Arena not found");
			return;
		}
		
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".info.world", null);
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.world", null);
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.x", null);
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.y", null);
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.z", null);
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.pitch", null);
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".lobby.yaw", null);
		ConfigManager.getInstance().getArenas().set(a.getPathName() + ".info.spawnlocations", null);
		ConfigManager.getInstance().reloadArenas();
		ConfigManager.getInstance().saveArenas();
		
		arenas.remove(a);
		p.sendMessage(Main.getInstance().LOGO + MessageManager.getInstance().ARENA_REMOVED);
	}
	
	@SuppressWarnings("deprecation")
	public void addPlayer(Player p, String s)
	{
		Arena a = getArena(s);
		if(a == null)
		{
			p.sendMessage(Main.getInstance().LOGO + MessageManager.getInstance().ARENA_NOT_FOUND);
			return;
		}
		
		if(playerArena.containsKey(p))
		{
			p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You are already in a game");
			return;
		}
		
		if(a.getState() == GameStates.LOBBY)
		{
			if(a.getAbleToJoin())
			{
				if(a.getInUse() == false)
				{
					a.getPlayersInventory().put(p.getUniqueId(), p.getInventory().getContents());
					a.getPlayersArmorInventory().put(p.getUniqueId(), p.getInventory().getArmorContents());
					a.getPlayersOrignalLocation().put(p.getUniqueId(), p.getLocation());
					
					p.getInventory().clear();
					p.setGameMode(GameMode.SURVIVAL);
					p.setFlying(false);
					
					a.getPlayers().add(p);
					playerArena.put(p, a);
					teleportPlayerToLobby(a, p);
					p.sendMessage(Main.getInstance().LOGO + MessageManager.getInstance().JOINED_ARENA);
					
					broadcastMessage(a, "Player, " + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + " has joined the game " + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN 
							+ a.getPlayers().size() + ChatColor.DARK_GRAY + "/" + ChatColor.GREEN + a.getMaxPlayers() + ChatColor.DARK_GRAY + "]");
					
					if(a.getPlayers().size() >= a.getMinPlayers() || a.getPlayers().size() == a.getMaxPlayers() && !a.hasForceStarted())
					{
						a.setState(GameStates.COUNTDOWN);
						a.setTask(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Countdown(30, a), 0, 20));
					}
				}
				else
				{
					p.sendMessage(Main.getInstance().LOGO + MessageManager.getInstance().ARENA_IN_GAME);
					return;
				}
			}
			else
			{
				p.sendMessage(Main.getInstance().LOGO + MessageManager.getInstance().ARENA_DISABLED);
				return;
			}
		}
		else
		{
			p.sendMessage(Main.getInstance().LOGO + MessageManager.getInstance().ARENA_IN_GAME);
			return;
		}
	}
	
	public void removePlayer(Player p)
	{
		Arena a = playerArena.get(p);
		
		if(a == null || !playerArena.containsKey(p))
		{
			p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You are not in an arena");
			return;
		}
		
		playerArena.remove(p);
		a.getPlayers().remove(p);
		
		if(a.getInGame().contains(p))
		{
			a.getInGame().remove(p);
		}
		
		if(a.getSpecs().contains(p))
		{
			removeSpec(a, p);
		}
		
		p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + "You have left the game");
		broadcastMessage(a, "Player, " + ChatColor.AQUA + " has left the game");
		
		if(a.getState() == GameStates.ENDING)
		{
			//Participation Reward
		}
	}
	
	public void addSpec(Arena a, Player p)
	{
		a.getInGame().remove(p);
		a.getSpecs().add(p);
		
		for(Player ps : a.getInGame())
		{
			ps.hidePlayer(p);
		}
		
		p.setFlying(true);
		p.setAllowFlight(true);
		p.setVelocity(new Vector(0,1.5,0));
		broadcastMessage(a, "Player, " + ChatColor.AQUA + p.getName() + ChatColor.YELLOW + " has lost and become a spectator");
	}

	public void removeSpec(Arena a, Player p)
	{
		a.getSpecs().remove(p);
		
		for(Player ps : a.getPlayers())
		{
			ps.showPlayer(p);
		}
		
		p.setAllowFlight(false);
	}
	
	@SuppressWarnings("deprecation")
	public void start(Arena a)
	{
		a.setInUse(true);
		a.setState(GameStates.WAITING);
		a.getInGame().addAll(a.getPlayers());
		teleportPlayerToArena(a);
		broadcastMessage(a, "You have been teleported to the Arena");
		broadcastMessage(a, "To play this game: ");
		broadcastMessage(a, "When the game starts you will be given a shovel to dig blocks underneath your enemies. When you fall you are out. Last player standing winss");
		
		a.setTask(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new BukkitRunnable()
		{
			public void run() 
			{
				int i = 10;
				if(i == 0)
				{
					Bukkit.getServer().getScheduler().cancelTask(a.getTask());
					broadcastMessage(a, "The game has begun");
					inGame(a);
				}
				else if(i == 10 || i == 5 || i == 4 || i == 3 || i == 2)
				{
					broadcastMessage(a, "Game begins in: " + ChatColor.GREEN + i + " Seconds");
				}
				else if(i == 1)
				{
					broadcastMessage(a, "Game begins in: " + ChatColor.GREEN + i + " Second");
				}
			}
		}, 0, 20));
	}
	
	@SuppressWarnings("deprecation")
	public void inGame(Arena a)
	{
		a.setState(GameStates.INGAME);
		a.setListenerToggle(true);
		a.setTask(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Countdown(600, a), 0, 20));
	
	}
	
	@SuppressWarnings("deprecation")
	public void endGame(Arena a)
	{
		a.setState(GameStates.ENDING);
		a.setListenerToggle(false);
		
		Player p = a.getInGame().get(0);
		
		broadcastMessage(a, "The game has ended");
		broadcastMessage(a, "The winner was: " + ChatColor.AQUA + p.getName());
		
		a.setTask(Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable()
		{
			public void run() 
			{
				for(Player ps : a.getPlayers())
				{
					ps.getInventory().setContents(a.getPlayersInventory().get(ps.getUniqueId()));
					ps.getInventory().setArmorContents(a.getPlayersArmorInventory().get(ps.getUniqueId()));
					ps.teleport(a.getPlayersOrignalLocation().get(ps.getUniqueId()));
					ps.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW +"You were teleported to your original location");
					
					a.getPlayersInventory().remove(ps);
					a.getPlayersArmorInventory().remove(ps);
					a.getPlayersOrignalLocation().remove(ps);
					a.getPlayers().remove(ps);
				}
				
			}
			
		}, 20*10));
		
		a.setTask(Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable()
		{
			public void run() 
			{
				a.getPlayers().clear();
				a.getSpecs().clear();
				a.getInGame().clear();
				a.getPlayersInventory().clear();
				a.getPlayersArmorInventory().clear();
				a.getPlayersOrignalLocation().clear();
				a.setAbleToJoin(true);
				a.setInUse(false);
				a.setState(GameStates.LOBBY);
			}
			
		}, 20*11));
		
	}
	
	public void teleportPlayerToLobby(Arena a, Player p)
	{
		Location loc = new Location(a.getLobby().getWorld(), a.getLobby().getX(), a.getLobby().getY(), a.getLobby().getZ(), a.getLobby().getPitch(), a.getLobby().getYaw());
		
		p.teleport(loc);
	}
	
	public void teleportPlayerToArena(Arena a)
	{

		for(Player p : a.getPlayers())
		{
			if(a.getPlayers().contains(p))
			{
				int entryNum = a.getPlayers().indexOf(p);
				
				Location loc = a.getSpawnLocations().get(entryNum);
				p.teleport(loc);
			}
		}
	
	}
	
	public void broadcastMessage(Arena a, String message)
	{
		for(Player p : a.getPlayers())
		{
			p.sendMessage(Main.getInstance().LOGO + ChatColor.YELLOW + message);
		}
	}
}
