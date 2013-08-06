package com.ysl3000.listerner;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ysl3000.chunkdata.PlayerChunkData;
import com.ysl3000.chunkster.Chunkcacher;
import com.ysl3000.chunkster.Chunkhandler;
import com.ysl3000.chunkster.Chunkster;
import com.ysl3000.chunkster.event.ChunkClaimEvent;
import com.ysl3000.chunkster.event.ChunkReleaseEvent;

public class ChunkListener implements Listener {

	public ChunkListener(Chunkster plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void cre(ChunkReleaseEvent e) {
		if (!e.isCancelled()) {
			Chunkhandler.modifyChunkHighlight(e.getPlayer(), 0);
			Chunkcacher.getChunkCacher().deleteData(e.getPlayer());
			e.getPlayer().sendMessage("Chunk released successfully");
		} else {
			e.getPlayer().sendMessage("Chunk not occupied");
		}
	}

	@EventHandler
	public void ccl(ChunkClaimEvent e) {

		if (!e.isCancelled()) {
			Chunkhandler.modifyChunkHighlight(e.getPlayer(), 50);
			PlayerChunkData pcd = new PlayerChunkData(
					e.getPlayer().getName(),
					e.getPlayer().getLocation().getChunk().getWorld().getName(),
					e.getPlayer().getLocation().getChunk().getX(), e
							.getPlayer().getLocation().getChunk().getZ());
			pcd.addOwner(e.getPlayer().getName());
			Chunkcacher.getChunkCacher().addChunkData(pcd);
			e.getPlayer().sendMessage("Chunk claimed successfully");
		} else {
			e.getPlayer().sendMessage("Chunk occupied");
		}
	}
}
