package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LinearDrive extends Command {

	double ticks;
	double power;
	
    public LinearDrive(double distance, double rate) {
    	ticks = distance;
    	power = rate;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetTicks();
    	Robot.chassis.driveRate(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.adjustForErrorDrive();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.chassis.getLeftDistance()==ticks
    		   ||Robot.chassis.getRightDistance()==ticks;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.driveRate(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
