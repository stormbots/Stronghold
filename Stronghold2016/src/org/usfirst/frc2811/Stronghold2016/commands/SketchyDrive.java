package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SketchyDrive extends Command {

	private double rate=0;
	
	/**
	 * Drive forward X seconds at sane speed
	 * @param timeout in seconds
	 */
    public SketchyDrive(double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.setTimeout(timeout);
    	rate=-0.75;
    }
    
    /**
     * Drive In one direction for X seconds at arbitrary speed
     * @param speed between -1 to 1
     * @param timeout in seconds
     */
    public SketchyDrive(double speed,double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.setTimeout(timeout);
    	rate=-speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.chassisDrive.arcadeDrive(rate,0);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.chassisDrive.arcadeDrive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
