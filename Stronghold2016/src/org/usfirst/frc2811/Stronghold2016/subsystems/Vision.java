// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2811.Stronghold2016.subsystems;

import java.util.ArrayList;

import org.usfirst.frc2811.Stronghold2016.VisionTarget;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem {
	
	/**
	 * NetworkTables handle to the NetworkTable GRIP will push values to
	 */
	private NetworkTable ntHandle;

	/**
	 * Name of the NetworkTable that GRIP will push values to
	 */
	private String tableName;
	
	/**
	 * Default/fallback/failure value for multi-number NetworkTables data types
	 */
	private double[] defaultValue = new double[0];
	
	/**
	 * Error message to display if the GRIP NetworkTable does not exist or cannot be connected to
	 */
	private String unconnectedError = "VISION: Error -> NT not connected!";
	
	
	/**
	 * Height of camera off the ground
	 */
	private double cameraHeight = 0;
	
	/**
	 * Distance forward or backward from the shooter to the camera
	 * Camera in front of shooter: Positive value
	 * Camera behind[ shooter: Negative value
	 */
	private double cameraOffsetDistanceY = 0;
	
	/**
	 * Camera angle rel to vertical plane
	 */
	private double cameraOffsetAngleY = 0;
	
	/**
	 * Diagonal field of view of the camera
	 */
	private double diagonalFieldOfView = 0;
	
	/**
	 * Number of horizontal pixels provided by the camera
	 */
	private double cameraPixelsX = 0;
	
	/**
	 * Number of vertical pixels provided by the camera
	 */
	private double cameraPixelsY = 0;
	
	
	/**
	 * Initialize the Vision subsystem
	 * 
	 * @param tableName Network Table name for GRIP to post values to
	 * @param cameraHeight The height of the camera off of the ground
	 * @param offsetY Front/back offset of camera, in feet, relative to shooter. down: < 0; up > 0
	 * @param cameraOffsetAngleY Angular offset of camera... how many degrees upward are we looking by default?
	 * @param diagonalFieldOfView The diagonal field of view (in degrees) of the camera used by the subsystem
	 * @param cameraPixelsX Number of horizontal pixels provided by the camera used by the subsystem
	 * @param cameraPixelsY Number of vertical pixels provided by the camera used by the subsystem
	 */
	public Vision(String networkTableName,
				  double cameraHeight, double offsetY, double cameraOffsetAngleY, // Physical attributes of the camera
				  double diagonalFieldOfView, int cameraPixelsX, int cameraPixelsY){ // Technical attributes of camera
		this.tableName = networkTableName;
		
		this.cameraHeight = cameraHeight;
		this.cameraOffsetDistanceY = offsetY;
		this.cameraOffsetAngleY = cameraOffsetAngleY;
		
		this.diagonalFieldOfView = diagonalFieldOfView;
		this.cameraPixelsX = cameraPixelsX;
		this.cameraPixelsY = cameraPixelsY;
	
		this.networkTableInit();
	}

	/**
	 * Initialize the tableName NetworkTable for vision values
	 */
	public void networkTableInit() {
		// getTable initializes tableName when it doesn't already exist
		ntHandle = NetworkTable.getTable(tableName);
		
		System.out.println("VISION: NT Connected: " + ntHandle.isConnected());
	}
	
	/**
	 * @return If the handle to GRIP's NetworkTable is valid and functioning
	 */
	private boolean tableConnected() {
		if(ntHandle.isConnected()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param tableKey The key on the GRIP NetworkTable to get values from
	 * @return Values in tableKey on the GRIP NetworkTable
	 */
	private double[] getValArray(String tableKey){ //can be used to get number array values such as area
		if (this.tableConnected()) {
			return ntHandle.getNumberArray(tableKey, defaultValue);
		} else {
			System.out.println(unconnectedError);
			return defaultValue;
		}
	}
	
	/*
	 * Get Y part of coordinate
	 * @return double the Y coord
	 */
	public double getTargetCoordY() {
		VisionTarget t = this.getBestTarget();
		
		if (t == null) {
			System.out.println("ERR: Null target. Returning -9999.");
			return -9999;
		}
		
		return t.getMappedY();
	}
	
	/**
	 * Returns the X part of the target coordinate, after image "rotation"
	 * @return the X part of the target center coordinate after image "rotation"
	 */
	public double getTargetCoordX() {
		VisionTarget t = this.getBestTarget();
		
		if (t == null) {
			System.out.println("ERR: Null target. Returning -9999.");
			return -9999;
		}
		
		return t.getMappedX();
	}
	
	/**
	 * @return Horizontal distance to target
	 */
	public double getDistanceToTarget() {
		double angleToTarget = this.getYAngleToTarget();
		
		if (angleToTarget < -9998 && angleToTarget > -10000) {
			System.out.println("NO TARGET");
			return -9999;
		}
		
		// goal center height is 8'1". Subtract the camera height to get the height difference (opp in trig)
		// this height is the distance from the ground to the middle of the target/goal
		double goalHeight = 8 + 1.0/12.0 - this.cameraHeight;
		
		/*  tan(angle) = goalHeight / distanceX
		    
		    distanceX*tan(angle) = goalHeight
		    
		    distanceX = goalHeight / tan(angle)
	    */
		double distanceX = goalHeight / Math.tan(Math.toRadians(angleToTarget));
		
		return distanceX;
	}
	/**
	 * Overload of isOnTarget(double angleTolerance). Passes default value of 3 as angleTolerance.
	 * @return Whether or not the robot/shooter is aligned with the target, +- 3 degrees.
	 */
	public boolean isOnTarget() {
		return isOnTarget(3);
	}
	
	/**
	 * @param angleTolerance Tolerance, in degrees, within which the actual angle can differ from "perfect" alignment
	 * @return Whether or not the robot/shooter is aligned with the target, +- angleTolerance degrees.
	 */
	public boolean isOnTarget(double angleTolerance) {
		VisionTarget t = this.getBestTarget();
		double expectedDegreesOffset = (t.getHeight() / 2) * this.diagonalFieldOfViewToXYFieldOfView()[1];
		
		double horizontalAngleOffset = this.getXAngleToTarget();
		
		/* Return true if the robot needs to turn less than angleTolerance degrees to be aligned
		 * with the target
		 */
		return Math.abs(horizontalAngleOffset - expectedDegreesOffset) <= angleTolerance;
	}
	
	/**
	 * Break down the diagonal field of view of the camera into horizontal FOV and vertical FOV
	 * @return an array containing {horizontal fov, vertical fov} in degrees
	 */
	private double[] diagonalFieldOfViewToXYFieldOfView(){ //this method is CLEAN
		
		// just an angle for the trig stuff... lower left acute angle for a 720p camera
		double angle = Math.toDegrees(Math.atan((double)this.cameraPixelsY/this.cameraPixelsX));
		
		// You can decompose a diagonal FOV with just basic trig
		double fovX = this.diagonalFieldOfView * Math.cos(Math.toRadians(angle));
		double fovY = this.diagonalFieldOfView * Math.sin(Math.toRadians(angle));

		double[] fovArray = {fovX, fovY};
		
		return fovArray;
	}
	
	/**
	 * Automatically returns the horizontal angle of the target (parallel to the plane of the field), based on values in the NetworkTable
	 * @return double angleHorizontal. Left - negative; Right - positive
	 */
	public double getXAngleToTarget() {
	
		VisionTarget t = this.getBestTarget();
		
		if (t == null) {
			System.out.println("NO TARGET. RETURNING -9999.");
			return -9999;
		}
		
		double objectPositionX = t.getMappedX();
		
		double[] FOVs = this.diagonalFieldOfViewToXYFieldOfView();
		
		/* Take the pixel offsets of the object from the center of the image
		 * Multiply by half of the FOV per pixel on that axis because we have FOV/2 field of view
		 * in each direction on that axis.
		 */
		double angleOffsetX = objectPositionX * (FOVs[1]/this.cameraPixelsY);

		return angleOffsetX;
	}
	
	/**
	 * Automatically returns the vertical angle of the target (perpendicular to the plane of the field), based on values in the NetworkTable
	 * @return double angleVertical. Down - negative; up - positive
	 * We should *NEVER* get a negative value, because that means the robot (or camera) is above the goal, which
	 * means it's probably flying.
	 */
	public double getYAngleToTarget() {
		
		VisionTarget t = this.getBestTarget();
		
		if (t == null) {
			System.out.println("ERR: No target detected. Returning -9999");
			return -9999;
		}
		
		double objectPositionY = t.getMappedY();
		
		double[] FOVs = this.diagonalFieldOfViewToXYFieldOfView();
		
		/* Take the pixel offsets of the object from the center of the image
		 * Multiply by half of the FOV per pixel on that axis because we have FOV/2 field of view
		 * in each direction on that axis.
		 */
		double angleOffsetY = objectPositionY  * (FOVs[0]/this.cameraPixelsX);
		
		return angleOffsetY + this.cameraOffsetAngleY;
	}
	
	/**
	 * @return IN THEORY, the true distance from the shooter to the target
	 */
	public double getRealDistanceToTarget() {
		// Should be as simple as this since these distances are in parallel directions
		return this.getDistanceToTarget() + this.cameraOffsetDistanceY;
	}
	
	/**
	 * @return VisionTarget instance of the largest goal detected by GRIP
	 */
	public VisionTarget getBestTarget() {
		if (this.tableConnected()) {

			ArrayList<VisionTarget> targets = new ArrayList<VisionTarget>();

			try {
				// 8 Apr - used to be double[] coordsX = this.getValArray("centerY"); but that would cause the target to be (Y, Y) in the VisionTarget object
				double[] coordsX = this.getValArray("centerX");

				// make sure we have a target first
				if (coordsX.length < 1) {
					return null;
				}

				// hope we get all these values before GRIP ticks again
				// and targets potentially disappear, which would cause an out
				// of bounds issue.
				double[] coordsY = this.getValArray("centerY");

				double[] heights = this.getValArray("height");
				double[] widths = this.getValArray("width");

				double[] areas = this.getValArray("area");
				double[] solidity = this.getValArray("solidity");

				for (int i = 0; i < (coordsX.length); i++) {
					targets.add(new VisionTarget(coordsX[i], coordsY[i], heights[i], widths[i], areas[i], solidity[i]));
				}

				// sort by area first using Comparable interface spec (will sort by area, in this case)
				targets.sort(null);

				// Return the best target, which should be a thing that's in a sane region of the
				// frame and the thing with the largest area (so no one-pixel things get picked!).
				return targets.get(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Caught ArrayIndexOutOfBoundsException. Some targets have disappeared!");
				return null;
			}
		} else {
			System.out.println(unconnectedError);
			return null;
		}
	}
	
	public boolean isAlignedToLeft() {
		VisionTarget t = this.getBestTarget();
		
		if (t == null) {
			System.out.println("isAlignedToLeft: No good target. Returning false.");
			return false;
		}
		
		double centerOffsetX = t.getMappedX();
		
		// Apr 8 - Refactored the return to make use of the already
		// existing centerOffsetThing() which does almost exactly
		// what we want already.
		return Math.abs(this.centerOffsetThing()) < 15;
	}
	
	public double centerOffsetThing() {
		VisionTarget t = this.getBestTarget();
		
		if (t == null) {
			System.out.println("cameraOffsetThing: No good target. Returning -9999.");
			return -9999;
		}
		
		// The offset is equal to the distance from the center,
		// which happens to be our coordinate after remapping.
		double centerOffsetX = t.getMappedX();
		
		// Return that offset plus half the width of the
		// target to get the total net offset from the right side.
		return (centerOffsetX - (t.getHeight() / 2));
	}
	
	public VisionTarget autonomousTargetingGetBestTarget() {
		// much like getBestTarget, but with more filtering...
		// note: this will expend more time!!!!
		
		if (this.tableConnected()) {

			ArrayList<VisionTarget> targets = new ArrayList<VisionTarget>();

			try {
				// 8 Apr - used to be double[] coordsX = this.getValArray("centerY"); but that would cause the target to be (Y, Y) in the VisionTarget object
				double[] coordsX = this.getValArray("centerX");

				// make sure we have a target first
				if (coordsX.length < 1) {
					return null;
				}

				// hope we get all these values before GRIP ticks again
				// and targets potentially disappear, which would cause an out
				// of bounds issue.
				double[] coordsY = this.getValArray("centerY");

				double[] heights = this.getValArray("height");
				double[] widths = this.getValArray("width");

				double[] areas = this.getValArray("area");
				double[] solidity = this.getValArray("solidity");

				for (int i = 0; i < (coordsX.length); i++) {
					targets.add(new VisionTarget(coordsX[i], coordsY[i], heights[i], widths[i], areas[i], solidity[i]));
				}
				
				// remove targets in bad positions from the ArrayList				
				for (VisionTarget t : targets) {
					if (t.getMappedY() < -250) { // really, if we're this far down, it's probably not the target we're looking for. filter it out.
						targets.remove(t);
					} else if (t.getMappedX() > 200) { // too far off toward the edge to reasonably be our target (I think)...
													   // effectively cuts off RIGHT 40 PX after remap (with respect to target detection)
						targets.remove(t);
					} else if (t.getMappedX() < -200) { // same as above EXCEPT for LEFT 40 PX
						targets.remove(t);
					}
				}

				// sort by area first using Comparable interface spec (will sort by area, in this case)
				targets.sort(null);
				
				// Apr 8 - Now, since we've removed stuff,
				// we need to check if we have anything...
				// If we don't anymore, return null since that's
				// what we already check for errors with.
				// If we don't do this, the return targets.get(0)
				// will throw an index out of bounds exception.
				
				if (targets.size() < 1) {
					System.out.println("autonomousTargetingGetBestTarget: ERR: All potential targets removed due to auto constraints. Returning null.");
					return null;
				}

				return targets.get(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Caught ArrayIndexOutOfBoundsException. Some targets have disappeared!");
				return null;
			}
		} else {
			System.out.println(unconnectedError);
			return null;
		}
	}
	
	// RIGHT version of centerOffsetThing()
	public double centerOffsetThingRight() {
		VisionTarget t = this.autonomousTargetingGetBestTarget();
		
		if (t == null) {
			System.out.println("cameraOffsetThingRight: No good target. Returning -9999.");
			return -9999;
		}
		
		// The offset is equal to the distance from the center,
		// which happens to be our coordinate after remapping.
		double centerOffsetX = t.getMappedX();
		
		// Return that offset plus half the width of the
		// target to get the total net offset from the right side.
		return (centerOffsetX + (t.getWidth() / 2));
	}
	
	public boolean isAlignedToRight() {
		
		// Change to this.getBestTarget() for non-auto stuff
		VisionTarget t = this.autonomousTargetingGetBestTarget();
		
		if (t == null) {
			System.out.println("isAlignedToLeft: No good target. Returning false.");
			
			// Saying we're aligned to a nonexistent target is silly.
			// So we don't say that.
			return false;
		}
		
		// Apr 8 - Refactored the return to make use of the already
		// existing centerOffsetThing() which does almost exactly
		// what we want already.
		return Math.abs(this.centerOffsetThingRight()) < 15;
	}
	
	public boolean targetDetected() {
		// Return if the best target we can find is in fact a thing
		// and neither nothingness nor an error.
		return !(this.getBestTarget() == null);
	}
	
	public boolean targetDetectedAutonomous() {
		// Return if the best target we can find is in fact a thing
		// and neither nothingness nor an error.
		return !(this.autonomousTargetingGetBestTarget() == null);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}