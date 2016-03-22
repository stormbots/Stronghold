package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Reads the joystick and puts the Intake where we want it
 */
public class IntakeAngleMonitor extends Command {
	private enum TargetPosition{ Joystick, BallIntake, Down, Up, FindHome, BallEject}
	TargetPosition mode;
    public IntakeAngleMonitor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	mode=TargetPosition.FindHome;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Get Joystick Values

    	if(Robot.oi.threeAxisJoystick.getRawButton(10)){
    		mode=TargetPosition.BallIntake;
    	}
    	if(Robot.oi.threeAxisJoystick.getRawButton(11)){
    		mode=TargetPosition.BallEject;
    	}
    	else if(Robot.oi.threeAxisJoystick.getRawAxis(3)<-.95){
        	mode=TargetPosition.FindHome;
        }
       
        


        //Do Stuff With Output
        if (mode==TargetPosition.Joystick){
            Robot.intakeLifter.setPosition(Robot.oi.threeAxisJoystick.getRawAxis(3)*1500);
        }
        else if (mode==TargetPosition.FindHome){
        	if(Robot.intakeLifter.intakeLifterHoming()){
        		mode=TargetPosition.Joystick;
        	}
        }
        else if (mode==TargetPosition.BallIntake){
        	Robot.intakeLifter.setPosition(1000);
        }
        else if (mode==TargetPosition.BallEject){
        	Robot.intakeLifter.setPosition(1000);
        }
        else {
        	System.out.println("IntakeLifterMonitor: Nothing to do");
        	mode=TargetPosition.Joystick;
        }


    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return Robot.intakeLifter.onTarget(5);
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
