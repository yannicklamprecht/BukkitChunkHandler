package com.ysl3000.chunkster;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.ysl3000.chunkster.event.ChunkClaimEvent;
import com.ysl3000.chunkster.event.ChunkReleaseEvent;

public class ChatListener implements Listener {

	public ChatListener(Chunkster plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onChat(PlayerCommandPreprocessEvent e) {

		String[] st = e.getMessage().split(" ");

		 if (st[0].equalsIgnoreCase("/chunk")) {
			e.setCancelled(true);
			Chunkhandler.modifyChunkHighlight(e.getPlayer(), 50);
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new ChunkClaimEvent(e.getPlayer(), e.getPlayer()
									.getWorld()
									.getChunkAt(e.getPlayer().getLocation())));
		} else if (st[0].equalsIgnoreCase("/unchunk")) {
			e.setCancelled(true);
			Chunkhandler.modifyChunkHighlight(e.getPlayer(), 0);
			Bukkit.getServer()
			.getPluginManager()
			.callEvent(
					new ChunkReleaseEvent(e.getPlayer(), e.getPlayer()
							.getWorld()
							.getChunkAt(e.getPlayer().getLocation())));

		}
	}
}
