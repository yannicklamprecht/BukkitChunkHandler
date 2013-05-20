package com.ysl3000.chunkster.event;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import com.ysl3000.chunkster.event.ChunkEvent;

public class ChunkClaimEvent extends ChunkEvent {

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public ChunkClaimEvent(Player p, Chunk ch) {
		super(p, ch);
	}
}
