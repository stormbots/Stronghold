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

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	
    //private static Encoder shooterQuadratureEncoderLeft = new Encoder(4, 5, false, EncodingType.k4X);
    //private static Encoder shooterQuadratureEncoderRight = new Encoder(2, 3, false, EncodingType.k4X);
    //private static CANTalon shooterCANTalonLeft = new CANTalon(0);
    //private static CANTalon shooterCANTalonRight = new CANTalon(1);
    

  
   
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // Initialize your subsystem here
    public Shooter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        
        //shooterQuadratureEncoderLeft.setDistancePerPulse(1.0);
        //shooterQuadratureEncoderLeft.setPIDSourceType(PIDSourceType.kRate);
        //shooterQuadratureEncoderRight.setDistancePerPulse(1.0);
        //shooterQuadratureEncoderRight.setPIDSourceType(PIDSourceType.kRate);
        
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
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
    	double theta = Math.toRadians(60);//TODO remeasure on physical robot
    	double time;
    	time = Math.sqrt(((y-Math.tan(theta)*x)*2/g));
    	double feetPerSecond = (x/time)/Math.cos(theta);//TODO Make Changes in math in the code
    	/*
    	GIANT DERIVED MONSTROSITY IS HERE
    	                          ___________________________________________________________________________
    	                         / [						] 2			   [							]2
    	                        /  | 		  X				| 			   |		tan(theta)*x		|	
    	                       /   |  ___________________	|			   |  _______________________	|
    	                      /	   |    _________________	|       +      |    __________________		|
    	                     /	   |   / 2y-2tan(theta)*x	|			   |   / 2y-2tan(theta)*x		|
    	                    /	   |  /  ________________   |			   |  /  _________________		|
    	                   /	   | /		 gravity		|			   | /		 gravity			|
    	feetPerSecond =   /		   |V						|			   |V							|
    	                 V		   [						]			   [ 							]
    	
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
    	return feetPerSecond;
    	//DO math to convert distance to velocity
    	
    }
    /**
     * This Converts the expected velocity that is required to shoot the ball to the goal x feet away. It goes from 
     * velocity divided by wheel radius, then multiplied by 4096 ticks times 100 per revolution, then divided by 1000
     * @param feetPerSecond
     * @return Ticks Per 10 Milliseconds
     */
    private double FPSToTicksPer50MS(double feetPerSecond){
    	double wheelRadius=1;//TODO Change this value to correct wheel radius
    	double TicksPer50MS = (feetPerSecond*4096*100)/(wheelRadius*1000);
    	
    	
    	//Get Velocity from shooter
    	//TODO do math to convert velocity from feet per second to ticks per second
    	return TicksPer50MS;
    }
    
    
 public void setShooterDistance(double distanceToTarget){
	//convert distance to required FPS
 	//convert FPS to required encoder ticks per second
	double calculatedoutput = FPSToTicksPer50MS(distanceToFPS(distanceToTarget));
	 
	 //Plan for fudging the numbers
	 double fudgesquared=0;
	 double fudgelinear=0;
	 double fudgeconstant=0;
	 
	 double output=calculatedoutput + (fudgesquared*fudgesquared) + (fudgelinear) +(fudgeconstant);
	 
	 //write to motor
	 Robot.shooterWheelPID.setMotorVelocity(output);
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
