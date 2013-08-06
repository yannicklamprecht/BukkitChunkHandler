package com.ysl3000.chunkster.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerChunkChangeEvent extends PlayerMoveEvent{

	private static final HandlerList hdl = new HandlerList();
	@Override
	public HandlerList getHandlers() {
		return hdl;
	}

	public static HandlerList getHandlerList() {
		return hdl;
	}
	public PlayerChunkChangeEvent(Player p, Location from, Location to, boolean canceled) {
		super(p, from, to);
		super.setCancelled(canceled);
	}
	
}
