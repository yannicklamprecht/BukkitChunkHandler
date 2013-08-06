package com.ysl3000.chunkster;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.ysl3000.listerner.BlockEventListener;
import com.ysl3000.listerner.ChatListener;
import com.ysl3000.listerner.ChunkListener;
import com.ysl3000.listerner.PlayerActionEventListener;

public class Chunkster extends JavaPlugin {

	Logger log;
	private static String path = "./plugins/ChunkData/";
	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		log.info("Chunkster enabled");
		new File(path).mkdir();
		Chunkcacher.getChunkCacher().loadFiles();
		new ChatListener(this);
		new PlayerActionEventListener(this);
		new ChunkListener(this);
		new BlockEventListener(this);
		new TimeCapsule(20000).start();
		
	}

	public void onDisable() {
		Chunkcacher.getChunkCacher().saveFiles();
		log.info("disabled");
	}

	public static String getPath() {
		return path;
	}
}
