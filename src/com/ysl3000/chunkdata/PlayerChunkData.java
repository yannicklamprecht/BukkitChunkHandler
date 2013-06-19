package com.ysl3000.chunkdata;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.ChatColor;

public class PlayerChunkData implements Serializable {

	private String chunkname;
	private static final long serialVersionUID = 1L;
	private String world;
	private Point point;
	private String mainowner;
	private ArrayList<String> owners;
	private ArrayList<String> members;
	private FlagContainer chunkFlags;
	private ArrayList<PlayerChunkFlags> playerflags;

	public PlayerChunkData(String mainowner, String world, int x, int z) {
		this.setChunkname(world + "_" + x + "_" + z);
		this.mainowner = mainowner;
		this.setWorld(world);
		this.owners = new ArrayList<String>();
		this.members = new ArrayList<String>();
		this.setPoint(new Point(x, z));
		this.chunkFlags = new FlagContainer();
		this.playerflags = new ArrayList<PlayerChunkFlags>();
	}

	public ArrayList<PlayerChunkFlags> getPlayerFlags() {
		return playerflags;
	}

	public PlayerChunkFlags getPlayerFlagByPlayerName(String playername) {
		for (PlayerChunkFlags f : this.getPlayerFlags()) {
			if (f.getPlayerName().equalsIgnoreCase(playername)) {
				return f;
			}
		}
		return null;
	}

	public FlagContainer getChunkFlags() {
		return chunkFlags;
	}

	public String toString(String playername) {

		return world + " " + point.getX() + " " + point.getZ() + ChatColor.GRAY
				+ "\nmainowner: " + ChatColor.RESET + mainowner
				+ ChatColor.GRAY + "\nowners: " + ChatColor.RESET
				+ ownersToString() + ChatColor.GRAY + "\nmembers: "
				+ ChatColor.RESET + membersToString() + ChatColor.GRAY
				+ "\nflags: " + this.chunkFlags.getFlagsToString()
				+ ChatColor.GRAY + "\nyour flags: "
				+ this.getPlayerFlagByPlayerName(playername).getFlagsToString();

	}

	/**
	 * 
	 * @param name
	 * @return if user mainowner of chunk
	 */
	public boolean isMainOwner(String name) {
		return this.mainowner.equalsIgnoreCase(name);
	}

	/**
	 * 
	 * @param name
	 * @return if owner of Chunk
	 */
	public boolean isOwner(String name) {
		for (String owner : this.getOwners()) {
			if (owner.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param name
	 * @return if user is a member of the chunk
	 */
	public boolean isMember(String name) {
		for (String member : this.getMembers()) {
			if (member.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	// MainOwner

	/**
	 * Only mainowner is posible to sell the chunk
	 * 
	 * @return mainowner
	 */
	public String getMainOwner() {
		return mainowner;
	}

	/**
	 * default: Server or null on Chunkcreate set main Owner when Chunk is
	 * bought
	 * 
	 * @param mainOwner
	 */
	public void setMainOwnerr(String mainOwner) {
		this.mainowner = mainOwner;
		addPlayerToFlags(mainOwner);
	}

	// Owners

	/**
	 * Owners can only modify the flags
	 * 
	 * @return Owners
	 */
	public ArrayList<String> getOwners() {
		return owners;
	}

	/**
	 * 
	 * @return Owners as a humanreadeble String
	 */
	public String ownersToString() {

		String ownersto = "";
		for (String s : this.getOwners()) {
			ownersto += s + ", ";
		}
		return (ownersto.length() != 0) ? ownersto.substring(0,
				(ownersto.length() - 1)) : "Not added yet";
	}

	/**
	 * Add an Owner to the chunk
	 * 
	 * @param owner
	 * @return success
	 */

	public boolean addOwner(String owners) {
		if (!this.getOwners().contains(owners)) {
			this.owners.add(owners);
			this.addPlayerToFlags(owners);
			return true;
		}
		return false;
	}

	/**
	 * Will remove a owner from a chunk
	 * 
	 * @param owner
	 * @return success
	 */
	public boolean removeOwner(String owner) {
		if (this.getOwners().contains(owner)) {
			this.getOwners().remove(owner);
			this.removePlayerFromFlags(owner);
			return true;
		}
		return false;
	}

	// Members

	/**
	 * Members are able to do everything at the chunk except flagchanging or
	 * chunkselling
	 * 
	 * @return members as an ArrayList
	 */
	public ArrayList<String> getMembers() {
		return members;
	}

	/**
	 * 
	 * @return members as an humanreadable String
	 */
	public String membersToString() {
		String memberto = "";
		for (String s : members) {
			memberto += s + ", ";
		}
		return (memberto.length() != 0) ? memberto.substring(0,
				(memberto.length() - 1)) : "Not added yet";
	}

	/**
	 * Will add a member to a chunk
	 * 
	 * @param member
	 * @return success
	 */
	public boolean addMember(String member) {

		if (!this.getMembers().contains(member)) {
			this.getMembers().add(member);
			this.addPlayerToFlags(member);
			return true;
		}
		return false;
	}

	/**
	 * Will remove a member from a chunk
	 * 
	 * @param member
	 * @return succes
	 */

	public boolean removeMember(String member) {
		if (this.getMembers().contains(member)) {
			this.getMembers().remove(member);
			this.removePlayerFromFlags(member);
			return true;
		}
		return false;
	}

	/**
	 * @return the point
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	private void setPoint(Point point) {
		this.point = point;
	}

	/**
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}

	/**
	 * @param world
	 *            the world to set
	 */
	private void setWorld(String world) {
		this.world = world;
	}

	/**
	 * @return the chunkname
	 */
	public String getChunkname() {
		return chunkname;
	}

	/**
	 * @param chunkname
	 *            the chunkname to set
	 */
	private void setChunkname(String chunkname) {
		this.chunkname = chunkname;
	}

	private void addPlayerToFlags(String playername) {
		if (this.getPlayerFlagByPlayerName(playername) != null)
			return;
		this.getPlayerFlags().add(new PlayerChunkFlags(playername));
	}

	private void removePlayerFromFlags(String playername) {
		if (this.getPlayerFlagByPlayerName(playername) == null)
			return;
		this.getPlayerFlags()
				.remove(this.getPlayerFlagByPlayerName(playername));
	}

}
