package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/** This command homes the intake. It leaves the arm in a 90 degree state where zero is parallel to the ground
 *  *
 */
public class IntakeHoming extends Command {

    public IntakeHoming() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initializing Homing Command");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeLifter.setHomingIntake();
  
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return Robot.intakeLifter.setHomingIntake();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Finishing Homing Command");
    	 System.out.println("Current Lifter Angle At End Of Homing" + Robot.intakeLifter.getCurrentAngle());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Failed! At angle "+Robot.intakeLifter.getCurrentAngle()); 
    }
}
