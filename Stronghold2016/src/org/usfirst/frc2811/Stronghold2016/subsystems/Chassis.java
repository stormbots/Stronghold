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
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Chassis extends Subsystem implements PIDOutput{

    public AHRS navxGyro = new AHRS(SerialPort.Port.kMXP); 
    public PIDController rotationPID;
    
    private Encoder leftEncoder = new Encoder(0,1);
    private Encoder rightEncoder = new Encoder(6,7);
    
    private Solenoid gearShifter = new Solenoid(0, 0);
    
    private SpeedController frontLeftMotor  = new Talon(0);
    private SpeedController frontRightMotor = new Talon(1);
    private SpeedController backLeftMotor   = new Talon(2);
    private SpeedController backRightMotor  = new Talon(3);
    
    private RobotDrive chassisDrive = new RobotDrive(frontLeftMotor, 
    		frontRightMotor, backLeftMotor, backRightMotor);
    
    private double rotateRate;
    private double pVal,iVal,dVal;
    private double tolerance = 3;
    
    public Chassis(double p, double i, double d){
    	pVal=p;
    	iVal=i;
    	dVal=d;
    }
    
    public void initDefaultCommand() {
    	//TODO Find default shifter position
    	gearShifter.set(false);
        
    	chassisDrive.setSafetyEnabled(true);
        chassisDrive.setExpiration(0.1);
        chassisDrive.setSensitivity(0.5);
        chassisDrive.setMaxOutput(1.0);
        
        rotationPID = new PIDController(pVal, iVal, dVal, navxGyro, this);
        rotationPID.setInputRange(-180.0, 180.0);
        rotationPID.setOutputRange(-1.0, 1.0);
        rotationPID.setAbsoluteTolerance(tolerance);
        rotationPID.setContinuous(true);
    }
    
    public void joystickDrive(){
    	chassisDrive.arcadeDrive(Robot.oi.gamePad.getRawAxis(0), Robot.oi.gamePad.getRawAxis(3));
    }
    
    /**
     * Used to drive robot autonomously, by setting arcadeDrive values
     * @param moveValue forward/reverse power, -1 to 1, inclusive
     * @param rotateValue rotation power, -1 to 1 inclusive
     */
    public void manualDrive(double moveValue, double rotateValue){
    	chassisDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void shiftGears(){
    	gearShifter.set(!gearShifter.get());
    }
    
    /** 
     * Might have to be called continuously //TODO test this
     * @param degrees Only set values from -179.9 to 179.9, 0 included. //TODO Requires testing. 
     */
    public void setRotation(double degrees){
    	rotationPID.setSetpoint(degrees);
    	chassisDrive.arcadeDrive(0, rotateRate);
    }
    
    /** 
     * Might have to be called continuously //TODO test this
     * @param forwardPower Relative speed from -1.0 to 1.0 inclusive
     * @param degrees Only set values from -179.9 to 179.9, 0 included. //TODO Requires testing. 
     */
    public void movingAlign(double forwardPower, double degrees){
    	rotationPID.setSetpoint(degrees);
    	chassisDrive.arcadeDrive(forwardPower, rotateRate);
    }
    
    public int getLeftEncoder(){
    	return leftEncoder.get();
    }
    
    public int getRightEncoder(){
    	return rightEncoder.get();
    }
    
    public void resetTicks(){
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    /**
     * @return Whether or not the robot is aligned to an angle (in degrees)
     */
    public boolean isOnTarget(){
    	return Math.abs(rotationPID.getSetpoint()-navxGyro.getAngle())<=tolerance;
    }

	@Override
	public void pidWrite(double output) {
		rotateRate = output;
		
	}
 
}

