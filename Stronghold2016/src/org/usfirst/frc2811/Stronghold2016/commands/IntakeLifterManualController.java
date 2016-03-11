package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.OI;
import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeLifterManualController extends Command {

    public IntakeLifterManualController() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("Moving Lifter Manually");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double angle=4;
        if(Robot.oi.gamePad.getPOV(0)==0){
            //Robot.intakeLifter.setAngleRelative(angle);
            Robot.intakeLifter.moveManually(1);
        }
        else if(Robot.oi.gamePad.getPOV(0)==180){
            //Robot.intakeLifter.setAngleRelative(-angle);
            Robot.intakeLifter.moveManually(1);
        }
        else {
        	//Stop the motor
            Robot.intakeLifter.moveManually(0);
        }
        
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        Robot.intakeLifter.moveManually(0);
    }
}
