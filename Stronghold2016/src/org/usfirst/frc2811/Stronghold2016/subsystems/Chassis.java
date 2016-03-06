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
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


/**
 *
 */
public class Chassis extends PIDSubsystem{

    //public AHRS navxGyro = new AHRS(SerialPort.Port.kMXP); 

    public PIDController rotationPID;
    
    private Encoder leftEncoder = new Encoder(0,1);
    private Encoder rightEncoder = new Encoder(6,7);
    
    private Solenoid gearShifter = new Solenoid(0, 0);
    
    private ChassisLeft	 left 	= new ChassisLeft(0, 0, 0);
    private ChassisRight right 	= new ChassisRight(0, 0, 0);
    
    /*
    private RobotDrive chassisDrive = new RobotDrive(left.frontLeftMotor, 
    		right.frontRightMotor, left.backLeftMotor, right.backRightMotor);
    */
    private ArcadeDrivePID chassisDrive = new ArcadeDrivePID(left, right);

    private double tolerance = 3;
    private double rotateRate;
    private double rateTolerance;
    
    public Chassis(double p, double i, double d){
    	super("GyroPID",p,i,d);
    }
    
    public void initDefaultCommand() {
    	//TODO Find default shifter position
    	gearShifter.set(false);
        
    	chassisDrive.setSafetyEnabled(true);
        chassisDrive.setExpiration(0.1);
        chassisDrive.setSensitivity(0.5);
        chassisDrive.setMaxOutput(1.0);

        getPIDController().setInputRange(-180.0, 180.0);
        getPIDController().setOutputRange(-1.0, 1.0);
        getPIDController().setAbsoluteTolerance(tolerance);
        getPIDController().setContinuous(true);
    }
    
    public void shiftGears(){
    	gearShifter.set(!gearShifter.get());
    }
    
    public void joystickDrive(){
    	chassisDrive.arcadeDrive(Robot.oi.gamePad.getRawAxis(0), Robot.oi.gamePad.getRawAxis(3), false);
    }
    
    /** 
     * Might have to be called continuously //TODO test continuous calling
     * @param degrees Only set values from -179.9 to 179.9, 0 included. //TODO Requires testing of 180 deg. 
     */
    public void setRotation(double degrees){
    	setSetpoint(degrees);
    	chassisDrive.arcadeDrive(0, rotateRate);
    }
    
    public void driveRate(double rate){
    	left.driveRate(rate);
    	right.driveRate(rate);
    }
    
    public void adjustForErrorDrive(){
    	//FIXME TODO FIXME TODO CHECK THE GETTER FUNCTIONS!!!!!
    	if(Math.abs(left.getLeftRate()-right.getRightRate())>=rateTolerance){
    		
    		if(left.getLeftRate()<right.getRightRate()){
    			right.driveRate(left.getLeftRate());
    		} else {
    			left.driveRate(right.getRightRate());
    		}
    		
    	} else if((left.getPIDController().get() == 1 && left.getLeftRate()<right.getRightRate())){
    		
    		if(left.getLeftRate()<right.getRightRate()){
    			right.driveRate(left.getLeftRate());
    		} else {
    			left.driveRate(right.getRightRate());
    		}
    		
    	} else if((right.getPIDController().get()==1&&right.getRightRate()<left.getLeftRate())){
    			
    		if(left.getLeftRate()<right.getRightRate()){
    			right.driveRate(left.getLeftRate());
    		} else {
    			left.driveRate(right.getRightRate());
    		}   	
    	
    	}
    }
    
    public double getLeftRate(){
    	return left.getLeftRate();
    }
    
    public int getLeftDistance(){
    	return left.getLeftDistance();
    }
    
    public double getRightRate(){
    	return right.getRightRate();
    }
    
    public int getRightDistance(){
    	return right.getRightDistance();
    }
    
    public void resetTicks(){
    	left.resetTicksLeft();
    	right.resetTicksRight();
    }
    
    /**
     * @return Whether or not the robot is aligned to an angle (in degrees)
     */
    public boolean isOnTarget(){
    	return true;
    	// Math.abs(getSetpoint()-navxGyro.getAngle())<=tolerance;
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
				//navxGyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		rotateRate=output;
		
		
	}
 
}

