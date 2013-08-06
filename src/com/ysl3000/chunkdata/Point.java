package com.ysl3000.chunkdata;

import java.io.Serializable;

public final class Point implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int x;
	private final int z;

	public Point(int x, int z) {
		this.x = x;
		this.z = z;
	}

	/**
	 * x–Coordinate from chunk
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * z–Coordinate from chunk
	 * 
	 * @return z
	 */
	public int getZ() {
		return z;
	}

	public boolean equals(Point p) {
		return (this.x == p.getX() && this.z == p.getZ());
	}

}
