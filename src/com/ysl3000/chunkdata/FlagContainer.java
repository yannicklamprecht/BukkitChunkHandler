package com.ysl3000.chunkdata;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.ChatColor;

public class FlagContainer implements Serializable{

	
	private static final long serialVersionUID = 1L;
	final private ArrayList<Flag> chunkflags;

	public FlagContainer() {
		this.chunkflags = new ArrayList<Flag>();
		this.resetFlags();
	}

	/**
	 * 
	 * @return flags as an arraylist
	 */
	public ArrayList<Flag> getFlags() {
		return chunkflags;
	}

	/**
	 * Modifying Flag values
	 * 
	 * @param name
	 * @param value
	 */
	public void setFlag(String name, boolean value) {
		this.getFlagByName(name).setValue(value);
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

	public boolean equalsFlag(String flag) {
		for (Flag f : this.getFlags()) {
			if (f.getKey().equalsIgnoreCase(flag)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Will reset the flags to default Flags: build, container, move
	 */
	public void resetFlags() {

		String[] defaultFlags = { "build", "container", "chat", "pvp" };
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
			s += ChatColor.GOLD + f.getKey() + ": "
					+ (f.getValue() ? ChatColor.GREEN : ChatColor.RED)
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
}
