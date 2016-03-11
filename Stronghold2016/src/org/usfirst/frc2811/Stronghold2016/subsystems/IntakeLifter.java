package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.hal.CanTalonJNI;



import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeLifter extends Subsystem {
	private static CANTalon intakeMotorLifter = new CANTalon(7); //Competition bot slot 7
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	//private double maxAmountofTicks=4096; //TODO test to find actual max ticks
	//private double minAmountofTicks=0;//TODO might be zero, but test to find actual min
	private double maxAmountofDegrees= 360; //TODO measure what max is
	private double minAmountofDegrees=0;// TODO check if min is actually zero degrees

	
	//TODO: Probably need to print ticks and angle, then move the arm, and write down the correct angles
	private double mapLowTicks=1024; //TODO set me based on where arm is in 0 degree position
	//private double mapLowTicks=-1024; //TODO other likely value
	private double mapHighTicks=0;
	private double mapLowAngle=0;
	private double mapHighAngle=100;
	
	public IntakeLifter(){
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//intakeMotorLifter.setProfile(0); //FIXME This was in recycle rush code.  Is it needed? 
    	//intakeMotorLifter.enableControl(); // didn't do anything? 
    	//intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
    	intakeMotorLifter.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	//intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
    	intakeMotorLifter.setVoltageRampRate(2); //FIXME find what a sane value is, this might be to slow
    	intakeMotorLifter.enableLimitSwitch(true, true);//FIXME I think this enables the limit switches, but not sure
    	//intakeMotorLifter.setForwardSoftLimit(maxAmountofTicks); // TODO find what this needs to be
    	//intakeMotorLifter.setReverseSoftLimit(minAmountofTicks); //TODO find what this needs to beintakeMotorLifter
    	//intakeMotorLifter.ConfigFwdLimitSwitchNormallyOpen(true);//FIXME is this right?
    	//intakeMotorLifter.ConfigRevLimitSwitchNormallyOpen(true); //FIXME is this right?
    	intakeMotorLifter.setPID(.1,0, .0001); //TODO Testbench values. figure out if we need this/ tune it
    	intakeMotorLifter.enable();
    	intakeMotorLifter.clearStickyFaults();
    	intakeMotorLifter.enableLimitSwitch(true, true);//FIXME I think this enables the limit switches, but not sure
    	
    	//Does not work on testbench
    	//intakeMotorLifter.reverseOutput(true);
    	//intakeMotorLifter.reverseSensor(false);

    	//Does not work on testbench
    	//intakeMotorLifter.reverseOutput(false)
    	//intakeMotorLifter.reverseSensor(true);
    	
    	//tested on competition bot at end of unbag, not sure if right
    	//intakeMotorLifter.reverseOutput(true);
    	//intakeMotorLifter.reverseSensor(true);
    	
    	// Dan suspects this is the correct version on the robot
    	//intakeMotorLifter.reverseOutput(false);
    	//intakeMotorLifter.reverseSensor(false);
    	//with these settings
    	//angle decreases downward
    	//ticks decrease downward

    	// Dan suspects this is the correct version on the robot
    	intakeMotorLifter.reverseOutput(true);
    	intakeMotorLifter.reverseSensor(false);

    	
    	//Until PID works, set the mode to VBus and disable
    	intakeMotorLifter.disable();
    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

	}

    public void initDefaultCommand() {
    }
    public double getTicks(){
    	return intakeMotorLifter.getEncPosition();
    }
    
    /**
     * Makes the Motor go up and hit the hard upper limit and set encoder to zero when hit.
     * @return true when the limit switch is hit and false when it is not true. Only valid while performing the homing sequence
     */
    public boolean setHomingIntake(){
    	//go up works correctly on robot with positive direction
    	double goUp= 0.5; 
    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);//TODO make sure it actually going up
    	intakeMotorLifter.set(goUp);
    	System.out.println("Motor Value" + intakeMotorLifter.getOutputVoltage());
    	//intakeMotorLifter.clearStickyFaults();
    	
    	//TODO: If homing doesn't intake, it's because we grabbed the wrong switch
    	// Verify the isUpSwitchPressed functions work correctly, and then this will return like we expect
    	if(intakeMotorLifter.isFwdLimitSwitchClosed()){
	    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
	    	intakeMotorLifter.setEncPosition(0);
	    	intakeMotorLifter.clearIAccum();
	    	intakeMotorLifter.enable();
	    	
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
     	//original, possibly wrong? return map(angle, maxAmountofDegrees, minAmountofDegrees,maxAmountofTicks,minAmountofTicks);
    	return map(angle, mapHighAngle, mapLowAngle,mapHighTicks,mapLowTicks); 
     }
     
     /**
      * @param 
      * @return Math that converts angles to ticks
      */
      private double ticksToAngle(double ticks){
      	//return map(ticks,maxAmountofTicks,minAmountofTicks, maxAmountofDegrees, minAmountofDegrees);
    	  return map(ticks,mapHighTicks,mapLowTicks, mapHighAngle, mapLowAngle);
    }
    
    /**
     *  lets you set a target angle (converted to ticks) for the arm
     * @param angle Target position in degree 
     */
    public void setAngle(double angle){
    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
    	intakeMotorLifter.set(angleToTicks(angle));
    }
    
    /**
     * Get the current arm position in degrees
     * @return arm position in degrees
     */
    public double getCurrentAngle() {
    	// confirms to work except for maybe backwards
    	return ticksToAngle(intakeMotorLifter.getEncPosition());
	}

    /**
     * Toggles between 90 and 0 degrees
     * if the angle is greater than 45, it set the angle to 0, or to the homed position
     * If the angle is less that 45, then it will set the intake to 100 TODO: Calibrate-might want different angles for toggle
     */
    public void toggleAngle() {
    	if(getCurrentAngle()>45) {
    		setAngle(0);
    	}
    	else{
    		setAngle(45);
    	}
    }
    /**
     *  Move the arm up or down a few degrees
     * @param angle number of degrees to move
     */
    public void setAngleRelative(double angle){
    	if(getCurrentAngle() + angle > maxAmountofDegrees){
    	 }
    	else if(getCurrentAngle()+ angle < minAmountofDegrees){
    	}
    	else{
    		setAngle(getCurrentAngle()+angle);
    	}
    	
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
		
		return Math.abs(getCurrentAngle()-getTargetAngle())<tolerance;
	}
	
    /**
     * Determine whether the intake is currently where it's intended to be set
     * Shorthand for onTarget(5 degrees)
     * @return true if within tolerance
     */
	
	public boolean onTarget() {
		return onTarget(5);
	}
	//public void clearStickyFaults(){
		//intakeMotorLifter.clearStickyFaults();
	//}
	/**
	 * 
	 * @return current setpoint in degrees
	 */
	private double getTargetAngle() {
		// TODO Auto-generated method stub
		return ticksToAngle(intakeMotorLifter.getSetpoint());
	} 	 
    
	/**
	 * Disables the PID controller
	 */
	public void disable() {
		// TODO Auto-generated method stub
		intakeMotorLifter.disable();
	} 	 

	/**
	 * Enable PID Controller
	 */
	public void enable() {
    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
		intakeMotorLifter.enable();
	} 	 

	//TODO: Verify that these are, in fact, the correct switches 
	/**
	 * 
	 * @return true if the switch in the Up position of the arm is pressed
	 */
	public boolean isUpSwitchPressed(){
		return intakeMotorLifter.isFwdLimitSwitchClosed();
	}
	
	/**
	 * 
	 * @return true if the switch in the Down position of the arm is pressed
	 */
	public boolean isDownSwitchPressed(){
		return intakeMotorLifter.isRevLimitSwitchClosed();
	}

	/**
	 * Move arm upward until it hits a switch.
	 * @return true if at up position
	 */
	public boolean moveToUpPosition(){
		intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		if(isUpSwitchPressed()){
			intakeMotorLifter.set(0);
			return true;
		}
		else{
			intakeMotorLifter.set(.6);
			return true;
		}
	}

	/**
	 * Move arm upward until it hits a switch.
	 * @return true if at Down position
	 */
	public boolean moveToDownPosition(){
		intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		if(isDownSwitchPressed()){
			intakeMotorLifter.set(0);
			return true;
		}
		else{
			intakeMotorLifter.set(-.2);
			return true;
		}
	}
public void doNotLift(){
	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	intakeMotorLifter.set(0);
}
	/**
	 * Allows the arm to be moved manually, if needed
	 * @param speed of motor in PercentVbus
	 */
	public void moveManually(double speed){
		intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		intakeMotorLifter.set(speed);
		//intakeMotorLifter.clearStickyFaults();
		}
}

