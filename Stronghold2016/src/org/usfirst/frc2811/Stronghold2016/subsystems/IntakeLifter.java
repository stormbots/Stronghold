package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeLifter extends Subsystem {
	private static CANTalon intakeMotorLifter = new CANTalon(5);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	intakeMotorLifter.changeControlMode(CANTalon.TalonControlMode.Position);
    	intakeMotorLifter.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    }
    public void setPositionInTicks(double ticks){
    	intakeMotorLifter.set(ticks);
    }
    public void angleToPositionInTicks(double angle){
    	double ticks=1;
    	return;
    }
}

