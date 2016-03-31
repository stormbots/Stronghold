
package org.usfirst.frc2811.Stronghold2016.commands;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeInSlow extends Command {

	boolean lastSwitch;
    public IntakeInSlow() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastSwitch=Robot.intakeLifter.intakeIsSwitchPressed();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeLifter.spinIntake(0.6);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean edge=(lastSwitch==true && Robot.intakeLifter.intakeIsSwitchPressed()==false);
    	lastSwitch=Robot.intakeLifter.intakeIsSwitchPressed();
        return edge;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
