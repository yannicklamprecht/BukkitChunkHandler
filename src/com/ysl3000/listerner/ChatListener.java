package com.ysl3000.listerner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.ysl3000.chunkdata.ChunkDataHandler;
import com.ysl3000.chunkdata.PlayerChunkData;
import com.ysl3000.chunkster.Chunkhandler;
import com.ysl3000.chunkster.Chunkster;
import com.ysl3000.chunkster.event.ChunkClaimEvent;
import com.ysl3000.chunkster.event.ChunkModifyEvent;
import com.ysl3000.chunkster.event.ChunkReleaseEvent;

public class ChatListener implements Listener {

	public ChatListener(Chunkster plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onChat(PlayerCommandPreprocessEvent e) {

		String[] st = e.getMessage().split(" ");

		if (st[0].equalsIgnoreCase("/chunk")) {
			e.setCancelled(true);
			Chunkhandler.modifyChunkHighlight(e.getPlayer(), 50);
			if (new ChunkDataHandler().loadCurrentChunkData(e.getPlayer()
					.getLocation()) == null) {
				PlayerChunkData pcd = new PlayerChunkData(e.getPlayer()
						.getName(), e.getPlayer().getLocation().getChunk()
						.getWorld().getName(), e.getPlayer().getLocation()
						.getChunk().getX(), e.getPlayer().getLocation()
						.getChunk().getZ());

				pcd.addOwner(e.getPlayer().getName());
				new ChunkDataHandler().write(pcd);
				e.getPlayer().sendMessage("Chunk claimed successfully");

			} else {
				e.getPlayer().sendMessage("Chunk occupied");
			}

			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new ChunkClaimEvent(e.getPlayer(), e.getPlayer()
									.getWorld()
									.getChunkAt(e.getPlayer().getLocation())));
		} else if (st[0].equalsIgnoreCase("/unchunk")) {
			e.setCancelled(true);
			Chunkhandler.modifyChunkHighlight(e.getPlayer(), 0);

			if (new ChunkDataHandler().loadCurrentChunkData(e.getPlayer()
					.getLocation()) != null) {

				new ChunkDataHandler().deleteFile(e.getPlayer());

				e.getPlayer().sendMessage("Chunk released successfully");
			} else {
				e.getPlayer().sendMessage("Chunk not occupied");
			}

			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new ChunkReleaseEvent(e.getPlayer(), e.getPlayer()
									.getWorld()
									.getChunkAt(e.getPlayer().getLocation())));

		} else if (st[0].equalsIgnoreCase("/chm")) {
			e.setCancelled(true);

			PlayerChunkData pcd = new ChunkDataHandler().loadCurrentChunkData(e
					.getPlayer().getLocation());

			if (pcd == null) {
				e.getPlayer().sendMessage("No Chunk detected");
				return;
			}

			if (st.length < 2) {
				e.getPlayer().sendMessage(
						ChatColor.GREEN + "_______Chunkmenu_______\n"
								+ "/chm <flag> [flags] <value>");
				return;
			}

			if (st[1].equalsIgnoreCase("flag")) {

				if (st.length < 3) {
					e.getPlayer().sendMessage(
							ChatColor.GREEN + "_____Flags_____\n");

					if (new ChunkDataHandler().loadCurrentChunkData(e
							.getPlayer().getLocation()) != null) {
						e.getPlayer()
								.sendMessage(
										new ChunkDataHandler()
												.loadCurrentChunkData(
														e.getPlayer()
																.getLocation())
												.toString());
					} else {
						e.getPlayer().sendMessage("No PlayerChunk created");
					}

					// new ChunkDataHandler().displayall(e.getPlayer());

					return;
				} else {
					if (st[2].equalsIgnoreCase("build")) {

						if (st.length == 4) {
							pcd.getFlagByName(st[2]).setValue(
									Boolean.parseBoolean(st[3]));
							new ChunkDataHandler().write(pcd);

							Bukkit.getServer()
									.getPluginManager()
									.callEvent(
											new ChunkModifyEvent(e.getPlayer(),
													e.getPlayer().getLocation()
															.getChunk()));
						} else if (st.length > 4 || st.length == 3) {
							e.getPlayer()
									.sendMessage(
											new ChunkDataHandler()
													.loadCurrentChunkData(
															e.getPlayer()
																	.getLocation())
													.getFlagByName(st[2])
													.getValue().toString());
						}
					} else if (st[2].equalsIgnoreCase("move")) {

						if (st.length == 4) {
							pcd.getFlagByName(st[2]).setValue(
									Boolean.parseBoolean(st[3]));
							new ChunkDataHandler().write(pcd);

							Bukkit.getServer()
									.getPluginManager()
									.callEvent(
											new ChunkModifyEvent(e.getPlayer(),
													e.getPlayer().getLocation()
															.getChunk()));
						} else if (st.length > 4 || st.length == 3) {
							e.getPlayer()
									.sendMessage(
											new ChunkDataHandler()
													.loadCurrentChunkData(
															e.getPlayer()
																	.getLocation())
													.getFlagByName(st[2])
													.getValue().toString());
						}
					}else if (st[2].equalsIgnoreCase("container")) {

						if (st.length == 4) {
							pcd.getFlagByName(st[2]).setValue(
									Boolean.parseBoolean(st[3]));
							new ChunkDataHandler().write(pcd);

							Bukkit.getServer()
									.getPluginManager()
									.callEvent(
											new ChunkModifyEvent(e.getPlayer(),
													e.getPlayer().getLocation()
															.getChunk()));
						} else if (st.length > 4 || st.length == 3) {
							e.getPlayer()
									.sendMessage(
											new ChunkDataHandler()
													.loadCurrentChunkData(
															e.getPlayer()
																	.getLocation())
													.getFlagByName(st[2])
													.getValue().toString());
						}
					}
				}

			}

		}
	}
}
