package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class ChassisLeft extends PIDSubsystem{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public SpeedController frontLeftMotor  = new Talon(0);
	public SpeedController backLeftMotor   = new Talon(2);
	
	public Encoder leftEncoder = new Encoder(0,1);

	public ChassisLeft(double p, double i, double d){
	  	super("LeftPID", p,i,d);
	}
	 
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		getPIDController().setOutputRange(-1, 1);
	    getPIDController().setContinuous(false);
	    getPIDController().enable();
	    
	    backLeftMotor.setInverted(true);
    }
	
	public void driveRate(double rate){
		setSetpoint(rate);
	}
	 
	public int getLeftDistance(){
	   	return leftEncoder.get();
	}
	
	public double getLeftRate(){
		return leftEncoder.getRate();
	}
	
    public void resetTicksLeft(){
    	leftEncoder.reset();
    }
    
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return leftEncoder.getRate();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		frontLeftMotor.set(output);
		backLeftMotor.set(output);
		
	}
}

