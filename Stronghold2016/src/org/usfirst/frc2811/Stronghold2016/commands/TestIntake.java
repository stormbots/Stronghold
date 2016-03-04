package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;
import org.usfirst.frc2811.Stronghold2016.subsystems.IntakeLifter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestIntake extends Command {

    public TestIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {;
    	Robot.chassis.manualDrive(1, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.oi.button5.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeLifter.setMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
