package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class ChassisRight extends PIDSubsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//TODO: These ideally would be private
	public  SpeedController frontRightMotor  = new Talon(1);
	public SpeedController backRightMotor   = new Talon(3);
	 
	public Encoder rightEncoder = new Encoder(2,3);

    public ChassisRight(double p, double i, double d) {
		super("RightPID", p, i, d);
		// TODO Auto-generated constructor stub
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		getPIDController().setOutputRange(-1, 1);
	    getPIDController().setContinuous(false);
	    getPIDController().enable();
	    rightEncoder.setReverseDirection(true);
	    //doesn't cause a racket, wrong
	    //frontRightMotor.setInverted(true);
	    //backRightMotor.setInverted(true);
	    
	    //Causes racket
	    //frontRightMotor.setInverted(false);
	    //backRightMotor.setInverted(false);
	    //NOTE: This code should be in the constructor. 
	    //This function has no guarantees that it's only run a single time.
    }
	 
	public void driveRate(double rate){
		setSetpoint(rate);
	}
    
	public int getRightDistance(){
	   	return rightEncoder.get();
	}
	
	public double getRightRate(){
		return rightEncoder.getRate();
	}
	
    public void resetTicksRight(){
    	rightEncoder.reset();
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return rightEncoder.getRate();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		//TODO: Ask supervisor if we need to constrain output, and to what value
		frontRightMotor.set(output);
		backRightMotor.set(output);
		System.out.println("Chassis Right: " + this.getPIDController().getError());
	}
}

