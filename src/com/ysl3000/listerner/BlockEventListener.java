package com.ysl3000.listerner;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import com.ysl3000.chunkster.Chunkster;

public class BlockEventListener implements Listener {

	public BlockEventListener(Chunkster plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void treegrow(StructureGrowEvent e) {

		for (BlockState b : e.getBlocks()) {
			if (e.getLocation().getChunk().getX() != b.getChunk().getX()
					|| e.getLocation().getChunk().getZ() != b.getChunk().getZ()) {
				b.setType(Material.AIR);
			}
		}

	}
}
