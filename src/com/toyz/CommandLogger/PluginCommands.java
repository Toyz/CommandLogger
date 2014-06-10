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
	Boolean is_Dumping = false;
	public PluginCommands(CommandLogger instance){
		main = instance;
	}
	
	@Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
		String player = "Console";
		Boolean is_Console = commandSender instanceof Player;
		ConsoleCommandSender console = commandSender.getServer().getConsoleSender();
        if (command.getName().equalsIgnoreCase("coml")) {
        	switch(strings[0].toLowerCase()){
        		case "purge":
        			if(is_Console){
        				if (commandSender.hasPermission("coml.purge")) {
        					PurgeServerLogs();
        					commandSender.sendMessage(ChatColor.RED + "Dumped " + ChatColor.AQUA + "CommandLogger" + ChatColor.RED + " logs");
        				}else{
        					PurgeServerLogs();
        					console.sendMessage(ChatColor.RED + "Dumped " + ChatColor.AQUA + "CommandLogger" + ChatColor.RED + " logs");
        				}
        			}
        		break;
        		default:
        			if (is_Console) {
                		player = ((Player) commandSender).getDisplayName();
                		commandSender.sendMessage(ChatColor.AQUA + " -> CommandLogger <- ");
                		commandSender.sendMessage(ChatColor.RED + " -> Created By, Toyz <- ");
                	}else{
                		console.sendMessage(ChatColor.AQUA + " -> CommandLogger <- ");
                		console.sendMessage(ChatColor.RED + " -> Created By, Toyz <- ");
                	}
                	break;
        	}
        }
        if (commandSender instanceof Player) {
    		player = ((Player) commandSender).getName();
        }
        File PlayerText = new File(CommandLogger.FolderPath + File.separator + player + ".txt");
 	   
 	   String message = s;
 	   if(!is_Dumping){
	 	   Date now = new Date();
	 		   try {
	 			FileUtils.writeStringToFile(PlayerText, "[" + now +"]" + message + "\r\n", true);
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
 	    }
 	    is_Dumping = false;
        return true;
	}
	
	private void PurgeServerLogs(){
		is_Dumping = true;
		File file = new File(CommandLogger.FolderPath);  
		 String[] LogFiles;      
         if(file.isDirectory()){  
        	 LogFiles = file.list();  
             for (int i=0; i<LogFiles.length; i++) {  
                 File myFile = new File(file, LogFiles[i]);   
                 myFile.delete();  
             }  
         }
	}
}
