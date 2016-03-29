package org.usfirst.frc2811.Stronghold2016.subsystems;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AltChassis extends Subsystem {
    
	private SpeedController m0 = new Talon(0);
	private SpeedController m1 = new Talon(1);
	private SpeedController m2 = new Talon(2);
	private SpeedController m3 = new Talon(3);
	public RobotDrive chassisDrive = new RobotDrive(m0,m1,m2,m3);
	private Solenoid shifter = new Solenoid(0,0);
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	shifter.set(false);
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shiftGears(){
    	shifter.set(!shifter.get());
    }

    public void shiftGearsHigh(){
    	shifter.set(false);
    }
    public void shiftGearsLow(){
    	shifter.set(true);
    }
    
    public boolean shifterState(){
    	return shifter.get();
    }

    public void joystickDrive(){
    	chassisDrive.arcadeDrive(Robot.oi.getMoveValue(), Robot.oi.getRotateValue());
    	
    }
}

