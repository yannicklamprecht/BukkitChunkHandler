package com.ysl3000.chunkster.event;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.ysl3000.chunkdata.Flag;

public class ChunkModifyEvent extends ChunkEvent{

	private static final HandlerList handlers = new HandlerList();
	private Flag flag;
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public ChunkModifyEvent(Player p, Chunk ch, Flag flag,boolean success ){
		super(p, ch,success);
		this.flag = flag;
	}
	
	public Flag getFlag(){
		return flag;
	}
	
	
	
}
