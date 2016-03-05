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

 //import com.kauailabs.navx.frc.AHRS;

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
public class Chassis extends Subsystem{


    private Encoder leftEncoder = new Encoder(0,1);
    private Encoder rightEncoder = new Encoder(12,13);
    
    private Solenoid gearShifter = new Solenoid(0, 0);
    
    private SpeedController frontLeftMotor  = new Talon(0);
    private SpeedController frontRightMotor = new Talon(1);
    private SpeedController backLeftMotor   = new Talon(2);
    private SpeedController backRightMotor  = new Talon(3);
    
    private RobotDrive chassisDrive = new RobotDrive(frontLeftMotor, 
    		frontRightMotor, backLeftMotor, backRightMotor);


    public void initDefaultCommand() {
    	//TODO Find default shifter position
    	gearShifter.set(false);
        
    	chassisDrive.setSafetyEnabled(true);
        chassisDrive.setExpiration(0.1);
        chassisDrive.setSensitivity(0.5);
        chassisDrive.setMaxOutput(1.0);
        
 //       rotationPID = new PIDController(pVal, iVal, dVal, navxGyro, this);
        /*rotationPID.setInputRange(-180.0, 180.0);
        rotationPID.setOutputRange(-1.0, 1.0);
        rotationPID.setAbsoluteTolerance(tolerance);
        rotationPID.setContinuous(true);
    
    */
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
    
}

