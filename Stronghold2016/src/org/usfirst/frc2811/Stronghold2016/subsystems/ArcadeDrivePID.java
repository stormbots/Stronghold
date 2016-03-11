package org.usfirst.frc2811.Stronghold2016.subsystems;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;

public class ArcadeDrivePID extends RobotDrive {
	
	private static SpeedController frontLeft = new Talon(0);
	private static SpeedController backLeft = new Talon(1);
	private static SpeedController frontRight = new Talon(2);
	private static SpeedController backRight = new Talon(3);
	private static Encoder leftEncoder = new Encoder(0,1);
	private static Encoder rightEncoder = new Encoder(2,3);
	
	public ChassisSide leftSide = new ChassisSide("Left",frontLeft,backLeft,leftEncoder,false);
	public ChassisSide rightSide = new ChassisSide("Right",frontRight,backRight,rightEncoder,false);
	
	
	public ArcadeDrivePID() {		
		super(frontLeft,backLeft,frontRight,backRight);
		
		leftEncoder.setDistancePerPulse(1);
		rightEncoder.setDistancePerPulse(1);
		
		//System.out.println("ArcadeDriveConstructor, Statement #" + Robot.counter);
		Robot.counter++;

	}

	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
	    // local variables to hold the computed PWM values for the motors
		//System.out.println("ArcadeDrive method, Statement #" + Robot.counter);
		Robot.counter++;
		
		if (!kArcadeStandard_Reported) {
	      UsageReporting.report(tResourceType.kResourceType_RobotDrive, 4,
	          tInstances.kRobotDrive_ArcadeStandard);
	      kArcadeStandard_Reported = true;
	    }

	    double leftMotorSpeed;
	    double rightMotorSpeed;

	    moveValue = limit(moveValue);
	    rotateValue = limit(rotateValue);

	    if (squaredInputs) {
	      // square the inputs (while preserving the sign) to increase fine control
	      // while permitting full power
	      if (moveValue >= 0.0) {
	        moveValue = (moveValue * moveValue);
	      } else {
	        moveValue = -(moveValue * moveValue);
	      }
	      if (rotateValue >= 0.0) {
	        rotateValue = (rotateValue * rotateValue);
	      } else {
	        rotateValue = -(rotateValue * rotateValue);
	      }
	    }

	    if (moveValue > 0.0) {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = Math.max(moveValue, rotateValue);
	      } else {
	        leftMotorSpeed = Math.max(moveValue, -rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      }
	    } else {
	      if (rotateValue > 0.0) {
	        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
	        rightMotorSpeed = moveValue + rotateValue;
	      } else {
	        leftMotorSpeed = moveValue - rotateValue;
	        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	      }
	    }

	    //pidDrive(leftMotorSpeed,rightMotorSpeed);
	    setLeftRightMotorOutputs(leftMotorSpeed,rightMotorSpeed);
	  }
	
	public void pidDrive(double leftTargetSpeed, double rightTargetSpeed){
		//System.out.println("ArcadeDrive PID method, Statement #" + Robot.counter);
		Robot.counter++;
		double adjustedLeft = leftTargetSpeed;
		double adjustedRight = rightTargetSpeed;
		double leftProportion = Math.abs(leftTargetSpeed/leftSide.getSideEncoderRate());
		double rightProportion = Math.abs(rightTargetSpeed/rightSide.getSideEncoderRate());
		
		if( (leftProportion>rightProportion) && (rightSide.getSideSetpoint()==1) ){
			adjustedLeft = leftTargetSpeed*rightProportion;
			
		} else if ( (rightProportion>leftProportion) && (leftSide.getSideSetpoint()==1) ) {
			adjustedRight = rightTargetSpeed*leftProportion;
			
		}
		
		setLeftRightMotorOutputs(adjustedLeft, adjustedRight);
	}
	
	public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
		//System.out.println("ArcadeDrive setLeftRight, Statement #" + Robot.counter);
		Robot.counter++;
	   
		leftSide.driveRate(leftOutput);
		rightSide.driveRate(-rightOutput);

	    if (m_safetyHelper != null)
	      m_safetyHelper.feed();
	}
	
	public void drive(double outputMagnitude, double curve) {
		//System.out.println("ArcadeDrive override drive, Statement #" + Robot.counter);
		Robot.counter++;
		if (!kArcadeRatioCurve_Reported) {
	      UsageReporting.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
	          tInstances.kRobotDrive_ArcadeRatioCurve);
	      kArcadeRatioCurve_Reported = true;
	    }
		
		stopMotor();
	}
		
	
}
