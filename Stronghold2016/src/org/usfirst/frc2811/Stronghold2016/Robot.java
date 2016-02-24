// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2811.Stronghold2016;

import  org.usfirst.frc2811.Stronghold2016.commands.AutonomousCommand;

import  org.usfirst.frc2811.Stronghold2016.commands.JoystickDrive;
import 	org.usfirst.frc2811.Stronghold2016.commands.SetAngle;

import  org.usfirst.frc2811.Stronghold2016.subsystems.Chassis;
import  org.usfirst.frc2811.Stronghold2016.subsystems.Intake;
import  org.usfirst.frc2811.Stronghold2016.subsystems.Shooter;
import  org.usfirst.frc2811.Stronghold2016.subsystems.Vision;

import 	edu.wpi.first.wpilibj.BuiltInAccelerometer;
import 	edu.wpi.first.wpilibj.Compressor;
import  edu.wpi.first.wpilibj.IterativeRobot;
import 	edu.wpi.first.wpilibj.PowerDistributionPanel;
import  edu.wpi.first.wpilibj.command.Command;
import  edu.wpi.first.wpilibj.command.Scheduler;
import  edu.wpi.first.wpilibj.livewindow.LiveWindow;
import  edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;

    public static Command resetAlignment;
    Command joystickDrive;
    public static Command setAngle;
	public static PowerDistributionPanel powerPanel;
    public static Compressor compressor;
    public static BuiltInAccelerometer onboardAccelerometer;
   
    public static OI oi;
    public static Vision vision;
    public static Intake intake;
    public static Shooter shooter;
    public static Chassis chassis;
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    	compressor = new Compressor();
        powerPanel = new PowerDistributionPanel();
        onboardAccelerometer = new BuiltInAccelerometer();
        
        //TODO: Set vision offsetX and offsetY correctly. 0.5 is a dummy value for now.
   		vision = new Vision("GRIP/myContoursReport", -1 * (6.5/12.0), 0.0, 40, 68.5, 1280, 720);
        intake = new Intake();
        shooter = new Shooter();
        //TODO Find/Set Chassis rotation PID values
        chassis = new Chassis(0, 0, 0);

        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        autonomousCommand = new AutonomousCommand(0,0);
        
        joystickDrive = new JoystickDrive();
        setAngle = new SetAngle();

    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        joystickDrive.start();
        SmartDashboard.putData("PDP", powerPanel);
        SmartDashboard.putData("Accelerometer",onboardAccelerometer);
        SmartDashboard.putData("Compressor", compressor);
       // SmartDashboard.putData("Gyro", chassis.navxGyro);
        //SmartDashboard.putBoolean("On Target?", Math.abs(chassis.rotationPID.getSetpoint()-chassis.navxGyro.getAngle())<=5);
        SmartDashboard.putBoolean("On Target?", chassis.isOnTarget()) ;
        chassis.setRotation(0);
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
