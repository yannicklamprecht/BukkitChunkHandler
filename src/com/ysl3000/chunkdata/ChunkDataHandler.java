package com.ysl3000.chunkdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ChunkDataHandler {

	private static String trenner = "_";

	public PlayerChunkData loadCurrentChunkData(Location l) {
		return this.read(l.getChunk().getWorld().getName(), l.getChunk().getX(), l.getChunk()
				.getZ());
	}

	public void write(PlayerChunkData data) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream("./ChunkData/"
					+ data.getWorld() + trenner + data.getPoint().getX()
					+ trenner + data.getPoint().getZ() + ".pcd");
			ObjectOutputStream objectOutput = new ObjectOutputStream(
					outputStream);
			objectOutput.writeObject(data);
			objectOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private PlayerChunkData read(String world, int x, int z) {
		PlayerChunkData session = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("./ChunkData/" + world + trenner
					+ x + trenner + z + ".pcd");
			ObjectInputStream objectInput = new ObjectInputStream(inputStream);
			session = (PlayerChunkData) objectInput.readObject();
			objectInput.close();
		} catch (IOException e) {
			// System.out.println("Filename not found");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		}

		return session;
	}

	public void displayall(Player p) {

		for (String s : listDir()) {
			p.sendMessage(lol(s).toString());
		}

	}

	public String[] listDir() {
		File dir = new File("./ChunkData/");
		String[] files = dir.list();
		return files;
	}

	private PlayerChunkData lol(String name) {

		PlayerChunkData session = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("./ChunkData/" + name);
			ObjectInputStream objectInput = new ObjectInputStream(inputStream);
			session = (PlayerChunkData) objectInput.readObject();
			objectInput.close();
		} catch (IOException e) {
			System.out.println("Filename not found");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		} finally {
		}
		return session;

	}

	public void deleteFile(Player p) {
		File f = new File("./ChunkData/"
				+ p.getLocation().getChunk().getWorld().getName() + trenner
				+ p.getLocation().getChunk().getX() + trenner
				+ p.getLocation().getChunk().getZ() + ".pcd");
		if (f.exists()) {
			f.delete();
		}
	}
}
