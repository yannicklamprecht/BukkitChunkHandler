package com.ysl3000.chunkster;

public class TimeCapsule extends Thread {

	private int sleeptime;

	public TimeCapsule(int time) {
		this.sleeptime = time;
	}

	@Override
	public void run() {

		while (isAlive()) {
			Chunkcacher.getChunkCacher().saveFiles();
			try {
				sleep(sleeptime);
			} catch (InterruptedException e) {
				System.err.println("DIndn");
			}
		}
	}

}
