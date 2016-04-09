package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterSetSpeedForAlignmentLineShot extends Command {

    public ShooterSetSpeedForAlignmentLineShot(double maxWaitTime) {
    	setTimeout(maxWaitTime);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	
    	double rawVelocity = 6750;//TODO this value for the Alignment Line and Many More
		Robot.shooter.setVelocityToShootFromBatter(rawVelocity );
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("Shooter isFinished");
    	return Robot.shooter.isAtTargetVelocity() || isTimedOut();
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Shooter exits the function");
    	//Ended successfully, we should be going to the next part of the process
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//Somebody cancelled, shut off the shooter.
    	Robot.shooter.setVelocityRaw(0);
    }
}
