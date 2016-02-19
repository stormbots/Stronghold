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

import java.io.IOException;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


/**
 * 
 */
public class Vision extends Subsystem {
	
	/**
	 * NetworkTables handle to the NetworkTable GRIP will push values to
	 */
	private NetworkTable grip;
	
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
	 * Initialize the Vision subsystem
	 * 
	 * @param networkTableName Network Table name for GRIP to post values to
	 * @param offsetX Left/Right offset of camera, in feet, relative to shooter. left: < 0; right > 0
	 * @param offsetY Up/Down offset of camera, in feet, relative to shooter. down: < 0; up > 0
	 */
	public Vision(String networkTableName, double offsetX, double offsetY) {
	//initial set up code	
		tableName = networkTableName; //name of the network table. Ex: "GRIP/myContoursReport"
		cameraOffsetDistanceX = offsetX;
		cameraOffsetDistanceY = offsetY;

		//this.cameraInit();

		// These may need to be reversed?
		this.netTableInit();
		this.gripInit();
	}

	// TODO: breaks
	/**
	 * Initialize the camera for the NIVision framework. Primarily used to send images to the DS/SmartDashboard.
	 * Do not run this unless you want to break GRIP, or unless you've fixed <b><i><u>the problem</u></i></b>.
	 */
	public void cameraInit() {
		Image frame;
		int session;
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
	}
	
	/**
	 * Initialize the tableName NetworkTable for vision values
	 */
	public void netTableInit() {
		grip = NetworkTable.getTable(tableName);
		System.out.println("VISION: NT Connected: " + grip.isConnected());
	}
	
	/**
	 * Start the GRIP process
	 */
	public void gripInit() { //initializes grip on the roborio
		try {
    		new ProcessBuilder("/usr/local/frc/JRE/bin/java", "-jar", "/home/lvuser/grip.jar",
    				"/home/lvuser/project.grip").inheritIO().start();
    		//TODO add print statement to make sure things work right
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
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
	
	/**
	 * @return Coordinate of the center of the target
	 */
	public double[] getTargetCoordinate() { //returns (x, y) coordinate 
		if(this.tableConnected()) {
			double getX = grip.getNumber("centerX", defaultSingleValue);
			double getY = grip.getNumber("centerY", defaultSingleValue);
			double[] centerXY = {getX, getY};
			return centerXY;
		} else {
			System.out.println(unconnectedError);
			return defaultValue;
		}
	}
	
	/**
	 * Get the angle between the shooter and the target
	 * @return [TEMP VALUE IS WRONG] The angle (with respect to robot rotation on the ground) between the shooter and the target
	 */
	public double getAngleToTarget() {		
		// TODO: Set this correctly
		return 9999.0;
	}
	
	/**
	 * @return Horizontal distance to target
	 */
	public double getDistanceToTarget() {
		//TODO: get angle from getAngleToTarget method. needs data points to acc for camera FOV
		double angleToTarget = 60.0;
		
		//TODO: Figure out robot/shooter height (origin of shot)
		int robotHeight = 2;
		
		// goal center height is 8'1". Subtract the robot height to get the height difference (opp in trig)
		// this height is the distance from the ground to the middle of the target/goal
		double goalHeight = 8.0 + (double)1/12 - robotHeight;
		
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
	 *
	 * @param angleTolerance Tolerance, in degrees, within which the actual angle can differ from "perfect" alignment
	 * @return Whether or not the robot/shooter is aligned with the target, +- angleTolerance degrees.
	 */
	public boolean isOnTarget(double angleTolerance) {
		double calculatedAngle = this.getAngleToTarget();
		
		// Return true if the robot needs to turn less than angleTolerance degrees to be aligned
		// with the target
		return Math.abs(calculatedAngle) <= angleTolerance;
	}
	
	/**
	 * 
	 * @param dfov diagonal fov of the camera (68.5 degrees for the lifecam)
	 * @return an array containing {horizontal fov, vertical fov}
	 */
	private static double[] diagonalToVerticalFOV(double dfov){
		int cameraPixelsX = 1280;
		int cameraPixelsY = 720;
		
		
		
		//double diagonalLengthPixels = Math.sqrt(Math.pow(cameraPixelsX, 2) + Math.pow(cameraPixelsY, 2));
		
		double llAngle = Math.toDegrees(Math.atan((double)cameraPixelsY/cameraPixelsX));
		
		double fovX = dfov * Math.cos(Math.toRadians(llAngle));
		double fovY = dfov * Math.sin(Math.toRadians(llAngle));
		
		System.out.println(fovX + " " + fovY);
		double[] fovArray = {fovX, fovY};
		return fovArray;
		
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