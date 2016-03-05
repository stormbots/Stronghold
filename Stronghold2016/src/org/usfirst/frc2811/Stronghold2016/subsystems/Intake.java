// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Intake extends Subsystem {

	private static SpeedController intakeMotorBelts = new Talon(4);
    private static Solenoid intakeSolenoid = new Solenoid(0, 1);
    private Encoder intakeEncoder = new Encoder(6,7);
    private DigitalInput ballReadySwitch = new DigitalInput(8);
    private boolean extended = false;
    private double intakespeed = .25; // TODO double check that this spins right way, also if it needs to be faster/slower
        // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	intakeSolenoid.set(false);
    	
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    public boolean isBallReady(){
    	return ballReadySwitch.get();
    }
    
    public void intakeSet(double speed){
    	intakeMotorBelts.set(speed);
	
    }
    public void intakeBall (){
    	intakeMotorBelts.set(intakespeed); 
    }
   public void spitBall (){
	   intakeMotorBelts.set(-intakespeed);
   }
   public void extendSolenoid(){
	   intakeSolenoid.set(extended);
   } 
   public void retractSolenoid(){
	   intakeSolenoid.set(!extended);
   }
    
    public void intakeSolenoidToggle(){
    	intakeSolenoid.set(!intakeSolenoid.get());
    }
    
}

