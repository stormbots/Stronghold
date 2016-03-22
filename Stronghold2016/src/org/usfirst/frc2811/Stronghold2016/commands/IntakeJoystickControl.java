package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Reads the joystick and puts the Intake where we want it
 * To disable, call Robot.intakeJoyStickControl.cancel(), 
 * and then run Robot.intakeJoyStickControl.enable() to restart. 
 */
public class IntakeJoystickControl extends Command {

    public IntakeJoystickControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("Joystick Flapper :" + Robot.oi.getLifterPaddlePosition());
        if(Robot.oi.getLifterPaddlePosition()<-.95){
        	Robot.intakeLifter.intakeLifterHoming();
        }
        else if(Robot.oi.threeAxisJoystick.getRawButton(10)){
        	Robot.intakeLifter.setPosition(1000);
        }
        else{
        	Robot.intakeLifter.setPosition(Robot.oi.getLifterPaddlePosition()*1500);

        }
       // intakeLifter.spinIntake(oi.threeAxisJoystick.getRawAxis(1));

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
