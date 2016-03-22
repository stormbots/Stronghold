package org.usfirst.frc2811.Stronghold2016.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterSequence extends CommandGroup {
    
    public  ShooterSequence() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	//addSequential(new AlignToTarget(),.1); //get vision code if applicable
    	//addSequential(new SetShooterDistance());	//spin shooter to align for target
    	addSequential(new SpitBall(),.1);	//unwind ball for just a moment to make sure it's away from shooter
    	addSequential(new ShootBall(),1);	//Disable intake forward stops and spin the ball inward
    }
}
