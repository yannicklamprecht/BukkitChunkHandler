package com.ysl3000.listerner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ysl3000.chunkdata.PlayerChunkData;
import com.ysl3000.chunkster.Chunkcacher;
import com.ysl3000.chunkster.Chunkster;
import com.ysl3000.chunkster.event.ChunkModifyEvent;
import com.ysl3000.chunkster.event.PlayerChunkChangeEvent;

public class PlayerActionEventListener implements Listener {

	public PlayerActionEventListener(Chunkster plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void playccE(PlayerChunkChangeEvent e) {
		if (Chunkcacher.getChunkCacher().getDataByLocation(e.getFrom()) != null
				&& Chunkcacher.getChunkCacher().getDataByLocation(e.getTo()) != null) {

			if (Chunkcacher
					.getChunkCacher()
					.getDataByLocation(e.getFrom())
					.getMainOwner()
					.equalsIgnoreCase(
							Chunkcacher.getChunkCacher()
									.getDataByLocation(e.getTo())
									.getMainOwner())) {
				return;
			}
		}

		if (Chunkcacher.getChunkCacher().getDataByLocation(e.getFrom()) != null) {
			e.getPlayer().sendMessage(
					ChatColor.GOLD
							+ "You left a Chunk "
							+ "\nfrom "
							+ ChatColor.GREEN
							+ Chunkcacher.getChunkCacher()
									.getDataByLocation(e.getFrom())
									.getMainOwner());
		}
		if (Chunkcacher.getChunkCacher().getDataByLocation(e.getTo()) != null) {
			e.getPlayer().sendMessage(
					ChatColor.GOLD
							+ "You entered a Chunk "
							+ "\nfrom "
							+ ChatColor.GREEN
							+ Chunkcacher.getChunkCacher()
									.getDataByLocation(e.getTo())
									.getMainOwner());
		}

	}

	@EventHandler
	public void modifyChunk(ChunkModifyEvent e) {
		if(!e.isCancelled()){
			e.getPlayer().sendMessage(
					e.getFlag().getKey() + " set to " + e.getFlag().getValue());
			
		}else{
			e.getPlayer().sendMessage("Modifing failed");
		}
	}

	@EventHandler
	public void breakBlock(BlockBreakEvent e) {

		if (Chunkcacher.getChunkCacher().getDataByLocation(
				e.getBlock().getLocation()) != null) {
			PlayerChunkData pcd = Chunkcacher.getChunkCacher()
					.getDataByLocation(e.getBlock().getLocation());

			if (!pcd.getChunkFlags().getFlagByName("build").getValue()) {

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
		if (Chunkcacher.getChunkCacher().getDataByLocation(
				e.getBlock().getLocation()) != null) {

			PlayerChunkData pcd = Chunkcacher.getChunkCacher()
					.getDataByLocation(e.getBlock().getLocation());

			if (!pcd.getChunkFlags().getFlagByName("build").getValue()) {

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
		if (e.getFrom().getChunk() != e.getTo().getChunk()) {
			Bukkit.getPluginManager().callEvent(
					new PlayerChunkChangeEvent(e.getPlayer(), e.getFrom(), e
							.getTo(), e.isCancelled()));
		}

	}

	@EventHandler
	public void plr(PlayerInteractEvent e) {

		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				&& e.getClickedBlock().getType().equals(Material.CHEST)) {

			if (Chunkcacher.getChunkCacher().getDataByLocation(
					e.getClickedBlock().getLocation()) != null) {
				PlayerChunkData pcd = Chunkcacher.getChunkCacher()
						.getDataByLocation(e.getClickedBlock().getLocation());

				if (!pcd.getChunkFlags().getFlagByName("container").getValue()) {

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

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		if (Chunkcacher.getChunkCacher().getDataByLocation(
				e.getPlayer().getLocation()) != null) {
			PlayerChunkData pcd = Chunkcacher.getChunkCacher()
					.getDataByLocation(e.getPlayer().getLocation());

			if (!pcd.getChunkFlags().getFlagByName("chat").getValue()) {

				if (!(pcd.isMember(e.getPlayer().getName())
						|| pcd.isMainOwner(e.getPlayer().getName()) || pcd
							.isOwner(e.getPlayer().getName()))) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("Chataccess denied");
				}
			}
		}
	}

}
