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
	
	public SpeedController frontRightMotor  = new Talon(1);
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
	    
	    backRightMotor.setInverted(true);
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
		frontRightMotor.set(output);
		backRightMotor.set(output);		
	}
}

