package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeToggleAngle extends Command {

	boolean goUp=false;
	boolean goDown=false;
	
    public IntakeToggleAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.intakeLifter.isUpSwitchPressed()){
    		goUp=true;
    	}
    	else if( Robot.intakeLifter.isDownSwitchPressed()){
    		goDown=true;
    	}
    	else{ //neither switch is pressed
    		goUp=true;
    	}
    		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//doing nothing, work is in isFinished
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(goUp){
    		return Robot.intakeLifter.moveToUpPosition();
    		}
    	else if(goDown){
    		return Robot.intakeLifter.moveToUpPosition();
    		}
    	else{
    		return true;
    		}
        //return Robot.intakeLifter.onTarget(5);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
