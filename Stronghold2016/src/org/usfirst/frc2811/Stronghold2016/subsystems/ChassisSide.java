package org.usfirst.frc2811.Stronghold2016.subsystems;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class ChassisSide extends PIDSubsystem {

	private SpeedController frontMotor;
	private SpeedController backMotor;
	private Encoder gearboxEncoder;
	
	private static double tickRateMax = 12000;
	private static double tickRateMin = -tickRateMax;
	
	private static double rateTolerance = .01;
	
	private boolean oppositeMotors;
	
	private static double P=1;
	private static double I=0.0001;
	private static double D=0.01;
	
	public ChassisSide(String name, SpeedController front, SpeedController back, Encoder wheelEncoder, boolean opposingMotors) {
		super(name, P,I,D);
		System.out.println("ChassisSide " + name + ", " + "Statement #" + Robot.counter);
		Robot.counter++;
		frontMotor = front;
		backMotor = back;
		gearboxEncoder = wheelEncoder;
		oppositeMotors=opposingMotors;

	}

	protected void initDefaultCommand() {

		getPIDController().enable();
		getPIDController().setInputRange(-1, 1);
		getPIDController().setOutputRange(-1, 1);
		getPIDController().setContinuous(false);
		//getPIDController().getAvgError()>.1&& targetSpeed~=-0; then reset();
		frontMotor.setInverted(false);
		backMotor.setInverted(oppositeMotors);
	}
	
		
	public void driveRate(double rate){
		if(Math.abs(rate)<.1){
			getPIDController().reset();
			getPIDController().enable();
			setSetpoint(0);
		} else {
			setSetpoint(rate);
		}

		//frontMotor.set(rate);
		//backMotor.set(rate);
	}
	
	
	public int getSideDistance(){
	   	return gearboxEncoder.get();
	}
	
	
	/**
	 * @return Motor values written by the PIDController (-1 to 1 inclusive)
	 */
	public double getSideSetpoint(){
		return getPIDController().get();
	}
	
	/**
	 * @return Ticks per second mapped to -1 to 1 inclusive
	 */
	public double getSideEncoderRate(){
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
		double maxcurrent=40;
		if(Robot.powerPanel.getCurrent(0)>maxcurrent||
			Robot.powerPanel.getCurrent(1)>maxcurrent||
		   	Robot.powerPanel.getCurrent(2)>maxcurrent||
		   	Robot.powerPanel.getCurrent(3)>maxcurrent){
				setSetpoint(getPIDController().getSetpoint()/2);
				System.err.println("Drawing too much power!");
				System.err.println("1: "+Robot.powerPanel.getCurrent(0));
				System.err.println("2: "+Robot.powerPanel.getCurrent(1));
				System.err.println("3: "+Robot.powerPanel.getCurrent(2));
				System.err.println("4: "+Robot.powerPanel.getCurrent(3));
		} else {
			frontMotor.set(output);
			backMotor.set(output);
		}
	
		
	}

	private static double map(double input, double maximum, double minimum, double outputMax, double outputMin){
    	double output = (input/(maximum-minimum)-minimum/(maximum-minimum))*(outputMax-outputMin)+outputMin;
    	if (output==Double.NaN){
    		output=minimum;//Shouldn't happen unless we divide by zero somewhere
    	}
    	return output; 
    }

}
