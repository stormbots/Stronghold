package org.usfirst.frc2811.Stronghold2016;

/**
 * Helper class for the Vision subsystem to keep data more organized when
 * doing things such as sorting/prioritizing/ranking targets.
 */
public class VisionTarget implements Comparable<VisionTarget> {

	private double coordinateX;
	private double coordinateY;
	
	private double width;
	private double height;
	
	private double area;
	
	private double solidity;
	
	/**
	 * 
	 * @param cX Center X of the target
	 * @param cY Center Y of the target
	 * @param h  Height of the target
	 * @param w  Width of the target
	 * @param area Area of the target
	 * @param solidity Solidity of target
	 */
	public VisionTarget(double cX, double cY, double h, double w, double area, double solidity) {
		this.coordinateX = cX;
		this.coordinateY = cY;
		
		this.height = h;
		this.width = w;
		
		this.area = area;
		
		this.solidity = solidity;
	}
	
	// Provides for natural Collections sorting via the 
	// Comparable interface
	public int compareTo(VisionTarget t2) {
		// we never will want a null object in first place, so
		// let's make sure that can't get to the front
		if (t2 == null) {
			return 1;
		}
		
		return (int)(this.area - t2.area);
	}
	/**
	 * @return Y-coordinate (which is X in our rotated system)
	 */
	public double getX() {
		return this.coordinateY;
	}
	/**
	 * @return X-coordinate (which is Y in our rotated system)
	 */
	public double getY() {
		return this.coordinateX;
	}
	
	public double getArea() {
		return this.area;
	}

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}
	
	public double getSolidity() {
		return this.solidity;
	}
	
}
