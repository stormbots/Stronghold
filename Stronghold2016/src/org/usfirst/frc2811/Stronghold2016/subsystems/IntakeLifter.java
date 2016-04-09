package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.hal.CanTalonJNI;



import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeLifter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private CANTalon intakeLifterMotor = new CANTalon(7);
    private CANTalon intakeMotor = new CANTalon(6);
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    /**
     * This is used to hold part of the PID
     */
    double iterm=0;
	private boolean homed;
	private boolean enabled;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public IntakeLifter(){
    	intakeLifterMotor.reset();
    	intakeLifterMotor.clearStickyFaults();
    	intakeLifterMotor.reverseOutput(true);//This is correct on the practice robot
    	intakeLifterMotor.enableBrakeMode(false);//It keeps it from stalling when hitting the low bar
    	intakeLifterMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
    	intakeLifterPIDInit();
    	//Robot.smartDashboard.getNumber("p", 1);
    	//smartDashboard.getNumber("i", 0);
    	//smartDashboard.getNumber("d", 0);
    	
    	intakeMotor.reset();
    	intakeMotor.clearStickyFaults();
    	intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	intakeMotor.enable();
    	intakeMotor.set(0);
    	intakeMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	enableIntakeLimits(false);
    	

    }
    
    public boolean intakeIsSwitchPressed(){
    	if(intakeMotor.isFwdLimitSwitchClosed()){
    		return true;
    	}
    	return false;
    }
    public void intakeLifterPIDInit(){
    	intakeLifterMotor.changeControlMode(CANTalon.TalonControlMode.Current);    	
    	//intakeLifterMotor.setPID(.251, 0.00, 00); //works good for the arm prototype
    	intakeLifterMotor.setPID(.251, 0.00, 0); //competition
    	intakeLifterMotor.setIZone(100);
    	intakeLifterMotor.enable();
    	resetEncoderPosition();
    	intakeLifterMotor.set(intakeLifterMotor.get());

    }
    
    public boolean intakeLifterHoming(){
    	homed=false;
    	intakeLifterMotor.changeControlMode(CANTalon.TalonControlMode.Current);
    	intakeLifterMotor.enable();
    	//intakeLifterMotor.set(0.25); //goes up on practice bot
    	//intakeLifterMotor.set(0.25); //goes down on competition bot
    	intakeLifterMotor.set(-4);
    	if(intakeLifterMotor.isFwdLimitSwitchClosed()){
    		intakeLifterPIDInit();
    		resetEncoderPosition();
    		homed=true;
    		return true;
    	}
    	return false;
    }
    public boolean spinIntake(double speed){
    	intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	intakeMotor.enable();
    	intakeMotor.set(speed);
    	return true;
    }
    
    /**
     * Spin the intake, but cap the rotation at a set number of turns
     * run resetIntakeBeltPosition() to initialize the current position
     * @param speed
     * @param rotations, as seen by the wheel holding the ball itself.
     * @return true if the intake is still moving
     */
    public boolean spinIntake(double speed,double rotations){
    	// One rotation measured as change from -1482304 to  -1476564;
    	double tickPerRotation=5740;
    	if(Math.abs(intakeMotor.get())<Math.abs(rotations*tickPerRotation)){
    		spinIntake(speed);
    		System.out.println("Encoder Value For Intake Ball" + intakeMotor.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Relative));
    		return true;
    	}
		else{
    		spinIntake(0);
    		return false;
		}
    }
    public void resetIntakeBeltPosition(){
    	intakeMotor.setPosition(0);
    }
    
    public void resetEncoderPosition(){
    	System.out.println("initial value"+	intakeLifterMotor.get());
    	intakeLifterMotor.setEncPosition((int) tickAngleMap.upTicks);
    }
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    	

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public void setPosition(double position){
    	//double pgain=smartDashboard.getNumber("p");
    	
    	if(!homed)return;
    	//if(!enabled)return;
    	
    	double pgain=.004;
    	double igain=0.000001;
    	double error=(position-intakeLifterMotor.getEncPosition());
    	double p=pgain*error;
    	iterm=iterm+igain*error;
    	//cap iterm at 100
    	if(iterm>100)iterm=100; 
    	if(iterm<-100)iterm=-100; 
    	
    	double out=p+iterm;
    	intakeLifterMotor.enable();
    	//stop doing things with small error, hopefully avoid oscillations
    	//if(error<2 && error>-2)out=0;
    	
    	//cap output at 5 amps
    	if(out>5)out=5;
    	if(out<-5)out=-5;
    	
    	if(out>0){
    		out=out/5;
    		System.out.println("Going Down?");
    	}
    	else{
    		System.out.println("Going Up?");    		
    	}
    	
    	intakeLifterMotor.set(out);
    	
    	System.out.println("=================");
		System.out.println("Position: " +position);
		System.out.println("EncPos: " +intakeLifterMotor.getEncPosition());
		//System.out.println("Pos: " +intakeLifterMotor.getPosition());
		//System.out.println("error : " +intakeLifterMotor.getClosedLoopError());

	    Timer.delay(.05);
    }
    
    
    public void readStuff(){
    	System.out.println("=================");
		System.out.println("Setpoint: " +intakeLifterMotor.getSetpoint());
		System.out.println("EncPos: " +intakeLifterMotor.getEncPosition());
		System.out.println("Pos: " +intakeLifterMotor.getPosition());
		System.out.println("error : " +intakeLifterMotor.getClosedLoopError());
		System.out.println("Current Angle" + getAngle());
		System.out.println(tickAngleMap.ticksToAngle(1000));
		System.out.println("intakeMotor" + intakeMotor.getEncPosition());
		//Intake Inti posiion -1482304 to -1476564
    }
    
	public boolean onTarget(int angle) {
		// TODO Auto-generated method stub
    	return intakeLifterMotor.getClosedLoopError()< tickAngleMap.angleToTicks(angle);
	}
    public boolean isOnTarget(){
    	return onTarget(5);
    }
    
    public void setAngle(double degrees){
    	setPosition(tickAngleMap.angleToTicks(degrees));
    }
    public double  getAngle(){
    	return tickAngleMap.ticksToAngle(intakeLifterMotor.getEncPosition());
    }
    
    public void  enableIntakeLimits(boolean enabled){
    	//intakeMotor.enableLimitSwitch(enabled, enabled);;
    	intakeMotor.enableLimitSwitch(false, false);;

    }
        
    /**
     * Tiny class used to hold some angle and tick values
     * Positions are based at nominal values, probably measured from all the way up (homed)
     * and parallel to floor. 
     */
    private static class tickAngleMap{
    	static double upTicks=-1525;
    	static double upAngle=108;
    	static double downAngle=0;
    	static double downTicks=662;
    	static double angleToTicks(double degrees){ 
    		return map(degrees,downAngle,upAngle,downTicks,upTicks);
    	}
    	static double ticksToAngle(double ticks){ 
    		return map(ticks,downTicks,upTicks,downAngle,upAngle);
	   	}
    	static double map(double x, double in_min, double in_max, double out_min, double out_max)	{
		  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
		}
    }

}

