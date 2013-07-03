package com.ysl3000.chunkdata;

public class PlayerChunkFlags extends FlagContainer {

	private static final long serialVersionUID = 1L;
	private String playerName;

	public PlayerChunkFlags(String name) {
		this.playerName = name;
	}

	public String getPlayerName() {
		return playerName;
	}
}
