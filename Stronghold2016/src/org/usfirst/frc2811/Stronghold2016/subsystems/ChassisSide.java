package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class ChassisSide extends PIDSubsystem {

	private SpeedController frontMotor;
	private SpeedController backMotor;
	private Encoder gearboxEncoder;
	
	private double tickRateMax = 800;
	private double tickRateMin = -tickRateMax;
	private boolean oppositeMotors;
	
	private static double P;
	private static double I;
	private static double D;
	
	public ChassisSide(String name, int frontPort, int backPort, int encoderPort1, int encoderPort2, boolean opposingMotors) {
		super(name, P,I,D);
		frontMotor = new Talon(frontPort);
		backMotor = new Talon(backPort);
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

	private double map(double input, double maximum, double minimum, double outputMax, double outputMin){
    	double output = (input/(maximum-minimum)-minimum/(maximum-minimum))*(outputMax-outputMin)+outputMin;
    	if (output==Double.NaN){
    		output=minimum;//Shouldn't happen unless we divide by zero somewhere
    	}
    	return output; 
    }

}
