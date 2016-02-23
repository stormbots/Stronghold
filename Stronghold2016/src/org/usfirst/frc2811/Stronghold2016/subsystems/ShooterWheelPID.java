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
    
    public void initDefaultCommand() {
        shooterMotorLeft = new CANTalon(0);
        shooterMotorRight = new CANTalon(1);
        shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterMotorRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        //shooterEncoderLeft = new Encoder(4, 5, false, EncodingType.k4X);
    	//shooterEncoderRight = new Encoder(2, 3, false, EncodingType.k4X);
         
    	// Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
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
    public double FPSToTicksPerSecond(double feetPerSecond){
    	double wheelRadius=1;
    	double fpsToRPSRatio=1;
    	double rotationPerSecond = feetPerSecond/wheelRadius;
    	
    	
    	//Get Velocity from shooter
    	//TODO do math to convert velocity from feet per second to ticks per second
    	return rotationPerSecond;
    }
    //Lets me test that the robot can shoot.  Should not be used in competition unless code goes very badly 
    public void testShooterMotors(){
    	shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorRight.set(.75); //TODO do these spin the right ways?
    	shooterMotorLeft.set(-.75);
    }
    public void shooterWheelsOff(){
    	shooterMotorRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	shooterMotorRight.set(0);
    	shooterMotorLeft.set(0);
    }
    
    
    
}



