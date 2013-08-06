package com.ysl3000.chunkster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ysl3000.chunkdata.PlayerChunkData;
import com.ysl3000.chunkdata.Point;

public class Chunkcacher {
	private static Chunkcacher mainCacher;
	private ArrayList<PlayerChunkData> chunkcache;

	private Chunkcacher() {
		this.chunkcache = new ArrayList<PlayerChunkData>();
	}

	public static Chunkcacher getChunkCacher() {

		if (mainCacher == null) {
			mainCacher = new Chunkcacher();
		}
		return mainCacher;
	}

	public void loadFiles() {

		int loaded = 0;
		int notLoaded = 0;
		int sum = 0;
		File f = new File(Chunkster.getPath());
		for (File fl : f.listFiles()) {

			try {
				FileInputStream inputStream = new FileInputStream(
						fl.getAbsolutePath());
				ObjectInputStream objectInput = new ObjectInputStream(
						inputStream);
				this.chunkcache.add((PlayerChunkData) objectInput.readObject());
				objectInput.close();
				loaded++;
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
				notLoaded++;
			}
			sum++;
		}
		System.out.println("-------Chunkcacher-------");
		System.out.println("Loaded:\t" + loaded);
		System.out.println("NotLoaded:\t" + notLoaded);
		System.out.println("Files:\t" + sum);
		System.out.println("-------------------------");
	}

	public void saveFiles() {

		for (PlayerChunkData pcd : chunkcache) {
			try {
				FileOutputStream outstream = new FileOutputStream(
						Chunkster.getPath() + pcd.getChunkname() + ".pcd");
				ObjectOutputStream objectou = new ObjectOutputStream(outstream);
				objectou.writeObject(pcd);
				objectou.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public PlayerChunkData getDataByLocation(Location loc) {
		for (PlayerChunkData cd : chunkcache) {

			if (cd.getPoint().equals(
					new Point(loc.getChunk().getX(), loc.getChunk().getZ()))) {
				return cd;
			}
		}
		return null;
	}

	public void addChunkData(PlayerChunkData data) {
		this.chunkcache.add(data);
	}

	public void deleteData(Player p) {
		PlayerChunkData pcd = this.getDataByLocation(p.getLocation());
		File f = new File(Chunkster.getPath() + pcd.getChunkname() + ".pcd");
		if (f.exists()) {
			f.delete();
		}
		this.chunkcache.remove(pcd);
	}

}
