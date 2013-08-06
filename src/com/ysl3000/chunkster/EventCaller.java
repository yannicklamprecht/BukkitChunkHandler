package com.ysl3000.chunkster;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ysl3000.chunkster.event.ChunkClaimEvent;
import com.ysl3000.chunkster.event.ChunkModifyEvent;
import com.ysl3000.chunkster.event.ChunkReleaseEvent;

public class EventCaller {
	private static EventCaller eventcaller;

	private EventCaller() {
	}

	public static EventCaller getEventCaller() {
		if (eventcaller == null) {
			eventcaller = new EventCaller();
		}
		return eventcaller;
	}

	public void callChunkReleaseEvent(Player p, boolean succes) {

		Bukkit.getServer()
				.getPluginManager()
				.callEvent(
						new ChunkReleaseEvent(p, p.getWorld().getChunkAt(
								p.getLocation()), succes));
	}

	public void callChunkClaimEvent(Player p) {

		Bukkit.getServer()
				.getPluginManager()
				.callEvent(
						new ChunkClaimEvent(
								p,
								p.getWorld().getChunkAt(p.getLocation()),
								(Chunkcacher.getChunkCacher()
										.getDataByLocation(p.getLocation()) == null) ? false
										: true));

	}

	public void callChunkModifyEvent(Player p, String[] st) {
		Bukkit.getServer()
				.getPluginManager()
				.callEvent(
						new ChunkModifyEvent(p, p.getLocation().getChunk(),
								Chunkcacher.getChunkCacher()
										.getDataByLocation(p.getLocation())
										.getChunkFlags().getFlagByName(st[2]),
								!Chunkcacher.getChunkCacher()
										.getDataByLocation(p.getLocation())
										.isMainOwner(p.getName())));
	}

}
