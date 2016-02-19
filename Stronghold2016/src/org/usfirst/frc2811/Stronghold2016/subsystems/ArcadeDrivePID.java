package org.usfirst.frc2811.Stronghold2016.subsystems;

import org.usfirst.frc2811.Stronghold2016.Robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;

public class ArcadeDrivePID extends RobotDrive {
	
	public ArcadeDrivePID() {
		
		super(Robot.chassisLeft.frontLeftMotor, Robot.chassisLeft.backLeftMotor, 
				Robot.chassisRight.frontRightMotor, Robot.chassisRight.backRightMotor);
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
	    // local variables to hold the computed PWM values for the motors
	    if (!kArcadeStandard_Reported) {
	      UsageReporting.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
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
	
	public void pidDrive(double leftSetSpeed, double rightSetSpeed){
		double adjustedLeft = leftSetSpeed;
		double adjustedRight = rightSetSpeed;
		double leftProportion = Math.abs(leftSetSpeed/Robot.chassisLeft.getLeftRate());
		double rightProportion = Math.abs(rightSetSpeed/Robot.chassisRight.getRightRate());
		
		if( (leftProportion>rightProportion) && (rightSetSpeed==1) ){
			adjustedLeft = leftSetSpeed*rightProportion;
			
		} else if ( (rightProportion>leftProportion) && (leftSetSpeed==1) ) {
			adjustedRight = rightSetSpeed*leftProportion;
			
		}
		
		setLeftRightMotorOutputs(adjustedLeft, adjustedRight);
	}
	
	public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
	   
		Robot.chassisLeft.driveRate(leftOutput);
		Robot.chassisRight.driveRate(rightOutput);

	    if (m_safetyHelper != null)
	      m_safetyHelper.feed();
	}
	
	
}
