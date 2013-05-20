package com.ysl3000.chunkster;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;


public class Chunkster extends JavaPlugin{

	Logger log;

	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		log.info("Chunkster enabled");
		new ChatListener(this);
	}

	public void onDisable() {
		log.info("disabled");
	}

	
	
	
	
}
