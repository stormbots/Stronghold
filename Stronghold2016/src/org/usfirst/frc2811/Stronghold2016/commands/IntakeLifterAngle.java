package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeLifterAngle extends Command {
	double angle;
    public IntakeLifterAngle(double Angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		angle=Angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//System.out.println("Moving lifter, going to "+angle+" from " + Robot.intakeLifter.getCurrentAngle());
    	Robot.intakeJoystickControl.cancel();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeLifter.setAngle(angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.intakeLifter.onTarget(5);
    }

    // Called once after isFinished returns true
    protected void end() {
    	//System.out.println("Current Lifter Angle" + Robot.intakeLifter.getCurrentAngle());
    	Robot.intakeJoystickControl.start();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intakeJoystickControl.start();
    }
}
