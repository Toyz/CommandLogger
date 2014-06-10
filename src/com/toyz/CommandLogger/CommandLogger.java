package com.toyz.CommandLogger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.*;

public class CommandLogger extends JavaPlugin {
	public static String FolderPath = null;
	public static String BackUpPath = null;
	public void onEnable() {
		 getDataFolder().mkdir();
		 if(!new File(getDataFolder() + File.separator  + "Commands").exists()){
			 new File(getDataFolder() + File.separator  + "Commands").mkdir(); 
		 }
		 if(!new File(getDataFolder() + File.separator  + "BackUps").exists()){
			 new File(getDataFolder() + File.separator  + "BackUps").mkdir(); 
		 }
		 FolderPath = getDataFolder() + File.separator  + "Commands";
		 BackUpPath = getDataFolder() + File.separator  + "BackUps";
		 PluginManager pm = getServer().getPluginManager();
		 
		 pm.registerEvents(new CommandListener(this), this);
		 getCommand("coml").setExecutor(new PluginCommands(this));
		 System.out.println("[CommandLogger] Starting up now");
	 }
	 
	 public void onDisable() {
		 System.out.println("[CommandLogger] Going down it looks like :[");
	 }
}
