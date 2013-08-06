package com.ysl3000.chunkdata;

public class PlayerChunkFlags extends FlagContainer {

	private static final long serialVersionUID = 1L;
	private final String playerName;

	public PlayerChunkFlags(String name) {
		super();
		this.playerName = name;
	}

	public String getPlayerName() {
		return playerName;
	}
}
