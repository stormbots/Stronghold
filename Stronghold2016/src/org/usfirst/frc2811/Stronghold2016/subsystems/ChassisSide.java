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
	
	private static double P;
	private static double I;
	private static double D;
	
	private double rotationRate;
	
	
	public ChassisSide(String name, int frontPort, int backPort, int encoderPort1, int encoderPort2) {
		super(name, P,I,D);
		frontMotor = new Talon(frontPort);
		backMotor = new Talon(backPort);
		
		// TODO Auto-generated constructor stub
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		getPIDController().enable();
		getPIDController().setOutputRange(-1, 1);
		getPIDController().setContinuous(false);
	}
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return map(gearboxEncoder.getRate(),tickRateMax,tickRateMin,1,-1);
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		rotationRate=output;
		
	}

	protected double map( double input, double maximum, double minimum, double outputMax, double outputMin){
    	double output = (input/(maximum-minimum)-minimum/(maximum-minimum))*(outputMax-outputMin)+outputMin;
    	if (output==Double.NaN){
    		output=minimum;//Shouldn't happen unless we divide by zero somewhere
    		}
    	return output; 
    }

}
