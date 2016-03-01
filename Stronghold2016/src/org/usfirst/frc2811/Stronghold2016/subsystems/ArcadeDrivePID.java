package org.usfirst.frc2811.Stronghold2016.subsystems;

import org.usfirst.frc2811.Stronghold2016.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;

public class ArcadeDrivePID extends RobotDrive {
	
	private static SpeedController frontLeft = RobotMap.frontLeft;
	private static SpeedController backLeft = RobotMap.backLeft;
	private static SpeedController frontRight = RobotMap.frontRight;
	private static SpeedController backRight = RobotMap.backRight;
	private static Encoder leftEncoder = RobotMap.leftEncoder;
	private static Encoder rightEncoder = RobotMap.rightEncoder;
	
	public ChassisSide leftSide = new ChassisSide("Left",frontLeft,backLeft,leftEncoder,false);
	public ChassisSide rightSide = new ChassisSide("Right",frontRight,backRight,rightEncoder,false);
	
	public ArcadeDrivePID() {		
		super(frontLeft,backLeft,frontRight,backRight);
		System.out.println("ArcadeDriveConstructor, Statement #" + RobotMap.counter);
		RobotMap.counter++;
		//leftSide 
		//rightSide 

	}
		
	@Override
	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
	    // local variables to hold the computed PWM values for the motors
		System.out.println("ArcadeDrive method, Statement #" + RobotMap.counter);
		RobotMap.counter++;
		
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

	    pidDrive(leftMotorSpeed,rightMotorSpeed);
	    //setLeftRightMotorOutputs(leftMotorSpeed,rightMotorSpeed);
	  }
	
	public void pidDrive(double leftTargetSpeed, double rightTargetSpeed){
		System.out.println("ArcadeDrive PID method, Statement #" + RobotMap.counter);
		RobotMap.counter++;
		double adjustedLeft = leftTargetSpeed;
		double adjustedRight = rightTargetSpeed;
		double leftProportion = Math.abs(leftTargetSpeed/leftSide.getSideRate());
		double rightProportion = Math.abs(rightTargetSpeed/rightSide.getSideRate());
		
		if( (leftProportion>rightProportion) && (rightSide.getSideRate()==1) ){
			adjustedLeft = leftTargetSpeed*rightProportion;
			
		} else if ( (rightProportion>leftProportion) && (leftSide.getSideRate()==1) ) {
			adjustedRight = rightTargetSpeed*leftProportion;
			
		}
		
		setLeftRightMotorOutputs(adjustedLeft, adjustedRight);
	}
	
	@Override
	public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
		System.out.println("ArcadeDrive setLeftRight, Statement #" + RobotMap.counter);
		RobotMap.counter++;
	   
		leftSide.driveRate(leftOutput);
		rightSide.driveRate(rightOutput);

	    if (m_safetyHelper != null)
	      m_safetyHelper.feed();
	}
	
	@Override
	public void drive(double outputMagnitude, double curve) {
		System.out.println("ArcadeDrive override drive, Statement #" + RobotMap.counter);
		RobotMap.counter++;
	   if (!kArcadeRatioCurve_Reported) {
	      UsageReporting.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
	          tInstances.kRobotDrive_ArcadeRatioCurve);
	      kArcadeRatioCurve_Reported = true;
	    }

	  }
	
	
}
