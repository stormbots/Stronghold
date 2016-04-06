package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SketchyDriveFeet extends Command {

	private double rateLeft = 0;
	private double rateRight = 0;
	private double feetLeft = 0;
	private double feetRight = 0;
	private boolean done=false;
	
	
	
    
    /**
     * Drive In one direction for X seconds at arbitrary speed
     * @param speed between -1 to 1
     * @param timeout in seconds
     */
    public SketchyDriveFeet(double speed,double feet,double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.setTimeout(timeout);
    	rateLeft = -speed;
    	rateRight = -speed;
    	
    	this.feetLeft=feet;
    	this.feetRight=feet;

    }
    public SketchyDriveFeet(double speedLeft, double speedRight,double feetLeft,double feetRight, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.setTimeout(timeout);
    	rateLeft = -speedLeft;
    	rateRight = -speedRight;
    	this.feetLeft=feetLeft;
    	this.feetRight=feetRight;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetDriveEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done=Robot.chassis.manualDriveFeet(rateLeft, rateRight, feetLeft, feetRight);    		    		    		

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	//the drive function 
    	if(done){
    		System.err.println("Done driving some feet!");
    		return true;
    	}
    	if(this.isTimedOut()){
    		System.err.println("Bleh! Timed out");
    		return true;
    	}
    	return false;
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
