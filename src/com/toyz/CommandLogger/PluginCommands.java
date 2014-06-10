package com.toyz.CommandLogger;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.minecraft.util.org.apache.commons.io.FileUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PluginCommands implements CommandExecutor{
	CommandLogger main = null;
	public PluginCommands(CommandLogger instance){
		main = instance;
	}
	
	@Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
		String player = "Console";
        if (command.getName().equalsIgnoreCase("coml")) {
        	if (commandSender instanceof Player) {
        		player = ((Player) commandSender).getDisplayName();
        		commandSender.sendMessage(ChatColor.AQUA + " -> CommandLogger <- ");
        		commandSender.sendMessage(ChatColor.RED + " -> Created By, Toyz <- ");
        	}else{
        		ConsoleCommandSender console = commandSender.getServer().getConsoleSender();
        		console.sendMessage(ChatColor.AQUA + " -> CommandLogger <- ");
        		console.sendMessage(ChatColor.RED + " -> Created By, Toyz <- ");
        	}
        }
        if (commandSender instanceof Player) {
    		player = ((Player) commandSender).getName();
        }
        File PlayerText = new File(CommandLogger.FolderPath + File.separator + player + ".txt");
 	   
 	   String message = s;
 	   System.out.println(message);
 	   Date now = new Date();
 		   try {
 			FileUtils.writeStringToFile(PlayerText, "[" + now +"]" + message + "\r\n", true);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        return true;
	}
}
