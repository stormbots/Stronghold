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
		double i=0.00;
		double d=0.00;
        shooterMotorLeft = new CANTalon(4); //Competition bot slot 4
        shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorLeft.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        shooterMotorLeft.clearStickyFaults();
        shooterMotorLeft.setPID(p,i,d); 
        shooterMotorLeft.reverseOutput(true);
        
        shooterMotorRight = new CANTalon(5); //competition bot slot 5
        shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorRight.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        shooterMotorRight.clearStickyFaults();
        shooterMotorRight.setPID(p,i,d);
        shooterMotorRight.reverseOutput(true);
        //These motors should not exceed 50 Amps
        // This is roughly 5000 RPM
        // Gearbox is a 1:1 gearbox
        //TODO Add rate limiting based on calculations
        shooterMotorRight.configMaxOutputVoltage(8); //still too high!
        shooterMotorLeft.configMaxOutputVoltage(8);  // still too high!

        shooterMotorRight.setVoltageRampRate(1); 
        shooterMotorLeft.setVoltageRampRate(1); 
        shooterMotorRight.enable();
        shooterMotorLeft.enable();
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
 /**
  * Sets a velocity of the Motors  
  * @param output
  */
    public void setMotorVelocity(double output){
    	shooterMotorLeft.set(output);
    	shooterMotorRight.set(output);

    	System.out.println( "motor rate Left:" + (int)shooterMotorLeft.getEncVelocity() );
    	System.out.println( "motor rate Right:" + (int)shooterMotorRight.getEncVelocity() );
    	System.out.println(shooterMotorLeft.getOutputCurrent());
     }
    
     
    
    //Lets me test that the robot can shoot.  Should not be used in competition unless code goes very badly 
    /*
     public  void testShooterMotors(double speed){
     	shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorRight.set(speed); //TODO do these spin the right ways?
    	shooterMotorLeft.set(speed);
    	System.out.println("Tick Rates: "+ shooterMotorRight.get());
    }
    public void shooterWheelsOff(){
    	shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorRight.set(0);
    	shooterMotorLeft.set(0);
    }
    //*/
    
    
}



