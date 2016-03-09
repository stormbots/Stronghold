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

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


/**
 * The Chassis implements a PID System that is controlled by the NavX gyro and compass
 */

public class Chassis extends PIDSubsystem{

    public AHRS navxGyro = new AHRS(SerialPort.Port.kMXP); 
    private Solenoid gearShifter = new Solenoid(0, 0);
    
    public ArcadeDrivePID chassisDrive;
    
    private double tolerance = 1.5;
    private double rotateRate;
    public boolean operatorControl;
       
    public Chassis(){
    	
    	super("GyroPID",.015,.003,.015);
    	System.out.println("Chassis, Statement #" + Robot.counter);
    	Robot.counter++;
    	chassisDrive = new ArcadeDrivePID();
    	System.out.println("Chassis after ArcadeDrivePID, Statement #" + Robot.counter);
    	Robot.counter++;

    	chassisDrive.setSafetyEnabled(true);
        chassisDrive.setExpiration(0.1);
        chassisDrive.setSensitivity(0.5);
        chassisDrive.setMaxOutput(1.0);

    	
    }
    
    public void initDefaultCommand() {

    	//TODO Find default shifter position
    	gearShifter.set(false);
        
        getPIDController().setInputRange(0, 360);
        getPIDController().setOutputRange(-1.0, 1.0);
        getPIDController().setAbsoluteTolerance(tolerance);
        getPIDController().setContinuous(true);

    }
    
    /**
     * Toggles the gear state of the robot Low<->High
     */
    public void shiftGears(){
    	gearShifter.set(!gearShifter.get());
    }
    
    public void setOperatorControl(){
    	operatorControl=true;
    }
    
    public void setCodeControl(){
    	operatorControl=false;
    }
    
    /**
     * Drives using direct joystick reads. The boolean operatorControl must be true for it to work.
     */
    public void joystickDrive(){
    	if(operatorControl) chassisDrive.arcadeDrive(Robot.oi.gamePad.getRawAxis(1), 
    			Robot.oi.gamePad.getRawAxis(2), false);
    }
    
    public void triggerDrive(){
    	if(operatorControl) chassisDrive.arcadeDrive(Robot.oi.triggerMath(), Robot.oi.xBox.getRawAxis(0));
    }
            
    /**
     * Allows for manual setting of movement values   
     * @param forward
     * @param rotate
     */
    public void manualDrive(double forward, double rotate){
   	   	setCodeControl();	
    	chassisDrive.arcadeDrive(forward, rotate, false);
    }
    
    /** 
     * Rotates the robot based on gyro values to a specified degree value
     * Needs to be called continuously
     */
    public void setRotation(double degrees){
    	setCodeControl();
    	getPIDController().enable();
    	setSetpoint(degrees);
    	
    	if(Math.abs(getPIDController().getError())<30){
    		chassisDrive.arcadeDrive(0, rotateRate);
    	} else {
    		chassisDrive.arcadeDrive(0, Math.signum(getPIDController().getError())*.75);
    		getPIDController().reset();
    	}
    }
    
    
    public void resetTicks(){
    	chassisDrive.leftSide.resetSideTicks();
    	chassisDrive.rightSide.resetSideTicks();
    }
    
    public boolean isRobotStable(){
    	return Math.abs(Robot.onboardAccelerometer.getZ()-1)<.1;
    }
        
    /**
     * @return Whether or not the robot is aligned to an angle (in degrees)
     */
    public boolean isOnTarget(){
    	System.out.println("Target Angle"+getSetpoint());
    	System.out.println("Actual Angle"+navxGyro.getAngle());
    	return Math.abs(getSetpoint()-navxGyro.getAngle())<=tolerance;
    }
    
    public void resetPID(){
    	getPIDController().reset();
    	getPIDController().enable();
    }
    
    public double getError(){
    	return getPIDController().getError();
    }
    
    public double getPIDOutput(){
    	return getPIDController().get();
    }
    
    public double getSetpoint(){
    	return getPIDController().getSetpoint();
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return navxGyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		rotateRate=output;
		
		
	}

}

