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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem {
	
	/**
	 * NetworkTables handle to the NetworkTable GRIP will push values to
	 */
	private NetworkTable grip;
	
	/**
	 * Handle to the GRIP process
	 */
	Process gripProcess;
	
	/**
	 * Name of the NetworkTable that GRIP will push values to
	 */
	private String tableName;
	
	/**
	 * Default/fallback/failure value for multi-number NetworkTables data types
	 */
	private double[] defaultValue = new double[0];
	
	/**
	 * Default/fallback/failure value for single number NetworkTables data types
	 */
	private double defaultSingleValue = 0;
	
	/**
	 * Error message to display if the GRIP NetworkTable does not exist or cannot be connected to
	 */
	private String unconnectedError = "VISION: Error -> NT not connected!";
	
	
	/**
	 * Height of camera off the ground
	 */
	private double cameraHeight = 0;
	
	/**
	 * Distance left or right from the shooter to the camera
	 * Camera right of shooter: Positive value
	 * Camera left of shooter: Negative value 
	 */
	private double cameraOffsetDistanceX = 0;
	
	/**
	 * Distance up or down from the shooter to the camera
	 * Camera above shooter: Positive value
	 * Camera below shooter: Negative value
	 */
	private double cameraOffsetDistanceY = 0;
	
	/**
	 * Camera angle rel to vertical plane
	 * TODO: Get better measurement. Halfway between 35 and 45 for now.
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
	 * @param networkTableName Network Table name for GRIP to post values to
	 * @param offsetX Left/Right offset of camera, in feet, relative to shooter. left: < 0; right > 0
	 * @param offsetY Up/Down offset of camera, in feet, relative to shooter. down: < 0; up > 0
	 * @param cameraAngleOffset Angular offset of camera... how many degrees upward are we looking by default?
	 * @param diagonalFieldOfView The diagonal field of view (in degrees) of the camera used by the subsystem
	 * @param cameraPixelsX Number of horizontal pixels provided by the camera used by the subsystem
	 * @param cameraPixelsY Number of vertical pixels provided by the camera used by the subsystem
	 */
	public Vision(String networkTableName,
				  double cameraHeight, double offsetX, double offsetY, double cameraOffsetAngleY, // Physical attributes of the camera
				  double diagonalFieldOfView, int cameraPixelsX, int cameraPixelsY){ // Technical attributes of camera
		this.tableName = networkTableName;
		
		this.cameraHeight = cameraHeight;
		this.cameraOffsetDistanceX = offsetX;
		this.cameraOffsetDistanceY = offsetY;
		this.cameraOffsetAngleY = cameraOffsetAngleY;
		
		this.diagonalFieldOfView = diagonalFieldOfView;
		this.cameraPixelsX = cameraPixelsX;
		this.cameraPixelsY = cameraPixelsY;

		//this.cameraInit();

		// These may need to be reversed?
		this.netTableInit();
		this.gripInit();
	}

	/**
	 * Initialize the tableName NetworkTable for vision values
	 */
	public void netTableInit() {
		// getTable initializes tableName when it doesn't already exist
		grip = NetworkTable.getTable(tableName);
		
		System.out.println("VISION: NT Connected: " + grip.isConnected());
	}
	
	/**
	 * Start the GRIP process
	 */
	public void gripInit() { //initializes grip on the roborio
		System.out.println("Attempting to start/restart Grip");
		
		boolean gripIsRunning = false;
		
		//Attempt to detect already running GRIP Process
		try {
			String process;
			// getRuntime: Returns the runtime object associated with the current Java application.
			// exec: Executes the specified string command in a separate process.
			Process p = Runtime.getRuntime().exec("ps -a | grep '*[p]roject.*' | awk '{print $1}'");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((process = input.readLine()) != null) {
				if (process.indexOf("project.grip") != -1) {
					int grippid = Integer.parseInt(process.substring(1, process.indexOf(" ",1)));
					System.out.println("PROCESS FOUND!!!!!!!!!!! " + grippid); // <-- Print all Process here line by line
					gripIsRunning = true;
				}
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}

		if (!gripIsRunning) { 
			try {
				System.out.println("GRIP not running -- starting");
				//				 prep to execute a new java program (GRIP) from the GRIP jarfile		
				gripProcess = new ProcessBuilder("/usr/local/frc/JRE/bin/java", "-jar", "/home/lvuser/grip.jar",
						// with our GRIP project file, the parent IO stream, and finally start the process
	    				"/home/lvuser/project.grip").start();
	    		//TODO add print statement to make sure things work right
				System.out.println("Grip successfully started");
	    		
	    	} catch (IOException e) {
	    		// The GRIP jarfile is not there, our project file is not there, or something else went horribly wrong.
	    		System.out.println("Exception! Grip could not be started");
	    		e.printStackTrace();
	    	}
		
		}
	}
	
	/**
	 * Kill the GRIP process
	 */
	public void gripStop() {
		gripProcess.destroyForcibly();
	}
	
	
	/**
	 * @return If the handle to GRIP's NetworkTable is valid and functioning
	 */
	private boolean tableConnected() {
		if(grip.isConnected()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param tableKey The key on the GRIP NetworkTable to get values from
	 * @return Values in tableKey on the GRIP NetworkTable
	 */
	public double[] getValArray(String tableKey){ //can be used to get number array values such as area
		if (this.tableConnected()) {
			return grip.getNumberArray(tableKey, defaultValue);
		} else {
			System.out.println(unconnectedError);
			return defaultValue;
		}
	}
	
	/*
	 * Get X part of coordinate
	 * @return double the x coord
	 */
	public double getTargetCoordY() {
		if (this.tableConnected()) {
			
			if (grip.getNumberArray("centerX", defaultValue).length < 1) {
				System.out.println("NO TARGET FOUND. RETURNING -9999.");
				return -9999;
			}
			
			double[] coordinates = grip.getNumberArray("centerX", defaultValue);
			System.out.println("Pre-Map targetCoordY result: " + coordinates[0]);
			
			System.out.println("Post-Map targetCoordY result: " + (coordinates[0] - this.cameraPixelsX/2));
			
			// GRIP maps (0,0) to top left
			// We want to remap the coordinate to be relative to center=(0,0)
			
			return (this.cameraPixelsX - coordinates[0]) - this.cameraPixelsX/2;
		} else {
			System.out.println(unconnectedError);
			return defaultSingleValue;
		}
	}
	
	/**
	 * Returns the Y part of the target coordinate
	 * @return the Y part of the target center coordinate
	 */
	public double getTargetCoordX() {
		if (this.tableConnected()) {
			//double coordinate = grip.getNumber("centerY", defaultSingleValue);
			double[] coordinate = grip.getNumberArray("centerY", defaultValue);
			if (coordinate.length==0){
				System.out.println("NO TARGET FOUND. RETURNING -9999.");
				return -9999;
			}
			
			System.out.println("getTargetCoordX result: " + coordinate[0]);
			
			// remap from GRIP pixel mapping to center-based pixel mapping
			
			System.out.println("-(" + coordinate[0] + " - " + this.cameraPixelsY/2 + ")");
			return -(coordinate[0] - this.cameraPixelsY/2); //return first value of centerY arrays
		} else {
			System.out.println(unconnectedError);
			return defaultSingleValue;
		}
	}
	
	/**
	 * @return Horizontal distance to target
	 */
	public double getDistanceToTarget() {
		double angleToTarget = this.getYAngleToTarget();
		
		// goal center height is 8'1". Subtract the camera height to get the height difference (opp in trig)
		// this height is the distance from the ground to the middle of the target/goal
		double goalHeight = 8.0 + (double)1/12.0 - this.cameraHeight;
		
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
		double horizontalAngleOffset = this.getXAngleToTarget();
		
		/* Return true if the robot needs to turn less than angleTolerance degrees to be aligned
		 * with the target
		 */
		return Math.abs(horizontalAngleOffset) <= angleTolerance;
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
		
		double objectPositionX = this.getTargetCoordX();
		
		double[] FOVs = this.diagonalFieldOfViewToXYFieldOfView();
		
		/* Take the pixel offsets of the object from the center of the image
		 * Multiply by half of the FOV per pixel on that axis because we have FOV/2 field of view
		 * in each direction on that axis.
		 */
		double angleOffsetX = (objectPositionX - (this.cameraPixelsX/2.0)) * (FOVs[0]/2/this.cameraPixelsX);

		return angleOffsetX;
	}
	
	/**
	 * Automatically returns the vertical angle of the target (perpendicular to the plane of the field), based on values in the NetworkTable
	 * @return double angleVertical. Down - negative; up - positive
	 * We should *NEVER* get a negative value, because that means the robot (or camera) is above the goal, which
	 * means it's probably flying.
	 */
	public double getYAngleToTarget() {
		
		double objectPositionY = this.getTargetCoordY();
		
		double[] FOVs = this.diagonalFieldOfViewToXYFieldOfView();
		
		/* Take the pixel offsets of the object from the center of the image
		 * Multiply by half of the FOV per pixel on that axis because we have FOV/2 field of view
		 * in each direction on that axis.
		 */
		double angleOffsetY = (objectPositionY - (this.cameraPixelsY/2.0)) * (FOVs[1]/2/this.cameraPixelsY);
		
		return angleOffsetY + this.cameraOffsetAngleY;
	}
	
	/**
	 * @return IN THEORY, the true X (horizontal) offset in feet from the shooter to the target.
	 * <br>GUIDE: greater than 0: need to drive rightward || less than 0: need to drive leftward
	 */
	public double getRealXOffsetToTarget() {
		double angleToTarget = this.getXAngleToTarget();
		
		System.out.println("getRealXOffsetToTarget -> angleToTarget = " + angleToTarget);
		
		double distanceToTarget = this.getDistanceToTarget();
		
		System.out.println("getRealXOffsetToTarget -> distanceToTarget = " + distanceToTarget);
		
		//tan(angleToTarget) = offsetToCamera / distanceToTarget
		//offsetToCamera = distanceToTarget * tan(angleToTarget)
		
		// We're constructing a right triangle on a plane parallel to the field
		// With (usually) long leg distanceToTarget and an adjacent angle derived from
		// camera FOV. Math.tan() helps work out the ratio to get the small leg length.
		double offsetXToCamera = distanceToTarget * Math.toDegrees(Math.tan(angleToTarget));
		
		// If the target is to the left, the offset increases our net X offset
		// Otherwise, it decreases our offset if the target is to the right.
		if (angleToTarget < 0) {
			// the negative sign denotes that movement leftward is required
			return -(Math.abs(offsetXToCamera) + this.cameraOffsetDistanceX);
		} else {
			return Math.abs(offsetXToCamera) - this.cameraOffsetDistanceX;
		}
	}
	
	/**
	 * @return IN THEORY, the true distance from the shooter to the target
	 */
	public double getRealDistanceToTarget() {
		// Should be as simple as this since these distances are in parallel directions
		return this.getDistanceToTarget() + this.cameraOffsetDistanceY;
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