package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestShooter extends Command {
/*
 * THIS IS FOR INITIAL TESTING ONLY.  IT WILL TELL BE THAT WE PHYSICALLY CAN SHOOT.  
 * DO NOT USE IN COMPETITION BECAUSE THE SHOOTER WILL ONLY RUN AT ONE SPEED 
 * 
 */
    public TestShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initializing the shooter");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.shooter.setShooterDistance(10);//THis is a TEST!!!
    	double d=(-Robot.oi.gamePad.getRawAxis(3)+1)*8;
    	Robot.shooter.setShooterDistance(d);
    
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false && !Robot.oi.button2.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Ending the Shooter");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Shooter command was killed! Stopping");
    	Robot.shooter.setShooterDistance(0);


    }
}
