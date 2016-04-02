package org.usfirst.frc2811.Stronghold2016.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterSequenceForLowPower extends CommandGroup {
    
    public  ShooterSequenceForLowPower() {
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

    	// unwind ball for just a moment to make sure it's all the way in but away from shooter
    	addSequential(new IntakeAdjustBallPosition(-.25,0.5),0.25);
    	addSequential(new Wait(.125));
    	//addSequential(new IntakeAdjustBallPosition(.25,2),.25);	
    		
    	
    	//addSequential(new AlignToTarget(),.1); //get vision code if applicable
    	//addSequential(new SetShooterDistance());	//spin shooter to align for target
    	addSequential(new ShooterSetSpeedForLowPowerShot(3),2);
    	addSequential(new Wait(2));
    	addSequential(new ShootBall(1),3);	//Disable intake forward stops and spin the ball inward
    	addSequential(new ShooterOff());
    	addSequential(new IntakeOff());
    }
}
