package com.ysl3000.chunkdata;

import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = 1L;
	private int x;
	private int z;

	public Point(int x, int z) {
		this.setX(x);
		this.setZ(z);
	}

	/**
	 * @return the z
	 */

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

	/**
	 * @param x
	 *            the x to set
	 */
	private void setX(int x) {
		this.x = x;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	private void setZ(int z) {
		this.z = z;
	}
}
