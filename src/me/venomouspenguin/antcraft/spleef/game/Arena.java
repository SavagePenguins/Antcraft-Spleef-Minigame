package me.venomouspenguin.antcraft.spleef.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.venomouspenguin.antcraft.spleef.core.ConfigManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Arena 
{
	
	private World arenaWorld;
	private String pathName;
	private String arenaID;
	private int minPlayers, maxPlayers;
	private List<Player> players = new ArrayList<Player>();
	private HashMap<UUID, ItemStack[]> playerInventory = new HashMap<UUID, ItemStack[]>();
	private HashMap<UUID, ItemStack[]> playerArmorSlots = new HashMap<UUID, ItemStack[]>();
	private HashMap<UUID, Location> playersOrginalLocation = new HashMap<UUID, Location>();
	private boolean inUse, ableToJoin, forcestarted, listenerToggle;
	private int task;
	
	private List<Player> spectators = new ArrayList<Player>();
	private List<Player> ingame = new ArrayList<Player>();
	private List<Location> spawnLocations = new ArrayList<Location>();
	private GameStates states;
	private Lobby lobby;
	
	public Arena(String arenaID, int minPlayers, int maxPlayers, Lobby lobby)
	{
		this.arenaID = arenaID;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.pathName = "antcraft.arenas." + this.arenaID;
		this.arenaWorld = Bukkit.getServer().getWorld(this.pathName + ".info.world");
		this.states = GameStates.LOBBY;
		this.ableToJoin = true;
		this.inUse = false;
		this.lobby = lobby;
		
		for(String s : ConfigManager.getInstance().getArenas().getConfigurationSection("spawnlocations").getKeys(false))
		{
			ConfigurationSection location = ConfigManager.getInstance().getArenas().getConfigurationSection(s);
			spawnLocations.add(new Location(arenaWorld, location.getDouble("x"), location.getDouble("y"), location.getDouble("z"), (float) location.getDouble("pitch"),
					(float) location.getDouble("yaw")));
		}
	}
	
	public String getArenaID()
	{
		return arenaID;
	}
	
	public int getMinPlayers()
	{
		return minPlayers;
	}

	public int getMaxPlayers()
	{
		return maxPlayers;
	}
	
	public World getArenaWorld()
	{
		return this.arenaWorld;
	}
	
	public List<Player> getPlayers()
	{
		return this.players;
	}
	public List<Player> getInGame()
	{
		return this.ingame;
	}
	public List<Player> getSpecs()
	{
		return this.spectators;
	}
	public String getPathName()
	{
		return this.pathName;
	}
	
	public GameStates getState()
	{
		return this.states;
	}
	
	public boolean getInUse()
	{
		return this.inUse;
	}
	
	public boolean getAbleToJoin()
	{
		return this.ableToJoin;
	}
	
	public HashMap<UUID, ItemStack[]> getPlayersInventory()
	{
		return this.playerInventory;
	}

	public HashMap<UUID, ItemStack[]> getPlayersArmorInventory()
	{
		return this.playerArmorSlots;
	}
	
	public HashMap<UUID, Location> getPlayersOrignalLocation()
	{
		return this.playersOrginalLocation;
	}
	
	public List<Location> getSpawnLocations()
	{
		return this.spawnLocations;
	}
	
	public Lobby getLobby()
	{
		return this.lobby;
	}
	
	public int getTask()
	{
		return this.task;
	}
	
	public void setTask(int value)
	{
		this.task = value;
	}
	
	public boolean hasForceStarted()
	{
		return this.forcestarted;
	}
	
	public boolean listenerToggle()
	{
		return this.listenerToggle;
	}
	
	public void setListenerToggle(boolean value)
	{
		this.listenerToggle = value;
	}
	
	public void setForceStarted(boolean value)
	{
		this.forcestarted = value;
	}
	
	public void setAbleToJoin(boolean value)
	{
		this.ableToJoin = value;
	}
	
	public void setInUse(boolean value)
	{
		this.inUse = value;
	}
	
	public void setState(GameStates s)
	{
		this.states = s;
	}
}
