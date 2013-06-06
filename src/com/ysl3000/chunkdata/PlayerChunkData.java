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
	final private ArrayList<Flag> flags;

	public PlayerChunkData(String mainowner, String world, int x, int z) {
		this.setChunkname(world + "_" + x + "_" + z);
		this.mainowner = mainowner;
		this.setWorld(world);
		this.owners = new ArrayList<String>();
		this.members = new ArrayList<String>();
		this.flags = new ArrayList<Flag>();
		this.resetFlags();
		this.setPoint(new Point(x, z));
	}

	public String toString() {

		return chunkname + " " + world + " " + point.getX() + " "
				+ point.getZ() + " " + mainowner + " " + ownersToString() + " "
				+ membersToString() + " " + getFlagsToString();

	}

	public boolean isMainOwner(String name) {
		return this.mainowner == name;
	}

	public boolean isOwner(String name) {
		for (String owner : this.getOwners()) {
			if (owner.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean isMember(String name) {
		for (String member : this.getMembers()) {
			if (member.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	// flags

	/**
	 * Modifying Flag values
	 * 
	 * @param name
	 * @param value
	 */

	public void setFlag(String name, boolean value) {
		this.getFlagByName(name).changeValue(value);
	}

	public Flag getFlagByName(String name) {
		Flag ref = null;
		for (Flag f : this.getFlags()) {
			if (f.getKey().equalsIgnoreCase(name)) {
				ref = f;
			}
		}
		return ref;
	}

	/**
	 * 
	 * @return flags as an arraylist
	 */
	public ArrayList<Flag> getFlags() {
		return flags;
	}

	/**
	 * Will reset the flags to default Flags: build, container, move
	 */
	public void resetFlags() {

		String[] defaultFlags = { "build", "container", "move" };
		for (String s : defaultFlags) {
			this.getFlags().add(new Flag(s, true));
		}
	}

	/**
	 * Concat flags to String
	 * 
	 * @return Flags as String
	 */
	public String getFlagsToString() {

		String s = "";
		for (Flag f : this.getFlags()) {
			s += ChatColor.GREEN + f.getKey() + ": " + ChatColor.RED
					+ f.getValue() + ChatColor.GREEN + ", ";
		}

		return s.substring(0, s.length() - 2);
	}

	/**
	 * Setting flags
	 * 
	 * @param s
	 *            String for flagname
	 * @param b
	 *            boolean for value
	 */
	public void modifyFlags(String s, boolean b) {
		for (Flag f : this.getFlags()) {
			f.setValue(f.getKey().equals(s) ? b : f.getValue());
		}
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
	 * @param owners
	 */
	public void addOwner(String owners) {
		this.owners.add(owners);
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
	 */
	public void addMember(String member) {

		if (!this.getMembers().contains(member)) {
			this.getMembers().add(member);
		}
	}

	/**
	 * Will remove a member from a chunk
	 * 
	 * @param member
	 */
	public void removeMember(String member) {
		if (this.getMembers().contains(member)) {
			this.getMembers().remove(member);
		}
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

}
