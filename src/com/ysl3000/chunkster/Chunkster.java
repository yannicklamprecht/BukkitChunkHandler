package com.ysl3000.chunkster;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.ysl3000.listerner.ChatListener;
import com.ysl3000.listerner.PlayerActionEventListener;

public class Chunkster extends JavaPlugin {

	Logger log;
	
	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		log.info("Chunkster enabled");
		new File("./ChunkData/").mkdir();
		new ChatListener(this);
		new PlayerActionEventListener(this);
	}

	public void onDisable() {
		log.info("disabled");
	}


}
