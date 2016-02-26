package org.usfirst.frc2811.Stronghold2016;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class RobotMap {
	
	public static SpeedController frontLeft;
	public static SpeedController backLeft;
	public static SpeedController frontRight;
	public static SpeedController backRight;
	public static Encoder leftEncoder;
	public static Encoder rightEncoder;
	
	public void init() {
		 
		frontLeft = new Talon(0);
		backLeft = new Talon(1);
	    frontRight = new Talon(2);
	    backRight = new Talon(3);
		
	    leftEncoder = new Encoder(0,1);
		leftEncoder.setDistancePerPulse(1);
		
		
		rightEncoder = new Encoder(2,3);
		rightEncoder.setDistancePerPulse(1);

		
		

		//TODO Calibrate PID values
		    
		    /*
		    public RobotDrive chassisDrive = new RobotDrive(left.frontLeftMotor, 
		    		right.frontRightMotor, left.backLeftMotor, right.backRightMotor);
		    */
		   
	 }

}
