package org.usfirst.frc2811.Stronghold2016.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;

public class ArcadeDrivePID extends RobotDrive {
	
	private ChassisSide left;
	private ChassisSide right;
	
	public ArcadeDrivePID(ChassisSide leftSide, ChassisSide rightSide) {
		
		super(leftSide.getFront(),leftSide.getBack(),rightSide.getFront(), rightSide.getBack());
		left = leftSide;
		right = rightSide;		

	}
		
	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
	    // local variables to hold the computed PWM values for the motors
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
		double adjustedLeft = leftTargetSpeed;
		double adjustedRight = rightTargetSpeed;
		double leftProportion = Math.abs(leftTargetSpeed/left.getSideRate());
		double rightProportion = Math.abs(rightTargetSpeed/right.getSideRate());
		
		if( (leftProportion>rightProportion) && (right.getSideRate()==1) ){
			adjustedLeft = leftTargetSpeed*rightProportion;
			
		} else if ( (rightProportion>leftProportion) && (left.getSideRate()==1) ) {
			adjustedRight = rightTargetSpeed*leftProportion;
			
		}
		
		setLeftRightMotorOutputs(adjustedLeft, adjustedRight);
	}
	
	public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
	   
		left.driveRate(leftOutput);
		right.driveRate(rightOutput);

	    if (m_safetyHelper != null)
	      m_safetyHelper.feed();
	}
	
	
}
