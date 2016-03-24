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

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Shooter tops out with getEncVelocity() of  ~53000. 
 * Tested speed for shooting a high goal from the batter is rate of 38176 ticks 

 */
public class Shooter extends Subsystem {
	
	public ShooterWheelPID shooterWheelPID;
 
    // Initialize your subsystem here
    public Shooter() {
         // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	
    	shooterWheelPID = new ShooterWheelPID();
    }

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double distanceToFPS(double distance){
    	double g = -32.174;//32 feet per second
    	double y = 7.7;//TODO double check target height
    	double x = distance;// FIXME get the distance to goal in x direction from camera in feet
    	if(distance<5){
    		System.out.println("Distance too short! Stopping");
    		return 0;
    	}

    	///*
    	double theta = Math.toRadians(62.5);//TODO remeasure on physical robot
		System.out.println("Theta radians" +Math.tan(theta));

    	double radicand=(2*(y-Math.tan(theta)*x)/g);
		System.out.println("Radicand: " +radicand);
		
    	double time = Math.sqrt(radicand);
    	System.out.println("time: " +time);
		
    	double feetPerSecond = (x/time)/Math.cos(theta);
    	System.out.println("feet per second: " +feetPerSecond);
    	
		return feetPerSecond;
		//*/
    	
    	
    	/*
    	Right Monstrosity goes here
    	
    					x
    	   ______________________________
    	    ________________________
    	   /	2(y-xtan(theta))
    	  /____________________
    	 /			gravity
    	V______________________________
    					cos(theta)
    	*/
    	
    	//DO math to convert distance to velocity
    	
    }
    /**
     * This Converts the expected velocity that is required to shoot the ball to the goal x feet away. It goes from 
     * velocity divided by wheel radius, then multiplied by 4096 ticks times 100 per revolution, then divided by 1000
     * @param feetPerSecond
     * @return Ticks Per 10 Milliseconds
     */
    private double FPSToTicksPer10MS(double feetPerSecond){
    	double wheelRadius=1/6.0;//TODO Change this value to correct wheel radius
    	double TicksPer50MS = (feetPerSecond*4096*100)/(wheelRadius*1000.0);
    	
    	System.out.println("tickRate: " +TicksPer50MS);

    	//Get Velocity from shooter
    	//TODO do math to convert velocity from feet per second to ticks per second
    	return TicksPer50MS;
    }
    
    /*
 private void setShooterRateManually(double tickRotation){
	 System.out.println("Manual write! "+tickRotation+" ticks per rate");
	 shooterWheelPID.setMotorVelocity(tickRotation);
 }
 */
 
 public void setShooterDistance(double distanceToTarget){
	//convert distance to required FPS
 	//convert FPS to required encoder ticks per second
	double calculatedoutput = FPSToTicksPer10MS(distanceToFPS(distanceToTarget));

	 System.out.println("Feet Per second:"+ distanceToFPS(distanceToTarget));
	 //System.out.printlin("" + )
	 System.out.println("Calculated output:"+ calculatedoutput);

	 //Plan for fudging the numbers
	 double fudgesquared=0;
	 double fudgelinear=0;
	 double fudgeconstant=0;
	 
	 double output=calculatedoutput + (fudgesquared*fudgesquared) + (fudgelinear) +(fudgeconstant);
	 
	 //write to motor
	 shooterWheelPID.setMotorVelocity(output);
	 //DONE!?
	 
    }
 	
    
    
    
    protected double map( double input, double maximum, double minimum, double outputMax, double outputMin){
    	double output = (input/(maximum-minimum)-minimum/(maximum-minimum))*(outputMax-outputMin)+outputMin;
    	if (output==Double.NaN){
    		output=minimum;//Shouldn't happen unless we divide by zero somewhere
    		}
    	return output; 
    }
    
}
