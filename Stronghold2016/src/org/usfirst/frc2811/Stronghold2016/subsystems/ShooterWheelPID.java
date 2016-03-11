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
		double p=0.5;
		double i=0.01;
		double d=0.01;
        shooterMotorLeft = new CANTalon(4); //Competition bot slot 4
        shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorLeft.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        shooterMotorLeft.clearStickyFaults();
        shooterMotorLeft.setPID(p,i,d); 
        
        shooterMotorRight = new CANTalon(5); //competition bot slot 5
        shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorRight.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        shooterMotorRight.clearStickyFaults();
        shooterMotorRight.setPID(p,i,d);

        //These motors should not exceed 50 Amps
        // This is roughly 5000 RPM
        // Gearbox is a 1:1 gearbox
        //TODO Add rate limiting based on calculations
        shooterMotorRight.configMaxOutputVoltage(5); //still too high!
        shooterMotorLeft.configMaxOutputVoltage(5);  // still too high!

        shooterMotorRight.setVoltageRampRate(0.5); 
        shooterMotorLeft.setVoltageRampRate(0.5);        
	}
	
    public void initDefaultCommand() {     
    	// Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    /*
    public void setVelocityInTicks(double ticksPerSecond){
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



