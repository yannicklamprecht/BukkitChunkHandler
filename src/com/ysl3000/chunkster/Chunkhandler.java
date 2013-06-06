package com.ysl3000.chunkster;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Chunkhandler {

	public static Block getBlock(World w, int x, int z) {
		return w.getBlockAt(x, w.getHighestBlockAt(x, z).getY(), z);
	}

	public static void modifyChunkHighlight(Player p, int id) {
		Chunk c = p.getWorld().getChunkAt(p.getLocation());
		for (int i = 1; i <= 16; i++) {

			if (i < 16 && i > 1) {
				getBlock(p.getWorld(), (i - 1) + c.getX() * 16,
						(1 - 1) + c.getZ() * 16).setTypeId(id);
				getBlock(p.getWorld(), (i - 1) + c.getX() * 16,
						(16 - 1) + c.getZ() * 16).setTypeId(id);
			} else {
				for (int a = 1; a <= 16; a++) {
					getBlock(p.getWorld(), (i - 1) + c.getX() * 16,
							(a - 1) + c.getZ() * 16).setTypeId(id);
				}
			}
		}

	}

	public static void test(){
		
	}

}
