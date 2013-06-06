package com.ysl3000.listerner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import com.ysl3000.chunkdata.ChunkDataHandler;
import com.ysl3000.chunkdata.PlayerChunkData;
import com.ysl3000.chunkster.Chunkster;
import com.ysl3000.chunkster.event.ChunkModifyEvent;
import com.ysl3000.chunkster.event.PlayerChunkChangeEvent;

public class PlayerActionEventListener implements Listener {

	public PlayerActionEventListener(Chunkster plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void playmove(PlayerMoveEvent e) {

		if (e.getFrom().getChunk() != e.getTo().getChunk()) {
			if (!e.isCancelled()) {

				Bukkit.getPluginManager().callEvent(
						new PlayerChunkChangeEvent(e.getPlayer(), e.getFrom(),
								e.getTo()));
			}
		}

	}

	@EventHandler
	public void playccE(PlayerChunkChangeEvent e) {

		e.getPlayer().sendMessage(
				ChatColor.GOLD + "You entered Chunk "
						+ e.getTo().getChunk().toString() + "\n"
						+ e.getTo().getChunk().getX() + " "
						+ e.getTo().getChunk().getZ());
	}

	@EventHandler
	public void modifyChunk(ChunkModifyEvent e) {
		e.getPlayer().sendMessage("Chunk modifyed");
	}

	@EventHandler
	public void breakBlock(BlockBreakEvent e) {

		if (new ChunkDataHandler().loadCurrentChunkData(e.getBlock()
				.getLocation()) != null) {
			PlayerChunkData pcd = new ChunkDataHandler().loadCurrentChunkData(e
					.getBlock().getLocation());

			if (!pcd.getFlagByName("build").getValue()) {

				if (!(pcd.isMember(e.getPlayer().getName())
						|| pcd.isMainOwner(e.getPlayer().getName()) || pcd
							.isOwner(e.getPlayer().getName()))) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("Can't build in this chunk");
				}
			}
		}
	}

	@EventHandler
	public void build(BlockPlaceEvent e) {
		if (new ChunkDataHandler().loadCurrentChunkData(e.getBlock()
				.getLocation()) != null) {

			PlayerChunkData pcd = new ChunkDataHandler().loadCurrentChunkData(e
					.getBlock().getLocation());

			if (!pcd.getFlagByName("build").getValue()) {

				if (!(pcd.isMember(e.getPlayer().getName())
						|| pcd.isMainOwner(e.getPlayer().getName()) || pcd
							.isOwner(e.getPlayer().getName()))) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("Can't build in this chunk");
				}
			}
		}
	}

	@EventHandler
	public void movee(PlayerMoveEvent e) {
		if (new ChunkDataHandler().loadCurrentChunkData(e.getTo()) != null) {

			PlayerChunkData pcd = new ChunkDataHandler().loadCurrentChunkData(e
					.getTo());

			if (!pcd.getFlagByName("move").getValue()) {

				if (!(pcd.isMember(e.getPlayer().getName())
						|| pcd.isMainOwner(e.getPlayer().getName()) || pcd
							.isOwner(e.getPlayer().getName()))) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("Can't move into this chunk");

					e.getPlayer().teleport(e.getFrom());
				}
			}
		}

	}

	@EventHandler
	public void plr(PlayerInteractEvent e) {

		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				&& e.getClickedBlock().getType().equals(Material.CHEST)) {
			
			if (new ChunkDataHandler().loadCurrentChunkData(e.getClickedBlock()
					.getLocation()) != null) {
				PlayerChunkData pcd = new ChunkDataHandler().loadCurrentChunkData(e
						.getClickedBlock().getLocation());

				if (!pcd.getFlagByName("container").getValue()) {

					if (!(pcd.isMember(e.getPlayer().getName())
							|| pcd.isMainOwner(e.getPlayer().getName()) || pcd
								.isOwner(e.getPlayer().getName()))) {
						e.setCancelled(true);
						e.getPlayer().sendMessage("Access to container denied");
					}
				}
			}

		}

	}

}
