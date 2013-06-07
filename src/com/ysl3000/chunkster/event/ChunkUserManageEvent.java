package com.ysl3000.chunkster.event;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ChunkUserManageEvent extends ChunkEvent {

	private String user;
	private String group;
	private String action;

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public ChunkUserManageEvent(Player p, Chunk ch, boolean success,
			String user, String group, String action) {
		super(p, ch, success);
		this.user = user;
		this.group = group;
		this.action=action;
	}

	public String getUser() {
		return this.user;
	}

	public String getGroup() {
		return this.group;
	}

	public String getAction(){
		return this.action;
	}
}
