package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Used to drive the robot directly forward at a 0 degree angle, autonomously
 */
public class AutoDrive extends Command {

	double distance;
	double speed;
	double moveValue;
	double targetAngle;
	
    public AutoDrive(double feet, double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	distance=feet;
    	speed=power;
    	targetAngle = Robot.chassis.navxGyro.getAngle();
    	
    }
    
    public AutoDrive(double feet, double power, double degrees) {
    	distance=feet;
    	speed=power;
    	targetAngle=degrees;    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetTicks();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.movingAlign(speed, targetAngle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return 	Robot.chassis.getLeftEncoder()==moveValue||
        		Robot.chassis.getRightEncoder()==moveValue;
    }

    // Called once after isFinished returns true
    protected void end() {
    	    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
