package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.OI;
import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeLifterManual extends Command {

    public IntakeLifterManual() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("starting intake liver command");

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double angle=4;
        if(Robot.oi.gamePad.getPOV(0)==0){
            Robot.intakeLifter.setAngleRelative(angle);
        }
        else if(Robot.oi.gamePad.getPOV(0)==180){
            Robot.intakeLifter.setAngleRelative(-angle);
        }
        else {
        	//do nothing
        }
        
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
