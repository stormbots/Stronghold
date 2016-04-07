package org.usfirst.frc2811.Stronghold2016.subsystems;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AltChassis extends Subsystem {
    
	private SpeedController m0 = new Talon(0);
	private SpeedController m1 = new Talon(1);
	private SpeedController m2 = new Talon(2);
	private SpeedController m3 = new Talon(3);
	public RobotDrive chassisDrive = new RobotDrive(m0,m1,m2,m3);
	private Solenoid shifter = new Solenoid(0,0);
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Encoder encoderLeft=new Encoder(0,1);
    public Encoder encoderRight=new Encoder(2,3);

    public void initDefaultCommand() {
    	shifter.set(false);
    	encoderLeft.setReverseDirection(true);
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shiftGears(){
    	shifter.set(!shifter.get());
    }

    public void shiftGearsHigh(){
    	shifter.set(false);
    }
    public void shiftGearsLow(){
    	shifter.set(true);
    }
    
    public boolean shifterState(){
    	return shifter.get();
    }

    public void joystickDrive(){
    	chassisDrive.arcadeDrive(Robot.oi.getMoveValue(), Robot.oi.getRotateValue());
    	
    }

    /**
     * Drive the robot a number of feet
     * @param leftSpeed Motor power for the left side
     * @param rightSpeed Motor power for the right side
     * @param feetLeft Number of feet for the left side to move
     * @param feetRight number of feet for the right side to move
     * @return
     */
    public boolean manualDriveFeet(double leftSpeed,double rightSpeed,double feetLeft,double feetRight){
    	// One rotation measured as change from -1482304 to  -1476564;
    	double tickPerFoot=7034/10;
    	
    	//check left side
    	if(Math.abs(encoderLeft.get())<Math.abs(feetLeft*tickPerFoot)){
    		//all good, do nothing
    		//leftSpeed=leftSpeed;
    	}
		else{
    		leftSpeed=0;
    		rightSpeed=0;
		}
    	
    	//check right side
    	if(Math.abs(encoderRight.get())<Math.abs(feetRight*tickPerFoot)){
    		//all good, do nothing
    		//rightSpeed=rightSpeed;
    	}
		else{
			leftSpeed=0;
    		rightSpeed=0;
		}
    	
    	chassisDrive.setLeftRightMotorOutputs(leftSpeed, rightSpeed);
    	
    	//return true if all values are being restricted to zero
    	return (leftSpeed ==0 && rightSpeed==0);
    }
    
    public void resetDriveEncoders(){
    	encoderLeft.reset();
    	encoderRight.reset();
    }
    
}

