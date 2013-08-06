package com.ysl3000.listerner;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.ysl3000.chunkdata.PlayerChunkData;
import com.ysl3000.chunkster.Chunkcacher;
import com.ysl3000.chunkster.Chunkster;
import com.ysl3000.chunkster.EventCaller;

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
			EventCaller.getEventCaller().callChunkClaimEvent(e.getPlayer());
		} else if (st[0].equalsIgnoreCase("/unchunk")) {
			e.setCancelled(true);
			EventCaller.getEventCaller().callChunkReleaseEvent(
					e.getPlayer(),
					!((Chunkcacher.getChunkCacher().getDataByLocation(
							e.getPlayer().getLocation()) != null && Chunkcacher
							.getChunkCacher()
							.getDataByLocation(e.getPlayer().getLocation())
							.getMainOwner().equals(e.getPlayer().getName()))));

		} else if (st[0].equalsIgnoreCase("/chm")) {
			e.setCancelled(true);

			PlayerChunkData pcd = Chunkcacher.getChunkCacher()
					.getDataByLocation(e.getPlayer().getLocation());

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
								Chunkcacher
										.getChunkCacher()
										.getDataByLocation(
												e.getPlayer().getLocation())
										.toString(e.getPlayer().getName()));
						return;
					} else {
						if (pcd.getChunkFlags().equalsFlag(st[2])) {
							modifier(st, e.getPlayer());
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
						addUserMember(e.getPlayer(), st);
					}
				}
				if (st[2].equalsIgnoreCase("owner")) {
					if (pcd.isMainOwner(e.getPlayer().getName())
							|| pcd.isOwner(e.getPlayer().getName())) {
						addUserOwner(e.getPlayer(), st);
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
					removeUserMember(e.getPlayer(), st);
				} else if (st[2].equalsIgnoreCase("owner")) {
					removeUserOwner(e.getPlayer(), st);
				}
			}
		}
	}

	private void modifier(String[] st, Player p) {

		if (st.length == 4) {
			Chunkcacher.getChunkCacher().getDataByLocation(p.getLocation())
					.getChunkFlags().getFlagByName(st[2])
					.setValue(Boolean.parseBoolean(st[3]));
			Chunkcacher.getChunkCacher().getDataByLocation(p.getLocation());
			EventCaller.getEventCaller().callChunkModifyEvent(p, st);
		} else if (st.length > 4 || st.length == 3) {
			p.sendMessage(ChatColor.GOLD
					+ st[2]
					+ ": "
					+ (Chunkcacher.getChunkCacher()
							.getDataByLocation(p.getLocation()).getChunkFlags()
							.getFlagByName(st[2]).getValue() ? ChatColor.GREEN
							: ChatColor.RED)
					+ Chunkcacher.getChunkCacher()
							.getDataByLocation(p.getLocation()).getChunkFlags()
							.getFlagByName(st[2]).getValue().toString());
		}

	}

	private void removeUserOwner(Player p, String[] st) {
		if (Chunkcacher.getChunkCacher().getDataByLocation(p.getLocation())
				.removeOwner(st[3])) {
			p.sendMessage(ChatColor.GOLD + "removed " + st[3]
					+ " succesfully to owners");
		} else {
			p.sendMessage(ChatColor.RED + "Failed to remove " + st[3]
					+ " from owners");
		}

	}

	private void removeUserMember(Player p, String[] st) {
		if (Chunkcacher.getChunkCacher().getDataByLocation(p.getLocation())
				.removeMember(st[3])) {
			p.sendMessage(ChatColor.GOLD + "removed " + st[3]
					+ " succesfully from members");
		} else {
			p.sendMessage(ChatColor.RED + "Failed to remove " + st[3]
					+ " from members");
		}
	}

	private void addUserOwner(Player p, String[] st) {
		if (Chunkcacher.getChunkCacher().getDataByLocation(p.getLocation())
				.addOwner(st[3])) {
			p.sendMessage(ChatColor.GOLD + "Added " + st[3]
					+ " succesfully to owners");
		} else {
			p.sendMessage(ChatColor.RED + "Failed to add " + st[3]
					+ " to owners");
		}
	}

	private void addUserMember(Player p, String[] st) {
		if (Chunkcacher.getChunkCacher().getDataByLocation(p.getLocation())
				.addMember(st[3])) {
			p.sendMessage(ChatColor.GOLD + "Added " + st[3]
					+ " succesfully to members");
		} else {
			p.sendMessage(ChatColor.RED + "Failed to add " + st[3]
					+ " to members");
		}
	}

}
