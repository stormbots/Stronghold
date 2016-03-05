package org.usfirst.frc2811.Stronghold2016.subsystems;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class ShooterWheelPID extends Subsystem {
	private CANTalon shooterMotorLeft;
	private CANTalon shooterMotorRight;
	//private Encoder shooterEncoderLeft;
    //private Encoder shooterEncoderRight;

    // Quad Encoders for Shooter encoder left and right
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
	public ShooterWheelPID(){
        shooterMotorLeft = new CANTalon(5);
        shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        shooterMotorLeft.clearStickyFaults();
        shooterMotorLeft.setPID(0.5,0.01, 0.01);
        
        shooterMotorRight = new CANTalon(4);
        shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorRight.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        shooterMotorRight.clearStickyFaults();
        shooterMotorRight.setPID(1,0,0);
	}
	
    public void initDefaultCommand() {     
    	// Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    /*
    public void setVelocityInTicks(double ticksPerSecond){
    	 shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
    	 shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.Speed);
    	shooterMotorLeft.set(ticksPerSecond);
    	shooterMotorRight.set(ticksPerSecond);
    	//Are ducks lactose intolerant?
    	//useful, non-tangential things
    	//stuff that makes the code better
    	//real thing
    	// shut up Austin ~Laurel
    }
    */
   
    public void setMotorVelocity(double output){
    	shooterMotorLeft.set(output);
    	shooterMotorRight.set(output);

    	System.out.println( "motor rate:" + (int)shooterMotorLeft.getEncVelocity() );
    	
    	System.out.println("Shooter Error: "+ (int)shooterMotorLeft.getError());
    }
    
     
    
    //Lets me test that the robot can shoot.  Should not be used in competition unless code goes very badly 
    /*
     private void testShooterMotors(){
     	shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorRight.set(.75); //TODO do these spin the right ways?
    	shooterMotorLeft.set(-.75);
    }
    private void shooterWheelsOff(){
    	shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorRight.set(0);
    	shooterMotorLeft.set(0);
    }
    //*/
    
    
}



