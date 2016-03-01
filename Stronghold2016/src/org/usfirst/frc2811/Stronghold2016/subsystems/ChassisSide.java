package org.usfirst.frc2811.Stronghold2016.subsystems;

import org.usfirst.frc2811.Stronghold2016.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class ChassisSide extends PIDSubsystem {

	private SpeedController frontMotor;
	private SpeedController backMotor;
	private Encoder gearboxEncoder;
	
	private static double tickRateMax = 800;
	private static double tickRateMin = -tickRateMax;
	
	private static double rateTolerance = .01;
	
	private boolean oppositeMotors;
	
	private static double P=.5;
	private static double I=0.0001;
	private static double D=0.0001;
	
	public ChassisSide(String name, SpeedController front, SpeedController back, Encoder wheelEncoder, boolean opposingMotors) {
		super(name, P,I,D);
		System.out.println("ChassisSide " + name + ", " + "Statement #" + RobotMap.counter);
		RobotMap.counter++;
		frontMotor = front;
		backMotor = back;
		gearboxEncoder = wheelEncoder;
		oppositeMotors=opposingMotors;

	}

	protected void initDefaultCommand() {

		getPIDController().enable();
		getPIDController().setOutputRange(-1, 1);
		getPIDController().setContinuous(false);
		frontMotor.setInverted(false);
		backMotor.setInverted(oppositeMotors);
	}
	
		
	public void driveRate(double rate){
		setSetpoint(rate);
	}
	
	
	public int getSideDistance(){
	   	return gearboxEncoder.get();
	}
	
	/**
	 * 
	 * @return Rate from -1 to 1 inclusive
	 */
	public double getSideRate(){
		return map(gearboxEncoder.getRate(),tickRateMax,tickRateMin,1,-1);
	}
	
	/**
	 * Resets ticks traveled
	 */
	public void resetSideTicks(){
    	gearboxEncoder.reset();
    }
	
	public SpeedController getFront(){
		return frontMotor;
	}
	
	public SpeedController getBack(){
		return backMotor;
	}
		
	@Override
	protected double returnPIDInput(){
		return map(gearboxEncoder.getRate(),tickRateMax,tickRateMin,1,-1);
	}

	@Override
	protected void usePIDOutput(double output) {

		frontMotor.set(output);
		backMotor.set(output);
		
	}

	private static double map(double input, double maximum, double minimum, double outputMax, double outputMin){
    	double output = (input/(maximum-minimum)-minimum/(maximum-minimum))*(outputMax-outputMin)+outputMin;
    	if (output==Double.NaN){
    		output=minimum;//Shouldn't happen unless we divide by zero somewhere
    	}
    	return output; 
    }

}
