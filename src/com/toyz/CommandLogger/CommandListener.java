package com.toyz.CommandLogger;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.minecraft.util.org.apache.commons.io.FileUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
	public CommandLogger commandlogger = null;
	public CommandListener(CommandLogger root){
		commandlogger = root;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPreCommand(PlayerCommandPreprocessEvent event)
    {
	   Player p = event.getPlayer();
	   File PlayerText = new File(CommandLogger.FolderPath + File.separator + p.getName() + ".txt");
	   
	   String message = event.getMessage();
	   Date now = new Date();
	   if(message.startsWith("/")){
		   try {
			FileUtils.writeStringToFile(PlayerText, "[" + now +"]" + message + "\r\n", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
    }
}
