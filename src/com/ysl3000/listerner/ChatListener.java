package com.ysl3000.listerner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.ysl3000.chunkdata.ChunkDataHandler;
import com.ysl3000.chunkdata.PlayerChunkData;
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

		if (st.length == 0)
			return;
		if (st[0].equalsIgnoreCase("/chunk")) {
			e.setCancelled(true);

			PlayerChunkData pcd = new ChunkDataHandler().loadCurrentChunkData(e
					.getPlayer().getLocation());
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new ChunkClaimEvent(e.getPlayer(), e.getPlayer()
									.getWorld()
									.getChunkAt(e.getPlayer().getLocation()),
									(pcd == null) ? true : false));

		} else if (st[0].equalsIgnoreCase("/unchunk")) {
			e.setCancelled(true);

			PlayerChunkData pcd = new ChunkDataHandler().loadCurrentChunkData(e
					.getPlayer().getLocation());
			if (pcd.isMainOwner(e.getPlayer().getName())) {
				Bukkit.getServer()
						.getPluginManager()
						.callEvent(
								new ChunkReleaseEvent(
										e.getPlayer(),
										e.getPlayer()
												.getWorld()
												.getChunkAt(
														e.getPlayer()
																.getLocation()),
										(pcd != null) ? true : false));
			} else {
				e.getPlayer()
						.sendMessage(
								ChatColor.RED
										+ "You aren't the mainowner. Permission denied!");
			}

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

				if (pcd.isMainOwner(e.getPlayer().getName())
						|| pcd.isOwner(e.getPlayer().getName())) {
					if (st.length < 3) {
						e.getPlayer().sendMessage(
								ChatColor.GREEN + "_____Flags_____\n");
						e.getPlayer().sendMessage(
								new ChunkDataHandler().loadCurrentChunkData(
										e.getPlayer().getLocation()).toString(
										e.getPlayer().getName()));
						return;
					} else {
						if (pcd.getChunkFlags().equalsFlag(st[2])) {
							modifier(st, pcd, e.getPlayer());
						} else {
							e.getPlayer().sendMessage(
									ChatColor.RED + "No such flag found!");
						}
					}
				} else {
					e.getPlayer()
							.sendMessage(
									ChatColor.RED
											+ "You're not allowed to use this command!");
				}
			} else if (st[1].equalsIgnoreCase("add")
					&& pcd.isMainOwner(e.getPlayer().getName())) {

				if (st.length <= 3) {
					e.getPlayer().sendMessage(
							ChatColor.RED + "Not enough arguments");
					return;
				}
				System.out.println(st[2]);
				if (st[2].equalsIgnoreCase("member")) {
					if (pcd.isMainOwner(e.getPlayer().getName())
							|| pcd.isOwner(e.getPlayer().getName())) {
						addUserMember(e.getPlayer(), pcd, st);
					}
				}
				if (st[2].equalsIgnoreCase("owner")) {
					if (pcd.isMainOwner(e.getPlayer().getName())
							|| pcd.isOwner(e.getPlayer().getName())) {
						addUserOwner(e.getPlayer(), pcd, st);
					}
				}

			} else if (st[1].equalsIgnoreCase("remove")
					&& pcd.isMainOwner(e.getPlayer().getName())) {

				if (st.length <= 3 || st.length >= 5) {
					e.getPlayer().sendMessage(
							ChatColor.RED + "Not enough arguments");
					return;
				}
				if (st[2].equalsIgnoreCase("member")) {
					removeUserMember(e.getPlayer(), pcd, st);
				} else if (st[2].equalsIgnoreCase("owner")) {
					removeUserOwner(e.getPlayer(), pcd, st);
				}
			}
		}
	}

	private void modifier(String[] st, PlayerChunkData pcd, Player p) {

		if (st.length == 4) {
			pcd.getChunkFlags().getFlagByName(st[2])
					.setValue(Boolean.parseBoolean(st[3]));
			new ChunkDataHandler().write(pcd);
			Bukkit.getServer()
					.getPluginManager()
					.callEvent(
							new ChunkModifyEvent(p, p.getLocation().getChunk(),
									pcd.getChunkFlags().getFlagByName(st[2]),
									true));
		} else if (st.length > 4 || st.length == 3) {
			p.sendMessage(ChatColor.GOLD
					+ st[2]
					+ ": "
					+ (pcd.getChunkFlags().getFlagByName(st[2]).getValue() ? ChatColor.GREEN
							: ChatColor.RED)
					+ pcd.getChunkFlags().getFlagByName(st[2]).getValue()
							.toString());
		}

	}

	private void removeUserOwner(Player p, PlayerChunkData pcd, String[] st) {
		if (pcd.removeOwner(st[3])) {
			p.sendMessage(ChatColor.GOLD + "Added " + st[3]
					+ " succesfully to owners");
			new ChunkDataHandler().write(pcd);

		} else {
			p.sendMessage(ChatColor.RED + "Failed to remove " + st[3]
					+ " from owners");
		}

	}

	private void removeUserMember(Player p, PlayerChunkData pcd, String[] st) {
		if (pcd.removeMember(st[3])) {
			p.sendMessage(ChatColor.GOLD + "removed " + st[3]
					+ " succesfully from members");
			new ChunkDataHandler().write(pcd);
		} else {
			p.sendMessage(ChatColor.RED + "Failed to remove " + st[3]
					+ " from members");
		}
	}

	private void addUserOwner(Player p, PlayerChunkData pcd, String[] st) {
		if (pcd.addOwner(st[3])) {
			p.sendMessage(ChatColor.GOLD + "Added " + st[3]
					+ " succesfully to owners");
			new ChunkDataHandler().write(pcd);
		} else {
			p.sendMessage(ChatColor.RED + "Failed to add " + st[3]
					+ " to owners");
		}
	}

	private void addUserMember(Player p, PlayerChunkData pcd, String[] st) {
		if (pcd.addMember(st[3])) {
			p.sendMessage(ChatColor.GOLD + "Added " + st[3]
					+ " succesfully to members");
			new ChunkDataHandler().write(pcd);
		} else {
			p.sendMessage(ChatColor.RED + "Failed to add " + st[3]
					+ " to members");
		}
	}
}
