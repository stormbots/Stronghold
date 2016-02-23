package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.hal.CanTalonJNI;



import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeLifter extends Subsystem {
	private static CANTalon intakeMotorLifter = new CANTalon(2); //TODO what should this be
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private double maxAmountofTicks=1; //TODO test to find actual max ticks
	private double minAmountofTicks=0;//TODO might be zero, but test to find actual min
	private double maxAmountofDegrees= 90; //TODO measure what max is
	private double minAmountofDegrees=0;// TODO check if min is actually zero degrees
	private DigitalInput intakeTopLimit = new DigitalInput(9);//TODO find actual places they are in
	private DigitalInput intakeBottomLimit = new DigitalInput(10); 
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//intakeMotorLifter.setProfile(0); FIXME This was in recycle rush code.  Is it needed? 
    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
    	intakeMotorLifter.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
    	intakeMotorLifter.setVoltageRampRate(1); //FIXME find what a sane value is, this might be to slow
    	intakeMotorLifter.enableLimitSwitch(true, true);//FIXME I think this enables the limit switches, but not sure
    	//intakeMotorLifter.setForwardSoftLimit(maxAmountofTicks); // TODO find what this needs to be
    	//intakeMotorLifter.setReverseSoftLimit(minAmountofTicks); //TODO find what this needs to beintakeMotorLifter
    	intakeMotorLifter.ConfigFwdLimitSwitchNormallyOpen(true);//FIXME is this right?
    	intakeMotorLifter.ConfigRevLimitSwitchNormallyOpen(true); //FIXME is this right?
    	//intakeMotorLifter.setPID(p, i, d); TODO figure out if we need this/ tune it
    
    	
    	
    	
    }
   
    public double angleToTicks(double angle){
    	return map(angle, maxAmountofDegrees, minAmountofDegrees,maxAmountofTicks,minAmountofTicks);
    }
    
    // lets you set a target angle (converted to ticks) for the arm
    public void setAngle(double angle){
    	intakeMotorLifter.set(angleToTicks(angle));
    }
    //tells us the setpoint of arm
    public double getArmSetpoint(){
    	return intakeMotorLifter.getSetpoint();
    }
    //returns what robot thinks the current angle is
    public double getCurrentAngle() {
    	return map(intakeMotorLifter.getEncPosition(), maxAmountofTicks, minAmountofTicks,  maxAmountofDegrees, minAmountofDegrees);
    }
    //Toggles between 90 and 0 degrees TODO: Calibrate-might want different angles for toggle
    public void toggleAngle() {
    	if(getCurrentAngle()>45) {
    		intakeMotorLifter.set(angleToTicks(90));
    	}
    	else{
    		intakeMotorLifter.set(angleToTicks(0));
    	}
    }
    public double getTicks(){
    	return intakeMotorLifter.getEncPosition();
    }
    //map function allows us to convert values from one unit to another ie:ticks to angle
    private double map( double input, double maximum, double minimum, double outputMax, double outputMin){ 
    	double output = (input/(maximum-minimum)-minimum/(maximum-minimum))*(outputMax-outputMin)+outputMin; 
    	if (output==Double.NaN){ 
    		output=minimum;//Shouldn't happen unless we divide by zero somewhere 
    	} 
    	return output;  
    } 	 
    

}

