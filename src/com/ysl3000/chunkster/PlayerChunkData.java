package com.ysl3000.chunkster;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerChunkData {
	private int x;
	private int z;
	private String mainowner;
	private ArrayList<String> owners;
	private ArrayList<String> members;
	final private HashMap<String, Boolean> flags;
	
	public PlayerChunkData(String mainowner,int x, int z){
		this.mainowner=mainowner;
		this.owners = new ArrayList<String>();
		this.members = new ArrayList<String>();
		this.flags= new HashMap<String, Boolean>();
		this.resetFlags();
	}
	
	public void resetFlags(){
		
		String[] defaultFlags = {"build","container","move"};
		for(String s: defaultFlags){
			this.getFlags().put(s, true);
		}
	}
	
	
	public String getCreator() {
		return mainowner;
	}
	public ArrayList<String> getOwners() {
		return owners;
	}
	public void setOwners(ArrayList<String> owners) {
		this.owners = owners;
	}
	
	//Members
	public ArrayList<String> getMembers() {
		return members;
	}
	public void addMembers(String member){
		
		if(!this.getMembers().contains(member)){
			this.getMembers().add(member);
		}
	}
	public void removeMembers(String member){
		if(this.getMembers().contains(member)){
			this.getMembers().remove(member);
		}
	}
	
	
	
	
	public HashMap<String, Boolean> getFlags() {
		return flags;
	}
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
	
}
