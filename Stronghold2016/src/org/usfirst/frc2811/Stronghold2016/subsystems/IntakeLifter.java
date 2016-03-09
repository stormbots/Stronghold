package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.hal.CanTalonJNI;



import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeLifter extends Subsystem {
	private static CANTalon intakeMotorLifter = new CANTalon(5); //TODO what should this be
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private double maxAmountofTicks=4096; //TODO test to find actual max ticks
	private double minAmountofTicks=0;//TODO might be zero, but test to find actual min
	private double maxAmountofDegrees= 360; //TODO measure what max is
	private double minAmountofDegrees=0;// TODO check if min is actually zero degrees
	
	public IntakeLifter(){
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//intakeMotorLifter.setProfile(0); //FIXME This was in recycle rush code.  Is it needed? 
    	//intakeMotorLifter.enableControl(); // didn't do anything? 
    	//intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
    	intakeMotorLifter.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	intakeMotorLifter.setVoltageRampRate(1); //FIXME find what a sane value is, this might be to slow
    	intakeMotorLifter.enableLimitSwitch(true, true);//FIXME I think this enables the limit switches, but not sure
    	//intakeMotorLifter.setForwardSoftLimit(maxAmountofTicks); // TODO find what this needs to be
    	//intakeMotorLifter.setReverseSoftLimit(minAmountofTicks); //TODO find what this needs to beintakeMotorLifter
    	//intakeMotorLifter.ConfigFwdLimitSwitchNormallyOpen(true);//FIXME is this right?
    	//intakeMotorLifter.ConfigRevLimitSwitchNormallyOpen(true); //FIXME is this right?
    	intakeMotorLifter.setPID(.5,.0001, .0001); //TODO Testbench values. figure out if we need this/ tune it
    	intakeMotorLifter.enable();
    
    	intakeMotorLifter.enableLimitSwitch(true, true);//FIXME I think this enables the limit switches, but not sure
    	
    	//Does not work on testbench
    	intakeMotorLifter.reverseOutput(true);//FIXME I think this enables the limit switches, but not sure
    	intakeMotorLifter.reverseSensor(false);;//FIXME I think this enables the limit switches, but not sure

    	//Does not work on testbench
    	intakeMotorLifter.reverseOutput(false);//FIXME I think this enables the limit switches, but not sure
    	intakeMotorLifter.reverseSensor(true);;//FIXME I think this enables the limit switches, but not sure

	}

    public void initDefaultCommand() {
    }
    /**
     * Makes the Motor go up and hit the hard upper limit and set encoder to zero when hit.
     * @return true when the limit switch is hit and false when it is not true. Only valid while performing the homing sequence
     */
    public boolean setHomingIntake(){
    	double goUp= -0.5; 
    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);//TODO make sure it actually going up
    	intakeMotorLifter.set(goUp);
    	
    	if(intakeMotorLifter.isFwdLimitSwitchClosed()){
	    	intakeMotorLifter.setEncPosition((int) angleToTicks(0));
	    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
	    	intakeMotorLifter.enable();
	    	intakeMotorLifter.clearIAccum();
	    	
	    	//Print some status things
	    	System.out.println("Enabled? "+ intakeMotorLifter.isEnabled());
	    	System.out.println("Setpoint? "+ intakeMotorLifter.getSetpoint());
	    	
	    	return true;
    	}
    	else {
    		return false;
    	}
    
    }
    
    /**
     * @param 
     * @return Math that converts angles to ticks
     */
     private double angleToTicks(double angle){
     	return map(angle, maxAmountofDegrees, minAmountofDegrees,maxAmountofTicks,minAmountofTicks);
     }
     /**
      * @param 
      * @return Math that converts angles to ticks
      */
      private double ticksToAngle(double ticks){
      	return map(ticks,maxAmountofTicks,minAmountofTicks, maxAmountofDegrees, minAmountofDegrees);
      }
    
    /**
     *  lets you set a target angle (converted to ticks) for the arm
     * @param angle Target position in degree 
     */
    public void setAngle(double angle){
    	intakeMotorLifter.set(angleToTicks(angle));
    }

    /**
     * Returns target position of arm
     * @return Arm setpoint in degrees
     */
    public double getArmSetpoint(){
    	return intakeMotorLifter.getSetpoint();
    }
    
    /**
     * Get the current arm position in degrees
     * @return arm position in degrees
     */
    public double getCurrentAngle() {
    	return map(intakeMotorLifter.get(),maxAmountofTicks,minAmountofTicks, maxAmountofDegrees, minAmountofDegrees);
    	//return map(intakeMotorLifter.getEncPosition(), maxAmountofTicks, minAmountofTicks,  maxAmountofDegrees, minAmountofDegrees);
    }
    /**
     * Sets motor from negative one to one. It should only be used for basic motor functionality
     * @param 
     */
    private void setMotor (double set){
		intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		intakeMotorLifter.set(set);
		
		
    }
    /**
     * Toggles between 90 and 0 degrees
     * if the angle is greater than 45, it set the angle to 0, or to the homed position
     * If the angle is less that 45, then it will set the intake to 100 TODO: Calibrate-might want different angles for toggle
     */
    public void toggleAngle() {
    	if(getCurrentAngle()>45) {
    		intakeMotorLifter.set(angleToTicks(0));
    	}
    	else{
    		intakeMotorLifter.set(angleToTicks(100));
    	}
    }
    /**
     *  Move the arm up or down a few degrees
     * @param input number of degrees to move
     */
    public void setAngleRelative(double input){
    	if(intakeMotorLifter.getEncPosition() + input > maxAmountofDegrees){
    		//intakeMotorLifter.set(maxAmountofTicks);
    	 }
    	else if(intakeMotorLifter.getEncPosition()+ input < minAmountofDegrees){
    		//intakeMotorLifter.set(minAmountofTicks);    		
    	}
    	else{
    		intakeMotorLifter.set(intakeMotorLifter.getEncPosition() + angleToTicks(input));
    	}
    	
    }
    
    private double getTicks(){
    	return intakeMotorLifter.getEncPosition();
    }
    //TODO test and find good numbers for changing setpoint
    public void changeSetpoint(double change) {
    	intakeMotorLifter.set(intakeMotorLifter.getSetpoint()+change);
    }
    //map function allows us to convert values from one unit to another ie:ticks to angle
    private double map( double input, double maximum, double minimum, double outputMax, double outputMin){ 
    	double output = (input/(maximum-minimum)-minimum/(maximum-minimum))*(outputMax-outputMin)+outputMin; 
    	if (output==Double.NaN){ 
    		output=minimum;//Shouldn't happen unless we divide by zero somewhere 
    	} 
    	return output;  
    }
    
    /**
     * Determine whether the intake is currently where it's intended to be set
     * @param tolerance in degrees for success
     * @return true if within tolerance
     */
	public boolean onTarget(double tolerance) {
		// TODO Auto-generated method stub
		
		return Math.abs(getCurrentAngle()-getSetpoint())<tolerance;
	}
	
    /**
     * Determine whether the intake is currently where it's intended to be set
     * Shorthand for onTarget(5 degrees)
     * @return true if within tolerance
     */
	
	public boolean onTarget() {
		return onTarget(5);
	}

	/**
	 * 
	 * @return current setpoint in degrees
	 */
	private double getSetpoint() {
		// TODO Auto-generated method stub
		return ticksToAngle(intakeMotorLifter.getSetpoint());
	} 	 
    

}

