package com.toyz.CommandLogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.*;

import net.minecraft.util.org.apache.commons.io.*;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
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
        	if(strings.length <= 0){
        		if (is_Console) {
            		player = ((Player) commandSender).getDisplayName();
            		commandSender.sendMessage(ChatColor.AQUA + " -> CommandLogger <- ");
            		commandSender.sendMessage(ChatColor.RED + " -> Created By, Toyz <- ");
            	}else{
            		console.sendMessage(ChatColor.AQUA + " -> CommandLogger <- ");
            		console.sendMessage(ChatColor.RED + " -> Created By, Toyz <- ");
            	}
        	}else{
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
	        		case "backup":
	        			Date date = new Date() ;
        				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
	        			if(is_Console){
	        				File backup = new File(CommandLogger.BackUpPath, dateFormat.format(date) + ".zip");
	        				FileOutputStream fos = null;
							try {
								fos = new FileOutputStream(backup);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        				BufferedOutputStream bos = new BufferedOutputStream(fos);
	        				ZipOutputStream zos = new ZipOutputStream(bos);
	        				if(commandSender.hasPermission("coml.backup")){
	        					try {
									addFolderToZip(new File(CommandLogger.FolderPath), zos, "/");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	        					try {
									zos.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	        					commandSender.sendMessage(ChatColor.RED + "Back up for " + ChatColor.AQUA + "CommandLogger" + ChatColor.RED + " logs has finished");
	        					if (commandSender.hasPermission("coml.purge"))
	        						PurgeServerLogs();
	        					commandSender.sendMessage(ChatColor.RED + "Dumped " + ChatColor.AQUA + "CommandLogger" + ChatColor.RED + " logs");
	        				}
	        			}else{
	        				File backup = new File(CommandLogger.BackUpPath, dateFormat.format(date) + ".zip");
	        				FileOutputStream fos = null;
							try {
								fos = new FileOutputStream(backup);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        				BufferedOutputStream bos = new BufferedOutputStream(fos);
	        				ZipOutputStream zos = new ZipOutputStream(bos);
	        				try {
								addFolderToZip(new File(CommandLogger.FolderPath), zos, "/");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        				try {
								zos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        		
        					console.sendMessage(ChatColor.RED + "Back up for " + ChatColor.AQUA + "CommandLogger" + ChatColor.RED + " logs has finished");
        					PurgeServerLogs();
        					console.sendMessage(ChatColor.RED + "Dumped " + ChatColor.AQUA + "CommandLogger" + ChatColor.RED + " logs");
	        			}
	        			break;
	        	}
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
	
	private void addFolderToZip(File folder, ZipOutputStream zip, String baseName) throws IOException {
	    File[] files = folder.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            addFolderToZip(file, zip, baseName);
	        } else {
	            String name = file.getAbsolutePath().substring(baseName.length());
	            ZipEntry zipEntry = new ZipEntry(name);
	            zip.putNextEntry(zipEntry);
	            IOUtils.copy(new FileInputStream(file), zip);
	            zip.closeEntry();
	        }
	    }
	}
}
